package engine.presentation;

import engine.businesslayer.entities.Complete;
import engine.businesslayer.entities.Quiz;
import engine.businesslayer.services.CompleteService;
import engine.businesslayer.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class Controller {

    @Autowired
    QuizService quizService;

    @Autowired
    CompleteService completeService;

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizbyId(@PathVariable Long id) {
        return quizService.findQuizById(id);
    }

    // old mapping from past stages
//    @GetMapping("/api/quizzes")
//    public ResponseEntity<List<Quiz>> getAllquizzes() {
//        return quizService.findAllQuiz();
//    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<Page<Quiz>> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<Quiz> list = quizService.findAllQuizzesWithPagination(page, pageSize, sortBy);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Page<Complete>> getAllCompleted(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "completedAt") String sortBy) {
        Page<Complete> list = completeService.findAllCompletedWithPagination(page, pageSize, sortBy);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
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


    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable Long id) {
        return quizService.delete(id);
    }
}
