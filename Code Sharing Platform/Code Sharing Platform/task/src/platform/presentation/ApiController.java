package platform.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.businesslayer.entities.Snippet;
import platform.businesslayer.services.SnippetService;

@RestController
public class ApiController {

    @Autowired
    SnippetService snippetService;

    @GetMapping("/api/code")
    public Snippet getCode() {
        return new Snippet("public static void main(String[] args) {\n    SpringApplication.run(CodeSharingPlatform.class, args);\n}");
    }
}
