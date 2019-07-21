package com.haulmont.testtask.gui.tabs;

import com.haulmont.testtask.data.dao.DoctorDAO;
import com.haulmont.testtask.data.dao.PatientDAO;
import com.haulmont.testtask.data.dao.PrescriptionDAO;
import com.haulmont.testtask.data.entity.Prescription;
import com.haulmont.testtask.data.entity.PrescriptionImpl;
import com.haulmont.testtask.gui.CreateWindow;
import com.haulmont.testtask.gui.EditWindow;
import com.haulmont.testtask.gui.StatisticWindow;
import com.haulmont.testtask.gui.forms.FiltersForms;
import com.haulmont.testtask.gui.forms.PrescriptionForm;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class PrescriptionTab extends VerticalLayout implements Observer {

    private UI mainUI;

    private Grid prescriptionsGrid = new Grid();

    private PrescriptionDAO prescriptionDAO;

    private List<Prescription> prescriptions;

    private FiltersForms filters;

    private PatientDAO patientDAO;

    private DoctorDAO doctorDAO;

    public PrescriptionTab(UI mainUI, PrescriptionDAO prescriptionDAO, PatientDAO patientDAO, DoctorDAO doctorDAO) {
        this.mainUI = mainUI;
        this.prescriptionDAO = prescriptionDAO;
        this.patientDAO = patientDAO;
        this.doctorDAO = doctorDAO;

        HorizontalLayout buttonLayout = getButtons();
        initPrescriptionsGrid();

        filters = new FiltersForms(patientDAO, this);

        addComponents(buttonLayout, filters, prescriptionsGrid);
        setExpandRatio(prescriptionsGrid, 1);
        setMargin(true);
        setSpacing(true);
        setSizeFull();

        update();
    }

    private void initPrescriptionsGrid() {
        prescriptionsGrid.setSizeFull();
        prescriptionsGrid.setColumns("description", "patient", "doctor", "creationDate", "priority", "daysValidity");
    }

    private HorizontalLayout getButtons() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button edit = new Button("Edit");
        edit.addClickListener(event -> {
            Prescription prescription = (Prescription) prescriptionsGrid.getSelectedRow();
            if (prescription != null) {
                try {
                    mainUI.addWindow(new EditWindow(this, new PrescriptionForm(patientDAO, doctorDAO), prescription, prescriptionDAO));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Button delete = new Button("Delete");
        delete.addClickListener(event -> {
            if (prescriptionsGrid.getSelectedRow() != null) {
                Prescription prescription = (Prescription) prescriptionsGrid.getSelectedRow();
                try {
                    prescriptionDAO.delete(prescription);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                update();
            }
        });

        Button create = new Button("Create");
        create.addClickListener(event -> {
            Prescription prescription = new PrescriptionImpl(0, null, null);
            try {
                mainUI.addWindow(new CreateWindow(this, prescription, new PrescriptionForm(patientDAO, doctorDAO), prescriptionDAO));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Button statistic = new Button("Statistic");
        statistic.addClickListener(event -> {
            mainUI.addWindow(new StatisticWindow(prescriptionDAO));
        });

        buttonLayout.addComponents(create, edit, delete, statistic);
        buttonLayout.setSpacing(true);

        return buttonLayout;
    }

    public void update() {
        try {
             prescriptions = prescriptionDAO.getAll(filters.getDescription(), filters.getPatient(), filters.getPriority());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        prescriptionsGrid.setContainerDataSource(new BeanItemContainer<Prescription>(Prescription.class, prescriptions));
        prescriptionsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
    }
}
