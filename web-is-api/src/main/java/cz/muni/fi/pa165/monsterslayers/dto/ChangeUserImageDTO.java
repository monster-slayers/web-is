package cz.muni.fi.pa165.monsterslayers.dto;

/**
 * Data transfer object for changing image in user profile
 * 
 * @author Tomáš Richter
 */
public class ChangeUserImageDTO {
    private Long userId;
    private byte[] image;
    private String imageMimeType;

    public ChangeUserImageDTO(Long userId, byte[] image, String imageMimeType) {
        this.userId = userId;
        this.image = image;
        this.imageMimeType = imageMimeType;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
