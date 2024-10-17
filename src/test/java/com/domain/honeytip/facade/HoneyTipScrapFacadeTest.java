package com.domain.honeytip.facade;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.ttoklip.domain.honeytip.facade.HoneyTipScrapFacade;
import com.api.ttoklip.global.success.Message;
import honeytip.fixture.HoneyTipFixture;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class HoneyTipScrapFacadeTest extends HoneyTipFacadeTestHelper {

    @InjectMocks
    private HoneyTipScrapFacade honeyTipScrapFacade;

    /* -------------------------------------------- SCRAP REGISTER TEST -------------------------------------------- */
    @Test
    void 허니팁_스크랩_등록_성공() {
        // Given
        var member = MemberFixture.일반_회원_생성();
        var honeyTip = HoneyTipFixture.본인_허니팁_생성(member);
        Long postId = honeyTip.getId();
        Long memberId = member.getId();

        // 스크랩이 존재하지 않는 경우 Mock 설정
        when(honeyTipScrapService.isHoneyTipScrapExists(postId, memberId)).thenReturn(false);
        when(honeyTipPostService.getHoneytip(postId)).thenReturn(honeyTip);
        when(memberService.findById(memberId)).thenReturn(member);

        // When
        Message result = honeyTipScrapFacade.register(postId, memberId);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getMessage()).contains("스크랩");
            softly.assertThat(result.getMessage()).contains("생성");
        });

        verify(honeyTipScrapService, times(1)).register(honeyTip, member);
        verify(honeyTipPostService, times(1)).getHoneytip(postId);
        verify(memberService, times(1)).findById(memberId);
    }

    @Test
    void 이미_존재하는_스크랩은_등록하지_않음() {
        // Given
        var member = MemberFixture.일반_회원_생성();
        var honeyTip = HoneyTipFixture.본인_허니팁_생성(member);
        Long postId = honeyTip.getId();
        Long memberId = member.getId();

        // 이미 스크랩이 존재하는 경우 Mock 설정
        when(honeyTipScrapService.isHoneyTipScrapExists(postId, memberId)).thenReturn(true);

        // When
        Message result = honeyTipScrapFacade.register(postId, memberId);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getMessage()).contains("스크랩");
        });

        verify(honeyTipScrapService, times(0)).register(honeyTip, member); // 스크랩 등록 메서드는 호출되지 않음
    }

    /* -------------------------------------------- SCRAP CANCEL TEST -------------------------------------------- */
    @Test
    void 허니팁_스크랩_취소_성공() {
        // Given
        var member = MemberFixture.일반_회원_생성();
        var honeyTip = HoneyTipFixture.본인_허니팁_생성(member);
        Long postId = honeyTip.getId();
        Long memberId = member.getId();

        when(honeyTipPostService.getHoneytip(postId)).thenReturn(honeyTip);

        // When
        Message result = honeyTipScrapFacade.cancel(postId, memberId);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getMessage()).contains("스크랩");
            softly.assertThat(result.getMessage()).contains("삭제");
        });

        verify(honeyTipScrapService, times(1)).cancelScrap(honeyTip, memberId);
        verify(honeyTipPostService, times(1)).getHoneytip(postId);
    }
}