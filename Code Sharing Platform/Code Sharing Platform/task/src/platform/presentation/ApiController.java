package platform.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import platform.businesslayer.entities.Snippet;
import platform.businesslayer.services.SnippetService;


@RestController
public class ApiController {

    @Autowired
    SnippetService snippetService;

    @GetMapping("/api/code")
    public Snippet getSnippet() {
        return snippetService.getSnippet();
    }


    @PostMapping("/api/code/new")
    public ResponseEntity<String> updateSnippet(@RequestBody Snippet snippet) {
        return snippetService.updateSnippet(snippet);
    }


    @GetMapping("/code")
    public ModelAndView getWebSnippet() {
        return snippetService.getCode();
    }

    @GetMapping("/code/new")
    public ModelAndView submitCode() {
        return snippetService.getSubmitCode();
    }
}
