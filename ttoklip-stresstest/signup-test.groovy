import static net.grinder.script.Grinder.grinder
import net.grinder.plugin.http.HTTPRequest
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.client.methods.HttpPost

class TestRunner {
    def test = new Test(1, "회원가입 테스트")
    def request = new HTTPRequest()

    @BeforeThread
    void beforeThread() {
        grinder.logger.info("Thread Start: ${grinder.threadNumber}")
    }

    @Test
    void run() {
        def url = "https://toychip.click/api/v1/auth/signup"
        HttpPost post = new HttpPost(url)

        def uniqueKey = System.currentTimeMillis() + Math.random().toString()
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

        // resources 디렉토리에 있는 profile.png 참조
        def profileImagePath = grinder.getScript().getScriptDirectory() + "/resources/profile.png"
        builder.addBinaryBody("profileImage", new File(profileImagePath))

        post.setEntity(builder.build())
        def response = request.POST(post)

        grinder.logger.info("Response: ${response.statusCode}")
    }
}
