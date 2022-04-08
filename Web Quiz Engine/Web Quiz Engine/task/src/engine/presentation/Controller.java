package engine.presentation;

import engine.businesslayer.Quiz;
import engine.businesslayer.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    QuizService quizService;

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizbyId(@PathVariable Long id) {
        return quizService.findQuizById(id);
    }

    @GetMapping("/api/quizzes")
    public List<Quiz> getAllquizzes() {
        return quizService.findAllQuiz();
    }

    @PostMapping("/api/quizzes")
    public Quiz addQuizzes(@Valid @RequestBody Quiz quiz) {
        quizService.saveNewQuizzes(quiz);
        return quiz;
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Answer getAnswer(@PathVariable Long id, @RequestBody Answer answer) {
        return quizService.returnAnswer(quizService.findQuizById(id), answer);
    }
}
