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
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SnippetService {
    private final SnippetsRepository snippetsRepository;
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss.SSSS";

    @Autowired
    public SnippetService(SnippetsRepository snippetsRepository) {
        this.snippetsRepository = snippetsRepository;
    }

    public Snippet getSnippet(String uuid) {
        Snippet snippet = snippetsRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (isSecretCode(snippet)) {
            if (snippet.getViews() != 0) {
                viewsRemaining(snippet);
                snippetsRepository.save(snippet);
            }
            if (snippet.getTime() != 0)
                timesRemaining(snippet);
            if (snippet.getTime() == 0 & snippet.getViews() == 0) {
                snippetsRepository.delete(snippet);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        return snippet;
    }

    public ResponseEntity<List<Snippet>> getAllSnippet() {
        List<Snippet> snippets = getTenMostRecentlyUploadedSnippets(snippetsRepository.findAll());
        return snippets.isEmpty() ? ResponseEntity.ok().build() : ResponseEntity.ok(snippets);
    }

    public ResponseEntity<String> saveSnippet(Snippet snippet) {
//        if (snippet.getViews() != null && snippet.getViews() < 1) snippet.setViews(null);
//        if (snippet.getTime() != null && snippet.getTime() < 1) snippet.setTime(null);
        UUID uuid = UUID.randomUUID();
        snippet.setUuid(uuid.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        snippet.setDate(LocalDateTime.now().format(formatter));
        snippetsRepository.save(snippet);
        return ResponseEntity.ok().body("{ \"id\" : \"" + snippet.getUuid() + "\" }");
    }

    public ModelAndView getWebSnippet(String uuid) {
        ModelAndView model = new ModelAndView("Snippet");
        Snippet snippet = snippetsRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (isSecretCode(snippet)) {
            if (snippet.getViews() != 0) {
                viewsRemaining(snippet);
                snippetsRepository.save(snippet);
            }
            if (snippet.getTime() != 0)
                timesRemaining(snippet);
            if (snippet.getTime() == 0 & snippet.getViews() == 0) {
                snippetsRepository.delete(snippet);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
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
        snippets = snippets.stream()
                .filter(snippet -> snippet.getTime() == 0 & snippet.getViews() == 0)
                .sorted(Comparator.comparing(Snippet::getDate).reversed())
                .collect(Collectors.toList());

        if (snippets.size() < 11) {
            return snippets;
        } else {
            return snippets.stream()
                    .limit(10)
                    .collect(Collectors.toList());
        }
    }

    public static boolean isSecretCode(Snippet snippet) {
        return snippet.getTime() != 0 | snippet.getViews() != 0;
    }

    public static void viewsRemaining(Snippet snippet) {
        if (snippet.getViews() != null) {
            snippet.setViews((Math.max(0L, snippet.getViews() - 1)));
        }
    }

    public static void timesRemaining(Snippet snippet) {
        if (snippet.getTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
            long secondsDiff = Math.abs(ChronoUnit.SECONDS.between(LocalDateTime.parse(snippet.getDate(), formatter), LocalDateTime.now()));
            snippet.setTime(Math.max(0L, snippet.getTime() - secondsDiff));
        }
    }

}

