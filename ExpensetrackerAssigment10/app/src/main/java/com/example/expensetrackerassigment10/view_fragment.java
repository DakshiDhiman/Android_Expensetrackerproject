package com.example.expensetrackerassigment10;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class view_fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Expense> list;
    ExpenseAdapter adapter;
    DatabaseHelper db;
    TextView total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view, container, false);

        recyclerView = view.findViewById(R.id.recycleView);
        total = view.findViewById(R.id.total);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = new DatabaseHelper(getContext());

        list = new ArrayList<>();

        Cursor cursor = db.getAllExpense();

        while(cursor.moveToNext()){

            list.add(new Expense(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));
        }

        Cursor totalCursor = db.getTotalAmount();

        if(totalCursor.moveToFirst()){

            String amount = totalCursor.getString(0);

            total.setText("Total : " + amount);
        }

        adapter = new ExpenseAdapter(getContext(), list);

        recyclerView.setAdapter(adapter);

        return view;
    }
}