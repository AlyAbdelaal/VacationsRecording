package alsayed.aly.vacationsrecordingfortechnicians;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class SinglNameReport extends AppCompatActivity {

    Button searchButton,excelf;
    public TextView sname;
    TextView count;

    public static String ssnn = "ass";
    public static void setSsnn(String ssnn) {
        SinglNameReport.ssnn = ssnn;
    }

    public static String smonth ,svacation ;



    Spinner spinnerm ,monthd,vacationd;
    ListView listView21;
    DBHelper dbh2;
    ArrayList<myArray> vacList;
    myArray marray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singl_name_report);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradiant));
        //actionBar.setIcon(R.mipmap.ic_launcher_round);
        actionBar.setTitle("إجازاتك");
        //---------------------------------
        count=(TextView)findViewById(R.id.edit_sum);
        listView21=(ListView)findViewById(R.id.listview21);
        shown();
        sname=(TextView)findViewById(R.id.edit_query);
        sname.setText(ssnn);
        //--------------search by name and month------------
        searchButton=(Button)findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shownm();
                final AlertDialog.Builder aDialog =new AlertDialog.Builder(SinglNameReport.this);
                aDialog.setTitle("البحث خاص لـ              "+ssnn+"       ");
                View mview=getLayoutInflater().inflate(R.layout.search_layout,null);

                monthd=(Spinner)mview.findViewById(R.id.monthsp);
                ArrayAdapter<String> adapterm =new
                        ArrayAdapter<String>(SinglNameReport.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.months));
                adapterm.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                monthd.setAdapter(adapterm);
                //-------------------------------------
                vacationd=(Spinner)mview.findViewById(R.id.vacation);
                ArrayAdapter<String> adapterv =new
                        ArrayAdapter<String>(SinglNameReport.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.vacations));
                adapterv.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                vacationd.setAdapter(adapterv);

                //-------------------------------------
                aDialog.setPositiveButton("بحث", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(monthd.getSelectedItem().toString().equalsIgnoreCase("إختر") &&
                                vacationd.getSelectedItem().toString().equalsIgnoreCase("إختر"))
                        {
                            Toast.makeText(getApplicationContext(),"   بالإسم فقط   "+"\n"+"لأنك لم تختر شيء",Toast.LENGTH_SHORT).show();
                            shown();
                        }
                        if(!monthd.getSelectedItem().toString().equalsIgnoreCase("إختر") &&
                                vacationd.getSelectedItem().toString().equalsIgnoreCase("إختر"))
                        {smonth=(Integer.parseInt(monthd.getSelectedItem().toString())-1)+"";
                            shownm();}
                        if(monthd.getSelectedItem().toString().equalsIgnoreCase("إختر") &&
                                !vacationd.getSelectedItem().toString().equalsIgnoreCase("إختر"))
                        {svacation=vacationd.getSelectedItem().toString();
                            shownv();}
                        if(!monthd.getSelectedItem().toString().equalsIgnoreCase("إختر") &&
                                !vacationd.getSelectedItem().toString().equalsIgnoreCase("إختر"))
                        {
                            smonth=(Integer.parseInt(monthd.getSelectedItem().toString())-1)+"";
                            svacation=vacationd.getSelectedItem().toString();
                            shownmv();}

                    }
                });
                aDialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                aDialog.setView(mview);
                aDialog.show();
            }
        });
        excelf=(Button)findViewById(R.id.excelb);
        excelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v7.app.AlertDialog.Builder dialo =new android.support.v7.app.AlertDialog.Builder(SinglNameReport.this);
                dialo.setTitle("ادخل اسم المف");
                final EditText input = new EditText(SinglNameReport.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                dialo.setView(input);


                dialo.setPositiveButton("موافق",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String xn =input.getText().toString();
                                all2excel2(xn);

                            }
                        }
                );
                dialo.show();
            }
        });


    }

    public void shown(){
        dbh2=new DBHelper(this);
        vacList = new ArrayList<>();
        Cursor data = dbh2.getnamsearch(ssnn);
        int numRows = data.getCount();
        count.setText("عدد التسجيلات   " + numRows);
        if (numRows == 0){
            Toast.makeText(this,"data base is empty",Toast.LENGTH_LONG).show();

        }else {
            while (data.moveToNext()){
                marray = new myArray(data.getString(1),data.getString(2),data.getString(3),data.getString(0));
                vacList.add(marray);
            }
            CustomAdapter adapter = new CustomAdapter(this,R.layout.adapter_view_layout,vacList);
            listView21 = (ListView)findViewById(R.id.listview21);
            listView21.setAdapter(adapter);
        }
    }
    public void shownm(){
        dbh2=new DBHelper(this);
        vacList = new ArrayList<>();
        Cursor data = dbh2.getnammonthsearch(ssnn,smonth);
        int numRows = data.getCount();
        count.setText("عدد التسجيلات   " + numRows);
        if (numRows == 0){
            CustomAdapter adapter = new CustomAdapter(this,R.layout.adapter_view_layout,vacList);
            listView21 = (ListView)findViewById(R.id.listview21);
            listView21.setAdapter(adapter);
            Toast.makeText(this,"data base is empty",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()){
                marray = new myArray(data.getString(1),data.getString(2),data.getString(3),data.getString(0));
                vacList.add(marray);
            }
            CustomAdapter adapter = new CustomAdapter(this,R.layout.adapter_view_layout,vacList);
            listView21 = (ListView)findViewById(R.id.listview21);
            listView21.setAdapter(adapter);
        }
    }
    public void shownmv(){
        dbh2=new DBHelper(this);
        vacList = new ArrayList<>();
        Cursor data = dbh2.getnammonthvacationsearch(ssnn,smonth,svacation);
        int numRows = data.getCount();
        count.setText("عدد التسجيلات   " + numRows);
        if (numRows == 0){
            CustomAdapter adapter = new CustomAdapter(this,R.layout.adapter_view_layout,vacList);
            listView21 = (ListView)findViewById(R.id.listview21);
            listView21.setAdapter(adapter);
            Toast.makeText(this,"data base is empty",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()){
                marray = new myArray(data.getString(1),data.getString(2),data.getString(3),data.getString(0));
                vacList.add(marray);
            }
            CustomAdapter adapter = new CustomAdapter(this,R.layout.adapter_view_layout,vacList);
            listView21 = (ListView)findViewById(R.id.listview21);
            listView21.setAdapter(adapter);
        }
    }
    public void shownv(){
        dbh2=new DBHelper(this);
        vacList = new ArrayList<>();
        Cursor data = dbh2.getnamvacationsearch(ssnn,svacation);
        int numRows = data.getCount();
        count.setText("عدد التسجيلات   " + numRows);
        if (numRows == 0){
            CustomAdapter adapter = new CustomAdapter(this,R.layout.adapter_view_layout,vacList);
            listView21 = (ListView)findViewById(R.id.listview21);
            listView21.setAdapter(adapter);
            Toast.makeText(this,"data base is empty",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()){
                marray = new myArray(data.getString(1),data.getString(2),data.getString(3),data.getString(0));
                vacList.add(marray);
            }
            CustomAdapter adapter = new CustomAdapter(this,R.layout.adapter_view_layout,vacList);
            listView21 = (ListView)findViewById(R.id.listview21);
            listView21.setAdapter(adapter);
        }
    }



     public void all2excel2(String xlsnam){

        File sd = Environment.getExternalStorageDirectory();
        String csvFile = xlsnam+".xls";
        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("userList", 0);
            // column and row
            sheet.addCell(new Label(0, 0, "الاسم"));
            sheet.addCell(new Label(1, 0, "الأجازة"));
            sheet.addCell(new Label(2, 0, "التاريخ"));
            int numRows = listView21.getAdapter().getCount();


            for(int i = 0; i < numRows; i++){
                //exList=userList;
                myArray aa=vacList.get(i);
                String name=aa.getName();
                String vac=aa.getVac();
                String dat=aa.getDate();
                sheet.addCell(new Label(0, i+1, name));
                sheet.addCell(new Label(1, i+1, vac));
                sheet.addCell(new Label(2, i+1, dat));
            }

            workbook.write();
            workbook.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public void balance(View view) {
    }
}
