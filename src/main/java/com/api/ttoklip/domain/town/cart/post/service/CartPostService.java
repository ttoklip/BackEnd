package com.api.ttoklip.domain.town.cart.post.service;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.image.service.CartImageService;
import com.api.ttoklip.domain.town.cart.itemUrl.service.ItemUrlService;
import com.api.ttoklip.domain.town.cart.post.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.post.dto.response.CartSingleResponse;
import com.api.ttoklip.domain.town.cart.post.editor.CartPostEditor;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.cart.post.entity.CartMember;
import com.api.ttoklip.domain.town.cart.post.entity.TradeStatus;
import com.api.ttoklip.domain.town.cart.post.repository.CartMemberRepository;
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

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartPostService {

    private final CartRepository cartRepository;
    private final CartImageService cartImageService;
    private final S3FileUploader s3FileUploader;
    private final ReportService reportService;
    private final ItemUrlService itemUrlService;
    private final CartMemberService cartMemberService;
    private final CartMemberRepository cartMemberRepository;

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
    public Message register(final CartCreateRequest request) {

        Member currentMember = getCurrentMember();

        Cart cart = Cart.of(request, currentMember);
        cartRepository.save(cart);

        addParticipant(cart.getId());

        List<MultipartFile> uploadImages = request.getImages();
        if (uploadImages != null && !uploadImages.isEmpty()) {
            registerImages(cart, uploadImages);
        }

        List<String> itemUrls = request.getItemUrls();
        if (itemUrls != null && !itemUrls.isEmpty()) {
            itemUrls.forEach(itemUrl -> itemUrlService.register(cart, itemUrl));
        }

        return Message.registerPostSuccess(Cart.class, cart.getId());
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

        List<String> itemUrls = request.getItemUrls();
        if (itemUrls != null && !itemUrls.isEmpty()) {
            editItemUrls(cart, itemUrls);
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

    private void editItemUrls(Cart cart, List<String> itemUrls) {
        itemUrlService.deleteAllByPostId(cart.getId());
        itemUrls.forEach(itemUrl -> itemUrlService.register(cart, itemUrl));
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */


    /* -------------------------------------------- Soft Delete -------------------------------------------- */
    public void delete(final Long postId) {
        Cart cart = findCart(postId);
        cart.deactivate(); // 비활성화
    }

    public Cart findCart(final Long postId) {
        return cartRepository.findByIdActivated(postId);
    }

    /* -------------------------------------------- Soft Delete 끝 -------------------------------------------- */


    /* -------------------------------------------- REPORT -------------------------------------------- */
    @Transactional
    public Message report(final Long postId, final ReportCreateRequest request) {
        Cart cart = findCartById(postId);
        reportService.reportCart(request, cart);

        return Message.reportPostSuccess(Cart.class, postId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */


    /* -------------------------------------------- UPDATE STATUS -------------------------------------------- */
    @Transactional
    public Message updateStatus(Long postId, TradeStatus newStatus) {
        Cart cart = findCartById(postId);
        cart.changeStatus(newStatus);
        return Message.editStatusSuccess(Cart.class, cart.getId());
    }

    /* -------------------------------------------- UPDATE STATUS 끝-------------------------------------------- */

    /* -------------------------------------------- PARTICIPANT -------------------------------------------- */
    // 공구 참여
    @Transactional
    public Message addParticipant(final Long cartId) {
        Cart cart = cartRepository.findByIdActivated(cartId);
        Long MaxValue = cart.getPartyMax();
        List<CartMember> cartMembers = cartMemberRepository.findByCartId(cartId);

        if (MaxValue < cartMembers.size() + 1) {
            throw new ApiException(ErrorType.PARTICIPANT_EXCEEDED);
        }
        // 이미 참가하기 눌렀을 때
        Long currentMemberId = getCurrentMember().getId();
        if (cartMemberRepository.existsCartMemberByMemberIdAndCartId(currentMemberId, cartId)) {
            throw new ApiException(ErrorType.ALREADY_PARTICIPATED);
        }
        cartMemberService.register(cart);
        if(cart.getCartMembers().size()==cart.getPartyMax()){
            cart.changeComplete();
        }

        return Message.addParticipantSuccess(Cart.class, cart.getId());
    }

    // 공구 취소
    @Transactional
    public Message removeParticipant(final Long cartId) {
        Long currentMemberId = getCurrentMember().getId();
        // 참가자가 공구에 참여하지 않았을 때
        CartMember cartMember = cartMemberRepository.findByMemberIdAndCartId(currentMemberId, cartId)
                .orElseThrow(() -> new ApiException(ErrorType.NOT_PARTICIPATED));

        cartMemberRepository.delete(cartMember);
        Cart cart = cartRepository.findByIdActivated(cartId);
        if(cart.getCartMembers().size()<cart.getPartyMax()){
            cart.changeProgress();
        }
        return Message.removeParticipantSuccess(Cart.class, cart.getId());
    }

    public int countParticipants(final Long cartId) {
        return cartRepository.countParticipants(cartId);
    }
    /* -------------------------------------------- PARTICIPANT 끝 -------------------------------------------- */


}
