package com.codemystack.apps.supagym.security;

import com.codemystack.apps.supagym.models.shared.Tenant;
import com.codemystack.apps.supagym.multitenancy.TenantIdentifierResolver;
import com.codemystack.apps.supagym.repositories.tenant.TenantRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class TenantAndJwtFilter extends OncePerRequestFilter {
    private final TenantRepository tenantRepository;
    private final TenantIdentifierResolver tenantIdentifierResolver;
    private final JwtService jwtService;
    private final TenantUserDetailsService userDetailsService;

    public TenantAndJwtFilter(
            TenantRepository tenantRepository,
            TenantIdentifierResolver tenantIdentifierResolver,
            JwtService jwtService,
            TenantUserDetailsService userDetailsService
    ) {
        this.tenantRepository = tenantRepository;
        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String tenantIdHeader = request.getHeader("X-Tenant-Id");
            String tenantSubdomain = request.getHeader("X-Tenant-Subdomain");

            System.out.println("TENANT: " + tenantIdHeader);

            Optional<Tenant> tenantOptional = Optional.empty();

            if(StringUtils.hasText(tenantIdHeader)) {
                System.out.println("Tenant has text...");
                UUID tenantId = UUID.fromString(tenantIdHeader);
                tenantOptional = tenantRepository.findByIdAndStatus_Code(tenantId, "active");
            } else if(StringUtils.hasText(tenantSubdomain)) {
                tenantOptional =  tenantRepository.findBySubdomainAndStatus_Code(tenantSubdomain, "active");
            }

            if(tenantOptional.isEmpty() &&
                !request.getRequestURI().startsWith("/api/public")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Tenant identifier is required");
                return;
            }

            Tenant tenant = tenantOptional.orElse(null);
            if(tenant == null) {
                System.out.println("Tenant not found");
            }

            if(tenant != null) {
                System.out.println("Tenant found..." + tenant.getSchemaName());
                tenantIdentifierResolver.setCurrentTenant(tenant.getSchemaName());
            }

            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                System.out.println("Token found..." + token);

                var claims = jwtService.parseToken(token);

                String email = claims.get("email", String.class);
                System.out.println("Email found..." + email);

                if(email != null) {
                    var userDetails = userDetailsService.loadUserByUsername(email);
                    var authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);
        } finally {
            tenantIdentifierResolver.clearCurrentTenant();
        }
    }
}
