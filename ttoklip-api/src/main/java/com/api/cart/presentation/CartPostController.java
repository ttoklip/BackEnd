package com.api.cart.presentation;

import com.api.cart.application.CartPostFacade;
import com.api.cart.presentation.dto.request.CartWebCreate;
import com.api.cart.presentation.dto.request.UpdateStatusRequest;
import com.api.cart.presentation.dto.response.CartGroupMemberResponse;
import com.api.cart.presentation.dto.response.CartResponse;
import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.domain.cart.domain.vo.TradeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CartPostController implements CartPostControllerDocs {

    private final CartPostFacade cartPostFacade;

    @Override
    public TtoklipResponse<Message> register(CartWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.register(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<CartResponse> getSinglePost(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        CartResponse response = cartPostFacade.getSinglePost(postId, currentMemberId);
        return new TtoklipResponse<>(response);
    }

    @Override
    public TtoklipResponse<Message> edit(Long postId, CartWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.edit(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> report(Long postId, ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.report(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> updateStatus(Long postId, UpdateStatusRequest request) {
        Message message = cartPostFacade.updateStatus(postId, TradeStatus.valueOf(request.getStatus()));
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> addParticipant(Long cartId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.addParticipant(cartId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> removeParticipant(Long cartId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.removeParticipant(cartId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Long> countParticipants(Long cartId) {
        Long count = cartPostFacade.countParticipants(cartId);
        return new TtoklipResponse<>(count);
    }

    @Override
    public TtoklipResponse<CartGroupMemberResponse> checkParticipants(Long cartId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        CartGroupMemberResponse response = cartPostFacade.checkParticipants(cartId, currentMemberId);
        return new TtoklipResponse<>(response);
    }
}
