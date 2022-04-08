package engine.businesslayer;

import engine.persistence.QuizRepository;
import engine.presentation.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz findQuizById(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such quiz"));
    }

    public List<Quiz> findAllQuiz() {
        Iterable<Quiz> iterable = quizRepository.findAll();
        List<Quiz> quizzes = new ArrayList<>();
        iterable.iterator().forEachRemaining(quizzes::add);
        return quizzes;
    }

    public void saveNewQuizzes(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public Answer returnAnswer(Quiz quiz, Answer answer) {
        if (answer.getAnswer() == null)
            answer.setAnswer(new ArrayList<>());
        if (quiz.getAnswer() == null)
            quiz.setAnswer(new ArrayList<>());
        List<Integer> listFromQuiz = quiz.getAnswer();
        List<Integer> listFromAnswer = answer.getAnswer();
        Collections.sort(listFromAnswer);
        Collections.sort(listFromQuiz);

        return Objects.deepEquals(listFromAnswer,listFromQuiz) ?
                new Answer(true, "Congratulations, you're right!") :
                new Answer(false, "Wrong answer! Please, try again.");
    }
}