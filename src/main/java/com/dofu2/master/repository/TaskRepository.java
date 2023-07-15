package com.dofu2.master.repository;

import com.dofu2.master.model.Task;
import com.dofu2.master.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByDueDate(Date dueDate);

    List<Task> findAllByDueDateAndUserId(Date dueDate, Long id);

    Task findByIdAndUser(long id, User user);
}
