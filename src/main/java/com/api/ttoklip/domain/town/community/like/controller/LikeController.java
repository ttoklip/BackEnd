//package com.api.ttoklip.domain.town.community.like.controller;
//
//import com.api.ttoklip.domain.town.community.constant.CommunityResponseConstant;
//import com.api.ttoklip.domain.town.community.like.dto.request.LikeRequest;
//import com.api.ttoklip.domain.town.community.like.service.CommunityLikeService;
//import com.api.ttoklip.global.success.Message;
//import com.api.ttoklip.global.success.SuccessResponse;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.ExampleObject;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1/town/comms")
//public class LikeController {
//
//    private final CommunityLikeService communityLikeService;
//
//    /* LIKE */
//    @Operation(summary = "소통해요 좋아요 생성",
//            description = "소통해요 게시글에 좋아요를 생성합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "소통해요 게시글에 좋아요 생성 성공",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = SuccessResponse.class),
//                            examples = @ExampleObject(
//                                    name = "SuccessResponse",
//                                    value = CommunityResponseConstant.createLike,
//                                    description = "소통해요 게시글에 좋아요가 생성되었습니다."
//                            )))})
//    @PostMapping("/{postId}/like")
//    public SuccessResponse<Message> pushLikeButton(@PathVariable Long postId, @RequestBody LikeRequest request) {
//        Message message = communityLikeService.pushLikeButton(request.getMemberId(), postId);
//        return new SuccessResponse<>(message);
//    }
//
//}
