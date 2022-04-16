package platform.businesslayer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import platform.businesslayer.entities.Snippet;
import platform.dataaccesslayer.SnippetsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SnippetService {
    private final SnippetsRepository snippetsRepository;
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss.SSS";

    @Autowired
    public SnippetService(SnippetsRepository snippetsRepository) {
        this.snippetsRepository = snippetsRepository;
    }

    public Snippet getSnippet(Long id) {
        return snippetsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Snippet>> getAllSnippet() {
        List<Snippet> snippets = getTenMostRecentlyUploadedSnippets(snippetsRepository.findAll());
        return snippets.isEmpty() ? ResponseEntity.ok().build() : ResponseEntity.ok(snippets);
    }

    public ResponseEntity<String> saveSnippet(Snippet snippet) {
        if (snippet.getViews() != null && snippet.getViews() < 1) snippet.setViews(null);
        if (snippet.getTime() !=null && snippet.getTime() < 1) snippet.setTime(null);
        UUID uuid = UUID.randomUUID();
        snippet.setUuid(uuid.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        snippet.setDate(LocalDateTime.now().format(formatter));
        snippetsRepository.save(snippet);
        return ResponseEntity.ok().body("{ \"id\" : \"" + snippet.getUuid() + "\" }");
    }

    public ModelAndView getWebSnippet(Long id) {
        ModelAndView model = new ModelAndView("Snippet");
        Snippet snippet = snippetsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addObject("code", snippet.getCode());
        model.addObject("date", snippet.getDate());
        model.addObject("views", snippet.getViews());
        model.addObject("time", snippet.getTime());
        return model;
    }

    public ModelAndView getLatestWebSnippets() {
        ModelAndView model = new ModelAndView("LatestSnippets");
        List<Snippet> snippets = getTenMostRecentlyUploadedSnippets(snippetsRepository.findAll());
        model.addObject("snippets", snippets);
        return model;
    }

    public ModelAndView getSubmitCode() {
        return new ModelAndView("CodeNew");
    }

    public static List<Snippet> getTenMostRecentlyUploadedSnippets(Iterable<Snippet> iterable) {
        List<Snippet> snippets = new ArrayList<>();
        iterable.iterator().forEachRemaining(snippets::add);
        if (snippets.size() < 11) {
            return snippets.stream()
                    .filter(snippet -> snippet.getTime() == null & snippet.getViews() == null)
                    .sorted(Comparator.comparing(Snippet::getDate).reversed())
                    .collect(Collectors.toList());
        } else {
            return snippets.stream()
                    .filter(snippet -> snippet.getTime() == null & snippet.getViews() == null)
                    .skip(snippets.size() - 10)
                    .sorted(Comparator.comparing(Snippet::getDate).reversed())
                    .collect(Collectors.toList());
        }
    }

    public static boolean isSecretCode(Snippet snippet) {
        return snippet.getTime() != null | snippet.getViews() != null;
    }

    public static void counter(Snippet snippet) {
        if (snippet.getViews() != null)
            snippet.setViews(snippet.getViews() - 1);
    }

}

