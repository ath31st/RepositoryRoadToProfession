package engine.businesslayer.services;

import engine.businesslayer.entities.Complete;
import engine.businesslayer.entities.User;
import engine.businesslayer.interfaceimpl.IAuthenticationFacade;
import engine.businesslayer.entities.Quiz;
import engine.persistence.CompleteRepository;
import engine.persistence.QuizRepository;
import engine.persistence.UserRepository;
import engine.presentation.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CompleteRepository completeRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    public QuizService(QuizRepository quizRepository, UserRepository userRepository, CompleteRepository completeRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.completeRepository = completeRepository;
    }

    public Quiz findQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such quiz"));
    }


    // old method from past stages
//    public ResponseEntity<List<Quiz>> findAllQuiz() {
//        Iterable<Quiz> iterable = quizRepository.findAll();
//        List<Quiz> quizzes = new ArrayList<>();
//        iterable.iterator().forEachRemaining(quizzes::add);
//        return quizzes.isEmpty() ? ResponseEntity.ok().build() : ResponseEntity.ok(quizzes);
//    }

    public Page<Quiz> findAllQuizzesWithPagination(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Quiz> pagedResult = quizRepository.findAll(paging);
        return pagedResult;
//        if(pagedResult.hasContent()) {
//            return pagedResult;
//        } else {
//            return new ArrayList<Quiz>();
//        }
    }


    public void saveNewQuizzes(Quiz quiz) {
        Authentication authentication = authenticationFacade.getAuthentication();
        quiz.setCreator(authentication.getName());
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

        if (Objects.deepEquals(listFromAnswer, listFromQuiz)) {
            Authentication authentication = authenticationFacade.getAuthentication();
            User user = userRepository.findByUsername(authentication.getName());
            completeRepository.save(new Complete(quiz.getId(), user.getId(), LocalDateTime.now()));

            return new Answer(true, "Congratulations, you're right!");
        } else return new Answer(false, "Wrong answer! Please, try again.");
    }

    public ResponseEntity delete(Long id) {
        if (quizRepository.findById(id).isPresent()) {
            Authentication authentication = authenticationFacade.getAuthentication();
            if (quizRepository.findById(id).get().getCreator().equals(authentication.getName())) {
                quizRepository.deleteById(id);
            } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}