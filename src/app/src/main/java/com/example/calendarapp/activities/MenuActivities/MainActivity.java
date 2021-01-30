package com.example.calendarapp.activities.MenuActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.calendarapp.R;
import com.example.calendarapp.activities.LoggingActivities.LogInPage;
import com.example.calendarapp.activities.LoggingActivities.SignUpPage;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Called when the Login button is pressed
    public void loginButton (View view) {
        startActivity(new Intent(MainActivity.this, LogInPage.class));
    }

    public void loginButton2 (View view) {
        startActivity(new Intent(MainActivity.this, Menu.class));
    }

    public void signUpButton(View view) {
        startActivity(new Intent(MainActivity.this, SignUpPage.class));
    }

}
