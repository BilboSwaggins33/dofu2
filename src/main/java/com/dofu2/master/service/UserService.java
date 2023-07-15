package com.dofu2.master.service;

import com.dofu2.config.service.AbstractTableService;
import com.dofu2.master.model.User;
import com.dofu2.master.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class UserService extends AbstractTableService<User, Long> {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public Boolean existsById(BigInteger openId) {
        return this.userRepository.existsByOpenId(openId);
    }

    public User findByOpenId(BigInteger openId) {
        return this.userRepository.findUserByOpenId(openId);
    }
}
