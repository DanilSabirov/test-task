package com.haulmont.testtask.database.dao;

import com.haulmont.testtask.database.entity.BaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T extends BaseEntity> {
    protected Connection connection;

    public BaseDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract void insert(T entity) throws SQLException;

    public abstract T getById(long id) throws SQLException;

    public abstract void update(T entity) throws SQLException;

    public void delete(T entity) throws SQLException {
        final String SQL = "DELETE FROM DOCTORS " +
                "WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setLong(1, entity.getId());
        statement.executeUpdate();
        statement.close();
    }

    public abstract List<T> getAll() throws SQLException;
}
