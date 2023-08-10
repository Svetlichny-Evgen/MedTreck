package com.example.myapplication.admin;

import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.admin.Admin;

import java.util.ArrayList;

public class AdminInfo extends AppCompatActivity {

    private TextView fullNameTextView;
    private TextView emailTextView;

    ArrayList<Admin> admins = new ArrayList<>();
    Admin admin = new Admin();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_info);
        String adminName = getIntent().getStringExtra("admin_name");
        Toast.makeText(getApplicationContext(), "Ви натиснули на " + adminName.toString(), Toast.LENGTH_SHORT).show();

        fullNameTextView = findViewById(R.id.full_name_textview);
        emailTextView = findViewById(R.id.email_textview);

        DatabaseHelper myDB = new DatabaseHelper(this);

        Cursor cursor = myDB.getAdminDataByName(adminName);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                admin.setId(Integer.parseInt(cursor.getString(0)));
                admin.setName( cursor.getString(1));
                admin.setEmail( cursor.getString(2));
                admin.setPassword(cursor.getString(3));
            }
        }

        fullNameTextView.setText(admin.getName());
        emailTextView.setText(admin.getEmail());

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрываем текущую активити и возвращаемся на предыдущую
            }
        });
    }

}