package com.openclassroom.service;

import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
   private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        ProBuddyUser proBuddyUser = userRepository.findByEmail(email);

        if (proBuddyUser == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(ProBuddyRole proBuddyRole : proBuddyUser.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(proBuddyRole.getName()));
        }


        return new org.springframework.security.core.userdetails.User(proBuddyUser.getEmail(), proBuddyUser.getPassword(), grantedAuthorities);
    }

}
