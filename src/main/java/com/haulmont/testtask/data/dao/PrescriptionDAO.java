package com.haulmont.testtask.data.dao;

import com.haulmont.testtask.data.entity.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class PrescriptionDAO extends BaseDAO<Prescription> {
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
        statement.setString(6, entity.getPriority().name());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Prescription getById(long id) throws SQLException {
        final String SQL = "SELECT * FROM PRESCRIPTIONS " +
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
        final String SQL = "UPDATE PRESCRIPTIONS " +
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
        final String SQL = "SELECT * FROM PRESCRIPTIONS";
        List<Prescription> list = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL);

        ResultSet res = statement.executeQuery();

        PatientDAO patientDAO = new PatientDAO(connection);
        DoctorDAO doctorDAO = new DoctorDAO(connection);

        matchPrescriptions(list, res, patientDAO, doctorDAO);

        statement.close();
        return list;
    }

    private void matchPrescriptions(List<Prescription> list, ResultSet res, PatientDAO patientDAO, DoctorDAO doctorDAO) throws SQLException {
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
    }

    public List<Prescription> getAll(String subString, Patient patient, Priority priority) throws SQLException {
        final String SQL = "SELECT * FROM PRESCRIPTIONS WHERE DESCRIPTION like (?) AND PATIENT_ID LIKE (?) AND PRIORITY LIKE (?)";
        List<Prescription> list = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setString(1, "%" + subString + "%");
        if (patient != null) {
            statement.setLong(2, patient.getId());
        }
        else {
            statement.setString(2, "%");
        }
        if (priority != null) {
            statement.setString(3, priority.name());
        }
        else {
            statement.setString(3, "%");
        }

        ResultSet res = statement.executeQuery();

        PatientDAO patientDAO = new PatientDAO(connection);
        DoctorDAO doctorDAO = new DoctorDAO(connection);

        matchPrescriptions(list, res, patientDAO, doctorDAO);

        statement.close();
        return list;
    }

    public Map<Doctor, Integer>  getDoctorsCountPrescriptions() throws SQLException {
        final String SQL = "SELECT DOCTOR_ID, COUNT(*) as CNT " +
                "FROM PRESCRIPTIONS " +
                "GROUP BY DOCTOR_ID";

        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(SQL);

        List<Doctor> doctors = new DoctorDAO(connection).getAll();
        Map<Long, Integer> doctorIDPrescriptions = new TreeMap<>();

        while (res.next()) {
            doctorIDPrescriptions.put(res.getLong("DOCTOR_ID"), res.getInt("CNT"));
        }

        Map<Doctor, Integer> doctorsCountPrescriptions = new TreeMap<>();

        for (Doctor doctor: doctors) {
            int cnt = doctorIDPrescriptions.get(doctor.getId());
            doctorsCountPrescriptions.put(doctor, cnt);
        }

        return doctorsCountPrescriptions;
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
