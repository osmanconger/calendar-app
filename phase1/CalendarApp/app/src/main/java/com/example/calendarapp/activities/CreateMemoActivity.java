package com.example.calendarapp.activities;

import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.calendarapp.R;

import static com.example.calendarapp.activities.LogInPage.user;

public class CreateMemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_memo);
    }

    public void createMemoButton(View view) {
        EditText text1 = (EditText) findViewById(R.id.editText24);
        String message = text1.getText().toString();
        user.memoHandler.createMemo(message);
    }
}
