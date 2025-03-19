package org.example.seguroform.Security;

import org.example.seguroform.data.UsuarioRepository;
import org.example.seguroform.logic.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            Usuario usr = usuarioRepository.findById(username).get();
            return new UserDetailsImp(usr);
        } catch (Exception e){
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
    }
}
