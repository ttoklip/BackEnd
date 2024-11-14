package com.api.cart.application;

import com.api.cart.presentation.dto.request.CartWebCreate;
import com.api.cart.presentation.dto.response.CartGroupMemberResponse;
import com.api.cart.presentation.dto.response.CartMemberResponse;
import com.api.cart.presentation.dto.response.CartResponse;
import com.api.common.ReportWebCreate;
import com.api.common.upload.MultipartFileAdapter;
import com.infrastructure.aws.upload.FileInput;
import com.infrastructure.aws.upload.Uploader;
import com.api.global.success.Message;
import com.common.NotiCategory;
import com.common.annotation.DistributedLock;
import com.common.annotation.FilterBadWord;
import com.common.annotation.SendNotification;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.cart.application.CartCommentService;
import com.domain.cart.application.CartImageService;
import com.domain.cart.application.CartMemberService;
import com.domain.cart.application.CartPostService;
import com.domain.cart.application.ItemUrlService;
import com.domain.cart.domain.Cart;
import com.domain.cart.domain.CartComment;
import com.domain.cart.domain.CartCreate;
import com.domain.cart.domain.CartMember;
import com.domain.cart.domain.CartPostEditor;
import com.domain.cart.domain.vo.TradeStatus;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.report.application.ReportService;
import com.domain.report.domain.ReportCreate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartPostFacade {

    private final CartPostService cartPostService;
    private final Uploader uploader;
    private final ItemUrlService itemUrlService;
    private final CartImageService cartImageService;
    private final ReportService reportService;
    private final CartMemberService cartMemberService;
    private final CartCommentService cartCommentService;
    private final MemberService memberService;

    /* -------------------------------------------- CREATE -------------------------------------------- */

    @Transactional
    @FilterBadWord
    @DistributedLock(keyPrefix = "cart-")
    public Message register(final CartWebCreate request, final Long memberId) {

        Member member = memberService.getById(memberId);
        CartCreate cartCreate = CartCreate.of(request.getTitle(), request.getContent(), request.getTotalPrice(),
                request.getLocation(), request.getChatUrl(), request.getPartyMax(), member);
        Cart cart = Cart.from(cartCreate);
        cartPostService.save(cart);

        addParticipant(cart.getId(), memberId);

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
        List<FileInput> files = multipartFiles.stream()
                .map(MultipartFileAdapter::new)
                .collect(Collectors.toList());
        List<String> uploadUrls = uploader.uploadFiles(files);

        uploadUrls.forEach(uploadUrl -> cartImageService.register(cart, uploadUrl));
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */


    /* -------------------------------------------- READ -------------------------------------------- */

    public CartResponse getSinglePost(final Long postId, final Long memberId) {
        Cart cartWithImg = cartPostService.findByIdFetchJoin(postId);
        List<CartComment> activeComments = cartCommentService.findActiveCommentsByCartId(postId);
        boolean isAlreadyJoin = cartMemberService.existsByMemberIdAndCartId(memberId, cartWithImg.getId());
        return CartResponse.of(cartWithImg, activeComments, isAlreadyJoin);
    }

    /* -------------------------------------------- READ 끝 -------------------------------------------- */


    /* -------------------------------------------- EDIT -------------------------------------------- */

    @Transactional
    @FilterBadWord
    public Message edit(final Long postId, final CartWebCreate request, final Long memberId) {
        Cart cart = cartPostService.findByIdActivated(postId);

        if (!cart.getMember().getId().equals(memberId)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_EDIT_POST);
        }

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

    private CartPostEditor getPostEditor(final CartWebCreate request, final Cart cart) {
        return cart.toEditor()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    private void editImages(final List<MultipartFile> multipartFiles, final Cart cart) {
        Long cartId = cart.getId();
        cartImageService.deleteAllByPostId(cartId);

        List<FileInput> files = multipartFiles.stream()
                .map(MultipartFileAdapter::new)
                .collect(Collectors.toList());
        List<String> uploadUrls = uploader.uploadFiles(files);

        uploadUrls.forEach(uploadUrl -> cartImageService.register(cart, uploadUrl));
    }

    private void editItemUrls(final Cart cart, final List<String> itemUrls) {
        itemUrlService.deleteAllByPostId(cart.getId());
        itemUrls.forEach(itemUrl -> itemUrlService.register(cart, itemUrl));
    }

    /* -------------------------------------------- EDIT 끝 -------------------------------------------- */


    /* -------------------------------------------- Soft Delete -------------------------------------------- */

    @Transactional
    public void delete(final Long postId) {
        Cart cart = findCartByIdActivated(postId);
        cart.deactivate(); // 비활성화
    }

    public Cart findCartByIdActivated(final Long postId) {
        return cartPostService.findByIdActivated(postId);
    }

    /* -------------------------------------------- Soft Delete 끝 -------------------------------------------- */

    /* -------------------------------------------- REPORT -------------------------------------------- */

    @Transactional
    public Message report(final Long postId, final ReportWebCreate request, final Long reporterId) {
        Cart cart = cartPostService.findByIdActivated(postId);
        ReportCreate create = ReportCreate.of(request.content(), request.getReportType());
        Member reporter = memberService.getById(reporterId);
        reportService.reportCart(create, cart, reporter);
        return Message.reportPostSuccess(Cart.class, postId);
    }

    /* -------------------------------------------- REPORT 끝 -------------------------------------------- */

    /* -------------------------------------------- UPDATE STATUS -------------------------------------------- */

    @Transactional
    public Message updateStatus(Long postId, TradeStatus newStatus) {
        Cart cart = cartPostService.findByIdActivated(postId);
        cart.changeStatus(newStatus);
        return Message.editStatusSuccess(Cart.class, cart.getId());
    }

    /* -------------------------------------------- UPDATE STATUS 끝-------------------------------------------- */


    /* -------------------------------------------- PARTICIPANT -------------------------------------------- */
    // 공구 참여
    @Transactional
    @SendNotification(notiCategory = NotiCategory.OUR_TOWN_TOGETHER)
    public Message addParticipant(final Long cartId, final Long memberId) {
        Cart cart = cartPostService.findByIdActivated(cartId);
        Long MaxValue = cart.getPartyMax();
        List<CartMember> cartMembers = cartMemberService.findByCartId(cartId);

        if (MaxValue < cartMembers.size() + 1) {
            throw new ApiException(ErrorType.PARTICIPANT_EXCEEDED);
        }
        if (cartMemberService.existsByMemberIdAndCartId(memberId, cartId)) {
            throw new ApiException(ErrorType.ALREADY_PARTICIPATED);
        }

        Member member = memberService.getById(memberId);
        cartMemberService.register(cart, member);
        if (cart.getCartMembers().size() == cart.getPartyMax()) {
            cart.changeComplete();
        }

        return Message.addParticipantSuccess(Cart.class, cart.getId());
    }

    // 공구 취소
    @Transactional
    public Message removeParticipant(final Long cartId, final Long memberId) {
        CartMember cartMember = cartMemberService.findByMemberIdAndCartId(memberId, cartId)
                .orElseThrow(() -> new ApiException(ErrorType.NOT_PARTICIPATED));

        cartMemberService.delete(cartMember.getId());

        Cart cart = cartPostService.findByIdActivated(cartId);

        if (cart.getCartMembers().size() < cart.getPartyMax()) {
            cart.changeStatusToProgress();
        }

        return Message.removeParticipantSuccess(Cart.class, cart.getId());
    }

    public Long countParticipants(final Long cartId) {
        Cart cart = cartPostService.findByIdActivated(cartId);
        return cartPostService.countParticipants(cart.getId());
    }

    public CartGroupMemberResponse checkParticipants(final Long cartId, final Long memberId) {
        isAllReadyParticipants(cartId, memberId);
        Cart cart = cartPostService.findByIdActivated(cartId);

        List<CartMemberResponse> cartMemberResponses = cart.getCartMembers().stream()
                .map(cartMember -> CartMemberResponse.of(
                                cartMember.getMember(), cartMember.getMember().getInterests()
                        )
                ).toList();

        return CartGroupMemberResponse.of(cartMemberResponses);
    }

    private void isAllReadyParticipants(final Long cartId, final Long memberId) {
        cartMemberService.findByMemberIdAndCartId(memberId, cartId)
                .orElseThrow(() -> new ApiException(ErrorType.NOT_PARTICIPATED));
    }
    /* -------------------------------------------- PARTICIPANT 끝 -------------------------------------------- */

}
