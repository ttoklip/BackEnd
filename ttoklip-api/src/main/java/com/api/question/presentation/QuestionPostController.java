package com.api.question.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.question.application.QuestionPostFacade;
import com.api.question.presentation.dto.request.QuestionWebCreate;
import com.api.question.presentation.dto.response.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuestionPostController implements QuestionPostControllerDocs {

    private final QuestionPostFacade questionPostFacade;

    @Override
    public TtoklipResponse<Message> register(QuestionWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionPostFacade.register(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<QuestionResponse> getSinglePost(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        QuestionResponse response = questionPostFacade.getSinglePost(postId, currentMemberId);
        return new TtoklipResponse<>(response);
    }

    @Override
    public TtoklipResponse<Message> report(Long postId, ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionPostFacade.report(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
