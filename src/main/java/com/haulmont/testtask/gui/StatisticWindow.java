package com.haulmont.testtask.gui;

import com.haulmont.testtask.data.dao.PrescriptionDAO;
import com.haulmont.testtask.data.entity.Doctor;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

import java.sql.SQLException;
import java.util.Map;

public class StatisticWindow extends Window {
    public StatisticWindow(PrescriptionDAO prescriptionDAO) {
        setModal(true);

        Grid grid = new Grid();
        grid.setColumns("Doctor", "Count descriptions");

        Map<Doctor, Integer> statistic = null;
        try {
            statistic = prescriptionDAO.getDoctorsCountPrescriptions();
        } catch (SQLException e) {
            e.printStackTrace();
            setContent(new Label("Can not load statistic!"));
            return;
        }

        for (Map.Entry<Doctor, Integer> entry: statistic.entrySet()) {
            grid.addRow(entry.getKey().toString(), entry.getValue().toString());
        }

        setContent(grid);
        center();
        setCaption("Statistic");
        setClosable(true);
    }
}
