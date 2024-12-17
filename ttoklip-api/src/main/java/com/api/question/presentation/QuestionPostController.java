package com.api.question.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.question.application.QuestionPostFacade;
import com.api.question.presentation.dto.request.QuestionWebCreate;
import com.api.question.presentation.dto.response.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question/post")
public class QuestionPostController implements QuestionPostControllerDocs {

    private final QuestionPostFacade questionPostFacade;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TtoklipResponse<Message> register(@Validated @ModelAttribute QuestionWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionPostFacade.register(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @GetMapping("/{postId}")
    public TtoklipResponse<QuestionResponse> getSinglePost(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        QuestionResponse response = questionPostFacade.getSinglePost(postId, currentMemberId);
        return new TtoklipResponse<>(response);
    }

    @Override
    @PostMapping("/report/{postId}")
    public TtoklipResponse<Message> report(@PathVariable Long postId,
                                           @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionPostFacade.report(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
