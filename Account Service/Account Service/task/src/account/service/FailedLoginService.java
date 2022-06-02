package account.service;

import account.entites.FailedLoginCounter;
import account.entites.User;
import account.repository.FailedLoginCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FailedLoginService {
    @Autowired
    private FailedLoginCounterRepository failedLoginCounterRepository;

    public void successLogin(String email) {
        FailedLoginCounter counter = failedLoginCounterRepository.findByEmailIgnoreCase(email).orElse(new FailedLoginCounter());
        counter.setCount(0);
        failedLoginCounterRepository.save(counter);
    }

    public void failedLoginUpdate(String email) {
        FailedLoginCounter counter = failedLoginCounterRepository.findByEmailIgnoreCase(email).orElse(new FailedLoginCounter());
        counter.setEmail(email);
        counter.setCount(counter.getCount() + 1);
        failedLoginCounterRepository.save(counter);
    }

    public int getCountFailedLogin(String email) {
        FailedLoginCounter counter = failedLoginCounterRepository.findByEmailIgnoreCase(email).orElse(new FailedLoginCounter());
        return counter.getCount();
    }

}
