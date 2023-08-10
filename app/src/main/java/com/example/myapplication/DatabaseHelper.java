package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;
    public static final String DATABASE_NAME = "hospitalll.db";
    public static final int DATABASE_VERSION = 1;
    private final Context сontext;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        сontext = context;
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME;

        // Копирование базы данных при первом запуске приложения
        if (!checkDatabaseExists(DB_PATH + DATABASE_NAME)) {
            try {
                copyDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkDatabaseExists(String dbPath) {
        File dbFile = new File(dbPath);
        return dbFile.exists();
    }

    public void copyDatabase() throws IOException {
        File dbFile = new File(DB_PATH + DATABASE_NAME);

        if (!checkDatabaseExists(DB_PATH + DATABASE_NAME)) {
            // Если база данных еще не существует, копируем ее из assets
            InputStream inputStream = сontext.getAssets().open(DATABASE_NAME);
            OutputStream outputStream = new FileOutputStream(dbFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Cursor getAllAdminsData() {
        String query = "SELECT * FROM admins";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getAllDoctorsData() {
        String query = "SELECT * FROM doctors";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getAllNursesData() {
        String name = "nurses";
        String query = "SELECT * FROM " + name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getAdminDataByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM admins WHERE admin_name = ?";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{name});
        }
        return cursor;
    }

    public Cursor getDoctorDataByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM doctors WHERE doctor_name = ?";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{name});
        }
        return cursor;
    }

    public Cursor getNurseDataByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT *  FROM nurses WHERE name = ?";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{name});
        }
        return cursor;
    }

    public Cursor getPatientDataByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM patients WHERE patient_name = ?";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{name});
        }
        return cursor;
    }

    public Cursor getPatientsByDoctor(int doctor_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM patients WHERE doctor_id = ?";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{String.valueOf(doctor_id)});
        }
        return cursor;
    }

    public Cursor getAllPatients() {
        String query = "SELECT * FROM patients";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getAdminIdByName(String adminName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT admin_id FROM admins WHERE admin_name = ?";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{String.valueOf(adminName)});
        }
        return cursor;
    }

    public boolean addPatientData(int patient_id, String patient_name, String patient_address, String patient_phone, String patient_email, int doctor_id, int patient_room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("patient_id", patient_id);
        values.put("patient_name", patient_name);
        values.put("patient_address", patient_address);
        values.put("patient_phone", patient_phone);
        values.put("patient_email", patient_email);
        values.put("doctor_id", doctor_id);
        values.put("patient_room", patient_room);
        long result = db.insert("patients", null, values);
        return result != -1;
    }

    public boolean addDoctorData(String doctor_name, String specification, String phone, String email, int admin_id, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("doctor_name", doctor_name);
        values.put("doctor_specification", specification);
        values.put("doctor_phone", phone);
        values.put("doctor_email", email);
        values.put("admin_id", admin_id);
        values.put("password", password);
        long result = db.insert("doctors", null, values);
        return result != -1;
    }

    public boolean addNurseData(String nurse_name, String email, String password, String shiftStart, String shiftEnd, int admin_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", nurse_name);
        values.put("email", email);
        values.put("password", password);
        values.put("shift_start", shiftStart);
        values.put("shift_end", shiftEnd);
        values.put("admin_id", admin_id);
        long result = db.insert("nurses", null, values);
        return result != -1;
    }

    public boolean addAdminData(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("admin_name", name);
        values.put("admin_email", email);
        values.put("admin_password", password);
        long result = db.insert("admins", null, values);
        return result != -1;
    }

    public boolean updateShiftDates(int nurse_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Получение текущей даты
        LocalDate currentDate = LocalDate.now();

        // Запись текущей даты в shiftStart
        String shiftStart = currentDate.toString();
        values.put("shift_start", shiftStart);

        // Получение текущей даты плюс один день
        LocalDate shiftEndDate = currentDate.plusDays(1);

        // Запись даты плюс один день в shiftEnd
        String shiftEnd = shiftEndDate.toString();
        values.put("shift_end", shiftEnd);

        int rowsAffected = db.update("nurses", values, "id = ?", new String[]{String.valueOf(nurse_id)});
        return rowsAffected > 0;
    }
    public Cursor getAllMedicationData() {
        String query = "SELECT * FROM medications";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getAllProcedureData() {
        String query = "SELECT * FROM procedures";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getMedicationByName(String name) {
        String query = "SELECT medication_id FROM medications WHERE medication_name = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{name});
        }
        return cursor;
    }

    public Cursor getProcedureByName(String name) {
        String query = "SELECT procedure_id FROM procedures WHERE procedure_name = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{name});
        }
        return cursor;
    }


    public boolean addAppointmentRecord(int patientId, int doctorId, int nurseId, int procedureId, int medicationId, String appointmentDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("patient_id", patientId);
        values.put("doctor_id", doctorId);
        values.put("nurse_id", nurseId);
        values.put("procedure_id", procedureId);
        values.put("medication_id", medicationId);
        values.put("appointment_date", appointmentDate);

        long result = db.insert("appointments", null, values);
        return result != -1;
    }


    public Cursor getAvailableWards () {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM wards WHERE current_capacity<max_capacity";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean incrementWardCount(int wardId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("current_capacity", "current_capacity + 1");

        int rowsAffected = db.update("wards", values, "id = ?", new String[]{String.valueOf(wardId)});
        return rowsAffected > 0;
    }

    public boolean decrementWardCount(int wardId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("current_capacity", "current_capacity - 1");

        int rowsAffected = db.update("wards", values, "id = ?", new String[]{String.valueOf(wardId)});
        return rowsAffected > 0;
    }


    public boolean deletePatientById(int patientId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete("patients", "patient_id = ?", new String[]{String.valueOf(patientId)});
        return rowsAffected > 0;
    }

    public boolean deleteNurseById(int nurseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete("nurses", "id = ?", new String[]{String.valueOf(nurseId)});
        return rowsAffected > 0;
    }

    public Cursor getAppointmentData(int patient_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM appointments WHERE patient_id = ?";
        String[] selectionArgs = {String.valueOf(patient_id)};
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, selectionArgs);
        }
        return cursor;
    }

    public Cursor getAppointmentById(int appointment_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM appointments WHERE appointment_id = ?";
        String[] selectionArgs = {String.valueOf(appointment_id)};
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, selectionArgs);
        }
        return cursor;
    }

    public Cursor getPatientNameById(int patient_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT patient_name FROM patients WHERE patient_id = ?";
        String[] selectionArgs = {String.valueOf(patient_id)};
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, selectionArgs);
        }
        return cursor;
    }

    public Cursor getNurseNameById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT name FROM nurses WHERE id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, selectionArgs);
        }
        return cursor;
    }

    public Cursor getDoctorNameById(int doc_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT doctor_name FROM doctors WHERE doctor_id = ?";
        String[] selectionArgs = {String.valueOf(doc_id)};
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, selectionArgs);
        }
        return cursor;
    }

    public Cursor getMedicationNameById(int medication_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT medication_name FROM medications WHERE medication_id = ?";
        String[] selectionArgs = {String.valueOf(medication_id)};
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, selectionArgs);
        }
        return cursor;
    }

    public Cursor getProcedureNameById(int medication_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT procedure_name FROM procedures WHERE procedure_id = ?";
        String[] selectionArgs = {String.valueOf(medication_id)};
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, selectionArgs);
        }
        return cursor;
    }


    public SQLiteDatabase openDatabase() throws SQLException {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
