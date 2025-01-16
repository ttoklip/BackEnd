/******************************************************
 * nGrinder Groovy 스크립트 예시
 * 
 * 주의:
 * - org.apache.http.entity.mime.MultipartEntityBuilder,
 *   org.apache.http.client.methods.HttpPost 클래스를 사용할 경우
 *   반드시 nGrinder 에이전트 쪽에 httpclient, httpmime 등
 *   필요한 라이브러리가 포함되어 있어야 합니다.
 *
 * - @Test, @BeforeThread 어노테이션은
 *   `net.grinder.scriptengine.groovy.annotation` 패키지의 것을 사용합니다.
 ******************************************************/

// [1] nGrinder에서 제공하는 Annotation Import
import net.grinder.scriptengine.groovy.annotation.BeforeThread
import net.grinder.scriptengine.groovy.annotation.Test

// [2] nGrinder 주요 객체(로거 등)를 가져오기 위해 필요한 Import
import net.grinder.script.Grinder
import static net.grinder.script.Grinder.grinder

// [3] nGrinder HTTP Plugin
import net.grinder.plugin.http.HTTPRequest
import net.grinder.plugin.http.HTTPResponse
import net.grinder.plugin.http.HTTPPluginControl

// [4] (필요하다면) Test 객체를 사용하려면 GTest import (테스트 관리/기록용)
import net.grinder.script.GTest

// [5] Apache HttpComponents (httpclient, httpmime 등) - 에이전트 lib 경로에 jar 필요
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.mime.MultipartEntityBuilder

// 자바의 File 클래스 등
import java.io.File

class TestRunner {

    // nGrinder에서 Test를 기록(Record)하기 위해 GTest 인스턴스 생성
    // 꼭 필요한 건 아니지만, 콘솔에서 통계(Transactions)로 잡히길 원하면 보통 사용합니다.
    public static GTest gTest
    public static HTTPRequest request

    // 스크립트 전체에서 사용할 로거
    public static def logger = grinder.logger

    /**
     * [선택] 스크립트 실행 전(프로세스 단위) 한번만 호출
     * @BeforeProcess (필요시)
     * static void beforeProcess() {
     *     // 여러 부가 설정(쿠키매니저 세팅 등) 가능
     * }
     */

    /**
     * [선택] 스레드가 시작할 때마다 호출
     *  - 로거를 통해 스레드 시작 알림
     */
    @BeforeThread
    void beforeThread() {
        if (!gTest) {
            // GTest를 여기서 초기화해도 되고, @BeforeProcess에서 해도 됩니다.
            gTest = new GTest(1, "회원가입 테스트")
            request = new HTTPRequest()
            gTest.record(request)  // 이 요청을 통계에 기록
        }
        logger.info("Thread Start: ${grinder.threadNumber}")
    }

    /**
     * [필수] 실제 테스트(트랜잭션) 로직
     *  - nGrinder 콘솔에서 @Test 메소드가 실제 호출 대상이 됩니다.
     */
    @Test
    void run() {
        // 실제 회원가입을 테스트할 URL
        def url = "https://toychip.click/api/v1/auth/signup"

        // Apache HttpComponents를 활용하여 HttpPost 객체 생성
        HttpPost post = new HttpPost(url)

        // 유니크한 이메일, 닉네임 생성 등을 위해 시간/난수 사용
        def uniqueKey = System.currentTimeMillis() + Math.random().toString()

        // MultipartEntityBuilder를 통해 Form-Data 구성
        def builder = MultipartEntityBuilder.create()
        builder.addTextBody("email", "test${uniqueKey}@example.com")
        builder.addTextBody("password", "1234")
        builder.addTextBody("originName", "Origin Name")
        builder.addTextBody("nickname", "nickname_${uniqueKey}")
        builder.addTextBody("independentYear", "${(1..5).toList().get((Math.random() * 5) as int)}")
        builder.addTextBody("independentMonth", "${(1..12).toList().get((Math.random() * 12) as int)}")
        builder.addTextBody("street", "서울특별시 동작구 노량진동")
        builder.addTextBody("agreeTermsOfService", "true")
        builder.addTextBody("agreePrivacyPolicy", "true")
        builder.addTextBody("agreeLocationService", "true")
        builder.addTextBody("categories", "HOUSEWORK")

        // nGrinder Script 디렉토리에 있는 파일 참조
        def profileImagePath = grinder.getScript().getScriptDirectory() + "/resources/profile.png"
        builder.addBinaryBody("profileImage", new File(profileImagePath))

        // Post Request에 Entity 연결
        post.setEntity(builder.build())

        // 실제 Request 전송
        HTTPResponse response = request.POST(post)

        // 응답 상태코드 로깅
        logger.info("Response Status: ${response.statusCode}")

        // 필요하다면 응답 Body 출력 등 추가 작업 가능
        // logger.info("Response Body: ${response.text}")
    }
}
