package com.dofu2.config.service;

import com.dofu2.config.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public abstract class AbstractTableService<T, ID extends Serializable> {

    private final JpaRepository<T, ID> repository;
    private Class<T> type;

    public AbstractTableService(JpaRepository<T,ID> repository) {
        this.repository = repository;
    }

    public T save(T object) {
        T result = null;
        try {
            result = repository.save(object);

        } catch(Exception e) {
            String json = null;
            try {
                json = new ObjectMapper().writeValueAsString(object);
            } catch (JsonProcessingException jsonProcessingException) {
            }
            String error = StringUtil.getExceptionAsString(e);
            String message = "Error saving " + object.getClass().getName();
            if (error.contains("Unknown column ")) {
                String field = error.substring(error.indexOf("Unknown column ") + "Unknown column '".length());
                field = field.substring(0, field.indexOf("'"));
                String table = object.getClass().getName();
                table = table.substring(table.lastIndexOf(".") + 1).toLowerCase();
                message += "\n\n#######################\nDid you add this field, " + field + ", to the " + table + "_audit table?\n#######################\n\n";
            }
            if (json != null) {
                message += "\n with values = \n" + json + "\n";
            }
            message += "With error = " + e.getMessage();
            log.error(message);
            throw e;
        }
        return result;
    }

    public List<T> save(List<T> list) {
        return repository.saveAll(list);
    }

    public void delete(ID key) {
        repository.deleteById(key);
    }

    public void deleteInBatch(List<T> list) {
        repository.deleteAll(list);
    }

    public void deleteInBatchById(List<ID> list) {
        repository.deleteAllById(list);
    }

    public T findOne(ID id) {
        if(id == null) {
            return null;
        }
        Optional<T> optionalT = repository.findById(id);
        return optionalT.orElse(null);
    }

    public List<T> findAll() {
        return repository.findAll();
    }




}
