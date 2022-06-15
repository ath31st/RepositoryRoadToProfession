package platform.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import platform.businesslayer.entities.Snippet;
import platform.businesslayer.services.SnippetService;

import java.util.List;


@RestController
public class ApiController {

    @Autowired
    SnippetService snippetService;

    @GetMapping("/api/code/{uuid}")
    public Snippet getSnippetById(@PathVariable String uuid) {
        return snippetService.getSnippet(uuid);
    }


    @PostMapping("/api/code/new")
    public ResponseEntity<String> saveNewSnippet(@RequestBody Snippet snippet) {
        return snippetService.saveSnippet(snippet);
    }

    @GetMapping("/api/code/latest")
    public ResponseEntity<List<Snippet>> getLatestSnippets() {
        return snippetService.getAllSnippet();
    }

    @GetMapping("/code/{uuid}")
    public ModelAndView getWebSnippet(@PathVariable String uuid) {
        return snippetService.getWebSnippet(uuid);
    }

    @GetMapping("/code/latest")
    public ModelAndView getLatestWebSnippets() {
        return snippetService.getLatestWebSnippets();
    }

    @GetMapping("/code/new")
    public ModelAndView submitCode() {
        return snippetService.getSubmitCode();
    }
}
