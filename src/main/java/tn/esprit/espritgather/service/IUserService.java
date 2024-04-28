package tn.esprit.espritgather.service;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.User;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    public List<User> retrieveAllUsers();
    public User retrieveUser(Long idUser);
    public User addUser(User u);
    public void removeUser(Long idUser);
    public User modifyUser(User user);
    public User retrieveUserByMail(String mail);

    public User saveUser(User u, MultipartFile imageFile) throws IOException;


}