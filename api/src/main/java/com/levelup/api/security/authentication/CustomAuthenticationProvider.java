package com.levelup.api.security.authentication;

import com.levelup.api.security.userdetails.LoginService;
import com.levelup.api.security.userdetails.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) authentication;
        UserDetails user = getUserDetail(customAuthenticationToken);

        perCheck(user);
        additionalAuthenticationChecks(user, (CustomAuthenticationToken) authentication);
        postCheck(user);

        return CustomAuthenticationToken.authenticated(
                user,
                authentication,
                user.getAuthorities()
        );
    }

    public boolean supports(Class<?> authentication) {
        return (CustomAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private UserDetails getUserDetail(CustomAuthenticationToken authentication) {
        String username = authentication.getName();

        try {
            return retrieveUser(username);
        }
        catch (UsernameNotFoundException ex) {
            log.debug("Failed to find user '" + username + "'");
            throw new BadCredentialsException("Bad credentials");
        }
    }

    private UserDetails retrieveUser(String username) throws AuthenticationException {
        try {
            User loadedUser = (User) loginService.loadUserByUsername(username);

            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        }
        catch (UsernameNotFoundException | InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    private void perCheck(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            log.debug("Failed to authenticate since user account is locked");
            throw new LockedException("User account is locked");
        }
        if (!user.isEnabled()) {
            log.debug("Failed to authenticate since user account is disabled");
            throw new DisabledException("User is disabled");
        }
        if (!user.isAccountNonExpired()) {
            log.debug("Failed to authenticate since user account has expired");
            throw new AccountExpiredException("User account has expired");
        }
    }

    private void postCheck(UserDetails user) {
        if (!user.isCredentialsNonExpired()) {
            log.debug("Failed to authenticate since user account credentials have expired");
            throw new CredentialsExpiredException("AbstractUserDetailsAuthenticationProvider.credentialsExpired, User credentials have expired");
        }
    }

    private void additionalAuthenticationChecks(UserDetails userDetails, CustomAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials, Bad credentials");
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            log.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials, Bad credentials");
        }
    }
}
