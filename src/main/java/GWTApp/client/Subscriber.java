package GWTApp.client;

import com.google.gwt.user.client.Window;

import java.io.Serializable;

public class Subscriber implements Serializable {
    private String name;

    private String phone;

    public Subscriber(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Subscriber(String parseString) {

        if(",".equalsIgnoreCase("" + parseString.charAt(parseString.length() - 1))){
            parseString += " ";
        }

        String[] arrParseString = parseString.split(",");

        this.name = arrParseString[0];
        this.phone = arrParseString[1];
    }

    public Subscriber() {
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString(){
        return name + "," + phone;
    }
}
