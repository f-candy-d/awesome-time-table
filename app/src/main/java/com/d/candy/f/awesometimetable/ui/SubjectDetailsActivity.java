package com.d.candy.f.awesometimetable.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.d.candy.f.awesometimetable.R;

public class SubjectDetailsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_details);

        Intent intent = getIntent();
        String mess = intent.getStringExtra("extra_test_message");
        ((TextView) findViewById(R.id.wwwww)).setText(mess);
    }
}
