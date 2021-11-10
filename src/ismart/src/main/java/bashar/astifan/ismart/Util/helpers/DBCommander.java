/* This Class By Bashar Astifan 
 * Year 2012 
 * 
 * How To Use : Important : When You Create New Table You Should Insert tempFields To Save Your Table Fields And Return Again if You Forget Them
 * tempFields=new String[]{"Name","Age","Year"};
 * T_Fields="Name VARCHAR,Age integer,Year date";
 * -------------------------------------------------------
 * DBStatics_Value.CTX=Classes_Test.this;
 * DBCommander.A_Create_DB(); Default Table From DBStatics_Value
 * DBCommander.B_Create_newTable(tableName, newTable_FiedlsAndTypes, tempFields)
 * DBCommander.Insert_Record(Table_Name, Fields, Values)
 * DBCommander.DeleteRecord(id, Table_Name)
 * DBCommander.Get_AllRecord(Table_Name, Fields)
 * DBCommander.Get_Record(id, Table_Name, Fields)
 * DBCommander.Get_Records_customCond(fromTable, returnFields, whereCondetion, selectionArgs[null], groupBy[null], having[null], orderBy[null], limit[null], allRecord[null])
 * DBCommander.UpdateRecord(id, Table_Name, Fields, Values)
 * DBCommander.UpdateRecord_customCond(Table_Name, Fields, Values, Condition)
 * DBCommander.Get_tempFields(Table_Name)
 * */

package bashar.astifan.ismart.Util.helpers;

import bashar.astifan.ismart.smart.mobi.iDatabaseManager;
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
public class DBCommander {
 public	static DBAdabter db = new DBAdabter(DBStaticsValue.CTX,
			DBStaticsValue.DB_Version, DBStaticsValue.DB_Name,
			DBStaticsValue.Table_Name, DBStaticsValue.Fields_NameAndTypes);

	public DBCommander() {
	}
	/**
	 * A_Create_or_Open_DB : <b>Open When DB Is Created
	 */
public static void A_Create_or_Open_DB() {
		// ----------------------------------
		// * Save Table Fields *
		// ----------------------------------
		Cursor c = null;
		try{
		 c = db.getFieldsRecords(DBStaticsValue.Table_Name);
		if (!c.moveToFirst()) {
			if(iDatabaseManager.isLog)Log.d("Table State", "Table Not Created Yet");
			db.fill_Fields(DBStaticsValue.tempFields_Name,
					DBStaticsValue.Table_Name);
		} else {
			if(iDatabaseManager.isLog)Log.d("Table State", "Table is Created");
		}
		}finally{
			c.close();
		}
	}
/**
 * B_Create_newTable
 * @param tableName
 * @param newTable_FiedlsAndTypes
 * @param tempFields
 * <p>
 * <b>Example:<p>
 * <code>T_Fields="Name VARCHAR,Age integer,Year date";</code>
 * <p><code>tempFields=new String[]{"Name","Age","Year"}; </code>
 */
	public static void B_Create_newTable(String tableName,
			String newTable_FiedlsAndTypes, String[] tempFields) {
		// ----------------------------------
		// * Create Table *
		// ----------------------------------
		if(tableName!=""&newTable_FiedlsAndTypes!=""&tempFields!=null){
		
		db.Create_new_Table(tableName, newTable_FiedlsAndTypes);
		// ----------------------------------
		// * Save Table Fields *
		// ----------------------------------
		Cursor a = null ;
		try{
		a= db.getFieldsRecords(tableName);
		if (!a.moveToFirst()) {
			if(iDatabaseManager.isLog)Log.d("New Table State", "Fields Saved");
			db.fill_Fields(tempFields,tableName);
		} else {
			if(iDatabaseManager.isLog)Log.d("New Table State", "Table is Created");
		}
		}finally
		{
			a.close();
		}
		}
	}
	/**
	 * Insert_Record
	 * @param Table_Name
	 * @param Fields
	 * @param Values
	 */
	public static void Insert_Record(String Table_Name,String[] Fields,String[] Values){
		if(Table_Name!=""&Fields!=null&Values!=null){
		
		db.insertRecord(Fields,Values,Table_Name);
		
		}
	}
	public static void Insert_Record(String Table_Name,String[] Fields,String[] Values,byte[] bt){
		if(Table_Name!=""&Fields!=null&Values!=null){
		
		db.insertRecord(Fields,Values,bt,Table_Name);
		
		}
	}
	/**
	 * Get_Record
	 * @param id
	 * @param Table_Name
	 * @param Fields
	 * @return Cursor
	 */
	public static Cursor Get_Record(long id,String Table_Name,String[] Fields){

		return db.getRecord(id, Fields,Table_Name);
		
		
	}
	/**
	 * Get_AllRecord
	 * @param Table_Name
	 * @param Fields
	 * @return Cursor
	 */
	public static Cursor Get_AllRecord(String Table_Name,String[] Fields){
		
		return db.getAllRecords(Fields,Table_Name);
	}
	/**
	 * UpdateRecord
	 * @param id
	 * @param Table_Name
	 * @param Fields
	 * @param Values
	 * @return boolean
	 */
	public static boolean UpdateRecord(long id,String Table_Name,String[] Fields,String[] Values){
		if(id!=0&Table_Name!=""&Fields!=null&Values!=null){
		
		boolean Res=db.updateRecord(id, Fields, Values,Table_Name);
		
		return Res;
		}else
		{
			return false;
		}
	}
	/**
	 *  DeleteRecord
	 * @param id
	 * @param Table_Name
	 * @return boolean
	 */
	public static boolean DeleteRecord(long id,String Table_Name){
		if(id!=0&Table_Name!=""){
		
		boolean Res=db.deleteRecord(id, Table_Name);
		
		return Res;
		}else{
			return false;
		}
		
	}
	
	
	/**
	 *  DeleteAllRecord
	 * @param Table_Name
	 * @return boolean
	 */
	public static boolean DeleteAllRecord(String Table_Name){
		if(Table_Name!=""){
		boolean Res=db.deleteallRecord(Table_Name);
		return Res;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Get_Records_customCond
	 * @param fromTable
	 * @param returnFields
	 * @param whereCondetion
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param limit
	 * @param allRecord
	 * @return Cursor
	 */
	public static Cursor Get_Records_customCond(String fromTable, String[] returnFields,
			String whereCondetion, String[] selectionArgs, String groupBy,
			String having, String orderBy, String limit, boolean allRecord){
		return db.select_Custom_Where(fromTable,returnFields , whereCondetion, selectionArgs, groupBy, having, orderBy,limit,allRecord);
	}
	/**
	 * UpdateRecord_customCond
	 * @param Table_Name
	 * @param Fields
	 * @param Values
	 * @param Condition
	 * @return boolean
	 */
	public static boolean UpdateRecord_customCond(String Table_Name,String[] Fields,String[] Values,String Condition){
		boolean Res=db.updateRecord_CustomCond(Fields, Values,Table_Name,Condition);
		return Res;
	}
	/**
	 * Get_tempFields
	 * @param Table_Name
	 * @return Cursor
	 */
	public static Cursor Get_tempFields(String Table_Name){
		return db.getFieldsRecords(Table_Name);
	}
}
