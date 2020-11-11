package com.openclassroom.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class ProBuddyUserDetails extends User implements UserDetails {

    private final String user;
    private String password;
    private ProBuddyUser proBuddyUser;
    private final Set<GrantedAuthority> authorities;
/*
    public ProBuddyUserDetails() {
        super("", "", null);
        authorities = new HashSet<>();
        user = "Unknown";
    }
*/
    public ProBuddyUserDetails(String user, String password, Collection<? extends GrantedAuthority> authorities) {
        super(user, password, authorities);
        this.user =  user;
        this.password = password;
        this.authorities = (Set<GrantedAuthority>) authorities;

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



}


