package fun.lww.securitydemo.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class MockServer {

    public static void main(String[] args) throws IOException {
        WireMock.configureFor("127.0.0.1", 8081);
        WireMock.removeAllMappings();

        ClassPathResource resource = new ClassPathResource("mockdata/user-create.json");
        String msg = FileUtils.readFileToString(resource.getFile(), "UTF-8");
        System.out.println(msg);

        mock("/user/1", msg);
        mock("/userinfo1/1", msg);

    }

    private static void mock(String url, String msg) {
        WireMock.stubFor(WireMock.get(url).willReturn(WireMock.aResponse().withBody(msg)));
    }
}
