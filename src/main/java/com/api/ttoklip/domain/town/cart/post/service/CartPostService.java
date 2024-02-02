package com.api.ttoklip.domain.town.cart.post.service;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.town.cart.post.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.post.dto.response.CartSingleResponse;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.cart.post.repository.CartRepository;
//import com.api.ttoklip.domain.town.community.image.service.ImageService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.s3.S3FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartPostService {

    private final CartRepository cartRepository;
    private final ImageService imageService;
    private final S3FileUploader s3FileUploader;
    private final ReportService reportService;


    /* -------------------------------------------- COMMON -------------------------------------------- */
    public Cart findCartById(final Long postId) {
        return cartRepository.findById(postId)
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
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
        uploadUrls.forEach(uploadUrl -> imageService.register(cart, uploadUrl));
    }

    private List<String> getImageUrls(final List<MultipartFile> multipartFiles) {
        return s3FileUploader.uploadMultipartFiles(multipartFiles);
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    public CartSingleResponse getSinglePost(final Long postId) {

        Cart cartWithImg = cartRepository.findByIdFetchJoin(postId);
        List<CartComment> activeComments = cartRepository.findActiveCommentsByCartId(postId);
        CartSingleResponse cartSingleResponse = CartSingleResponse.of(cartWithImg, activeComments);
        return cartSingleResponse;
    }


    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public void report(final Long postId, final ReportCreateRequest request) {
        Cart cart = findCartById(postId);
        reportService.reportCart(request, cart);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */


    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */


    /* -------------------------------------------- Soft Delete -------------------------------------------- */
    public void delete(final Long postId) {
        Cart cart = findCart(postId);
        cart.deactivate(); // 비활성화
    }

    private Cart findCart(final Long postId) {
        return cartRepository.findByIdUndeleted(postId);
    }

    /* -------------------------------------------- Soft Delete 끝 -------------------------------------------- */
}
