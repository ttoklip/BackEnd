//package com.api.honeytip.facade;
//
//import static honeytip.fixture.HoneyTipFixture.URL_X_사진_X_단순_꿀팁_게시글_요청_픽스처;
//import static honeytip.fixture.HoneyTipFixture.URL_있음_게시글_요청_픽스처;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.SoftAssertions.assertSoftly;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
//import com.api.ttoklip.domain.honeytip.controller.dto.request.HoneyTipCreateRequest;
//import com.api.ttoklip.domain.honeytip.controller.dto.request.HoneyTipEditReq;
//import com.api.ttoklip.domain.honeytip.controller.dto.response.HoneyTipSingleResponse;
//import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
//import com.api.ttoklip.domain.honeytip.domain.HoneyTipComment;
//import com.api.ttoklip.domain.honeytip.facade.HoneyTipPostFacade;
//import com.api.ttoklip.domain.main.dto.response.CategoryPagingResponse;
//import com.api.ttoklip.domain.main.dto.response.CategoryResponses;
//import com.api.ttoklip.global.success.Message;
//import honeytip.fixture.HoneyTipFixture;
//import java.util.Arrays;
//import java.util.List;
//import member.fixture.MemberFixture;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.web.multipart.MultipartFile;
//import report.fixture.ReportFixture;
//
///**
// * HoneyTipPostFacade 는 각 메서드들을 조합해 HoneyTip 게시글을 생성하는 것으로 메서드가 성공 호출되는지만을 검증합니다.
// * 자세한 비즈니스 로직은 Service 테스트에서 담당합니다.
// */
//
//@SuppressWarnings("NonAsciiCharacters")
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//public class HoneyTipPostFacadeTest extends HoneyTipFacadeTestHelper {
//
//    @InjectMocks
//    private HoneyTipPostFacade honeyTipPostFacade;
//
//    /* -------------------------------------------- CREATE METHOD CALL TEST -------------------------------------------- */
//
//    @Test
//    void 허니팁_게시글_생성_사진만_포함_메서드_호출_성공() {
//        // Given
//        HoneyTipCreateRequest request = 사진_포함된_게시글_요청_픽스처();
//        var member = MemberFixture.일반_회원_생성1();
//        when(memberService.findById(member.getId())).thenReturn(member);
//
//        // S3 업로드 결과를 모킹하여 가짜 URL 리스트 반환
//        List<String> fakeUrls = Arrays.asList("http://fake-url1.com", "http://fake-url2.com");
//        when(honeyTipPostService.uploadImages(any())).thenReturn(fakeUrls);
//
//        // When
//        Message result = honeyTipPostFacade.register(request, member.getId());
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("HoneyTip", "생성");
//        });
//
//        // 이미지 리스트의 크기만큼 register 메서드 호출 횟수 검증
//        int imageCount = fakeUrls.size();
//        verify(honeyTipImageService, times(imageCount)).register(any(), any());
//        verify(honeyTipPostService, times(1)).saveHoneyTipPost(any());
//    }
//
//    @Test
//    void 허니팁_게시글_생성_URL만_포함된_메서드_호출_성공() {
//        // Given
//        HoneyTipCreateRequest request = URL_있음_게시글_요청_픽스처();
//        var member = MemberFixture.일반_회원_생성1();
//        when(memberService.findById(member.getId())).thenReturn(member);
//
//        // When
//        Message result = honeyTipPostFacade.register(request, member.getId());
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("HoneyTip", "생성");
//        });
//        verify(honeyTipPostService, times(1)).saveHoneyTipPost(any());
//
//        // URL 리스트의 크기만큼 register 메서드 호출 횟수 검증
//        int urlCount = request.getUrl() != null ? request.getUrl().size() : 0;
//        verify(honeyTipUrlService, times(urlCount)).register(any(), any());
//    }
//
//    @Test
//    void 허니팁_게시글_생성_사진과_URL_모두_없음_메서드_호출_성공() {
//        // Given
//        HoneyTipCreateRequest request = URL_X_사진_X_단순_꿀팁_게시글_요청_픽스처();
//        var member = MemberFixture.일반_회원_생성1();
//        when(memberService.findById(member.getId())).thenReturn(member);
//
//        // When
//        Message result = honeyTipPostFacade.register(request, member.getId());
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("HoneyTip", "생성");
//        });
//        verify(honeyTipPostService, times(1)).saveHoneyTipPost(any());
//    }
//
//    @Test
//    void 허니팁_게시글_생성_사진과_URL_모두_있음_메서드_호출_성공() {
//        // Given
//        HoneyTipCreateRequest request = 사진_URL_둘다_있는_게시글_요청_픽스처();
//        var member = MemberFixture.일반_회원_생성1();
//        when(memberService.findById(member.getId())).thenReturn(member);
//
//        // S3 업로드 결과를 모킹하여 가짜 URL 리스트 반환
//        List<String> fakeUrls = Arrays.asList("http://fake-url1.com", "http://fake-url2.com");
//        when(honeyTipPostService.uploadImages(any())).thenReturn(fakeUrls);
//
//        // When
//        Message result = honeyTipPostFacade.register(request, member.getId());
//
//        // Then
//        assertThat(result).isNotNull();
//        assertThat(result.getMessage()).contains("HoneyTip", "생성");
//        verify(honeyTipPostService, times(1)).saveHoneyTipPost(any());
//
//        // 이미지 리스트의 크기만큼 register 메서드 호출 횟수 검증
//        int imageCount = fakeUrls.size();
//        System.out.println("imageCount = " + imageCount);
//        verify(honeyTipImageService, times(imageCount)).register(any(), any());
//
//        // URL 리스트의 크기만큼 register 메서드 호출 횟수 검증
//        int urlCount = request.getUrl() != null ? request.getUrl().size() : 0;
//        verify(honeyTipUrlService, times(urlCount)).register(any(), any());
//    }
//
//    private HoneyTipCreateRequest 사진_URL_둘다_있는_게시글_요청_픽스처() {
//        List<MultipartFile> 이미지_리스트 = List.of(mock(MultipartFile.class), mock(MultipartFile.class));
//        List<String> urls = List.of("https://example.com1", "https://example.com2");
//        return HoneyTipCreateRequest.builder()
//                .title("사진과 URL이 모두 포함된 허니팁")
//                .content("이 게시글은 사진과 URL을 포함합니다.")
//                .category("WELFARE_POLICY")
//                .url(urls)
//                .images(이미지_리스트)
//                .build();
//    }
//
//    private HoneyTipCreateRequest 사진_포함된_게시글_요청_픽스처() {
//        List<MultipartFile> 이미지_리스트 = List.of(mock(MultipartFile.class), mock(MultipartFile.class));
//        return HoneyTipCreateRequest.builder()
//                .title("사진과 URL이 모두 포함된 허니팁")
//                .content("이 게시글은 사진과 URL을 포함합니다.")
//                .category("WELFARE_POLICY")
//                .url(null)
//                .images(이미지_리스트)
//                .build();
//    }
//
//    /* -------------------------------------------- CREATE METHOD CALL TEST END -------------------------------------------- */
//
//
//    /* -------------------------------------------- EDIT METHOD CALL TEST -------------------------------------------- */
//    @Test
//    void 허니팁_수정_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_사진_URL_포함하여_생성(member);
//        HoneyTipEditReq editRequest = 허니팁_수정_요청_픽스처();
//
//        when(honeyTipPostService.getHoneytip(honeyTip.getId())).thenReturn(honeyTip);
//        when(memberService.findById(member.getId())).thenReturn(member);
//
//        // S3 업로드 결과를 모킹하여 가짜 URL 리스트 반환
//        List<String> fakeUrls = Arrays.asList("http://fake-url1.com", "http://fake-url2.com");
//        when(honeyTipPostService.uploadImages(any())).thenReturn(fakeUrls);
//
//        // When
//        Message result = honeyTipPostFacade.edit(honeyTip.getId(), editRequest, member.getId());
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("HoneyTip", "수정");
//        });
//
//        // 허니팁의 ID로 수정 메서드 호출 검증
//        verify(honeyTipPostService, times(1)).getHoneytip(honeyTip.getId());
//        verify(honeyTipUrlService, times(1)).updateHoneyTipUrls(honeyTip, editRequest.getUrl());
//        verify(honeyTipImageService, times(1)).deleteImages(editRequest.getDeleteImageIds(), member.getId());
//
//        // 추가 이미지 등록 횟수 검증 (모킹된 S3 업로드 URL 사용)
//        int addImageCount = fakeUrls.size();
//        verify(honeyTipImageService, times(addImageCount)).register(any(), any());
//    }
//
//
//    public HoneyTipEditReq 허니팁_수정_요청_픽스처() {
//        return HoneyTipEditReq.builder()
//                .title("수정된 허니팁 타이틀")
//                .content("수정된 허니팁 내용")
//                .url(List.of("https://new-url1.com"))
//                .addImages(List.of(mock(MultipartFile.class))) // MultipartFile 모킹
//                .deleteImageIds(List.of(1L)) // 삭제할 이미지 ID
//                .build();
//    }
//
//    /* -------------------------------------------- EDIT METHOD CALL TEST END -------------------------------------------- */
//
//
//    /* -------------------------------------------- DELETE METHOD CALL TEST -------------------------------------------- */
//    @Test
//    void 허니팁_삭제_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_사진_URL_포함하여_생성(member);
//
//        when(honeyTipPostService.getHoneytip(honeyTip.getId())).thenReturn(honeyTip);
//        when(memberService.findById(member.getId())).thenReturn(member);
//
//        // When
//        Message result = honeyTipPostFacade.delete(honeyTip.getId(), member.getId());
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("HoneyTip", "삭제");
//            softly.assertThat(honeyTip.isDeleted()).isTrue();
//        });
//        // 연관된 URL. 이미지 엔티티들이 비활성화되었는지 확인
//        honeyTip.getHoneyTipUrls().forEach(url -> assertThat(url.isDeleted()).isTrue());
//        honeyTip.getHoneyTipImages().forEach(image -> assertThat(image.isDeleted()).isTrue());
//
//        // 허니팁의 ID로 삭제 메서드 호출 검증
//        verify(honeyTipPostService, times(1)).getHoneytip(honeyTip.getId());
//        verify(honeyTipPostService, times(1)).checkEditPermission(honeyTip, member.getId());
//    }
//
//    /* -------------------------------------------- DELETE METHOD CALL TEST END -------------------------------------------- */
//
//
//    /* -------------------------------------------- REPORT METHOD CALL TEST -------------------------------------------- */
//    @Test
//    void 허니팁_게시글_신고_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        HoneyTip honeyTip = HoneyTipFixture.타인_허니팁_생성();
//        ReportCreateRequest request = ReportFixture.신고_요청_픽스처();
//
//        when(honeyTipPostService.getHoneytip(honeyTip.getId())).thenReturn(honeyTip);
//        when(memberService.findById(member.getId())).thenReturn(member); // 이 부분이 중요합니다.
//
//        // When
//        Message result = honeyTipPostFacade.report(honeyTip.getId(), request, member.getId());
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("HoneyTip", "신고");
//        });
//
//        verify(honeyTipPostService, times(1)).getHoneytip(honeyTip.getId());
//        verify(reportService, times(1)).reportHoneyTip(request, honeyTip, member); // member가 제대로 전달되는지 확인
//    }
//
//    /* -------------------------------------------- REPORT METHOD CALL TEST END -------------------------------------------- */
//
//
//    /* -------------------------------------------- 단건 READ 메서드 테스트 -------------------------------------------- */
//    @Test
//    void 허니팁_게시글_단건_조회_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_사진_URL_포함하여_생성(member);
//        List<HoneyTipComment> comments = List.of(); // 댓글 리스트는 빈 리스트로 설정
//
//        when(honeyTipPostService.findHoneyTipWithDetails(honeyTip.getId())).thenReturn(honeyTip);
//        when(honeyTipCommentService.findCommentsByHoneyTipId(honeyTip.getId())).thenReturn(comments);
//        // 가짜 좋아요 수
//        when(honeyTipLikeService.countHoneyTipLikes(honeyTip.getId())).thenReturn(10L);
//        // 가짜 스크랩 수
//        when(honeyTipScrapService.countHoneyTipScraps(honeyTip.getId())).thenReturn(5L);
//        // 현재 유저가 좋아요 눌렀는지 여부
//        when(honeyTipLikeService.isHoneyTipLikeExists(honeyTip.getId(), member.getId())).thenReturn(true);
//        // 현재 유저가 스크랩했는지 여부
//        when(honeyTipScrapService.isHoneyTipScrapExists(honeyTip.getId(), member.getId())).thenReturn(false);
//
//        // When
//        HoneyTipSingleResponse response = honeyTipPostFacade.getSinglePost(honeyTip.getId(), member.getId());
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(response).isNotNull();
//            softly.assertThat(response.getLikeCount()).isEqualTo(10);
//            softly.assertThat(response.getScrapCount()).isEqualTo(5);
//            softly.assertThat(response.isLikedByCurrentUser()).isTrue();
//            softly.assertThat(response.isScrapedByCurrentUser()).isFalse();
//        });
//
//        // 각 서비스 메서드들이 제대로 호출되었는지 검증
//        verify(honeyTipPostService, times(1)).findHoneyTipWithDetails(honeyTip.getId());
//        verify(honeyTipCommentService, times(1)).findCommentsByHoneyTipId(honeyTip.getId());
//        verify(honeyTipLikeService, times(1)).countHoneyTipLikes(honeyTip.getId());
//        verify(honeyTipScrapService, times(1)).countHoneyTipScraps(honeyTip.getId());
//        verify(honeyTipLikeService, times(1)).isHoneyTipLikeExists(honeyTip.getId(), member.getId());
//        verify(honeyTipScrapService, times(1)).isHoneyTipScrapExists(honeyTip.getId(), member.getId());
//    }
//    /* -------------------------------------------- 단건 READ 메서드 테스트 끝 -------------------------------------------- */
//
//
//    /* -------------------------------------------- 카테고리별 10개씩 메인 조회 메서드 테스트 -------------------------------------------- */
//    @Test
//    void 허니팁_카테고리별_메인_조회_메서드_호출_성공() {
//        // Given
//        List<HoneyTip> houseworkTips = HoneyTipFixture.허니팁_집안일_크기가_10인_리스트_생성();
//        List<HoneyTip> recipeTips = HoneyTipFixture.허니팁_레시피_크기가_10인_리스트_생성();
//        List<HoneyTip> safeLivingTips = HoneyTipFixture.허니팁_안전생활_크기가_10인_리스트_생성();
//        List<HoneyTip> welfarePolicyTips = HoneyTipFixture.허니팁_복지정책_크기가_10인_리스트_생성();
//
//        when(honeyTipPostService.findHouseworkTips()).thenReturn(houseworkTips);
//        when(honeyTipPostService.findRecipeTips()).thenReturn(recipeTips);
//        when(honeyTipPostService.findSafeLivingTips()).thenReturn(safeLivingTips);
//        when(honeyTipPostService.findWelfarePolicyTips()).thenReturn(welfarePolicyTips);
//
//        // When
//        CategoryResponses response = honeyTipPostFacade.getDefaultCategoryRead();
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(response).isNotNull();
//            softly.assertThat(response.getHousework()).hasSize(houseworkTips.size());
//            softly.assertThat(response.getCooking()).hasSize(recipeTips.size());
//            softly.assertThat(response.getSafeLiving()).hasSize(safeLivingTips.size());
//            softly.assertThat(response.getWelfarePolicy()).hasSize(welfarePolicyTips.size());
//        });
//
//        verify(honeyTipPostService, times(1)).findHouseworkTips();
//        verify(honeyTipPostService, times(1)).findRecipeTips();
//        verify(honeyTipPostService, times(1)).findSafeLivingTips();
//        verify(honeyTipPostService, times(1)).findWelfarePolicyTips();
//    }
//
//    /* -------------------------------------------- 카테고리별 10개씩 메인 조회 메서드 테스트 끝 -------------------------------------------- */
//
//
//    /* -------------------------------------------- 카테고리별 페이징 조회 메서드 테스트 -------------------------------------------- */
//    @Test
//    void 허니팁_카테고리별_페이징_요청_메서드_호출_성공_레시피() {
//        // Given
//        PageRequest pageable = PageRequest.of(0, 10);
//        List<HoneyTip> 레시피_허니팁_리스트 = HoneyTipFixture.허니팁_레시피_크기가_10인_리스트_생성();
//        Page<HoneyTip> page = new PageImpl<>(레시피_허니팁_리스트, pageable, 레시피_허니팁_리스트.size());
//
//        when(honeyTipPostService.matchCategoryPaging(Category.RECIPE, pageable)).thenReturn(page);
//
//        // When
//        CategoryPagingResponse response = honeyTipPostFacade.matchCategoryPaging(Category.RECIPE, pageable);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(response).isNotNull();
//            softly.assertThat(response.category()).isEqualTo(Category.RECIPE);
//            softly.assertThat(response.data()).hasSize(10);
//            softly.assertThat(response.totalElements()).isEqualTo(10);
//            softly.assertThat(response.totalPage()).isEqualTo(1);
//            softly.assertThat(response.isFirst()).isTrue();
//            softly.assertThat(response.isLast()).isTrue();
//        });
//    }
//
//    @Test
//    void 허니팁_카테고리별_페이징_요청_메서드_호출_성공_안전생활() {
//        // Given
//        PageRequest pageable = PageRequest.of(0, 10);
//        List<HoneyTip> 안전생활_허니팁_리스트 = HoneyTipFixture.허니팁_안전생활_크기가_10인_리스트_생성();
//        Page<HoneyTip> page = new PageImpl<>(안전생활_허니팁_리스트, pageable, 안전생활_허니팁_리스트.size());
//
//        when(honeyTipPostService.matchCategoryPaging(Category.SAFE_LIVING, pageable)).thenReturn(page);
//
//        // When
//        CategoryPagingResponse response = honeyTipPostFacade.matchCategoryPaging(Category.SAFE_LIVING, pageable);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(response).isNotNull();
//            softly.assertThat(response.category()).isEqualTo(Category.SAFE_LIVING);
//            softly.assertThat(response.data()).hasSize(10);
//            softly.assertThat(response.totalElements()).isEqualTo(10);
//            softly.assertThat(response.totalPage()).isEqualTo(1);
//            softly.assertThat(response.isFirst()).isTrue();
//            softly.assertThat(response.isLast()).isTrue();
//        });
//    }
//
//    @Test
//    void 허니팁_카테고리별_페이징_요청_메서드_호출_성공_복지정책() {
//        // Given
//        PageRequest pageable = PageRequest.of(0, 10);
//        List<HoneyTip> 복지정책_허니팁_리스트 = HoneyTipFixture.허니팁_복지정책_크기가_10인_리스트_생성();
//        Page<HoneyTip> page = new PageImpl<>(복지정책_허니팁_리스트, pageable, 복지정책_허니팁_리스트.size());
//
//        when(honeyTipPostService.matchCategoryPaging(Category.WELFARE_POLICY, pageable)).thenReturn(page);
//
//        // When
//        CategoryPagingResponse response = honeyTipPostFacade.matchCategoryPaging(Category.WELFARE_POLICY, pageable);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(response).isNotNull();
//            softly.assertThat(response.category()).isEqualTo(Category.WELFARE_POLICY);
//            softly.assertThat(response.data()).hasSize(10);
//            softly.assertThat(response.totalElements()).isEqualTo(10);
//            softly.assertThat(response.totalPage()).isEqualTo(1);
//            softly.assertThat(response.isFirst()).isTrue();
//            softly.assertThat(response.isLast()).isTrue();
//        });
//    }
//
//    /* -------------------------------------------- 카테고리별 페이징 조회 메서드 테스트 끝 -------------------------------------------- */
//}