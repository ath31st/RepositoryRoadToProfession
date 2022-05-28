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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<User> changeUserRole(RoleChangeRequest roleChangeRequest) {
        User user = userRepository.findUserByEmailIgnoreCase(roleChangeRequest.getUser()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!")
        );

        String stringRoleFromReq = "ROLE_" + roleChangeRequest.getRole().toUpperCase();
        String stringOperationFromReq = roleChangeRequest.getOperation().toUpperCase();

        checkExistingRole(stringRoleFromReq);
        checkExistingOperation(stringOperationFromReq);

        Role roleFromReq = Role.valueOf(stringRoleFromReq);
        Operation operationFromReq = Operation.valueOf(stringOperationFromReq);

        if (operationFromReq.equals(Operation.GRANT)) {
            grantOperation(user, roleFromReq);
        } else if (operationFromReq.equals(Operation.REMOVE)) {
            removeOperation(user, roleFromReq);
        }
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }

    private void grantOperation(User user, Role roleFromReq) {
        Set<Role> roles = user.getRoles();
        checkCompatibleRoles(roles, roleFromReq);
        roles.add(roleFromReq);
        user.setRoles(roles);
    }

    private void removeOperation(User user, Role roleFromReq) {
        checkAdminRole(roleFromReq);
        checkExistingRoleUser(user, roleFromReq);
        checkCountRolesUser(user);
        Set<Role> roles = user.getRoles();
        roles.remove(roleFromReq);
        user.setRoles(roles);
    }

    private void checkExistingRole(String roleFromReq) {
        if (Arrays.stream(Role.values()).noneMatch(role -> role.name().equals(roleFromReq)))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
    }


    private void checkExistingOperation(String operationFromReq) {
        if (Arrays.stream(Operation.values()).noneMatch(operation -> operation.name().equals(operationFromReq)))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found!");
    }


    private void checkExistingRoleUser(User user, Role roleFromReq) {
        if (!user.getRoles().contains(roleFromReq)) {
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

    private void checkCompatibleRoles(Set<Role> roles, Role roleFromReq) {
        if ((roles.contains(Role.ROLE_ACCOUNTANT) | roles.contains(Role.ROLE_USER)) & roleFromReq.equals(Role.ROLE_ADMINISTRATOR)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't add new role!");
        } else if (roles.contains(Role.ROLE_ADMINISTRATOR) & (roleFromReq.equals(Role.ROLE_ACCOUNTANT)) | roleFromReq.equals(Role.ROLE_USER)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't add new role!");
        }
    }
}
