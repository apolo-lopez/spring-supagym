package com.codemystack.apps.supagym.api.auth;

import com.codemystack.apps.supagym.api.auth.dto.AuthRequest;
import com.codemystack.apps.supagym.api.auth.dto.AuthResponse;
import com.codemystack.apps.supagym.api.auth.dto.JwtUserView;
import com.codemystack.apps.supagym.models.shared.Tenant;
import com.codemystack.apps.supagym.models.user.Role;
import com.codemystack.apps.supagym.models.user.User;
import com.codemystack.apps.supagym.multitenancy.TenantIdentifierResolver;
import com.codemystack.apps.supagym.repositories.tenant.TenantRepository;
import com.codemystack.apps.supagym.repositories.user.RoleRepository;
import com.codemystack.apps.supagym.repositories.user.UserRepository;
import com.codemystack.apps.supagym.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TenantRepository tenantRepository;
    private final TenantIdentifierResolver tenantIdentifierResolver;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            TenantRepository tenantRepository,
            TenantIdentifierResolver tenantIdentifierResolver,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tenantRepository = tenantRepository;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Optional<Tenant> tenantOptional = Optional.empty();
        System.out.println("Tenant: " + request.tenantId());
        if(request.tenantId() != null && !request.tenantId().isBlank()) {
            tenantOptional = tenantRepository.findByIdAndStatus_Code(
                    UUID.fromString(request.tenantId()),
                    "active");
        } else if(request.tenantSubdomain() != null
                && !request.tenantSubdomain().isBlank()) {
            tenantOptional = tenantRepository.findBySubdomainAndStatus_Code(
                    request.tenantSubdomain(),
                    "active"
            );
        }

        Tenant tenant = tenantOptional.orElseThrow(() -> new
                IllegalArgumentException("Tenant not found or inactive"));

        tenantIdentifierResolver.setCurrentTenant(tenant.getSchemaName());

        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
            );
            authenticationManager.authenticate(authToken);

            User user = userRepository.findByEmailAndDeletedAtIsNull(request.email())
                    .orElseThrow(() ->
                            new IllegalArgumentException("User not found"));
            Role role = roleRepository.findById(user.getRoleId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Role not found"));

            String token  = jwtService.generateToken(
                    user.getId(),
                    user.getEmail(),
                    role.getName(),
                    tenant.getId(),
                    tenant.getSchemaName()
            );

            JwtUserView userView = new JwtUserView(
                    user.getId(),
                    user.getEmail(),
                    user.getFullName(),
                    role.getName()
            );

            return ResponseEntity.ok(new AuthResponse(token, "Bearer", userView));
        } finally {
            tenantIdentifierResolver.clearCurrentTenant();
        }
    }
}
