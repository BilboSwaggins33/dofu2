package com.dofu2.master.controller;

import com.dofu2.config.controller.AbstractController;
import com.dofu2.config.service.AbstractTableService;
import com.dofu2.master.model.User;
import com.dofu2.master.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dofu2.config.security.TokenUtils;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController<User, Long> {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> save(String credential, HttpServletRequest request) throws GeneralSecurityException, IOException {
        if (TokenUtils.validateToken(credential)) {
            if (!userService.existsById(TokenUtils.getUserIdFromToken(credential))) {
                User user = new User(TokenUtils.getUserIdFromToken(credential), TokenUtils.getUserEmailFromToken(credential));
                this.userService.save(user);
            }
            return ResponseEntity.ok(TokenUtils.getClaimFromToken(credential));

        } else {
            System.out.println("Invalid ID token.");
            return ResponseEntity.ok(ResponseEntity.noContent().build());

        }

    }

}