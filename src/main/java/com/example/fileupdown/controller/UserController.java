package com.example.fileupdown.controller;

import com.example.fileupdown.DTO.DownloadProfilePictureDTO;
import com.example.fileupdown.entity.User;
import com.example.fileupdown.repository.UserRepository;
import com.example.fileupdown.service.FileStorageService;
import com.example.fileupdown.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public User create(@RequestBody User user){
        user.setId(null);
        return userRepository.saveAndFlush(user);
    }

    @PostMapping("/{id}/profile")
    public User uploadProfileImagine(@PathVariable Long id, @RequestParam MultipartFile profilePicture) throws Exception {
        return userService.uploadProfilePicture(id, profilePicture);
    }

    @GetMapping
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getOne(@PathVariable Long id){
        return userRepository.findById(id);
    }

    @GetMapping("/{id}/profile")
    public @ResponseBody byte[] getProfileImagine(@PathVariable Long id, HttpServletResponse response) throws Exception{
        DownloadProfilePictureDTO downloadProfileDTO = userService.downloadProfilePicture(id);
        String fileName = downloadProfileDTO.getUser().getProfilePicture();
        if (fileName == null) throw new Exception("User does not have profile picture");
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension){
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content-Disposition", "attachment; filename= \""+fileName + "\"");
        return downloadProfileDTO.getProfileImage();
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable Long id){
        user.setId(id);
        return userRepository.saveAndFlush(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        userService.remove(id);

    }
}
