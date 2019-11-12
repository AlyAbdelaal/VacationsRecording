package alsayed.aly.vacationsrecordingfortechnicians;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public String searchn="Ali Ahmed";
    public static final String DATABASE_NAME="Vacation.db";
    public static final String TABLEVACATIONS="vacations";
    public static final String NAME="name";
    public static final String DATE="date";
    public static final String ID="id";
    public static final String MONTH="month";
    public static final String VACATION="vacation";
    public static final String VACATIONSNAMES="settings";

    public String selectedYear = MainActivity.SHOW_YEAR;


    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table vacations (id integer primary key AUTOINCREMENT,name text ,vacation text,date text,MONTH text,year text)");
        db.execSQL("create table o_vacations (id integer primary key ,name text ,vacation text,date text,MONTH text)");
        db.execSQL("create table settings (vid integer primary key AUTOINCREMENT,vname text)");

        db.execSQL("create table vacations_balance (p_id integer primary key AUTOINCREMENT,p_name text,ordinary int,sudden int)");

        db.execSQL("INSERT INTO settings (vname) VALUES ('أعتيادي'),('عارضة'),('بدل راحة'),('إعتذار عن يوم'),('إعتذار عن الوقت الإضافي'),('إعتذار سفن'),('إذن شخصي'),('إذن تأخير'),('مرضي'),('مأمورية رسمية'),('مأمورية عيادة')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS vacations");
        onCreate(db);

    }
    public boolean insertvacation (String name,String vacation,String date,String month){
        SQLiteDatabase dp=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("vacation",vacation);
        contentValues.put("date",date);
        contentValues.put("month",month);
        //contentValues.put("year",year);
        dp.insert("vacations",null,contentValues);
        return true;
    }
    //-------------vacationsBalance--------------
    //---------insert----
    public boolean insertVacationsBalance (String p_name,int ordinary,int sudden){
        SQLiteDatabase db=this.getWritableDatabase();
        //db.execSQL("CREATE TABLE IF  NOT EXISTS vacations_balance (p_id integer primary key AUTOINCREMENT,p_name text,ordinary int,sudden int)");
        ContentValues contentValues=new ContentValues();
        contentValues.put("p_name",p_name);
        contentValues.put("ordinary",ordinary);
        contentValues.put("sudden",sudden);

        db.insert("vacations_balance",null,contentValues);
        return true;
    }
    //----------update-----
    public boolean updateBalance (String p_name,int ordinary,int sudden)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ordinary",ordinary);
        contentValues.put("sudden",sudden);
        db.update("vacations_balance",contentValues,"p_name = ?",new String[]{p_name});
        return true;
    }
    //----------getBalance---
    public Cursor getBalance(String p_name){
        SQLiteDatabase ddbb = this.getReadableDatabase();
        Cursor data = ddbb.rawQuery("SELECT * FROM vacations_balance WHERE p_name = ? ",new String[]{p_name});
        return data;
    }

    //---------------------------------------------
    public Integer deleteVacationType (String dn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("settings","vname = ?",new String[]{dn});
    }
    public boolean insertNewVacation (String vName){
        SQLiteDatabase dp=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("vname",vName);
        dp.insert("settings",null,contentValues);
        return true;
    }
    public ArrayList<String> getVacationsNames(){
        ArrayList<String>vn=new ArrayList<String>();
        SQLiteDatabase ddbb = this.getReadableDatabase();
        Cursor data = ddbb.rawQuery("select * from settings ",null);
        while (data.moveToNext()){
            vn.add(data.getString(1));
        }

        return vn;
    }

    //----------------------------------------------
    public Integer deleteRecord (int dn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("vacations","ID = ?",new String[]{Integer.toString(dn)});
    }
    //----------------------------------------------

   public boolean updatrecord (int id,String name,String date,String vacation,String month)
   {
       SQLiteDatabase db=this.getWritableDatabase();
       ContentValues contentValues=new ContentValues();
       contentValues.put("name",name);
       contentValues.put("vacation",vacation);
       contentValues.put("date",date);
       contentValues.put("month",month);
       db.update("vacations",contentValues,"ID = ?",new String[]{Integer.toString(id)});
       return true;
   }


    public Cursor getnamsearch(String ns){
        SQLiteDatabase ddbb = this.getReadableDatabase();
        //حل مشكلة السنة من قراءة السنة من التاريخ وعرض سنة محددة
        Cursor data = ddbb.rawQuery("select * ,substr(date,11,4) AS year from vacations where NAME = ? AND year = ?  ORDER BY NAME, MONTH , date ",new String[]{ns,selectedYear});
        return data;
    }
    public Cursor getnammonthsearch(String ns,String month){
        SQLiteDatabase ddbb = this.getReadableDatabase();
        Cursor data = ddbb.rawQuery("select * ,substr(date,11,4) AS year from vacations where NAME = ? AND MONTH = ? AND year = ? ORDER BY date ",new String[]{ns,month,selectedYear});
        return data;
    }
    public Cursor getnamvacationsearch(String ns,String vacation){
        SQLiteDatabase ddbb = this.getReadableDatabase();
        Cursor data = ddbb.rawQuery("select * ,substr(date,11,4) AS year from vacations where NAME = ? AND VACATION = ? AND year = ? ORDER BY MONTH ,date",new String[]{ns,vacation,selectedYear});
        return data;
    }
    public Cursor getnammonthvacationsearch(String ns,String month,String vacation){
        SQLiteDatabase ddbb = this.getReadableDatabase();
        Cursor data = ddbb.rawQuery("select * ,substr(date,11,4) AS year from vacations where NAME = ? AND MONTH = ? AND VACATION =? AND year = ? ORDER BY date",new String[]{ns,month,vacation,selectedYear});
        return data;
    }
