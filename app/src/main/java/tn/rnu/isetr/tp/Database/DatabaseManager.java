package tn.rnu.isetr.tp.Database;

import static tn.rnu.isetr.tp.Database.DatabaseHelper.COLUMN_TEACHER_EMAIL;
import static tn.rnu.isetr.tp.Database.DatabaseHelper.COLUMN_USER_EMAIL;
import static tn.rnu.isetr.tp.Database.DatabaseHelper.COLUMN_USER_NAME;
import static tn.rnu.isetr.tp.Database.DatabaseHelper.COLUMN_USER_PASSWORD;
import static tn.rnu.isetr.tp.Database.DatabaseHelper.COLUMN_USER_PHONE;
import static tn.rnu.isetr.tp.Database.DatabaseHelper.TABLE_USER;
import static tn.rnu.isetr.tp.Database.DatabaseHelper.COLUMN_TEACHER_ID;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.StringTokenizer;

import tn.rnu.isetr.tp.Entity.cour;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context){
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();

    }
    // Get ALl Users
    public Cursor getAllUsers() {
        return database.query(
                DatabaseHelper.TABLE_USER, // Table
                null,                         // All columns
                null,                         // No WHERE clause
                null,                         // No WHERE args
                null,                         // No GROUP BY
                null,                         // No HAVING
                null                          // No ORDER BY
        );
    }
    // Insert a Teacher
    public void insertTeacher(String name, String email) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TEACHER_NAME, name);
        values.put(DatabaseHelper.COLUMN_TEACHER_EMAIL, email);
        database.insert(DatabaseHelper.TABLE_TEACHER, null, values);
    }
    // Insert a Course
    public void insertCourse(String name, double nbHeure, String type, String enseigId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_COURS_NAME, name);
        values.put(DatabaseHelper.COLUMN_COURS_NB_HEURE, nbHeure);
        values.put(DatabaseHelper.COLUMN_COURS_TYPE, type);
        values.put(DatabaseHelper.COLUMN_COURS_ENSEIG_ID, enseigId);
        database.insert(DatabaseHelper.TABLE_COURS, null, values);
    }
    public Cursor getAllTeachers() {
        return database.query(
                DatabaseHelper.TABLE_TEACHER, // Table
                null,                         // All columns
                null,                         // No WHERE clause
                null,                         // No WHERE args
                null,                         // No GROUP BY
                null,                         // No HAVING
                null                          // No ORDER BY
        );

    }
    //Insert a User
    public boolean insertUser(String name, String email, String password, String PhoneNumber) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_PHONE, PhoneNumber);
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }
    //Get User by Email
    public Cursor  getUserByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(TABLE_USER, null, COLUMN_USER_EMAIL + " = ?", new String[]{email}, null, null, null);
    }

    // Authenticate User
    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?", new String[]{email, password}, null, null, null);
        boolean IsAuthenticated = cursor.getCount() > 0;
        cursor.close();
        return IsAuthenticated;
    }


    //Add teacher
    public void addTeacher(String name, String email) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TEACHER_NAME, name);
        values.put(DatabaseHelper.COLUMN_TEACHER_EMAIL, email);
        database.insert(DatabaseHelper.TABLE_TEACHER, null, values);
    }
    //Delete teacher
    public Boolean deleteTeacher(String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.TABLE_TEACHER, COLUMN_TEACHER_EMAIL + " = ?", new String[]{email});
        db.close();
        return rows > 0;

    }

    //Get all Courses
    public Cursor getAllCourses() {
        return database.query(
                DatabaseHelper.TABLE_COURS, // Table
                null,                         // All columns
                null,                         // No WHERE clause
                null,                         // No WHERE args
                null,                         // No GROUP BY
                null,                         // No HAVING
                null                          // No ORDER BY
        );
    }
    //update User
    public boolean updateUser(String name, String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);

        int rows = db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?", new String[]{email});
        return rows > 0;
    }
    //update course

    //Delete Course
    public Boolean deleteCourse(String nom) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows =  db.delete("Cours", "name = ?", new String[]{String.valueOf(nom)});
        db.close();

        return rows > 0;

    }
    // Add a Task
    public long addTask(String title, String description, String dueDate, int priority, String status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("date", dueDate);
        values.put("priority", priority);
        values.put("status", status);
        return db.insert("tasks", null, values);
    }

    // Get All Tasks
    public Cursor getAllTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tasks ORDER BY priority DESC", null);
    }

    // Delete a Task
    public boolean deleteTask(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("tasks", "_id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    // Update a Task
    public boolean updateTask(int id, String title, String description, String dueDate, int priority, String status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        values.put("date", dueDate);
        values.put("priority", priority);
        values.put("status", status);
        return db.update("tasks", values, "_id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean updateCourse(cour cours) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_COURS_NAME, cours.getNom());
        values.put(DatabaseHelper.COLUMN_COURS_NB_HEURE, cours.getHeure());
        values.put(DatabaseHelper.COLUMN_COURS_TYPE, cours.getType());
        values.put(DatabaseHelper.COLUMN_COURS_ENSEIG_ID, cours.getEnseignant());

        int rowsAffected = database.update(
                DatabaseHelper.TABLE_COURS,
                values,
                DatabaseHelper.COLUMN_COURS_ID + " = ?",
                new String[]{String.valueOf(cours.getID())}
        );

        return rowsAffected > 0;
    }
}
