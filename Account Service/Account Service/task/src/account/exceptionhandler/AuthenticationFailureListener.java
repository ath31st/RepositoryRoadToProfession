package account.exceptionhandler;


import account.service.FailedLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private FailedLoginService failedLoginService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
//        User user = (User) event.getAuthentication().getPrincipal();
//        failedLoginService.failedLoginUpdate(user);
    }

}