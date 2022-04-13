package platform.businesslayer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import platform.businesslayer.entities.Snippet;
import platform.dataaccesslayer.SnippetsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SnippetService {
    private final SnippetsRepository snippetsRepository;
    private static final String DATE_FORMATTER = "yyyy/MM/dd HH:mm:ss";
    private Snippet snippetDB =new Snippet("public static void ...","2020/01/01 12:00:03");

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

    public ResponseEntity<String> usingResponseEntityBuilderAndHttpHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("title", "Code");
        String date = snippetDB.getDate();

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("<html>\n" +
                        "   <head>\n" +
                        "       <title>Code</title>\n" +
                        "   </head>\n" +
                        "   <body>\n" +
                        "       <span id=\"load_date\">\n" +

                                     date +

                                "</span>" +
                        "      <pre id=\"code_snippet\">\n" +

                                    snippetDB.getCode() +

                                "</pre>\n" +
                            "</body>\n" +
                        "</html>");
    }

    public ResponseEntity<String> codeNew() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("title", "Create");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("<html>\n" +
                        "<head>\n" +
                        "<title>Create</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<textarea id=\"code_snippet\">\n" +
                        "// write your code here" +
                        "</textarea>\n" +
                        "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button\">\n" +
                        "</body>\n" +
                        "</html>");
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