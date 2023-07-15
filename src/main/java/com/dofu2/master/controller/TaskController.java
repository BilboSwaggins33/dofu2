package com.dofu2.master.controller;

import com.dofu2.config.controller.AbstractController;
import com.dofu2.config.security.AuthoritiesConstants;
import com.dofu2.config.security.TokenUtils;
import com.dofu2.config.util.Constants;
import com.dofu2.config.util.StringUtils;
import com.dofu2.master.model.Task;
import com.dofu2.master.model.User;
import com.dofu2.master.service.TaskService;
import com.dofu2.master.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController extends AbstractController<Task, Long> {
    private TaskService taskService;
    private UserService userService;
    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        super(taskService);
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody Task object, HttpServletRequest request) throws GeneralSecurityException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(AuthoritiesConstants.AUTHORIZATION);
        authToken = StringUtils.removeStart(authToken, "Bearer").trim();

        BigInteger userOpenId = TokenUtils.getUserIdFromToken(authToken);
        if (this.userService.existsById(userOpenId)) {
            User user = this.userService.findByOpenId(userOpenId);
            object.setUser(user);
            Task result = this.taskService.save(object);
            return ResponseEntity.ok(result);

        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/findByDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findTasksByDateAndUserId(@RequestParam(name = "date") String date, HttpServletRequest request) throws GeneralSecurityException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DF_YYYY_MM_DD);
        SimpleDateFormat targetsdf = new SimpleDateFormat(Constants.DefaultDateFormat);
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(AuthoritiesConstants.AUTHORIZATION);
        authToken = StringUtils.removeStart(authToken, "Bearer").trim();

        BigInteger userId = TokenUtils.getUserIdFromToken(authToken);
        if (userId != null) {
            try {
                List<Task> results = this.taskService.findTasksByDateAndUserId(new Date(sdf.parse(date).getTime()), userId);
                if (results == null)
                    return ResponseEntity.noContent().build();
                else
                    return ResponseEntity.ok(results);

            } catch (ParseException pe) {
                throw new RuntimeException(pe);
            }
        } else {
            return ResponseEntity.noContent().build();
        }

    }

}
