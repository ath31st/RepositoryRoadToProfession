package platform.businesslayer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import platform.businesslayer.entities.Snippet;
import platform.dataaccesslayer.SnippetsRepository;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SnippetService {
    private final SnippetsRepository snippetsRepository;
    private static final String DATE_FORMATTER = "yyyy/MM/dd HH:mm:ss";
    private Snippet snippetDB = new Snippet("public static void ...", "2020/01/01 12:00:03");

    @Autowired
    public SnippetService(SnippetsRepository snippetsRepository) {
        this.snippetsRepository = snippetsRepository;
    }

    public Snippet getSnippet() {
        return snippetDB;
    }

    public ResponseEntity<String> updateSnippet(Snippet snippet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        snippetDB.setDate(LocalDateTime.now().format(formatter));
        snippetDB.setCode(snippet.getCode());
        return ResponseEntity.ok().body("{}");
    }

    public ModelAndView getCode(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        ModelAndView model = new ModelAndView("Code");
        model.addObject("code", snippetDB.getCode());
        model.addObject("date", snippetDB.getDate());
        return model;
    }

    public ModelAndView getSubmitCode(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        return new ModelAndView("CodeNew");
    }

}


/*
    public ResponseEntity<List<Snippet>> getAllSnippet() {
        Iterable<Snippet> iterable = snippetsRepository.findAll();
        List<Snippet> snippets = new ArrayList<>();
        iterable.iterator().forEachRemaining(snippets::add);
        return snippets.isEmpty() ? ResponseEntity.ok().build() : ResponseEntity.ok(snippets);
    }

    public String getCodeFromSnippetById(Long id) {
        return snippetsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getCode();
    }

    public Snippet saveNewSnippet(Snippet snippet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        snippet.setDate(LocalDateTime.now().format(formatter));
        snippetsRepository.save(snippet);
        return new Snippet();
    }
*/