package GWTApp.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class checkBox  extends Composite {
    interface checkBoxUiBinder extends UiBinder<Widget, checkBox> {
    }

    private static checkBoxUiBinder ourUiBinder = GWT.create(checkBoxUiBinder.class);

    @UiField
    CheckBox checkBox;

    public checkBox(String className) {
        initWidget(ourUiBinder.createAndBindUi(this));
        ((InputElement)checkBox.getElement().getFirstChild()).addClassName(className);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }
}