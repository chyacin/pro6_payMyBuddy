package com.openclassroom.repositories;

import com.openclassroom.model.ProBuddyRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<ProBuddyRole, Integer> {

    ProBuddyRole findByName(String name);
}
