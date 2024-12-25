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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question/post")
public class QuestionPostController implements QuestionPostControllerDocs {

    private final QuestionPostFacade questionPostFacade;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TtoklipResponse<Message> register(
            final @Validated @ModelAttribute QuestionWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                questionPostFacade.register(request, currentMemberId)
        );
    }

    @Override
    @GetMapping("/{postId}")
    public TtoklipResponse<QuestionResponse> getSinglePost(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                questionPostFacade.getSinglePost(postId, currentMemberId)
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
                questionPostFacade.report(postId, request, currentMemberId)
        );
    }
}
