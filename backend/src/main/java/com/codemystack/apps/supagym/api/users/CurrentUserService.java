package com.codemystack.apps.supagym.api.users;

import com.codemystack.apps.supagym.api.users.dto.UserResponse;
import com.codemystack.apps.supagym.models.user.Role;
import com.codemystack.apps.supagym.models.user.User;
import com.codemystack.apps.supagym.repositories.user.RoleRepository;
import com.codemystack.apps.supagym.repositories.user.UserRepository;
import com.codemystack.apps.supagym.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CurrentUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUTH = " + authentication);
        if(authentication == null || !(authentication.getPrincipal() instanceof SecurityUser principal)) {
            throw new IllegalStateException("No authenticated user");
        }

        User user = principal.getDomainUser();
        System.out.println("CURRENT USER = " + user.getEmail());
        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalStateException("No role found"));

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                role.getName(),
                user.getIsActive()
        );
    }
}
