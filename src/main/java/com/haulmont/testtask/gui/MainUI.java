package com.haulmont.testtask.gui;

import com.haulmont.testtask.data.ConnectionManager;
import com.haulmont.testtask.data.dao.DoctorDAO;
import com.haulmont.testtask.data.dao.PatientDAO;
import com.haulmont.testtask.gui.tabs.DoctorsTab;
import com.haulmont.testtask.gui.tabs.PatientsTab;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Connection;
import java.sql.SQLException;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private final HorizontalLayout main = new HorizontalLayout();

    private final TabSheet tabs = new TabSheet();

    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    private Connection connection = null;

    private PatientDAO patientDAO;

    private DoctorDAO doctorDAO;

    @Override
    protected void init(VaadinRequest request) {
        try {
            initPersistence();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        //test

        initMain();

        //test

        setContent(main);
    }

    private void initMain() {
        PatientsTab patientsTab = new PatientsTab(this, patientDAO);
        DoctorsTab doctorsTab = new DoctorsTab(this, doctorDAO);
        tabs.addTab(patientsTab, "Patients");
        tabs.addTab(doctorsTab, "Doctors");


        main.addComponents(tabs);
        //main.setExpandRatio(patientsTab, 1);
        main.setMargin(true);
        main.setSpacing(true);
        main.setSizeFull();
    }

    private void initPersistence() throws SQLException{
        connection = connectionManager.getConnection();
        patientDAO = new PatientDAO(connection);
        doctorDAO = new DoctorDAO(connection);
    }
}