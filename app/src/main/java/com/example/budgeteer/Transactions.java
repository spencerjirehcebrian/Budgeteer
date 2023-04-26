package com.example.budgeteer;

public class Transactions {

    private int id;

    private String user_email;

    private String _class;
    private Integer amount;
    private String date;
    private String time;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return user_email;
    }
    public void setUserEmail(String user_email) {this.user_email = user_email;}

    public String get_Class() {
        return _class;
    }
    public void set_Class(String _class) {this._class = _class;}

    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {this.amount = amount;}

    public String getDate() {
        return date;
    }
    public void setDate(String date) {this.date = date;}

    public String getTime() {
        return time;
    }
    public void setTime(String time) {this.time = time;}


}
