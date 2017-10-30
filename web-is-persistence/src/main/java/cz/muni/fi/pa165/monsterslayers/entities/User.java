package cz.muni.fi.pa165.monsterslayers.entities;

import cz.muni.fi.pa165.monsterslayers.entities.enums.RightsLevel;
import cz.muni.fi.pa165.monsterslayers.entities.enums.UserStatus;

import javax.persistence.*;
import java.util.Objects;

/**
 * Basic entity class - User
 *
 * @author David Kizivat
 */
@Entity
@Table(name = "UsersTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Lob
    private byte[] image;
    private String imageMimeType;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
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
        if (!(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        if (other.email == null) {
            if (this.email != null) {
                return false;
            }
        } else if (!this.email.equals(other.email)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(email);
        return hash;
    }
}
