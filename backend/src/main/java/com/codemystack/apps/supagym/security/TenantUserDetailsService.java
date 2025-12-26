package com.codemystack.apps.supagym.security;

import com.codemystack.apps.supagym.models.user.Role;
import com.codemystack.apps.supagym.models.user.User;
import com.codemystack.apps.supagym.repositories.user.RoleRepository;
import com.codemystack.apps.supagym.repositories.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TenantUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public TenantUserDetailsService(UserRepository userRepository,
                                    RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new UsernameNotFoundException("Role not found"));

        return new SecurityUser(user, role.getName());
    }
}
