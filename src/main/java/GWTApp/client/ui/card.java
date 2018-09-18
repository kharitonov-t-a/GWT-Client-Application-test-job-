package GWTApp.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

public class card extends Composite {

    interface cardUiBinder extends UiBinder<Widget, card> {
    }

    private static cardUiBinder ourUiBinder = GWT.create(cardUiBinder.class);

    @UiField
    TextBox name;

    @UiField
    TextBox phone;

    /**
     * номер строки для редактирования
     * или если новая то -1
     */
    private int changeRowNumber;

    /**
     * получаем номер строки для редактирования
     * @return - int <br>
     */
    public int getChangeRowNumber() {
        return changeRowNumber;
    }

    public void setChangeRowNumber(int changeRowNumber) {
        this.changeRowNumber = changeRowNumber;
    }

    /**
     * Изменить существующую карточку
     * @param name
     * @param phone
     */
    public card(String name, String phone) {
        super();
        initWidget(ourUiBinder.createAndBindUi(this));
        this.name.setValue(name);
        this.phone.setValue(phone);
    }

    /**
     * Создать новую карточку
     */
    public card() {
        super();
        initWidget(ourUiBinder.createAndBindUi(this));
        setChangeRowNumber(-1);
        this.name.setValue("");
        this.phone.setValue("");
    }

    public String getName(){
        return name.getValue();
    }

    public String getPhone(){
        return phone.getValue();
    }
}