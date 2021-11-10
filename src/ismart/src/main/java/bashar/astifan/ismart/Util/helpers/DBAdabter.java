package bashar.astifan.ismart.Util.helpers;

import bashar.astifan.ismart.smart.mobi.iDatabaseManager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 *
 *
 * @author Bashar Astifan <br>
 *         <a href=
 *         "astifan.online"
 *         >Read More</a> <br>
 * @version 2.0
 *
 */
public class DBAdabter {
	public String KEY_ROWID = "id";
	public ArrayList<String> Fields;
	private String DATABASE_NAME = "DB";
	private String DATABASE_TABLE = "Table";
	private String DATABASE_Fields = "DB_Fields";
	private int DATABASE_VERSION = 1;
	private String TAG = "DBAdapter";
	private final Context context;
	private String DATABASE_CREATE;
	private String fieldsName_Save;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private String Query_Str = "";
	boolean newTable = false;
boolean isLog=false;
	public DBAdabter(Context ctx, int DB_Version, String Db_Name,
			String tabel_Name, String DBFields) {
		this.context = ctx;
		isLog=iDatabaseManager.isLog;
		DATABASE_VERSION = DB_Version;
		DATABASE_NAME = Db_Name;
		DATABASE_TABLE = tabel_Name;
		DATABASE_CREATE = "create table if not exists " + DATABASE_TABLE
				+ " (id integer primary key autoincrement, " + DBFields + ");";
		if(isLog)Log.d("Fields Query", DATABASE_CREATE);
		fieldsName_Save = "create table if not exists " + DATABASE_Fields
				+ " (id integer primary key autoincrement, "
				+ "Field_Name VARCHAR,Tables_Name VARCHAR);";
		if(isLog)Log.d("Fields Query", fieldsName_Save);
		DBHelper = new DatabaseHelper(context);
	}

	private class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
				if(isLog)Log.d("------", DATABASE_CREATE);
				db.execSQL(fieldsName_Save);
				if(isLog)Log.d("------", fieldsName_Save);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if(isLog)Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			onCreate(db);
		}
	}

	// ---opens the database---
	public DBAdabter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public Cursor sql_ExcuteQurey(String SqlQurey,String[] selectionArgs ){
		
		return db.rawQuery(SqlQurey, selectionArgs);
	}
	public void sql_BoolExcuteQurey(String SqlQurey){
		
		 db.execSQL(SqlQurey);
	}
	public void Create_new_Table(String table_Name, String Fields_Name) {
		if (table_Name != "" & Fields_Name != "") {
			Query_Str = "create table if not exists " + table_Name
					+ " (id integer primary key autoincrement, " + Fields_Name
					+ ");";
			db.execSQL(Query_Str);
			if(isLog)Log.d("Create_new_Table", Query_Str);
		}

	}
