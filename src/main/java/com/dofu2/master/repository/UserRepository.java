package com.dofu2.master.repository;

import com.dofu2.master.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByOpenId(BigInteger openId);

    Boolean existsByOpenId(BigInteger openId);
}
