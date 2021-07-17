package com.example.flightreservation.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

//@Entity
public class Role extends AbstractEntity implements GrantedAuthority { /*User can have multiple roles and multiple roles can be assigned to a user*/
    private String name;
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }

    @Override
    public String getAuthority() {
        return name;
    }
}
