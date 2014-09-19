package com.mcm.database;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PzDatabaseHelper<DatabaseErrorHandler> extends SQLiteOpenHelper {

	// Database Version
    private static int DATABASE_VERSION = 1;

    // Database Name
    private static String DATABASE_NAME = "MyDb";
    private static String DATABASE_PATH = "";
    
//    // Database Path
//    public static String DATABASE_PATH = "/data/data/com.myapp.spirit/databases/";

    // Contacts table name
    private static String TEST_TABLE = "TestTable";
    

    public PzDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context.deleteDatabase(DATABASE_NAME);
        DATABASE_PATH = "/data/data/" + context + "/databases/";
        // TODO Auto-generated constructor stub
    }
    
    public PzDatabaseHelper(Context context, String dbName, String dbPath, int dbVersion) {    	
    	super(context, dbName, null, DATABASE_VERSION);
//    	context.deleteDatabase(dbName);
    	
    	DATABASE_NAME = dbName;    	
    	DATABASE_VERSION = dbVersion;

    	if(dbPath == "")
    		DATABASE_PATH = "/data/data/" + context + "/databases/";
    	else    		    		
    		DATABASE_PATH = dbPath;
    }
    
    public PzDatabaseHelper(Context context, String dbName, String dbPath, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
		super(context, dbName, factory, version);
		
//    	context.deleteDatabase(dbName);
    	
    	DATABASE_NAME = dbName;
    	DATABASE_VERSION = version;

    	if(dbPath == "")
    		DATABASE_PATH = "/data/data/" + context + "/databases/";
    	else    		    		
    		DATABASE_PATH = dbPath;
    	
	}
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
//    	createTestTable(db);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	public boolean isDatabaseExists(Context context) {
		File database = context.getDatabasePath(DATABASE_NAME);
		Log.w("Path", "" + database);
		return database.exists();
	}
	
	private void createTestTable(SQLiteDatabase database) {
        String testTableQuery = "CREATE TABLE IF NOT EXISTS " + TEST_TABLE + " (ID INTEGER PRIMARY KEY, Name INTEGER NOT NULL)";        
        database.execSQL(testTableQuery);
	}
	
	public void createTable(SQLiteDatabase database, String tableQuery) {
		database.execSQL(tableQuery);
//		database.close();
	}
	
	public void dropTable(SQLiteDatabase database, String tableName) {
		database.execSQL("DROP TABLE IF EXISTS '" + tableName + "'");
	}
	
	public void deleteTable(SQLiteDatabase database, String tableName) {
		database.delete(tableName, null, null);
	}
	
	public void truncateTable(SQLiteDatabase database, String tableName) {
		database.execSQL("DELETE * FROM '" + tableName + "'");
	}
	
	public long insertInToTable(SQLiteDatabase database, String tableName, ContentValues contentValues) {
		return database.insert(tableName, null, contentValues);
	}
	
	public void removeFromTable(SQLiteDatabase database, String tableName, String whereClause) {
		Log.e("query", "" + whereClause);
		database.delete(tableName, whereClause, null);
	}
	
	public void updateTableItem(SQLiteDatabase database, String tableName, ContentValues contentValues, String whereClause) {
		Log.w("query", "" + whereClause);
		database.update(tableName, contentValues, whereClause, null);
	}

}
