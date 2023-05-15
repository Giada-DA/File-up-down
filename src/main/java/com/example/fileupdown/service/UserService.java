package com.example.fileupdown.service;

import com.example.fileupdown.DTO.DownloadProfilePictureDTO;
import com.example.fileupdown.entity.User;
import com.example.fileupdown.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileStorageService fileStorageService;

    public User getUser(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) throw new Exception("User not present");
        return optionalUser.get();
    }

    public User uploadProfilePicture(Long userId, MultipartFile profilePicture) throws Exception {
        User user = getUser(userId);
        if (user.getProfilePicture() != null){
            fileStorageService.remove(user.getProfilePicture());
        }
        String fileName = fileStorageService.upload(profilePicture);
        user.setProfilePicture(fileName);
        return userRepository.saveAndFlush(user);
    }

    public DownloadProfilePictureDTO downloadProfilePicture(Long userId) throws Exception {
        User user = getUser(userId);
        DownloadProfilePictureDTO dto = new DownloadProfilePictureDTO();
        dto.setUser(user);
        if (user.getProfilePicture() == null) return dto;
        byte[] profilePictureBytes = fileStorageService.download(user.getProfilePicture());
        dto.setProfileImage(profilePictureBytes);
        return dto;
    }

    public void remove(Long userId) throws Exception {
        User user = getUser(userId);
        if (user.getProfilePicture() != null){
            fileStorageService.remove(user.getProfilePicture());
        }
        userRepository.deleteById(userId);
    }
}
