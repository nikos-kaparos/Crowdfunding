package com.example.SpirngSecEx.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
public class UserPrincipal implements UserDetails {

    private Users user;
    private Supporter supporter;


    public UserPrincipal(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (user.getRole().equals("admin")) {
            return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
        } else if (user.getRole().equals("supporter")) {
            return Collections.singletonList(new SimpleGrantedAuthority("SUPPORTER"));
        }else if (user.getRole().equals("seller")) {
            return Collections.singletonList(new SimpleGrantedAuthority("SELLER"));
        }
            return Collections.singletonList(new SimpleGrantedAuthority("USERS"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
}
