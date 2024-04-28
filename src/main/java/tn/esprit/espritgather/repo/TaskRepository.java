package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.espritgather.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findTasksByEventIdEvent(Long eventId);
}