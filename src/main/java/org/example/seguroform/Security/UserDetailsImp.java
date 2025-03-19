package org.example.seguroform.Security;

import org.example.seguroform.logic.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImp implements UserDetails {   //implements UserDetails
    private Usuario usuario;

    public UserDetailsImp(Usuario usr) {
        this.usuario = usuario;
    }
    public Usuario getUsuario() {return usuario;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> autorities = new ArrayList<>();
        autorities.add(new SimpleGrantedAuthority(usuario.getRol()));
        return autorities;
    }

    @Override
    public String getPassword() {
        return usuario.getClave();
    }

    @Override
    public String getUsername() {
        return usuario.getId();
    }

    @Override
    public boolean isAccountNonExpired() {return true; }

    @Override
    public boolean isAccountNonLocked() {return true; }

    @Override
    public boolean isCredentialsNonExpired() {return true; }

    @Override
    public boolean isEnabled() {
        return true;
    }
}