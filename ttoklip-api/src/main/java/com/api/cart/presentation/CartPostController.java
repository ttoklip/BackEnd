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
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/town/carts")

public class CartPostController implements CartPostControllerDocs {

    private final CartPostFacade cartPostFacade;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TtoklipResponse<Message> register(@Validated @ModelAttribute CartWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.register(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @GetMapping("/{postId}")
    public TtoklipResponse<CartResponse> getSinglePost(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        CartResponse response = cartPostFacade.getSinglePost(postId, currentMemberId);
        return new TtoklipResponse<>(response);
    }

    @Override
    @PatchMapping("/{postId}")
    public TtoklipResponse<Message> edit(@PathVariable Long postId,
                                         @Validated @ModelAttribute CartWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.edit(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/report/{postId}")
    public TtoklipResponse<Message> report(@PathVariable Long postId,
                                           @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.report(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PatchMapping("/{postId}/status")
    public TtoklipResponse<Message> updateStatus(@PathVariable Long postId, @RequestBody UpdateStatusRequest request) {
        Message message = cartPostFacade.updateStatus(postId, TradeStatus.valueOf(request.getStatus()));
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/participants/{cartId}")
    public TtoklipResponse<Message> addParticipant(@PathVariable Long cartId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.addParticipant(cartId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/participants/{cartId}")
    public TtoklipResponse<Message> removeParticipant(@PathVariable Long cartId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.removeParticipant(cartId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @GetMapping("/participants/count/{cartId}")
    public TtoklipResponse<Long> countParticipants(@PathVariable Long cartId) {
        Long count = cartPostFacade.countParticipants(cartId);
        return new TtoklipResponse<>(count);
    }

    @Override
    @GetMapping("/participants/members/{cartId}")
    public TtoklipResponse<CartGroupMemberResponse> checkParticipants(@PathVariable Long cartId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        CartGroupMemberResponse response = cartPostFacade.checkParticipants(cartId, currentMemberId);
        return new TtoklipResponse<>(response);
    }
}
