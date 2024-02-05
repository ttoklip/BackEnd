package com.api.ttoklip.domain.town.cart.post.service;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.image.service.CartImageService;
import com.api.ttoklip.domain.town.cart.post.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.post.dto.response.CartSingleResponse;
import com.api.ttoklip.domain.town.cart.post.editor.CartPostEditor;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.cart.post.repository.CartRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartPostService {

    private final CartRepository cartRepository;
    private final CartImageService cartImageService;
    private final S3FileUploader s3FileUploader;
    private final ReportService reportService;


    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Cart findCartById(final Long postId) {
        return cartRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
    }

    public Cart getCart(final Long postId) {
        return cartRepository.findByIdActivated(postId);
    }

    private List<String> uploadImages(final List<MultipartFile> uploadImages) {
        return s3FileUploader.uploadMultipartFiles(uploadImages);
    }

    /* -------------------------------------------- COMMON 끝 -------------------------------------------- */


    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Long register(final CartCreateRequest request) {

        Cart cart = Cart.of(request);
        cartRepository.save(cart);

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(cart, uploadImages);
        }

        return cart.getId();
    }

    private void registerImages(final Cart cart, final List<MultipartFile> multipartFiles) {
        List<String> uploadUrls = getImageUrls(multipartFiles);
        uploadUrls.forEach(uploadUrl -> cartImageService.register(cart, uploadUrl));
    }

    private List<String> getImageUrls(final List<MultipartFile> multipartFiles) {
        return s3FileUploader.uploadMultipartFiles(multipartFiles);
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- READ -------------------------------------------- */
    public CartSingleResponse getSinglePost(final Long postId) {

        Cart cartWithImg = cartRepository.findByIdFetchJoin(postId);
        List<CartComment> activeComments = cartRepository.findActiveCommentsByCartId(postId);
        CartSingleResponse cartSingleResponse = CartSingleResponse.of(cartWithImg, activeComments);
        return cartSingleResponse;
    }

    /* -------------------------------------------- READ 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */
    @Transactional
    public Message edit(final Long postId, final CartCreateRequest request) {

        Cart cart = getCart(postId);

        CartPostEditor postEditor = getPostEditor(request, cart);
        cart.edit(postEditor);

        List<MultipartFile> images = request.getImages();
        if (images != null && !images.isEmpty()) {
            editImages(images, cart);
        }

        return Message.editPostSuccess(Cart.class, cart.getId());
    }

    private CartPostEditor getPostEditor(final CartCreateRequest request, final Cart cart) {
        CartPostEditor.CartPostEditorBuilder editorBuilder = cart.toEditor();
        CartPostEditor cartPostEditor = editorBuilder
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return cartPostEditor;
    }

    private void editImages(final List<MultipartFile> multipartFiles, final Cart cart) {
        Long cartId = cart.getId();
        // 기존 이미지 전부 제거
        cartImageService.deleteAllByPostId(cartId);

        // 새로운 이미지 업로드
        List<String> uploadUrls = uploadImages(multipartFiles);
        uploadUrls.forEach(uploadUrl -> cartImageService.register(cart, uploadUrl));
    }


    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */


    /* -------------------------------------------- Soft Delete -------------------------------------------- */
    public void delete(final Long postId) {
        Cart cart = findCart(postId);
        cart.deactivate(); // 비활성화
    }

    //
    private Cart findCart(final Long postId) {
        return cartRepository.findByIdUnDeleted(postId);
    }

    /* -------------------------------------------- Soft Delete 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public void report(final Long postId, final ReportCreateRequest request) {
        Cart cart = findCartById(postId);
        reportService.reportCart(request, cart);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */
}
