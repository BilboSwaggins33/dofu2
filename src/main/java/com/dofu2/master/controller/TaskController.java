package com.dofu2.master.controller;

import com.dofu2.config.controller.AbstractController;
import com.dofu2.config.util.Constants;
import com.dofu2.master.model.Task;
import com.dofu2.master.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController extends AbstractController<Task, Long> {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        super(taskService);
        this.taskService = taskService;
    }


    @GetMapping(value = "/findByDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findTasksByDate(@RequestParam(name="date") String date, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DF_YYYY_MM_DD);
        SimpleDateFormat targetsdf = new SimpleDateFormat(Constants.DefaultDateFormat);
        try {
            List<Task> results = this.taskService.findTasksByDate(new Date(sdf.parse(date).getTime()));
            if (results == null)
                return ResponseEntity.noContent().build();
            else
                return ResponseEntity.ok(results);

        } catch (ParseException pe) {
            throw new RuntimeException(pe);
        }


    }

}
