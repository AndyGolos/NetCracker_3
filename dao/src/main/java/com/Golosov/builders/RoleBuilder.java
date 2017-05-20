package com.Golosov.builders;

import com.Golosov.entities.Role;
import com.Golosov.entities.Type;
import com.Golosov.entities.User;

import java.util.Set;

/**
 * Created by Андрей on 17.05.2017.
 */
public class RoleBuilder {

    private RoleBuilder(){
    }

    private final Role role = new Role();

    public static class RoleEntityBuilder{

        private Role role = new Role();

        public RoleEntityBuilder id(long id){
            this.role.setId(id);
            return this;
        }

        public RoleEntityBuilder role(String role){
            this.role.setRole(role);
            return this;
        }

        public RoleEntityBuilder users(Set<User> users){
            this.role.setUsers(users);
            return this;
        }

        public Role build(){
            return new RoleBuilder().createType(this);
        }
    }

    private Role createType(RoleEntityBuilder builder) {
        this.role.setId(builder.role.getId());
        this.role.setRole(builder.role.getRole());
        this.role.setUsers(builder.role.getUsers());
        return this.role;
    }
}