/*
    public Cursor getlistrecored(){
        SQLiteDatabase ddbb = this.getReadableDatabase();
        Cursor data = ddbb.rawQuery("SELECT * FROM vacations",null);
        return data;
    }
    */
    public Cursor getlistrecored(){
        SQLiteDatabase ddbb = this.getReadableDatabase();
        Cursor data = ddbb.rawQuery("SELECT * ,substr(date,11,4) AS year FROM vacations WHERE year = ? ORDER BY NAME ,MONTH , date ",new String[]{selectedYear});
        return data;
    }



    public Cursor getmoth(int id){
        SQLiteDatabase ddbb = this.getReadableDatabase();
        Cursor data =ddbb.rawQuery("select * ,substr(date,11,4) AS year from vacations where ID = ? AND year = ?",new String[]{Integer.toString(id),selectedYear});
        return data;
    }
    public ArrayList<String> getoldnames(){
        SQLiteDatabase ddbb = this.getReadableDatabase();
        Cursor data =ddbb.rawQuery("select * ,substr(date,11,4) AS year from vacations ORDER BY NAME ",null);
        ArrayList<String> oldnames=new ArrayList<String>();
        while (data.moveToNext() ) {
            oldnames.add(data.getString(1));
        }
        return oldnames;
    }

    public boolean checkNewRecod(String ns,String vac,String dat)
    {
        ArrayList arrayListch=new ArrayList();
        SQLiteDatabase db =this.getReadableDatabase();
        //String[]params=new String[]{ns,mon};
        Cursor res=db.rawQuery("select * ,substr(date,11,4) AS year from vacations where NAME = ? AND DATE = ? AND VACATION =?",new String[]{ns,dat,vac});
        res.moveToFirst();
        while(res.isAfterLast()==false){
            arrayListch.add(res.getString(res.getColumnIndex(ID)));

            res.moveToNext();
        }
        if(arrayListch==null) {
            return true;
        }else return false;
    }
    public boolean checkNewRecod2(String ns,String vac,String dat)
    {
        ArrayList arrayListch=new ArrayList();
        SQLiteDatabase db =this.getReadableDatabase();
        //String[]params=new String[]{ns,mon};
        Cursor res=db.rawQuery("select * ,substr(date,11,4) AS year from vacations where NAME = ? AND DATE = ? AND VACATION =?",new String[]{ns,dat,vac});
        res.moveToFirst();
        if(res.getCount()>0){
            res.close();
            return true;


        }else{return false;}

    }
    /*
    public boolean insertToOld (){

        //Cursor data = db.rawQuery("SELECT * FROM o_vacation",null);
        SQLiteDatabase db=this.getWritableDatabase();
        try{
            db.execSQL("INSERT INTO o_vacations SELECT * FROM vacations");
            db.execSQL("DELETE FROM vacations");
        }catch (SQLiteException e){
            db.execSQL("create table o_vacations (id integer primary key ,name text ,vacation text,date text,MONTH text)");
            db.execSQL("INSERT INTO o_vacations SELECT * FROM vacations");
            db.execSQL("DELETE FROM vacations");
        }

        return true;
    }
    public String recordYear(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data=db.rawQuery("SELECT DATE FROM vacations LIMIT 1",null);
        ArrayList<String> rda=new ArrayList<String>();
        while (data.moveToNext() ) {
            rda.add(data.getString(0));
        }

        if(data.getCount()==1){
            String rd =rda.get(0);
            String r =rd.substring(rd.length()-4);

            return r;
        }else{return "0";}
    }

*/

}
