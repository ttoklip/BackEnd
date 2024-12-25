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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/town/carts")

public class CartPostController implements CartPostControllerDocs {

    private final CartPostFacade cartPostFacade;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TtoklipResponse<Message> register(
            final @Validated @ModelAttribute CartWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                cartPostFacade.register(request, currentMemberId)
        );
    }

    @Override
    @GetMapping("/{postId}")
    public TtoklipResponse<CartResponse> getSinglePost(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                cartPostFacade.getSinglePost(postId, currentMemberId)
        );
    }

    @Override
    @PatchMapping("/{postId}")
    public TtoklipResponse<Message> edit(
            final @PathVariable Long postId,
            final @Validated @ModelAttribute CartWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                cartPostFacade.edit(postId, request, currentMemberId)
        );
    }

    @Override
    @PostMapping("/report/{postId}")
    public TtoklipResponse<Message> report(
            final @PathVariable Long postId,
            final @RequestBody ReportWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                cartPostFacade.report(postId, request, currentMemberId)
        );
    }

    @Override
    @PatchMapping("/{postId}/status")
    public TtoklipResponse<Message> updateStatus(
            final @PathVariable Long postId,
            final @RequestBody UpdateStatusRequest request
    ) {
        return TtoklipResponse.ok(
                cartPostFacade.updateStatus(postId, TradeStatus.valueOf(request.getStatus()))
        );
    }

    @Override
    @PostMapping("/participants/{cartId}")
    public TtoklipResponse<Message> addParticipant(
            final @PathVariable Long cartId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                cartPostFacade.addParticipant(cartId, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/participants/{cartId}")
    public TtoklipResponse<Message> removeParticipant(
            final @PathVariable Long cartId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                cartPostFacade.removeParticipant(cartId, currentMemberId)
        );
    }

    @Override
    @GetMapping("/participants/count/{cartId}")
    public TtoklipResponse<Long> countParticipants(
            final @PathVariable Long cartId
    ) {
        return TtoklipResponse.ok(
                cartPostFacade.countParticipants(cartId)
        );
    }

    @Override
    @GetMapping("/participants/members/{cartId}")
    public TtoklipResponse<CartGroupMemberResponse> checkParticipants(
            final @PathVariable Long cartId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                cartPostFacade.checkParticipants(cartId, currentMemberId)
        );
    }
}
