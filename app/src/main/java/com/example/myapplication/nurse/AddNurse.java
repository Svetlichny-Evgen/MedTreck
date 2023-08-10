package com.example.myapplication.nurse;

import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.doctor.AddDoctor;

public class AddNurse extends AppCompatActivity {
    EditText fio;
    EditText email;
    EditText password;
    Button add;
    private int adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nurse);

        adminId = getIntent().getIntExtra("admin_id", -1);

        Context context = getApplicationContext();
        DatabaseHelper myDB = new DatabaseHelper(context);

        fio = findViewById(R.id.fioEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        add = findViewById(R.id.addNurseButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nurseFio = fio.getText().toString();
                String nurseEmail = email.getText().toString();
                String nursePass = password.getText().toString();

                boolean result = myDB.addNurseData(nurseFio, nurseEmail, nursePass, "0", "0", adminId);

                if (result) {
                    Toast.makeText(AddNurse.this, "Мед. сестра була успішно додана", Toast.LENGTH_SHORT).show();
                    // Здесь можно выполнить дополнительные действия после успешного добавления пациента
                } else {
                    Toast.makeText(AddNurse.this, "помилака при додаванні мед. сестри", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}