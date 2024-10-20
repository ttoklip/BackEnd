package com.domain.honeytip.service;

import com.api.ttoklip.domain.common.comment.repository.CommentRepository;
import com.api.ttoklip.domain.honeytip.service.HoneyTipCommentService;
import com.domain.comment.repository.FakeCommentRepository;
import com.domain.honeytip.repository.FakeHoneyTipPostRepository;
import org.junit.jupiter.api.BeforeEach;

public class HoneyTipCommentServiceTest {

    private HoneyTipCommentService honeyTipCommentService;
    private CommentRepository commentRepository;
    private FakeHoneyTipPostRepository postRepository;

    @BeforeEach
    void setUp() {
        commentRepository = new FakeCommentRepository();
        honeyTipCommentService = new HoneyTipCommentService(commentRepository);
        postRepository = new FakeHoneyTipPostRepository();
    }

    /*

    엔티티 상속에 의한 Builder 직접적인 필드 설정 불가, 어떻게 할지 추후 논의

    @Test
    void testSaveAndFindCommentsByHoneyTipId_usingFixtures() {
        // Given
        var honeyTipCreateRequest = HoneyTipFixture.타인_허니팁_생성(); // HoneyTip 픽스처 생성
        var comment1 = CommentFixture.꿀팁_최상위_댓글_생성(honeyTipCreateRequest); // 최상위 댓글 픽스처 생성
        var comment2 = CommentFixture.꿀팁_대댓글_생성(comment1, honeyTipCreateRequest); // 대댓글 픽스처 생성
        System.out.println("comment2 = " + comment2);

        System.out.println("flag - honeyTipCreateRequest = " + honeyTipCreateRequest);

        HoneyTip savedHoneyTip = HoneyTip.builder()
                .id(honeyTipCreateRequest.getId())  // 자동으로 증가된 ID 설정
                .title(honeyTipCreateRequest.getTitle())
                .content(honeyTipCreateRequest.getContent())
                .category(honeyTipCreateRequest.getCategory())
                .honeyTipUrls(honeyTipCreateRequest.getHoneyTipUrls())
                .honeyTipImages(honeyTipCreateRequest.getHoneyTipImages())
                .honeyTipComments(List.of(comment1, comment2))
                .honeyTipScraps(honeyTipCreateRequest.getHoneyTipScraps())
                .honeyTipLikes(honeyTipCreateRequest.getHoneyTipLikes())
                .build();

        System.out.println("flag - savedHoneyTip = " + savedHoneyTip);

        HoneyTip honeyTip = postRepository.save(savedHoneyTip);

        System.out.println("flag - honeyTip = " + honeyTip);

        // When
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        List<HoneyTipComment> comments = honeyTipCommentService.findCommentsByHoneyTipId(
                honeyTip.getId());

        System.out.println("flag - comments = " + comments);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(comments).isNotNull();
            softly.assertThat(comments.size()).isEqualTo(2);
            softly.assertThat(comments).extracting("content").containsExactly("최상위 댓글 생성 내용", "부모 댓글에 대한 대댓글 생성 내용");
        });
    }

     */
}
