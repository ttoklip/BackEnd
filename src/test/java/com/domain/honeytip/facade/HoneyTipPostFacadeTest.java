package com.domain.honeytip.facade;

import static honeytip.fixture.HoneyTipFixture.URL_X_사진_X_단순_꿀팁_게시글_요청_픽스처;
import static honeytip.fixture.HoneyTipFixture.URL_있음_게시글_요청_픽스처;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.api.ttoklip.domain.honeytip.controller.dto.request.HoneyTipCreateRequest;
import com.api.ttoklip.domain.honeytip.facade.HoneyTipPostFacade;
import com.api.ttoklip.domain.honeytip.service.HoneyTipImageService;
import com.api.ttoklip.domain.honeytip.service.HoneyTipPostService;
import com.api.ttoklip.domain.honeytip.service.HoneyTipUrlService;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.s3.S3FileUploader;
import com.api.ttoklip.global.success.Message;
import java.util.Arrays;
import java.util.List;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

/**
 * HoneyTipPostFacade 는 각 메서드들을 조합해 HoneyTip 게시글을 생성하는 것으로 메서드가 성공 호출되는지만을 검증합니다.
 * 자세한 비즈니스 로직은 Service 테스트에서 담당합니다.
 */

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class HoneyTipPostFacadeTest {

    @InjectMocks
    private HoneyTipPostFacade honeyTipPostFacade;

    @Mock
    private MemberService memberService;

    @Mock
    private HoneyTipPostService honeyTipPostService;

    @Mock
    private HoneyTipUrlService honeyTipUrlService;

    @Mock
    private HoneyTipImageService honeyTipImageService;

    @Mock
    private S3FileUploader s3FileUploader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 사진만_포함된_허니팁_게시글_등록_메서드_호출_성공() {
        // Given
        HoneyTipCreateRequest request = 사진_포함된_게시글_요청_픽스처();
        var member = MemberFixture.일반_회원_생성();
        when(memberService.findById(member.getId())).thenReturn(member);

        // S3 업로드 결과를 모킹하여 가짜 URL 리스트 반환
        List<String> fakeUrls = Arrays.asList("http://fake-url1.com", "http://fake-url2.com");
        when(honeyTipPostService.uploadImages(any())).thenReturn(fakeUrls);

        // When
        Message result = honeyTipPostFacade.register(request, member.getId());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).contains("HoneyTip", "생성");
        verify(honeyTipPostService, times(1)).saveHoneyTipPost(any());

        // 이미지 리스트의 크기만큼 register 메서드 호출 횟수 검증
        int imageCount = fakeUrls.size();
        verify(honeyTipImageService, times(imageCount)).register(any(), any());
    }

    @Test
    void URL만_포함된_허니팁_게시글_등록_메서드_호출_성공() {
        // Given
        HoneyTipCreateRequest request = URL_있음_게시글_요청_픽스처();
        var member = MemberFixture.일반_회원_생성();
        when(memberService.findById(member.getId())).thenReturn(member);

        // When
        Message result = honeyTipPostFacade.register(request, member.getId());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).contains("HoneyTip", "생성");
        verify(honeyTipPostService, times(1)).saveHoneyTipPost(any());

        // URL 리스트의 크기만큼 register 메서드 호출 횟수 검증
        int urlCount = request.getUrl() != null ? request.getUrl().size() : 0;
        verify(honeyTipUrlService, times(urlCount)).register(any(), any());
    }

    @Test
    void 사진과_URL_모두_없는_허니팁_게시글_등록_메서드_호출_성공() {
        // Given
        HoneyTipCreateRequest request = URL_X_사진_X_단순_꿀팁_게시글_요청_픽스처();
        var member = MemberFixture.일반_회원_생성();
        when(memberService.findById(member.getId())).thenReturn(member);

        // When
        Message result = honeyTipPostFacade.register(request, member.getId());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).contains("HoneyTip", "생성");
        verify(honeyTipPostService, times(1)).saveHoneyTipPost(any());
    }

    @Test
    void 사진과_URL_모두_있는_허니팁_게시글_등록_메서드_호출_성공() {
        // Given
        HoneyTipCreateRequest request = 사진_URL_둘다_있는_게시글_요청_픽스처();
        var member = MemberFixture.일반_회원_생성();
        when(memberService.findById(member.getId())).thenReturn(member);

        // S3 업로드 결과를 모킹하여 가짜 URL 리스트 반환
        List<String> fakeUrls = Arrays.asList("http://fake-url1.com", "http://fake-url2.com");
        when(honeyTipPostService.uploadImages(any())).thenReturn(fakeUrls);

        // When
        Message result = honeyTipPostFacade.register(request, member.getId());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).contains("HoneyTip", "생성");
        verify(honeyTipPostService, times(1)).saveHoneyTipPost(any());

        // 이미지 리스트의 크기만큼 register 메서드 호출 횟수 검증
        int imageCount = fakeUrls.size();
        System.out.println("imageCount = " + imageCount);
        verify(honeyTipImageService, times(imageCount)).register(any(), any());

        // URL 리스트의 크기만큼 register 메서드 호출 횟수 검증
        int urlCount = request.getUrl() != null ? request.getUrl().size() : 0;
        verify(honeyTipUrlService, times(urlCount)).register(any(), any());
    }

    private HoneyTipCreateRequest 사진_URL_둘다_있는_게시글_요청_픽스처() {
        List<MultipartFile> 이미지_리스트 = List.of(mock(MultipartFile.class), mock(MultipartFile.class));
        List<String> urls = List.of("https://example.com1", "https://example.com2");
        return HoneyTipCreateRequest.builder()
                .title("사진과 URL이 모두 포함된 허니팁")
                .content("이 게시글은 사진과 URL을 포함합니다.")
                .category("WELFARE_POLICY")
                .url(urls)
                .images(이미지_리스트)
                .build();
    }

    private HoneyTipCreateRequest 사진_포함된_게시글_요청_픽스처() {
        List<MultipartFile> 이미지_리스트 = List.of(mock(MultipartFile.class), mock(MultipartFile.class));
        return HoneyTipCreateRequest.builder()
                .title("사진과 URL이 모두 포함된 허니팁")
                .content("이 게시글은 사진과 URL을 포함합니다.")
                .category("WELFARE_POLICY")
                .url(null)
                .images(이미지_리스트)
                .build();
    }
}