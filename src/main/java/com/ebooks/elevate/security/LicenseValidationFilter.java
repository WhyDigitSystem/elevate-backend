package com.ebooks.elevate.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ebooks.elevate.entity.CompanyVO;
import com.ebooks.elevate.repo.CompanyRepo;
import com.ebooks.elevate.service.LicenseService;

public class LicenseValidationFilter extends OncePerRequestFilter {

    private final LicenseService licenseService;
    private final CompanyRepo companyRepo;

    public LicenseValidationFilter(LicenseService licenseService,
                                   CompanyRepo companyRepo) {
        this.licenseService = licenseService;
        this.companyRepo = companyRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("🔥 LicenseValidationFilter HIT : " + request.getRequestURI());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUTH = " + auth);

        if (auth != null && auth.isAuthenticated()
                && auth.getPrincipal() instanceof UserPrincipal) {

            UserPrincipal user = (UserPrincipal) auth.getPrincipal();
            Long orgId = user.getOrgId();

            CompanyVO company = companyRepo.findById(orgId).orElse(null);

            if (company == null || company.getLicense() == null ||
                !licenseService.validateLicenseKey(company.getId(), company.getLicense())) {

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write(
                        "{\"message\":\"License expired. Please contact administrator.\"}"
                );
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
