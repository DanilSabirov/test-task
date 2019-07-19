package com.haulmont.testtask.data.dao;

import com.haulmont.testtask.data.entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO extends BaseDAO<Prescription> {
    private Connection connection;

    public PrescriptionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(Prescription entity) throws SQLException {
        final String SQL = "INSERT INTO PRESCRIPTIONS (DESCRIPTION, PATIENT_ID, DOCTOR_ID, CREATION_DATE, " +
                "DAYS_VALIDITY, PRIORITY) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setString(1,entity.getDescription());
        statement.setLong(2, entity.getPatient().getId());
        statement.setLong(3, entity.getDoctor().getId());
        statement.setDate(4, Date.valueOf(entity.getCreationDate()));
        statement.setInt(5, entity.getDaysValidity());
        statement.setString(6, entity.getPriority().toString());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public PrescriptionImpl getById(long id) throws SQLException {
        final String SQL = "SELECT * FROM PRESCRIPTION " +
                "WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setLong(1, id);

        ResultSet res = statement.executeQuery();
        if (res.next()) {
            String description = res.getString("DESCRIPTION");
            long patientId = res.getLong("PATIENT_ID");
            long doctorId = res.getLong("DOCTOR_ID");
            LocalDate creationDate = res.getDate("CREATION_DATE").toLocalDate();
            int daysValidity = res.getInt("DAYS_VALIDITY");
            Priority priority = Priority.valueOf(res.getString("PRIORITY"));

            Patient patient = new PatientDAO(connection).getById(patientId);
            Doctor doctor = new DoctorDAO(connection).getById(doctorId);

            statement.close();
            return new PrescriptionImpl(id, description, patient, doctor, creationDate, daysValidity, priority);
        }

        statement.close();
        return null;
    }

    @Override
    public void update(Prescription entity) throws SQLException {
        final String SQL = "UPDATE PRESCRIPTION " +
                "SET DESCRIPTION = ?, PATIENT_ID = ?, DOCTOR_ID = ?, " +
                "CREATION_DATE = ?, DAYS_VALIDITY = ?, PRIORITY = ? " +
                "WHERE ID = ?";

        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setString(1, entity.getDescription());
        statement.setLong(2, entity.getPatient().getId());
        statement.setLong(3, entity.getDoctor().getId());
        statement.setDate(4, Date.valueOf(entity.getCreationDate()));
        statement.setInt(5, entity.getDaysValidity());
        statement.setString(6, entity.getPriority().toString());
        statement.setLong(7, entity.getId());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public List<Prescription> getAll() throws SQLException {
        String SQL = "SELECT * FROM PRESCRIPTION";
        List<PrescriptionImpl> list = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL);

        ResultSet res = statement.executeQuery();

        PatientDAO patientDAO = new PatientDAO(connection);
        DoctorDAO doctorDAO = new DoctorDAO(connection);

        while (res.next()) {
            long id = res.getLong("ID");
            String description = res.getString("DESCRIPTION");
            long patientId = res.getLong("PATIENT_ID");
            long doctorId = res.getLong("DOCTOR_ID");
            LocalDate creationDate = res.getDate("CREATION_DATE").toLocalDate();
            int daysValidity = res.getInt("DAYS_VALIDITY");
            Priority priority = Priority.valueOf(res.getString("PRIORITY"));

            Patient patient = patientDAO.getById(patientId);
            Doctor doctor = doctorDAO.getById(doctorId);

            list.add(new PrescriptionImpl(id, description, patient, doctor, creationDate, daysValidity, priority));
        }

        statement.close();
        return null;
    }

    @Override
    public void delete(Prescription entity) throws SQLException {
        final String SQL = "DELETE FROM PRESCRIPTIONS " +
                "WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setLong(1, entity.getId());
        statement.executeUpdate();
        statement.close();
    }
}
