package com.haulmont.testtask.gui;

import com.haulmont.testtask.data.dao.BaseDAO;
import com.haulmont.testtask.data.entity.Entity;
import com.haulmont.testtask.gui.forms.EditableForm;
import com.haulmont.testtask.gui.tabs.Observer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;

public class CreateWindow extends Window {
    private Observer observer;

    private BaseDAO dao;

    private EditableForm editableForm;

    private Button create = new Button("Create");

    private Button cancel = new Button("Cancel");

    private Entity entity;

    public CreateWindow(Observer observer, Entity entity, EditableForm editableForm, BaseDAO dao) {
        super("Create");
        this.editableForm = editableForm;
        this.dao = dao;
        this.entity = entity;
        this.observer = observer;

        create.addClickListener(listener -> create());
        create.setStyleName(ValoTheme.BUTTON_PRIMARY);
        create.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        cancel.addClickListener(listener -> cancel());

        editableForm.set(entity);

        HorizontalLayout buttons = new HorizontalLayout(create, cancel);
        buttons.setSpacing(true);

        setContent(new VerticalLayout(editableForm, buttons));

        center();
        setModal(true);
    }

    private void create() {
        entity = editableForm.get();

        try {
            dao.insert(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        observer.update();
        close();
    }

    private void cancel() {
        close();
    }
}
