package tn.esprit.espritgather.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.repo.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static tn.esprit.espritgather.control.EventRestController.uploadDirectory;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }
    public User retrieveUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    public User addUser(User u) {
        return userRepository.save(u);
    }
    public void removeUser(Long userId) {
        userRepository.deleteById(userId);
    }
    public User modifyUser(User user) {
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        return userRepository.save(user);
    }

    @Override
    public User retrieveUserByMail(String mail) {
        return userRepository.findBymail(mail).orElse(null);
    }

    public User saveUser(User u, MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            String fileName = saveImage(imageFile);
            u.setImagePath(fileName);
        }
        return userRepository.save(u);
    }


    private String saveImage(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();
        String filePath = uploadDirectory + File.separator + fileName;
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(filePath);
        Files.write(path, bytes);
        return fileName;
    }
}