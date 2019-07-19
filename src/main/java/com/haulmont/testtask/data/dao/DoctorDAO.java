package com.haulmont.testtask.data.dao;

import com.haulmont.testtask.data.entity.Doctor;
import com.haulmont.testtask.data.entity.DoctorImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO extends BaseDAO<Doctor> {
    public DoctorDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(Doctor entity) throws SQLException {
        final String SQL = "INSERT INTO DOCTORS (NAME, SURNAME, PATRONYMIC, SPECIALTY) " +
                "VALUES (?, ?, ?, ?);";

        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setString(3, entity.getPatronymic());
        statement.setString(4, entity.getSpecialty());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public DoctorImpl getById(long id) throws SQLException {
        final String SQL = "SELECT * FROM DOCTORS WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setLong(1, id);

        ResultSet res = statement.executeQuery();
        if (res.next()) {

            DoctorImpl doctor = new DoctorImpl(res.getLong("ID"), res.getString("NAME"),
                    res.getString("SURNAME"), res.getString("PATRONYMIC"),
                    res.getString("SPECIALTY"));
            statement.close();
            return doctor;
        }

        statement.close();
        return null;
    }

    @Override
    public void update(Doctor entity) throws SQLException {
        final String SQL = "UPDATE DOCTORS " +
                "SET NAME = ?, SURNAME = ?, PATRONYMIC = ?, SPECIALTY = ?" +
                "WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setString(3, entity.getPatronymic());
        statement.setString(4, entity.getSpecialty());
        statement.setLong(5, entity.getId());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public List<Doctor> getAll() throws SQLException {
        final String SQL = "SELECT * FROM DOCTORS";

        List<Doctor> list = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL);
        ResultSet res = statement.executeQuery();

        while (res.next()) {
            DoctorImpl doctor = new DoctorImpl(res.getLong("ID"), res.getString("NAME"),
                    res.getString("SURNAME"), res.getString("PATRONYMIC"),
                    res.getString("SPECIALTY"));
            list.add(doctor);
        }

        statement.close();
        return list;
    }

    @Override
    public void delete(Doctor entity) throws SQLException {
        final String SQL = "DELETE FROM DOCTORS " +
                "WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setLong(1, entity.getId());
        statement.executeUpdate();
        statement.close();
    }
}
