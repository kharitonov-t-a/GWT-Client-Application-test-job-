package GWTApp.client.ui;

import GWTApp.client.Subscriber;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.List;

public class table extends Composite {
    interface tableUiBinder extends UiBinder<Widget, table> {
    }

    private static tableUiBinder ourUiBinder = GWT.create(tableUiBinder.class);

    @UiField
    FlexTable table;

    public table(ArrayList<Subscriber> Subs) {

        initWidget(ourUiBinder.createAndBindUi(this));

        checkBox deleteCheckBox;

        table.setText(0, 0, "Name");
        table.setText(0, 1, "Phone");
        table.insertRow(1);

        int counterRow = 1;
        for (Subscriber sub:Subs) {
            deleteCheckBox = new checkBox("Delete");
            table.setText(counterRow, 0, sub.getName());
            table.setText(counterRow, 1, sub.getPhone());
            table.setWidget(counterRow, 2, deleteCheckBox);
            counterRow++;
            table.insertRow(counterRow);
        }
    }

    public List<Integer> getSelectedRows(){
        int rowCount = table.getRowCount();
        List<Integer> selectedRows = new ArrayList<Integer>();
        for (int i = 1; i < rowCount - 1; i++){
            if(((checkBox)table.getWidget(i, 2)).getCheckBox().getValue()){
                selectedRows.add(i - 1);
            }
        }
        return selectedRows;
    }

    public FlexTable getTable() {
        return table;
    }
}