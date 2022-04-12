package platform.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.businesslayer.entities.Snippet;
import platform.businesslayer.services.SnippetService;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    SnippetService snippetService;

    @GetMapping("/api/code")
    public ResponseEntity<List<Snippet>> getCode() {
        return snippetService.getAllSnippet();
    }

    @PostMapping("/api/code/new")
    public Snippet saveNewSnippet(@RequestBody Snippet snippet) {
        return snippetService.saveNewSnippet(snippet);
    }
}
