package com.haulmont.testtask.gui.tabs;

import com.haulmont.testtask.data.dao.DoctorDAO;
import com.haulmont.testtask.data.entity.Doctor;
import com.haulmont.testtask.data.entity.DoctorImpl;
import com.haulmont.testtask.gui.CreateWindow;
import com.haulmont.testtask.gui.EditWindow;
import com.haulmont.testtask.gui.forms.DoctorForm;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class DoctorsTab extends VerticalLayout implements Observer {
    private UI mainUI;

    private Grid doctorsGrid = new Grid();

    private DoctorDAO doctorDAO;

    private List<Doctor> doctors;

    public DoctorsTab(UI mainUI, DoctorDAO doctorDAO) {
        this.mainUI = mainUI;
        this.doctorDAO = doctorDAO;

        HorizontalLayout buttonLayout = getButtons();
        initPatientsGrid();

        addComponents(buttonLayout, doctorsGrid);
        setExpandRatio(doctorsGrid, 1);
        setMargin(true);
        setSpacing(true);
        setSizeFull();

        update();
    }

    private void initPatientsGrid() {
        doctorsGrid.setSizeFull();
        doctorsGrid.setColumns("name", "surname", "patronymic", "specialty");
        doctorsGrid.setColumnOrder("surname", "name", "patronymic");
    }

    private HorizontalLayout getButtons() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button edit = new Button("Edit");
        edit.addClickListener(event -> {
            if (doctorsGrid.getSelectedRow() != null) {
                Doctor doctor = (Doctor) doctorsGrid.getSelectedRow();
                mainUI.addWindow(new EditWindow(this, new DoctorForm(), doctor, doctorDAO));
            }
        });

        Button delete = new Button("Delete");
        delete.addClickListener(event -> {
            if (doctorsGrid.getSelectedRow() != null) {
                Doctor doctor = (Doctor) doctorsGrid.getSelectedRow();
                try {
                    if (doctorDAO.canDelete(doctor)) {
                        doctorDAO.delete(doctor);
                    }
                    else {
                        Notification.show("Doctor has prescription!", Notification.Type.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Notification.show("Failed delete doctor!");
                }
                update();
            }
        });

        Button create = new Button("Create");
        create.addClickListener(event -> {
            Doctor doctor = new DoctorImpl(0);
            mainUI.addWindow(new CreateWindow(this, doctor, new DoctorForm(), doctorDAO));
        });

        buttonLayout.addComponents(create, edit, delete);
        buttonLayout.setSpacing(true);

        return buttonLayout;
    }

    public void update() {
        try {
            doctors = doctorDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            Notification.show("Failed to load list of doctors!", Notification.Type.ERROR_MESSAGE);
        }
        doctorsGrid.setContainerDataSource(new BeanItemContainer<Doctor>(Doctor.class, doctors));
        doctorsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
    }
}
