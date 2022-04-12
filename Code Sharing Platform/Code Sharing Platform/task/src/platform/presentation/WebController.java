package platform.presentation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/code")
    public ResponseEntity<String> usingResponseEntityBuilderAndHttpHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("title", "Code");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("<html>\n" +
                        "<head>\n" +
                        "    <title>Code</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<pre>\n" +
                        "public static void main(String[] args) {\n" +
                        "    SpringApplication.run(CodeSharingPlatform.class, args);\n" +
                        "}</pre>\n" +
                        "</body>\n" +
                        "</html>");
    }
}
