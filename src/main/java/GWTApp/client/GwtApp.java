package GWTApp.client;

import GWTApp.client.ui.card;
import GWTApp.client.ui.table;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GwtApp implements EntryPoint {

//  хранилище
    private Storage session;

//  список абонентов
    private static ArrayList<Subscriber> Subscribers = new ArrayList<Subscriber>();

//  виджет таблицы
    private table tableWidjet;

//  виджет карточки
    private card cardWidjet;

    public void onModuleLoad() {

//      проверяем работает ли хранилище сессии
        if (!Storage.isSessionStorageSupported()) {
            Window.alert("Web Storage NOT supported in this browser!");
            return;
        }

//      хранилище сессии
        session = Storage.getSessionStorageIfSupported();

//      если хранилище не пустое и мы до этого не удалили все строки таблицы записываем из хранилища в Subscribers
        if(session.getItem("Sub0") != null/* && !session.getItem("Sub0").equalsIgnoreCase("")*/){
            for (int i = 0; i < session.getLength(); i ++){
                Subscribers.add(new Subscriber(session.getItem("Sub"+i)));
//                if(session.getItem("Sub"+i))
            }
//       При первой загрузе пишем начальные данные в Subscribers и хранилище
        }else if(session.getItem("Sub0") == null) {
            Subscribers.add(new Subscriber("John","111"));
            Subscribers.add(new Subscriber("Mary", "222"));
            Subscribers.add(new Subscriber("Zander", "333"));
            int counter = 0;
            for (Subscriber sub:Subscribers) {
                session.setItem("Sub" + counter, sub.toString());
                counter ++;
            }
        }

        setTableWidget();
        setTableButtons();
    }

    /**
     * устанавливаем в panelBox таблицу
     */
    public void setTableWidget(){

        RootPanel.get("panelBox").clear();
        tableWidjet = new table(Subscribers);

        tableWidjet.getTable().addClickHandler(new ClickHandler() {
            int counterClick = 0;
            long timeClick = 0;
            @Override
            public void onClick(ClickEvent event) {

                //если это первое нажатие или промежуток между нажатиями меньше 100
                if(counterClick == 0 || System.currentTimeMillis() - timeClick < 300){
                    counterClick++;
                    timeClick = System.currentTimeMillis();
                }else {
                    counterClick = 0;
                    timeClick = 0;
                }

                // если двойной клик
                if(counterClick == 2){
                    HTMLTable.Cell cell = tableWidjet.getTable().getCellForEvent(event);

                    setCardWidget(cell.getRowIndex() - 1);
                    setCardButtons();

                    counterClick = 0;
                    timeClick = 0;
                }
            }
        });
        RootPanel.get("panelBox").add(tableWidjet);
    }

    /**
     * устанавливаем в panelBox карточку
     * @param rowIndex - какую строку таблицы изменяем, если -1 то создаем новую
     */
    public void setCardWidget(int rowIndex){
        RootPanel.get("panelBox").clear();
        if(rowIndex != -1){
            cardWidjet = new card(Subscribers.get(rowIndex).getName(), Subscribers.get(rowIndex).getPhone());
            cardWidjet.setChangeRowNumber(rowIndex);
        }else{
            cardWidjet = new card();
        }
        RootPanel.get("panelBox").add(cardWidjet);
    }

    /**
     * устанавливаем в buttonBox кнопки таблицы - удалить и создать
     */
    public void setTableButtons() {

        RootPanel.get("buttonBox").clear();
        Button deleteBtn = new Button("Delete");
        Button createBtn = new Button("Create");

        deleteBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                List<Integer> selectedRows = tableWidjet.getSelectedRows();
                Collections.reverse(selectedRows);
                for (Integer rowNumber: selectedRows) {
                    Subscribers.remove(rowNumber.intValue());
                }
                setTableWidget();
                writeSessionStorage();
            }
        });
        RootPanel.get("buttonBox").add(deleteBtn);

        createBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                setCardWidget(-1);
                setCardButtons();
            }
        });
        RootPanel.get("buttonBox").add(createBtn);
    }

    /**
     * устанавливаем в buttonBox кнопки карточки - сохранить и отменить
     */
    public void setCardButtons() {
        RootPanel.get("buttonBox").clear();
        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");

        saveBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                if(cardWidjet.getChangeRowNumber() != -1){
                    Subscriber subscriber = Subscribers.get(cardWidjet.getChangeRowNumber());
                    subscriber.setName(cardWidjet.getName());
                    subscriber.setPhone(cardWidjet.getPhone());
                    Subscribers.set(cardWidjet.getChangeRowNumber(), subscriber);
                }else{
                    Subscribers.add(new Subscriber(cardWidjet.getName(),cardWidjet.getPhone()));
                }

                setTableWidget();
                setTableButtons();
                writeSessionStorage();
            }
        });
        RootPanel.get("buttonBox").add(saveBtn);

        cancelBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setTableWidget();
                setTableButtons();
            }
        });
        RootPanel.get("buttonBox").add(cancelBtn);
    }

    /**
     * записываем в хранилище строки таблицы
     */
    private void writeSessionStorage(){
        session.clear();
        int counter = 0;
        for (Subscriber sub:Subscribers) {
            session.setItem("Sub" + counter, sub.toString());
            counter ++;
        }
//        if(counter == 0){
//            session.setItem("Sub" + counter, "");
//        }
    }
}