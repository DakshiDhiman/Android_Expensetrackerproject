package com.example.expensetrackerassigment10;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddFragment extends Fragment {

    EditText title, amount, category, date;
    Button save;

    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        title = view.findViewById(R.id.etTitle);
        amount = view.findViewById(R.id.etAmount);
        category = view.findViewById(R.id.etCategory);
        date = view.findViewById(R.id.etDate);

        save = view.findViewById(R.id.save);

        db = new DatabaseHelper(getContext());

        date.setFocusable(false);

        date.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    getContext(),
                    (view1, y, m, d) -> {
                        date.setText(d + "-" + (m + 1) + "-" + y);
                    },
                    year,
                    month,
                    day
            );

            dialog.show();
        });

        save.setOnClickListener(v -> {

            String titleText = title.getText().toString().trim();
            String amountText = amount.getText().toString().trim();
            String categoryText = category.getText().toString().trim();
            String dateText = date.getText().toString().trim();

            if(titleText.isEmpty() ||
                    amountText.isEmpty() ||
                    categoryText.isEmpty() ||
                    dateText.isEmpty()){

                Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();

                return;
            }

            boolean result = db.insertExpense(
                    titleText,
                    amountText,
                    categoryText,
                    dateText
            );

            if(result){

                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

                title.setText("");
                amount.setText("");
                category.setText("");
                date.setText("");
            }

            else{
                Toast.makeText(getContext(), "Not Saved", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}