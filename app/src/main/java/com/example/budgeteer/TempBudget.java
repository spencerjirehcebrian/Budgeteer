package com.example.budgeteer;

import java.util.ArrayList;

public class TempBudget {

    private String mName;
    private boolean mOnline;

    public TempBudget(String name, boolean online) {
        mName = name;
        mOnline = online;
    }

    public String getName() {
        return mName;
    }

    public boolean isOnline() {
        return mOnline;
    }

    private static int lastContactId = 0;

    public static ArrayList<TempBudget> createContactsList(int numContacts) {
        ArrayList<TempBudget> contacts = new ArrayList<TempBudget>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new TempBudget("Person " + ++lastContactId, i <= numContacts / 2));
        }

        return contacts;
    }
}
