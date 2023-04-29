package com.example.budgeteer;

public class Notifications {

    private int id;

    private int budget_id;
    private String user_email;
    private String _class;
    private Integer percent;

    private String date;

    private String time;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getBudgetID() {
        return budget_id;
    }
    public void setBudgetID(Integer budget_id) {
        this.budget_id = budget_id;
    }

    public String getUserEmail() {
        return user_email;
    }
    public void setUserEmail(String user_email) {this.user_email = user_email;}

    public String get_Class() {
        return _class;
    }
    public void set_Class(String _class) {this._class = _class;}

    public Integer getPercent() {
        return percent;
    }
    public void setPercent(Integer percent) {this.percent = percent;}

    public String getDate() {
        return date;
    }
    public void setDate(String date) {this.date = date;}

    public String getTime() {
        return time;
    }
    public void setTime(String time) {this.time = time;}


}
