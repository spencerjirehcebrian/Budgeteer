package com.example.budgeteer;

public class Budget {

    private int id;

    private String user_email;

    private String _class;
    private Integer max;
    private Integer expenses;

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

    public Integer getMax() {
        return max;
    }
    public void setMax(Integer max) {this.max = max;}

    public Integer getExpenses() {
        return expenses;
    }
    public void setExpenses(Integer expenses) {this.expenses = expenses;}



}
