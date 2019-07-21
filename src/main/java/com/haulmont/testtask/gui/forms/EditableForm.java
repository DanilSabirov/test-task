package com.haulmont.testtask.gui.forms;

import com.haulmont.testtask.data.entity.Entity;
import com.vaadin.ui.Component;

public interface EditableForm<T extends Entity> extends Component {
    T get();

    void set(T entity);

    boolean isValid();
}
