package com.example.bsautos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bsautos.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla usuarios
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    // Tabla autos
    public static final String TABLE_AUTOS = "autos";
    public static final String COLUMN_AUTO_ID = "_id";
    public static final String COLUMN_AUTO_MARCA = "marca";
    public static final String COLUMN_AUTO_MODELO = "modelo";
    public static final String COLUMN_AUTO_COLOR = "color";
    public static final String COLUMN_AUTO_ESTADO = "estado";
    public static final String COLUMN_AUTO_VALOR = "valor";
    public static final String COLUMN_AUTO_KM = "km";
    public static final String COLUMN_AUTO_FOTO = "foto"; // NUEVO

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla usuarios
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Tabla autos (ahora con foto)
        String CREATE_AUTOS_TABLE = "CREATE TABLE " + TABLE_AUTOS + "("
                + COLUMN_AUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_AUTO_MARCA + " TEXT,"
                + COLUMN_AUTO_MODELO + " TEXT,"
                + COLUMN_AUTO_COLOR + " TEXT,"
                + COLUMN_AUTO_ESTADO + " TEXT,"
                + COLUMN_AUTO_VALOR + " REAL,"
                + COLUMN_AUTO_KM + " INTEGER,"
                + COLUMN_AUTO_FOTO + " TEXT"
                + ")";
        db.execSQL(CREATE_AUTOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTOS);
        onCreate(db);
    }

    // Usuarios
    public boolean insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null,
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // AUTOS

    // Insertar auto (con foto)
    public long insertAuto(String marca, String modelo, String color, String estado, double valor, int km) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_MARCA, marca);
        values.put(COLUMN_AUTO_MODELO, modelo);
        values.put(COLUMN_AUTO_COLOR, color);
        values.put(COLUMN_AUTO_ESTADO, estado);
        values.put(COLUMN_AUTO_VALOR, valor);
        values.put(COLUMN_AUTO_KM, km);
        return db.insert(TABLE_AUTOS, null, values);
    }


    // Actualizar auto (con foto)
    public int updateAuto(long autoId, String marca, String modelo, String color, String estado, double valor, int km) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTO_MARCA, marca);
        values.put(COLUMN_AUTO_MODELO, modelo);
        values.put(COLUMN_AUTO_COLOR, color);
        values.put(COLUMN_AUTO_ESTADO, estado);
        values.put(COLUMN_AUTO_VALOR, valor);
        values.put(COLUMN_AUTO_KM, km);
        return db.update(TABLE_AUTOS, values, COLUMN_AUTO_ID + "=?", new String[]{String.valueOf(autoId)});
    }


    public Cursor getAllAutos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_AUTOS, null);
    }

    public int deleteAuto(long autoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_AUTOS, COLUMN_AUTO_ID + "=?", new String[]{String.valueOf(autoId)});
    }

    public Cursor getAutoById(long autoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_AUTOS + " WHERE " + COLUMN_AUTO_ID + "=?", new String[]{String.valueOf(autoId)});
    }
}
