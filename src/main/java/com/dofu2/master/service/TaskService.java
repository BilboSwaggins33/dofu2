package com.dofu2.master.service;

import com.dofu2.config.service.AbstractTableService;
import com.dofu2.master.model.Task;
import com.dofu2.master.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.List;

@Service
public class TaskService extends AbstractTableService<Task, Long> {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        super(taskRepository);
        this.taskRepository = taskRepository;
    }


    public List<Task> findTasksByDate(Date date) {
        return this.taskRepository.findAllByDueDate(date);
    }


}
