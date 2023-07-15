package com.dofu2.master.service;

import com.dofu2.config.service.AbstractTableService;
import com.dofu2.master.model.Task;
import com.dofu2.master.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

@Service
public class TaskService extends AbstractTableService<Task, Long> {

    private TaskRepository taskRepository;
    private UserService userService;
    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService) {
        super(taskRepository);
        this.taskRepository = taskRepository;
        this.userService = userService;
    }


    public List<Task> findTasksByDate(Date date) {
        return this.taskRepository.findAllByDueDate(date);
    }
    public List<Task> findTasksByDateAndUserId(Date date, BigInteger id) {
        if (this.userService.findByOpenId(id) != null) {
            return this.taskRepository.findAllByDueDateAndUserId(date, this.userService.findByOpenId(id).getId());
        } else {
            return null;
        }
    }

    public Task findTaskByIdandUserId(Long taskId, BigInteger userId) {
        if (this.userService.findByOpenId(userId) != null) {
            return this.taskRepository.findByIdAndUser(taskId, this.userService.findByOpenId(userId));
        } else {
            return null;
        }
    }


}
