package com.levelup.job.security.userdetails;

import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ExceptionCode;
import com.levelup.job.infrastructure.jpaentity.AdminEntity;
import com.levelup.job.infrastructure.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminEntity admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.ADMIN_NOT_FOUND));

        return User.of(
                admin.getUsername(),
                admin.getPassword(),
                List.of(Role.ADMIN)
        );
    }
}
