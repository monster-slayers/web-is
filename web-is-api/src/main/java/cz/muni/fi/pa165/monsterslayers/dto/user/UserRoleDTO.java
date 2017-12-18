package cz.muni.fi.pa165.monsterslayers.dto.user;

import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;

import java.util.List;

public class UserRoleDTO {
    private String email;
    private List<RightsLevel> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RightsLevel> getRoles() {
        return roles;
    }

    public void setRoles(List<RightsLevel> roles) {
        this.roles = roles;
    }
}
