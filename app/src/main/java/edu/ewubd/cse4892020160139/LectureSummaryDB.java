package edu.ewubd.cse4892020160139;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LectureSummaryDB extends SQLiteOpenHelper {

  public LectureSummaryDB(Context context) {
    super(context, "LectureSummaryDatabaseUpdate.db", null, 1);
//    context.deleteDatabase("LectureSummaryDatabaseUpdate.db");
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    System.out.println("DB@OnCreate");
    String sql = "CREATE TABLE LectureTable  ("
        + "UniqueID INTEGER PRIMARY KEY,"
        + "name TEXT,"
        + "ID TEXT,"
        + "course TEXT,"
        + "type TEXT,"
        + "date TEXT,"
        + "lecture TEXT,"
        + "topic TEXT,"
        + "summary TEXT"
        + ")";
    db.execSQL(sql);
  }


  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    System.out.println("Write code to modify database schema here");
    // db.execSQL("ALTER table my_table  ......");
    // db.execSQL("CREATE TABLE  ......");
  }

  public void insertLectureSummaryDB(Integer UniqueID,String name, String ID, String course,String type, String date, String lecture, String topic , String summary) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cols = new ContentValues();
    cols.put("UniqueID", UniqueID);
    cols.put("name", name);
    cols.put("ID", ID);
    cols.put("course", course);
    cols.put("type", type);
    cols.put("date", date);
    cols.put("lecture", lecture);
    cols.put("topic", topic);
    cols.put("summary", summary);
    // Add other attributes
    db.insert("LectureTable", null, cols);
    db.close();
  }

  public void updateLectureSummaryDB(Integer UniqueID,String name, String ID, String course,String type, String date, String lecture, String topic , String summary) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cols = new ContentValues();
    cols.put("name", name);
    cols.put("ID",ID);
    cols.put("course", course);
    cols.put("type", type);
    cols.put("date", date);
    cols.put("lecture", lecture);
    cols.put("topic", topic);
    cols.put("summary", summary);
    // Add other attributes
    db.update("LectureTable", cols, "UniqueID=?",new String[]{String.valueOf(UniqueID)});
    db.close();
  }

  public void deleteLectureSummaryDB(String ID) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete("LectureTable", "ID=?", new String[]{ID});
    db.close();
  }

  public Cursor selectLectureSummaryDB(String query) {
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor res = null;
    try {
      res = db.rawQuery(query, null);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return res;
  }
  public boolean hasRecord(Integer UniqueID) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM LectureTable WHERE UniqueID=?", new String[]{String.valueOf(UniqueID)});
    boolean hasRecord = cursor.getCount() > 0;
    cursor.close();
    return hasRecord;
  }



}


