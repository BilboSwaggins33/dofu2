package com.dofu2.master.model;

import com.dofu2.config.util.Constants;
import com.dofu2.config.util.JsonDateDeserializer;
import com.dofu2.config.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.sql.Date;


@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
//    @JsonFormat(pattern = Constants.DF_YYYY_MM_DD)
    @Column(name = "created_at")
    private Date createdAt;

    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
//    @JsonFormat(pattern = Constants.DF_YYYY_MM_DD)
    @Column(name = "due_date")
    private Date dueDate;

    @Column(name="complete")
    private Boolean complete;


}
