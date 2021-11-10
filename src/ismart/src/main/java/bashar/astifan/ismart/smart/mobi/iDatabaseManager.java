package bashar.astifan.ismart.smart.mobi;

import java.util.ArrayList;
import java.util.HashMap;

import bashar.astifan.ismart.Util.helpers.DBCommander;
import bashar.astifan.ismart.Util.helpers.DBStaticsValue;
import android.content.Context;
import android.database.Cursor;
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
public class iDatabaseManager {
	public static String VARCHAR = "VARCHAR";
	public static String BLOB = "BLOB";
	public static String TEXT = "TEXT";
	public static String integer = "integer";
	public static String Date = "date";
	public static String ORDERBY_ASC = "ASC";
	public static String ORDERBY_DESC = "DESC";
	public static String PRIMARY_KEY="PRIMARY KEY";
	public static boolean isLog=false;
	public static void Setup(Context ctx, String DBName, String Table,
			String[] TableFields, String[] TableFieldsTypes, String DBVersion) {
		DBStaticsValue.CTX = ctx;
		DBStaticsValue.DB_Name = DBName;
		String Fields = "";
		String[] tempFields = new String[TableFields.length];
		for (int i = 0; i < TableFields.length; i++) {
			Fields += TableFields[i] + " " + TableFieldsTypes[i];
			tempFields[i] = TableFields[i];
			if (i + 1 != TableFields.length) {
				Fields += ",";
			}

		}
		if(isLog)Log.i("DBFields", "DBFields : " + Fields);
		DBStaticsValue.Fields_NameAndTypes = Fields;
		DBStaticsValue.tempFields_Name = tempFields;
		DBStaticsValue.Table_Name = Table;
		DBCommander.db.open();
		DBCommander.db.Do_Custom_Query("PRAGMA foreign_keys = ON");
		DBCommander.A_Create_or_Open_DB();
		DBCommander.db.close();
	}

	public static void CreateTable(String TableName, String[] TableFields,
			String[] TableFieldsType) {
		DBCommander.db.open();
		String Fields = "";
		String[] tempFields = new String[TableFields.length];
		for (int i = 0; i < TableFields.length; i++) {
			Fields += TableFields[i] + " " + TableFieldsType[i];
			tempFields[i] = TableFields[i];
			if (i + 1 != TableFields.length) {
				Fields += ",";
			}
		}
		DBCommander.B_Create_newTable(TableName, Fields, tempFields);
		DBCommander.db.close();
	}