//------------------------
	public void Do_Custom_Query(String Query_Text) {
		if ( Query_Text != ""){
			db.execSQL(Query_Text);
			if(isLog)Log.d("Do_Custom_Query", Query_Text);
		}

	}
	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	// ---insert a record into the database---
	public long insertRecord(String[] DBFields, String[] DBValues,
			String fromTable) {
		ContentValues initialValues = new ContentValues();
		if (DBFields != null) {
			for (int i = 0; i < DBFields.length; i++) {
				initialValues.put(DBFields[i], DBValues[i]);
			}
		}
		if(isLog)Log.d("insertRecord", "insertRecord To :"+fromTable);
		return db.insert(fromTable, null, initialValues);
	}
	public long insertRecord(String[] DBFields, String[] DBValues,byte[] bt,
			String fromTable) {
		ContentValues initialValues = new ContentValues();
		if (DBFields != null) {
			for (int i = 0; i < DBFields.length-1; i++) {
				initialValues.put(DBFields[i], DBValues[i]);
				if(isLog)Log.d("", "Field Name : "+DBFields[i]);
			}
		}
		if(bt!=null){
			if(isLog)Log.d("", "BLOB Field Name : "+DBFields[DBFields.length-1]);
			initialValues.put(DBFields[DBFields.length-1], bt);
		}
		if(isLog)Log.d("insertRecord", "insertRecord To :"+fromTable);
		return db.insert(fromTable, null, initialValues);
	}
	public void fill_Fields(String[] DBFields, String Table_Name) {
		ContentValues initialValues = new ContentValues();
		if (DBFields != null) {
			for (int i = 0; i < DBFields.length; i++) {
				initialValues.put("Field_Name", DBFields[i]);
				initialValues.put("Tables_Name", Table_Name);
				db.insert(DATABASE_Fields, null, initialValues);
			}

		}

	}

	public Cursor getFieldsRecords(String Table_Name) {
		if(isLog)Log.d("getFieldsRecords", "getFieldsRecords For :"+Table_Name);
		return db.query(DATABASE_Fields,
				new String[] { KEY_ROWID, "Field_Name" }, "Tables_Name='"
						+ Table_Name + "'", null, null, null, null, null);
		
	}

	// ---deletes a particular record---
	public boolean deleteRecord(long rowId,String Table_Name) {
		if(isLog)Log.d("deleteRecord", "deleteRecord id :"+rowId);
		return db.delete(Table_Name, KEY_ROWID + "=" + rowId, null) > 0;
	}
	// ---deletes a particular record---
	public boolean deleteallRecord(String Table_Name) {
		if(isLog)Log.d("deleteRecord", "deleteRecord id :");
		return db.delete(Table_Name, null, null) > 0;
	}
	// ---retrieves all the records---
	public Cursor getAllRecords(String[] DBFi, String fromTable) {
		ArrayList<String> DBFields = new ArrayList<String>();
		DBFields.add(KEY_ROWID);
		for (int i = 0; i < DBFi.length; i++) {
			DBFields.add(DBFi[i]);
		}
		String[] FDBF = new String[DBFields.size()];
		for (int j = 0; j < FDBF.length; j++) {
			FDBF[j] = DBFields.get(j);
		}
		if(isLog)Log.d("getAllRecords", "getAllRecords From :"+fromTable);
		return db.query(fromTable, FDBF, null, null, null, null, null, null);
	}

	// ---retrieves a particular record---
	public Cursor getRecord(long rowId, String[] DBFi, String fromTable)
			throws SQLException {
		ArrayList<String> DBFields = new ArrayList<String>();
		DBFields.add(KEY_ROWID);
		for (int i = 0; i < DBFi.length; i++) {
			DBFields.add(DBFi[i]);
		}
		String[] FDBF = new String[DBFields.size()];
		for (int j = 0; j < FDBF.length; j++) {
			FDBF[j] = DBFields.get(j);
		}
		Cursor mCursor = db.query(true, fromTable, FDBF, KEY_ROWID + "="
				+ rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		if(isLog)Log.d("getRecord", "getRecord From :"+fromTable);
		return mCursor;
	}

	// =---- Custom Where -----//
	public Cursor select_Custom_Where(String fromTable, String[] returnFields,
			String whereCondetion, String[] selectionArgs, String groupBy,
			String having, String orderBy, String limit, boolean allRecord)
			throws SQLException {
		Cursor mCursor = db.query(fromTable, returnFields, whereCondetion,
				selectionArgs, groupBy, having, orderBy, limit);
		if (mCursor != null & allRecord != true) {
			mCursor.moveToFirst();
		}
		if(isLog)Log.d("select_Custom_Where", "select_Custom_Where From :"+fromTable);
		return mCursor;
	}

	// ---updates a record---
	public boolean updateRecord(long rowId, String[] DBFields,
			String[] DBValues, String toTable) {
		ContentValues args = new ContentValues();
		if (DBFields != null) {
			for (int i = 0; i < DBFields.length; i++) {
				args.put(DBFields[i], DBValues[i]);
			}
		}
		if(isLog)Log.d("updateRecord", "updateRecord To :"+toTable);
		return db.update(toTable, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// --------------------------------------
	// ---updates a record---
	public boolean updateRecord_CustomCond(String[] DBFields,
			String[] DBValues, String toTable, String WhereCond) {
		ContentValues args = new ContentValues();
		if (DBFields != null) {
			for (int i = 0; i < DBFields.length; i++) {
				args.put(DBFields[i], DBValues[i]);
			}
		}
		if(isLog)Log.d("updateRecord_CustomCond", "updateRecord_CustomCond To :"+toTable);
		return db.update(toTable, args, WhereCond, null) > 0;
	}
}
