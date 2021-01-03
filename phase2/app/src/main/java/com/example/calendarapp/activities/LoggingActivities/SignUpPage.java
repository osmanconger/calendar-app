package com.example.calendarapp.activities.LoggingActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.calendarapp.R;
import com.example.calendarapp.database.CallbackClasses.BooleanCallback;
import com.example.calendarapp.database.LoggingClasses.SignUpManager;

public class SignUpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
    }

    public void signUpButton (View view) {
        EditText text1 = (EditText) findViewById(R.id.editText);
        EditText text2 = (EditText) findViewById(R.id.editText2);
        EditText text3 = (EditText) findViewById(R.id.editText31);
        String username = text1.getText().toString();
        String name = text2.getText().toString();
        String password = text3.getText().toString();
        SignUpManager sm = new SignUpManager();
        sm.signUpUser(username, name, password, new BooleanCallback() {
            @Override
            public void onCallback(Boolean bool) {
                if(bool) {
                    startActivity(new Intent(SignUpPage.this, LogInPage.class));
                }
            }
        });
    }

}