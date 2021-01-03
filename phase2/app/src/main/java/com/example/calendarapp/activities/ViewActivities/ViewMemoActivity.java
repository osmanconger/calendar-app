package com.example.calendarapp.activities.ViewActivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.calendarapp.R;
import com.example.calendarapp.database.CallbackClasses.HashtableCallback;
import com.example.calendarapp.database.DataClasses.DatabaseMemo;

import java.util.Hashtable;

import static com.example.calendarapp.activities.LoggingActivities.LogInPage.user;

public class ViewMemoActivity extends AppCompatActivity {

    private static String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memo);
        Intent intent = this.getIntent();
        eventId = intent.getStringExtra("eventId");
        init();
    }

    private void init() {
        TableLayout tl = findViewById(R.id.table);
        Typeface type = Typeface.SANS_SERIF;
        user.calendarHandler.getCurrentCalendar().memoHandler.getWithId(eventId, new HashtableCallback() {
            int i = 0;
            @Override
            public void onCallback(Hashtable hashtable) {
                hashtable.forEach((k, v) -> {
                    DatabaseMemo dbMemo = (DatabaseMemo) v;
                    if(!dbMemo.message.equals("")) {
                        TableRow row = new TableRow(ViewMemoActivity.this);
                        TableRow row2 = new TableRow(ViewMemoActivity.this);
                        TableRow row3 = new TableRow(ViewMemoActivity.this);
                        TextView tv = new TextView(ViewMemoActivity.this);
                        tv.setText("\n" + dbMemo.message + "\n");
                        tv.setMaxLines(10);
                        tv.setMaxWidth(tl.getWidth() - 10);
                        tv.setTypeface(type);
                        tv.setTextSize(18);
                        Button delete = new Button(ViewMemoActivity.this, null, R.style.Widget_AppCompat_Light_ActionButton);
                        delete.setText("DELETE");
                        delete.setTextColor(Color.RED);
                        delete.setGravity(Gravity.CENTER_HORIZONTAL);
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                user.calendarHandler.getCurrentCalendar().memoHandler.deleteWithId((String) k);
                                finish();
                                startActivity(getIntent());
                            }
                        });
                        row.addView(tv);
                        row2.addView(delete);
                        TextView tv2 = new TextView(ViewMemoActivity.this);
                        tv2.setText("________________________________________________________");
                        row3.addView(tv2);
                        tl.addView(row, i);
                        i = i + 1;
                        tl.addView(row2, i);
                        i = i + 1;
                        tl.addView(row3, i);
                        i = i + 1;
                    }
                });
            }
        });
    }

    public void addMemo(View v) {
        EditText et = findViewById(R.id.editText36);
        String message = et.getText().toString();
        user.calendarHandler.getCurrentCalendar().memoHandler.createMemo(message, Integer.parseInt(eventId));
        finish();
        startActivity(getIntent());
    }
}
