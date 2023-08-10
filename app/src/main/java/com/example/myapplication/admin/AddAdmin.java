package com.example.myapplication.admin;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.patient.AddPatient;

public class AddAdmin extends AppCompatActivity {

    EditText fio;
    EditText email;
    EditText password;
    Button addAdminBtn;

    Admin admin = new Admin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        Context context = getApplicationContext();
        DatabaseHelper myDB = new DatabaseHelper(context);

        fio = findViewById(R.id.fioEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        addAdminBtn = findViewById(R.id.addAdminButton);
        addAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminFio = fio.getText().toString();
                String adminEmail = email.getText().toString();
                String adminPassword = password.getText().toString();

                boolean result = myDB.addAdminData(adminFio, adminEmail, adminPassword);

                if (result) {
                    Toast.makeText(AddAdmin.this, "Адміністратор був успішно доданий", Toast.LENGTH_SHORT).show();
                    // Здесь можно выполнить дополнительные действия после успешного добавления пациента
                } else {
                    Toast.makeText(AddAdmin.this, "Помилка при додаванні адміністратора", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}