package cz.muni.fi.pa165.monsterslayers.dto.user;

import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;

import java.util.Objects;

/**
 * Basic data transfer object for user
 *
 * @author Tomáš Richter
 */
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    //hash of the password
    private String password;

    private byte[] image;
    private String imageMimeType;

    private RightsLevel rightsLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageMimeType() {
        return imageMimeType;
    }

    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }

    public RightsLevel getRightsLevel() {
        return rightsLevel;
    }

    public void setRightsLevel(RightsLevel rightsLevel) {
        this.rightsLevel = rightsLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDTO)) {
            return false;
        }
        UserDTO other = (UserDTO) o;
        return (Objects.equals(this.email, other.getEmail()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(email);
        return hash;
    }
}
