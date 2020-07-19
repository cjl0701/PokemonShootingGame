package org.pokemonshootinggame.game;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super (context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL ( "CREATE TABLE RankBoard(PK INTEGER NOT NULL,score INTEGER)" );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}