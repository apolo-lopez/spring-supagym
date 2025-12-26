package com.codemystack.apps.supagym.api.users;

import com.codemystack.apps.supagym.api.users.dto.UserCreateRequest;
import com.codemystack.apps.supagym.api.users.dto.UserResponse;
import com.codemystack.apps.supagym.api.users.dto.UserUpdateRequest;
import com.codemystack.apps.supagym.models.branch.Branch;
import com.codemystack.apps.supagym.models.user.Role;
import com.codemystack.apps.supagym.models.user.User;
import com.codemystack.apps.supagym.repositories.branch.BranchRepository;
import com.codemystack.apps.supagym.repositories.user.RoleRepository;
import com.codemystack.apps.supagym.repositories.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(
            CurrentUserService currentUserService,
            UserRepository userRepository,
            RoleRepository roleRepository,
            BranchRepository branchRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.currentUserService = currentUserService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.branchRepository = branchRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        System.out.println(">>> /api/users/me handler");
        return ResponseEntity.ok(currentUserService.getCurrentUser());
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<List<UserResponse>> list() {
        List<UserResponse> users = userRepository.findAll()
                .stream()
                .filter(u -> u.getDeletedAt() == null)
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        User user = userRepository.findById(id)
                .filter(u -> u.getDeletedAt() == null)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return ResponseEntity.ok(toResponse(user));
    }

    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest request) {
        System.out.println(">>> /api/users/create");
        System.out.println("Request role: " + request.roleId());

        userRepository.findByEmailAndDeletedAtIsNull(request.email())
                .ifPresent(u -> {
                    throw new IllegalStateException("User already registered");
                });

        Role role = roleRepository.findById(request.roleId())
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        User user = new User();
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        
        if(request.fullName() != null) {
            user.setFullName(request.fullName());
        }
        if (request.phone() != null) {
            user.setPhone(request.phone());
        }

        user.setRoleId(role.getId());

        if (request.branchId() != null) {
            Branch branch = branchRepository.findById(request.branchId())
                    .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
            user.setBranchId(branch.getId());
        }
        if(request.dateOfBirth() != null) {
            user.setDateOfBirth(request.dateOfBirth());
        }
        if(request.emergencyContactName() != null) {
            user.setEmergencyContactName(request.emergencyContactName());
        }
        if(request.emergencyContactPhone() != null) {
            user.setEmergencyContactPhone(request.emergencyContactPhone());
        }
        if(request.fcmToken() != null) {
            user.setFcmToken(request.fcmToken());
        }
        if(request.gender() != null) {
            user.setGender(request.gender());
        }
        if(request.languagePreference() != null) {
            user.setLanguagePreference(request.languagePreference());
        }
        user.setIsActive(true);

        userRepository.save(user);

        return ResponseEntity.status(201).body(toResponse(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<UserResponse> update(
            @PathVariable UUID id,
            @RequestBody UserUpdateRequest request
    ) {
        User user = userRepository.findById(id)
                .filter(u -> u.getDeletedAt() == null)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.fullName() != null) {
            user.setFullName(request.fullName());
        }
        if (request.phone() != null) {
            user.setPhone(request.phone());
        }
        if (request.roleId() != null) {
            Role role = roleRepository.findById(request.roleId())
                    .orElseThrow(() -> new IllegalArgumentException("Role not found"));
            user.setRoleId(role.getId());
        }
        if (request.branchId() != null) {
            Branch branch = branchRepository.findById(request.branchId())
                            .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
            user.setBranchId(branch.getId());
        }
        if(request.dateOfBirth() != null) {
            user.setDateOfBirth(request.dateOfBirth());
        }
        if(request.emergencyContactName() != null) {
            user.setEmergencyContactName(request.emergencyContactName());
        }
        if(request.emergencyContactPhone() != null) {
            user.setEmergencyContactPhone(request.emergencyContactPhone());
        }
        if(request.fcmToken() != null) {
            user.setFcmToken(request.fcmToken());
        }
        if(request.gender() != null) {
            user.setGender(request.gender());
        }
        if(request.languagePreference() != null) {
            user.setLanguagePreference(request.languagePreference());
        }
        if (request.isActive() != null) {
            user.setIsActive(request.isActive());
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok(toResponse(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        User user = userRepository.findById(id)
                .filter(u -> u.getDeletedAt() == null)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setDeletedAt(LocalDateTime.now());
        user.setIsActive(false);
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }

    private UserResponse toResponse(User user) {
        Role role = roleRepository.findById(user.getRoleId())
                .orElse(null);
        String roleName = role != null ? role.getName() : null;

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                roleName,
                user.getIsActive()
        );
    }
}
