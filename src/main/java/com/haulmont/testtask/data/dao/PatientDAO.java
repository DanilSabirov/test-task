package com.haulmont.testtask.data.dao;

import com.haulmont.testtask.data.entity.Patient;
import com.haulmont.testtask.data.entity.PatientImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO extends BaseDAO<Patient> {
    public PatientDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(Patient entity) throws SQLException {
        final String SQL = "INSERT INTO PATIENTS (NAME, SURNAME, PATRONYMIC, PHONE_NUMBER) " +
                "VALUES(?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setString(3, entity.getPatronymic());
        statement.setString(4, entity.getPhoneNumber());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Patient getById(long id) throws SQLException {
        final String SQL = "SELECT * FROM PATIENTS WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setLong(1, id);

        ResultSet res = statement.executeQuery();
        if (res.next()) {
            PatientImpl patient = new PatientImpl(res.getLong("ID"), res.getString("NAME"),
                    res.getString("SURNAME"), res.getString("PATRONYMIC"),
                    res.getString("PHONE_NUMBER"));
            statement.close();
            return patient;
        }

        statement.close();
        return null;
    }

    @Override
    public void update(Patient entity) throws SQLException {
        final String SQL = "UPDATE PATIENTS " +
                "SET NAME = ?, SURNAME = ?, PATRONYMIC = ?, PHONE_NUMBER = ? " +
                "WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setString(3, entity.getPatronymic());
        statement.setString(4, entity.getPhoneNumber());
        statement.setLong(5, entity.getId());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public List<Patient> getAll() throws SQLException {
        final String SQL = "SELECT * FROM PATIENTS";
        List<Patient> list = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            list.add(new PatientImpl(res.getLong("ID"), res.getString("NAME"),
                    res.getString("SURNAME"), res.getString("PATRONYMIC"),
                    res.getString("PHONE_NUMBER")));
        }

        statement.close();
        return list;
    }

    @Override
    public void delete(Patient entity) throws SQLException {
        final String SQL = "DELETE FROM PATIENTS " +
                "WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setLong(1, entity.getId());
        statement.executeUpdate();
        statement.close();
    }

    public boolean canDelete(Patient patient) throws SQLException {
        final String SQL = "SELECT COUNT(*) as CNT " +
                "FROM (SELECT ID FROM PRESCRIPTIONS WHERE PATIENT_ID = ?)";
        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setLong(1, patient.getId());

        ResultSet res = statement.executeQuery();
        res.next();
        int cnt = res.getInt("CNT");
        return cnt == 0;
    }
}
