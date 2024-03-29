package com.haulmont.testtask.gui;


import com.haulmont.testtask.data.dao.BaseDAO;
import com.haulmont.testtask.data.entity.Entity;
import com.haulmont.testtask.gui.forms.EditableForm;
import com.haulmont.testtask.gui.tabs.Observer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

public class EditWindow extends Window {
    private Observer observer;

    private EditableForm editableForm;

    private Button save = new Button("Save");

    private Button cancel = new Button("Cancel");

    private Entity entity;

    private BaseDAO dao;

    public EditWindow(Observer observer, EditableForm editableForm, Entity entity, BaseDAO dao) {
        super("Edit");
        this.entity = entity;
        this.editableForm = editableForm;
        this.dao = dao;
        this.observer = observer;

        save.addClickListener(listener -> save());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        cancel.addClickListener(listener -> cancel());

        editableForm.set(entity);

        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        buttons.setSpacing(true);

        VerticalLayout layout = new VerticalLayout(editableForm, buttons);
        layout.setMargin(true);

        setContent(layout);

        center();
        setModal(true);
    }

    private void save() {
        if (editableForm.isValid()) {
            entity = editableForm.get();

            try {
                dao.update(entity);
            } catch (SQLException e) {
                e.printStackTrace();
                Notification.show("Failed save!");
            }

            observer.update();
            close();
        }
    }

    private void cancel() {
        close();
    }
}