	public static void InsertRecord(String Table_Name, String[] Fields,
			String[] Values) {
		DBCommander.db.open();
		DBCommander.Insert_Record(Table_Name, Fields, Values);
		DBCommander.db.close();
	}
	public static void InsertRecord(String Table_Name, String[] Fields,
			String[] Values,byte[] bt) {
		DBCommander.db.open();
		DBCommander.Insert_Record(Table_Name, Fields, Values,bt);
		DBCommander.db.close();
	}
	public static HashMap<String, String> GetRecord(long id, String TableName,
			String[] Fields) {
		final HashMap<String, String> items = new HashMap<String, String>();
		DBCommander.db.open();

		Cursor c = null;
		try {
			c = DBCommander.Get_Record(id, TableName, Fields);
			if (c.moveToFirst()) {

				for (int i = 0; i < c.getCount(); i++) {

					for (int j = 0; j < Fields.length; j++) {
						int index = c.getColumnIndex(Fields[j]);
						items.put(Fields[j], c.getString(index).toString());
					}
					if (i + 1 < c.getCount()) {
						c.moveToNext();
					}
				}

			}
		} finally {
			c.close();
		}
		DBCommander.db.close();
		return items;
	}
	public static HashMap<String, String> GetRecord( String TableName,
			String[] Fields,String WhereCondition) {
		final HashMap<String, String> items = new HashMap<String, String>();
		DBCommander.db.open();

		Cursor c = null;
		try {
			c = DBCommander.Get_Records_customCond(TableName, Fields, WhereCondition, null, null, null, null, null, false);
			if (c.moveToFirst()) {

				for (int i = 0; i < c.getCount(); i++) {

					for (int j = 0; j < Fields.length; j++) {
						int index = c.getColumnIndex(Fields[j]);
						items.put(Fields[j], c.getString(index).toString());
					}
					if (i + 1 < c.getCount()) {
						c.moveToNext();
					}
				}

			}
		} finally {
			c.close();
		}
		DBCommander.db.close();
		return items;
	}
	public static HashMap<String, byte[]> GetBLOB( String TableName,
			String[] Fields,String WhereCondition) {
		final HashMap<String, byte[]> items = new HashMap<String, byte[]>();
		DBCommander.db.open();

		Cursor c = null;
		try {
			c = DBCommander.Get_Records_customCond(TableName, Fields, WhereCondition, null, null, null, null, null, false);
			if (c.moveToFirst()) {

				for (int i = 0; i < c.getCount(); i++) {

					for (int j = 0; j < Fields.length; j++) {
						int index = c.getColumnIndex(Fields[j]);
						items.put(Fields[j], c.getBlob(index));
					}
					if (i + 1 < c.getCount()) {
						c.moveToNext();
					}
				}

			}
		} finally {
			c.close();
		}
		DBCommander.db.close();
		return items;
	}
	public static HashMap<String, ArrayList<String>> GetAllRecord(String TableName,
			String[] Fields) {
		final HashMap<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();
		DBCommander.db.open();
		
		Cursor c = null;
		try {
			c = DBCommander.Get_AllRecord(TableName, Fields);
			if (c.moveToFirst()) {

				for (int i = 0; i < c.getCount(); i++) {

					for (int j = 0; j < Fields.length; j++) {
						int index = c.getColumnIndex(Fields[j]);
						if(isLog)Log.i("", "Value of "+Fields[j]+" : "+ c.getString(index).toString());
						ArrayList<String> oldval=new ArrayList<String>();
						if(items.get(Fields[j])!=null)oldval.addAll(items.get(Fields[j]));
						oldval.add(c.getString(index).toString());
						items.put(Fields[j],oldval);
						if(isLog)Log.i(""," New size of key : "+Fields[j]+" : "+items.get(Fields[j]).size());
					}
					if (i + 1 < c.getCount()) {
						c.moveToNext();
					}
				}

			}
		} finally {
			c.close();
		}
		DBCommander.db.close();
		return items;
	}
	public static HashMap<String, ArrayList<String>> GetAllRecord(String TableName,
			String[] Fields,String WhereCondition) {
		final HashMap<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();
		DBCommander.db.open();
		
		Cursor c = null;
		try {
			c = DBCommander.Get_Records_customCond(TableName, Fields, WhereCondition, null, null, null, null, null, true);
			if (c.moveToFirst()) {

				for (int i = 0; i < c.getCount(); i++) {

					for (int j = 0; j < Fields.length; j++) {
						int index = c.getColumnIndex(Fields[j]);
						if(isLog)Log.i("", "Value of "+Fields[j]+" : "+ c.getString(index).toString());
						ArrayList<String> oldval=new ArrayList<String>();
						if(items.get(Fields[j])!=null)oldval.addAll(items.get(Fields[j]));
						oldval.add(c.getString(index).toString());
						items.put(Fields[j],oldval);
						if(isLog)Log.i(""," New size of key : "+Fields[j]+" : "+items.get(Fields[j]).size());
					}
					if (i + 1 < c.getCount()) {
						c.moveToNext();
					}
				}

			}
		} finally {
			c.close();
		}
		DBCommander.db.close();
		return items;
	}
	public static HashMap<String, ArrayList<String>> GetAllRecord(String TableName,
			String[] Fields,String WhereCondition,String GroupBy) {
		final HashMap<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();
		DBCommander.db.open();
		
		Cursor c = null;
		try {
			c = DBCommander.Get_Records_customCond(TableName, Fields, WhereCondition, null, GroupBy, null, null, null, true);
			if (c.moveToFirst()) {

				for (int i = 0; i < c.getCount(); i++) {

					for (int j = 0; j < Fields.length; j++) {
						int index = c.getColumnIndex(Fields[j]);
						if(isLog)Log.i("", "Value of "+Fields[j]+" : "+ c.getString(index).toString());
						ArrayList<String> oldval=new ArrayList<String>();
						if(items.get(Fields[j])!=null)oldval.addAll(items.get(Fields[j]));
						oldval.add(c.getString(index).toString());
						items.put(Fields[j],oldval);
						if(isLog)Log.i(""," New size of key : "+Fields[j]+" : "+items.get(Fields[j]).size());
					}
					if (i + 1 < c.getCount()) {
						c.moveToNext();
					}
				}

			}
		} finally {
			c.close();
		}
		DBCommander.db.close();
		return items;
	}
	public static HashMap<String, ArrayList<String>> GetAllRecord(String TableName,
			String[] Fields,String WhereCondition,String OrderBy,String Limits) {
		final HashMap<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();
		DBCommander.db.open();
		
		Cursor c = null;
		try {
			c = DBCommander.Get_Records_customCond(TableName, Fields, WhereCondition, null, null, null, OrderBy, Limits, true);
			if (c.moveToFirst()) {

				for (int i = 0; i < c.getCount(); i++) {

					for (int j = 0; j < Fields.length; j++) {
						int index = c.getColumnIndex(Fields[j]);
						if(isLog)Log.i("", "Value of "+Fields[j]+" : "+ c.getString(index).toString());
						ArrayList<String> oldval=new ArrayList<String>();
						if(items.get(Fields[j])!=null)oldval.addAll(items.get(Fields[j]));
						oldval.add(c.getString(index).toString());
						items.put(Fields[j],oldval);
						if(isLog)Log.i(""," New size of key : "+Fields[j]+" : "+items.get(Fields[j]).size());
					}
					if (i + 1 < c.getCount()) {
						c.moveToNext();
					}
				}

			}
		} finally {
			c.close();
		}
		DBCommander.db.close();
		return items;
	}
	
