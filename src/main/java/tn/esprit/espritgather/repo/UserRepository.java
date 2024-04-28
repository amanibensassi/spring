package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.espritgather.entity.User;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User,Long> {


    User findByIdUser(Long userId);
    Optional<User> findBymail(String mail);
}