package platform.businesslayer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import platform.businesslayer.entities.Snippet;
import platform.dataaccesslayer.SnippetsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SnippetService {
    private final SnippetsRepository snippetsRepository;
    private static final String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";

    @Autowired
    public SnippetService(SnippetsRepository snippetsRepository) {
        this.snippetsRepository = snippetsRepository;
    }

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

}