	public static boolean DeleteRecord(long id,String TableName){
		DBCommander.db.open();
		boolean state = DBCommander.DeleteRecord(id, TableName);
		DBCommander.db.close();
		return state;
	}
	public static boolean DeleteAllRecord(String TableName){
		DBCommander.db.open();
		boolean state = DBCommander.DeleteAllRecord(TableName);
		DBCommander.db.close();
		return state;
	}
	public static boolean UpdateTable(long id,String TableName,String[] Fields,String[] Values){
		DBCommander.db.open();
		boolean state = DBCommander.UpdateRecord(id, TableName, Fields, Values);
		DBCommander.db.close();
		return state;
	}
	public static boolean UpdateTable(String TableName,String[] Fields,String[] Values,String WhereCondition){
		DBCommander.db.open();
		boolean state = DBCommander.UpdateRecord_customCond(TableName, Fields, Values, WhereCondition);
		DBCommander.db.close();
		return state;
	}
	public static ArrayList<String> GetTempFields(String TableName) {
		DBCommander.db.open();
		ArrayList<String> items=new ArrayList<String>();
		Cursor c = null;
		try {
			items.clear();
			c = DBCommander.Get_tempFields(TableName);
			if (c.moveToFirst()) {

				for (int i = 0; i < c.getCount(); i++) {
					int index=c.getColumnIndex("Field_Name");
					items.add(c.getString(index));
					if(isLog)Log.e("Table Name : "+TableName, "Field "+i+" : " +items.get(i));
					if (i + 1 < c.getCount()) {
						c.moveToNext();
					}
				}

			}
		} finally {
			c.close();
		}
		DBCommander.db.close();
		return items;
	}
	/**
	 * @param Query
	 * @return Cursor
	 * 
	 * <br>
	 * <b>Important : </b>use <b>iDatabaseManager.CloseConnection();</b> after you finished
	 */
	public static Cursor ExecuteQuery(String Query){
		DBCommander.db.open();
		return DBCommander.db.sql_ExcuteQurey(Query, null);
	}
	public static void NonQuery(String Query){
		DBCommander.db.open();
		DBCommander.db.Do_Custom_Query(Query);
		DBCommander.db.close();
	}
	public static void CloseConnection(){
	  DBCommander.db.close(); 
	}
}
