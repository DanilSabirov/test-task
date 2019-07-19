package com.haulmont.testtask.data.dao;

import com.haulmont.testtask.data.entity.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T extends Entity> {
    protected Connection connection;

    public BaseDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract void insert(T entity) throws SQLException;

    public abstract T getById(long id) throws SQLException;

    public abstract void update(T entity) throws SQLException;

    public abstract void delete(T entity) throws SQLException;

    public abstract List<T> getAll() throws SQLException;
}
