package report.fixture;

import com.domain.report.domain.ReportCreate;
import com.domain.report.domain.ReportType;

public class ReportFixture {

    public static ReportCreate 신고_요청_픽스처() {
        return ReportCreate.of("이 게시글은 부적절한 내용을 포함하고 있습니다.", ReportType.ABUSE);
    }
}