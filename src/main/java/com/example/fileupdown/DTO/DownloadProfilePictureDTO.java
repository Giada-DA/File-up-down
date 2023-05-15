package com.example.fileupdown.DTO;


import com.example.fileupdown.entity.User;

public class DownloadProfilePictureDTO {

    private User user;
    private byte[] profileImage;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}
