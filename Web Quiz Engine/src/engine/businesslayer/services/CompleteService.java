package engine.businesslayer.services;


import engine.businesslayer.entities.Complete;
import engine.businesslayer.entities.User;
import engine.businesslayer.interfaceimpl.IAuthenticationFacade;
import engine.persistence.CompleteRepository;
import engine.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;



@Service
public class CompleteService {

    private final CompleteRepository completeRepository;
    private final UserRepository userRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    public CompleteService(CompleteRepository completeRepository, UserRepository userRepository) {
        this.completeRepository = completeRepository;
        this.userRepository = userRepository;
    }


    public Page<Complete> findAllCompletedWithPagination(Integer pageNo, Integer pageSize, String sortBy) {
        Authentication authentication = authenticationFacade.getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Complete> pagedResult = completeRepository.findIdCompleteWithPagination(paging, user.getId());
      // Page<Complete> pagedResult = completeRepository.findAll(paging);
        return pagedResult;
    }


}
