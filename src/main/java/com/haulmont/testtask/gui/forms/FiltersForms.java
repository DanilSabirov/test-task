package com.haulmont.testtask.gui.forms;

import com.haulmont.testtask.data.dao.PatientDAO;
import com.haulmont.testtask.data.entity.Patient;
import com.haulmont.testtask.data.entity.Priority;
import com.haulmont.testtask.gui.tabs.Observer;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class FiltersForms extends FormLayout {
    private HorizontalLayout layout = new HorizontalLayout();

    private PatientDAO patientDAO;

    private TextField descriptionFilter = new TextField("Description");

    private ComboBox patientFilter = new ComboBox("Patient");

    private NativeSelect priorityFilter = new NativeSelect("Priority");

    private Observer observer;

    public FiltersForms(PatientDAO patientDAO, Observer observer) {
        this.patientDAO = patientDAO;
        this.observer = observer;

        layout.setSpacing(true);

        descriptionFilter.addTextChangeListener(event -> {
            descriptionFilter.setValue(event.getText());
            observer.update();
        });
        descriptionFilter.setBuffered(false);

        patientFilter.addFocusListener(event -> {
            try {
                patientFilter.addItems(patientDAO.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        patientFilter.addValueChangeListener(event -> {
            observer.update();
        });

        priorityFilter.addItems(Priority.values());
        priorityFilter.setNullSelectionAllowed(true);
        priorityFilter.setNullSelectionItemId("");

        priorityFilter.addValueChangeListener(event -> {
            observer.update();
        });

        layout.addComponents(descriptionFilter, patientFilter, priorityFilter);

        addComponent(layout);
    }

    public String getDescription() {
        return descriptionFilter.getValue();
    }

    public Patient getPatient() {
        return (Patient) patientFilter.getValue();
    }

    public Priority getPriority() {
        return (Priority) priorityFilter.getValue();
    }
}
