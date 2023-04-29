package com.example.budgeteer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private NotificationDatabaseHelper databaseHelper;
    private RecyclerView recyclerViewUsers;
    private List<Notifications> listNotifications;

    private NotificationAdapter notificationAdapter;

    private String globalEmail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notification_fragment, container, false);

        recyclerViewUsers = (RecyclerView) v.findViewById(R.id.recycler);

        listNotifications = new ArrayList<>();

        databaseHelper = new NotificationDatabaseHelper(v.getContext());

        notificationAdapter = new NotificationAdapter(listNotifications, globalEmail);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(v.getContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(notificationAdapter);

        getDataFromSQLite();

        HistoryActivity activity = (HistoryActivity) getActivity();
        globalEmail = activity.getMyData();

        return v;
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listNotifications.clear();
                listNotifications.addAll(databaseHelper.getAllNotifications(globalEmail));
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                notificationAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
