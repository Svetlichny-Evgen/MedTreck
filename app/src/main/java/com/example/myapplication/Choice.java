package com.example.myapplication;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.admin.AdminActivity;
import com.example.myapplication.doctor.AddDoctor;
import com.example.myapplication.nurse.AddNurse;

public class Choice extends AppCompatActivity {
    Button addDoctor;
    Button addNurse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        addDoctor = findViewById(R.id.addDoctorButton);
        addNurse = findViewById(R.id.addNurseButton);

        int adminId = getIntent().getIntExtra("admin_id", -1);

        addDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice.this, AddDoctor.class);
                intent.putExtra("admin_id", adminId);
                startActivity(intent);
            }
        });

        addNurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice.this, AddNurse.class);
                intent.putExtra("admin_id", adminId);
                startActivity(intent);
            }
        });

    }
}