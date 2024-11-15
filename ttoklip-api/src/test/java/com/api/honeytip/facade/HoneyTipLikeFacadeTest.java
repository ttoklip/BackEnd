//package com.api.honeytip.facade;
//
//import static org.assertj.core.api.SoftAssertions.assertSoftly;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.api.honeytip.application.HoneyTipLikeFacade;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//
//@SuppressWarnings("NonAsciiCharacters")
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//public class HoneyTipLikeFacadeTest extends HoneyTipFacadeTestHelper {
//
//    @InjectMocks
//    private HoneyTipLikeFacade honeyTipLikeFacade;
//
//    /* -------------------------------------------- LIKE REGISTER TEST -------------------------------------------- */
//    @Test
//    void 허니팁_좋아요_등록_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        var honeyTip = HoneyTipFixture.본인_허니팁_생성(member);
//        Long postId = honeyTip.getId();
//        Long memberId = member.getId();
//
//        // 좋아요가 존재하지 않는 경우 Mock 설정
//        when(honeyTipLikeService.isHoneyTipLikeExists(postId, memberId)).thenReturn(false);
//        when(honeyTipPostService.getHoneytip(postId)).thenReturn(honeyTip);
//        when(memberService.findById(memberId)).thenReturn(member);
//
//        // When
//        Message result = honeyTipLikeFacade.register(postId, memberId);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("좋아요");
//            softly.assertThat(result.getMessage()).contains("생성");
//        });
//
//        verify(honeyTipLikeService, times(1)).register(honeyTip, member);
//        verify(honeyTipPostService, times(1)).getHoneytip(postId);
//        verify(memberService, times(1)).findById(memberId);
//    }
//
//    @Test
//    void 이미_존재하는_좋아요는_등록하지_않는_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        var honeyTip = HoneyTipFixture.본인_허니팁_생성(member);
//        Long postId = honeyTip.getId();
//        Long memberId = member.getId();
//
//        // 이미 좋아요가 존재하는 경우 Mock 설정
//        when(honeyTipLikeService.isHoneyTipLikeExists(postId, memberId)).thenReturn(true);
//
//        // When
//        Message result = honeyTipLikeFacade.register(postId, memberId);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("좋아요");
//        });
//
//        verify(honeyTipLikeService, times(0)).register(honeyTip, member); // 좋아요 등록 메서드는 호출되지 않음
//    }
//
//    /* -------------------------------------------- LIKE CANCEL TEST -------------------------------------------- */
//    @Test
//    void 허니팁_좋아요_취소_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        var honeyTip = HoneyTipFixture.본인_허니팁_생성(member);
//        Long postId = honeyTip.getId();
//        Long memberId = member.getId();
//
//        when(honeyTipPostService.getHoneytip(postId)).thenReturn(honeyTip);
//
//        // When
//        Message result = honeyTipLikeFacade.cancel(postId, memberId);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("좋아요");
//            softly.assertThat(result.getMessage()).contains("삭제");
//        });
//
//        verify(honeyTipLikeService, times(1)).cancel(honeyTip, memberId);
//        verify(honeyTipPostService, times(1)).getHoneytip(postId);
//    }
//}
