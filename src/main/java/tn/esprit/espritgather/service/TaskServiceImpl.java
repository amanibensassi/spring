package tn.esprit.espritgather.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.espritgather.entity.Event;
import tn.esprit.espritgather.entity.Task;
import tn.esprit.espritgather.entity.User;
import tn.esprit.espritgather.repo.EventRepository;
import tn.esprit.espritgather.repo.TaskRepository;
import tn.esprit.espritgather.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements ITaskService {
    TaskRepository taskRepository;

    private  final EventRepository eventRepository;

    private  final UserRepository userRepository;


    public List<Task> retrieveAllTasks() {
        return taskRepository.findAll();
    }

    public Task retrieveTask(Long taskId) {
        return taskRepository.findById(taskId).get();
    }



    public void removeTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task modifyTask(Task task) {
        return taskRepository.save(task);
    }


    @Override
    public Task createTask(Task task, Long eventId) {
        // Retrieve the event from the database
       /* Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));*/
        Event event = new Event();
        event.setIdEvent(eventId);
        // Set the task's event
        task.setEvent(event);
        return taskRepository.save(task);
    }


    @Override
    public List<Task> retrieveTasksByEvent(Long eventId) {
        return taskRepository.findTasksByEventIdEvent(eventId);
    }

    @Override
    public User retrieveUser(Long userId) {
        return userRepository.findByIdUser(userId);

    }


}