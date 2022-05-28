package account.service;

import account.entites.User;
import account.repository.UserRepository;
import account.util.Operation;
import account.util.Role;
import account.util.RoleChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<User> changeUserRole(RoleChangeRequest roleChangeRequest) {
        User user = userRepository.findUserByEmailIgnoreCase(roleChangeRequest.getUser()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found!")
        );
        Role roleFromReq = Role.valueOf("ROLE_" + roleChangeRequest.getRole().name().toUpperCase());
        checkExistingRole(roleFromReq);
        checkExistingOperation(roleChangeRequest);
        if (roleChangeRequest.getOperation().equals(Operation.GRANT)) {
            grantOperation(user, roleFromReq);
        } else if (roleChangeRequest.getOperation().equals(Operation.REMOVE)) {
            removeOperation(user, roleFromReq);
        }
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }

    private void grantOperation(User user, Role roleFromReq) {
        List<Role> roles = user.getRoles();
        roles.add(roleFromReq);
        Collections.sort(roles);
        user.setRoles(roles);
    }

    private void removeOperation(User user, Role roleFromReq) {
        checkAdminRole(roleFromReq);
        checkExistingRoleUser(user, roleFromReq);
        checkCountRolesUser(user);
        List<Role> roles = user.getRoles();
        roles.remove(roleFromReq);
        user.setRoles(roles);
    }

    private void checkExistingRole(Role roleFromReq) {
        if (!Arrays.asList(Role.values()).contains(roleFromReq)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        }
    }

    private void checkExistingOperation(RoleChangeRequest roleChangeRequest) {
        if (!Arrays.asList(Operation.values()).contains(roleChangeRequest.getOperation())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found!");
        }
    }

    private void checkExistingRoleUser(User user, Role roleFromReq) {
        if (user.getRoles().contains(roleFromReq)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
        }
    }

    private void checkCountRolesUser(User user) {
        if (user.getRoles().size() == 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user must have at least one role!");
        }
    }

    private void checkAdminRole(Role roleFromReq) {
        if (roleFromReq.equals(Role.ROLE_ADMINISTRATOR)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        }
    }
}
