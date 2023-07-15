package com.dofu2.master.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "open_id")
    private BigInteger openId;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Task> tasks = new HashSet<>();

    public User() {}

    public User(BigInteger openId, String email) {
        this.setOpenId(openId);
        this.setEmail(email);
    }
}
