package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class Controller {


    private List<Quiz> quizzes = new ArrayList<>();

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizbyId(@PathVariable int id) {
        for (Quiz quiz : quizzes) {
            if(quiz.getId() == id)
            return quiz;
        }
        throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"No such quiz");
    }

    @GetMapping("/api/quizzes")
    public List<Quiz> getAllquizzes() {
        return quizzes;
    }

    @PostMapping("/api/quizzes")
    public Quiz setQuizzes(@RequestBody Quiz quiz) {
        quizzes.add(quiz);
        Quiz.setIdAfterCreateList(quizzes);
        return quiz;
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Answer getAnswer(@PathVariable int id, @RequestParam("answer") int answer) {
        return quizzes.get(id - 1).getAnswer() == answer ? new Answer(true, "Congratulations, you're right!") :
                new Answer(false, "Wrong answer! Please, try again.");
    }
}
