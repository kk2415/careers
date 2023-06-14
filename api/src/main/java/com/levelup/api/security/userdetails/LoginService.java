package com.levelup.api.security.userdetails;

import com.levelup.api.security.userdetails.jpaentity.Admin;
import com.levelup.api.security.userdetails.repository.AdminRepository;
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
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자 계정입니다."));

        return User.of(
                admin.getUsername(),
                admin.getPassword(),
                List.of(Role.ADMIN)
        );
    }
}
