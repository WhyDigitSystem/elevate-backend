package com.ebooks.elevate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ebooks.elevate.repo.CompanyRepo;
import com.ebooks.elevate.service.LicenseService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

	/* ===================== FILTER BEANS ===================== */

	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	@Bean
	public LicenseValidationFilter licenseValidationFilter(LicenseService licenseService, CompanyRepo companyRepo) {
		return new LicenseValidationFilter(licenseService, companyRepo);
	}

	@Bean
	public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	/* ===================== SECURITY CONFIG ===================== */

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, TokenAuthenticationFilter tokenAuthenticationFilter,
			LicenseValidationFilter licenseValidationFilter) throws Exception {

		http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
				.disable().formLogin().disable().httpBasic().disable().exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint()).and().authorizeRequests()

				/* ===== STATIC RESOURCES ===== */
				.antMatchers("/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
						"/**/*.html", "/**/*.css", "/**/*.js")
				.permitAll()

				/* ===== SWAGGER ===== */
				.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
						"/swagger-ui.html", "/swagger-ui/*")
				.permitAll()

				/* ===== AUTH & PUBLIC APIS ===== */
				.antMatchers("/api/auth/signup", "/api/auth/login", "/api/auth/logout", "/api/auth/getRefreshToken",
						"/api/auth/**", "/api/commonmaster/**", "/api/master/**", "/api/user/**", "/images/**",
						"/api/transaction/**", "/api/GlobalParam/**", "/api/arreceivable/**", "/api/payable/**",
						"/api/businesscontroller/**", "/api/documentType/**", "/api/taxInvoice/**",
						"/api/arapAdjustments/**", "/api/companycontroller/**", "/api/costdebitnote/**",
						"/api/costInvoice/**", "/api/irnCreditNote/**", "/api/excelfileupload/**",
						"/api/clientcompanycontroller/**", "/api/ticketcontroller/**", "/api/trailBalanceController/**",
						"/api/eLReportController/**", "/api/MonthlyProcess/**", "/api/Budget/**", "/api/license/**",
						"/api/files/**", "/api/email/**")
				.permitAll()

				/* ===== PROTECTED APIS ===== */
				.antMatchers("/api/**").hasAnyRole("USER", "GUEST_USER")

				.anyRequest().authenticated();
		/* ===================== FILTER ORDER ===================== */

		// 1️⃣ JWT authentication (sets SecurityContext)
		http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		// 2️⃣ License validation (uses SecurityContext)
				http.addFilterAfter(licenseValidationFilter, UsernamePasswordAuthenticationFilter.class);
		

		return http.build();
	}
}
