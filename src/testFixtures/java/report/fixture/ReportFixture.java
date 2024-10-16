package report.fixture;

import com.api.ttoklip.domain.common.report.domain.ReportType;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;

public class ReportFixture {

    public static ReportCreateRequest 신고_요청_픽스처() {
        return ReportCreateRequest.builder()
                .content("이 게시글은 부적절한 내용을 포함하고 있습니다.")
                .reportType(ReportType.ABUSE.name())
                .build();
    }
}
