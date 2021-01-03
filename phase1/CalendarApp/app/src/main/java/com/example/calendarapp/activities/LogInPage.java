package com.example.calendarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.calendarapp.R;
import com.example.calendarapp.calendar.User;
import com.example.calendarapp.database.UserCallback;
import com.example.calendarapp.database.LogInManager;

public class LogInPage extends AppCompatActivity {
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }

    // Called when the sign in button in the second activity is clicked

    public void signInButton (View view) {
        EditText text1 = (EditText) findViewById(R.id.editText);
        EditText text2 = (EditText) findViewById(R.id.editText2);
        String username = text1.getText().toString();
        String password = text2.getText().toString();
        LogInManager lm = new LogInManager();
        lm.tryLogIn(username, password, new UserCallback() {
            @Override
            public void onCallback(User userFromCallback) {
                if(userFromCallback != null) {
                    Intent i = new Intent(LogInPage.this, Menu.class);
                    user = userFromCallback;
                    startActivity(i);
                }
            }
        });
    }

}