package com.levelup.job.security.userdetails;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements UserDetails {

    private String username;
    private String password;
    private List<? extends GrantedAuthority> roles;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static User of(String username, String password, List<Role> roles) {
        return new User(
                username,
                password,
                roles.stream()
                        .map((role) -> new SimpleGrantedAuthority(role.toString()))
                        .toList());
    }

    public static User createMock() {
        return new User(
                null,
                null,
                null);
    }

    public String getRole() {
        return roles.get(0).toString();
    }
}
