package com.o3sa.mobipugapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.o3sa.mobipugapp.dumpdata.DumpData;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kiran on 23-10-2018.
 */

public class Database extends SQLiteOpenHelper {


    Context context;

    public String Product_id= "pro_id";
    public String Product_name="pro_name";
    public String Product_qty="pro_qty";
    public String Product_image="pro_img";
    public String Product_price="pro_price";
    public String Product_AttrId="pro_attrid";
    public String Mycart_table= "mycart";
    public String UserId= "userid";
    public Database(Context applicationcontext) {
        super(applicationcontext, "mobipugapp.db", null, 2);
        Log.d("database","Created");
        context = applicationcontext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query,mycart_query;
        query = "CREATE TABLE userdata ( idno INTEGER PRIMARY KEY,Userid Text,UserType Text)";
        db.execSQL(query);
        query = "insert into userdata(Userid,UserType)values('0','0')";
        Log.i("query", "query"+query);
        db.execSQL(query);
        mycart_query = "CREATE TABLE "+ Mycart_table
                +"( idno INTEGER PRIMARY KEY,"+ UserId + " Text,"+ Product_id + " Text,"+ Product_name+" Text,"
                + Product_qty+" Text,"+ Product_image+" Text,"+ Product_price+" Text,"+ Product_AttrId+" Text"+")";
        db.execSQL(mycart_query);
        Log.d("userdata","userdata Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS userdata";
        db.execSQL(query);
        onCreate(db);

    }

    public ArrayList<HashMap<String, String>> getAllDevice() {

        StoredObjects.LogMethod("hello","hello");
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM userdata";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);



        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    StoredObjects.LogMethod("hello","hello"+"<>"+cursor.getColumnNames()+"<>"+database.getVersion());
                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("idno", cursor.getString(cursor.getColumnIndex("idno")));
                    map.put("Userid", cursor.getString(cursor.getColumnIndex("Userid")));
                    map.put("UserType", cursor.getString(cursor.getColumnIndex("UserType")));
                    wordList.add(map);

                    StoredObjects.UserId = cursor.getString(1);
                    StoredObjects.UserType=cursor.getString(2);

                } while (cursor.moveToNext());

            }
        }

        return wordList;
    }
    public void insertID(String userId) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Userid", userId);
        database.insert("userdata", null, values);
        database.close();

    }

    public void deleteLastDataTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("userdata", null, null);
    }
    public void UpdateUserId(String userId) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Userid", userId);
        database.update("userdata", values,null, null);//insert("userdata", null, values);
        database.close();

    }
    public void UpdateUserType(String userId) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserType", userId);
        database.update("userdata", values,null, null);//insert("userdata", null, values);
        database.close();

    }


    public void Delete_MyCart_Items(String user_id,String item_id) {

        StoredObjects.LogMethod("hello","hello"+user_id+"------"+item_id);

        String selectQuery = "DELETE  FROM " + Mycart_table +" where "+UserId +"="+user_id +" and " +Product_id +"="+item_id ;
        StoredObjects.LogMethod("hello","hello:--"+selectQuery);
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(selectQuery);

    }
    public void Delete_MyCarttable(String user_id){
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(Mycart_table, UserId + "=?", new String[] { user_id });
    }
    public ArrayList<DumpData> GetMyCartData(String user_id) {
        ArrayList<DumpData> wordList;
        wordList = new ArrayList<DumpData>();
        wordList.clear();
        String selectQuery = "SELECT  * FROM " + Mycart_table +" where "+UserId +"="+user_id ;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {


                DumpData data = new DumpData();
                data.cart_customerid = cursor.getString(1);
                data.cart_proid = cursor.getString(2);
                data.cart_proname = cursor.getString(3);
                data.cart_proqty = cursor.getString(4);
                data.cart_proimg = cursor.getString(5);
                data.cart_proprice = cursor.getString(6);
                data.cart_attrid=cursor.getString(7);
                wordList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return wordList;
    }
    public void Update_Mycart_Items(String user_id,String item_id,String quanity) {

        ArrayList<DumpData> wordList;
        wordList = new ArrayList<DumpData>();
        wordList.clear();
        String selectQuery = "UPDATE " + Mycart_table +" SET "+ Product_qty +" = "+ quanity  +" where "+UserId +"="+user_id +" and " +Product_id +" = "+item_id ;

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(selectQuery);


    }

    public void MyCart_insert( String user_id, String product_id,String pro_name, String pro_qty, String pro_img
            , String pro_price,String product_AttrId) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UserId, user_id);
        values.put(Product_id, product_id);
        values.put(Product_name, pro_name);
        values.put(Product_qty, pro_qty);
        values.put(Product_image, pro_img);
        values.put(Product_price, pro_price);

        values.put(Product_AttrId, product_AttrId);
        database.insert(Mycart_table, null, values);
        database.close();

    }

    public boolean checkproductfromcart(String User_id,String ItemId_) {

        String[] columns = {
                UserId
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = UserId + " = ?" + " AND " + Product_id + " = ?";
        String[] selectionArgs = {User_id, ItemId_};
        Cursor cursor = db.query(Mycart_table, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

}

