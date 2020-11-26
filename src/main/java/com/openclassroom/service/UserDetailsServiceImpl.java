package com.openclassroom.service;

import com.openclassroom.model.ProBuddyRole;
import com.openclassroom.model.ProBuddyUser;
import com.openclassroom.model.ProBuddyUserDetails;
import com.openclassroom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
public class  UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @Override
    @Transactional
    public ProBuddyUserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        ProBuddyUser proBuddyUser = userRepository.findUserByEmail(email);

        if (proBuddyUser == null) {
        //    loginService.createLoginHistory(null, Timestamp.from(Instant.now()), false);

            throw new UsernameNotFoundException("Could not find user");

        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(ProBuddyRole proBuddyRole : proBuddyUser.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(proBuddyRole.getName()));
        }

        // save login history
        loginService.createLoginHistory(proBuddyUser, Timestamp.from(Instant.now()), true);


        //return new org.springframework.security.core.userdetails.User(proBuddyUser.getEmail(), proBuddyUser.getPassword(), grantedAuthorities);
        return new ProBuddyUserDetails(proBuddyUser.getEmail(), proBuddyUser.getPassword(), grantedAuthorities);
    }

}
