package alsayed.aly.vacationsrecordingfortechnicians;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class add extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    AutoCompleteTextView editText;
    EditText nOfDays;
    private TextView datepicker;
    String Vactyp;
    public Button save,updat;
    public String sMonth;
    public DBHelper dbh=new DBHelper(this);
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public ArrayList<String> oldNames , autoNames,vacationsNams;
    public int dday, dmonth ,dyear ,ndays;
    //--------update strings
    public static int a=0;

    public static void setUpid(int upid) {
        add.upid = upid;
    }

    public static int upid,vacPosition;

    public static void setVacPosition(int vacPosition) {
        add.vacPosition = vacPosition;
    }

    public static String upname,upvac,upda;

    public static void setUpname(String upname) {
        add.upname = upname;
    }

    public static void setUpvac(String upvac) {
        add.upvac = upvac;
    }

    public static void setUpda(String upda) {
        add.upda = upda;
    }

    public static void setA(int a) {

        add.a = a;
    }
    public Calendar vdate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradiant));
        //actionBar.setIcon(R.mipmap.ic_launcher_round);
        actionBar.setTitle("إجازاتك");



        save=(Button)findViewById(R.id.save);
        editText=(AutoCompleteTextView)findViewById(R.id.nameEditetext);

        datepicker=(TextView)findViewById(R.id.datepicker);
        spinner=(Spinner)findViewById(R.id.spinner1);
        nOfDays=(EditText)findViewById(R.id.num_of_days);

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal =Calendar.getInstance();
                dyear =cal.get(Calendar.YEAR);
                dmonth =cal.get(Calendar.MONTH);
                dday =cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog DDialog;
                DDialog = new DatePickerDialog(
                        add.this,android.R.style.Theme_Holo_Light_Dialog
                        ,mDateSetListener,dyear,dmonth,dday);
                DDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                DDialog.show();
            }
        });


        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dmonth=month;
                month=month+1;
                sMonth=Integer.toString((month));
                datepicker.setText(dayOfMonth+"/"+month+"/"+year);
                dday=dayOfMonth;

                dyear=year;
                //SimpleDateFormat sdf =new SimpleDateFormat("dd /MM /yyyy");
                //Calendar vdate=new GregorianCalendar(year,month,dayOfMonth);
                //datepicker.setText(sdf.format(vdate.getTime()));

            }
        };
        //---------------------------------------------
        /**/
        //final ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.vacations,android.R.layout.simple_spinner_item);
        vacationsNams=dbh.getVacationsNames();
        //final ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.vacations,android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vacationsNams);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //================================



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ndays=parseInt(nOfDays.getText().toString());

                if (editText.getText().toString().length() == 0 ||
                        Vactyp.toString().equalsIgnoreCase("إختر")  ||
                        datepicker.getText().toString().equalsIgnoreCase( "حدد التاريخ")) {
                    Toast.makeText(getApplicationContext(), "أكمل البيانات", Toast.LENGTH_SHORT).show();
                    boolean rch = dbh.checkNewRecod(editText.getText().toString(), Vactyp, datepicker.getText().toString());
                }else{
                    if (dbh.checkNewRecod2(editText.getText().toString(), Vactyp, datepicker.getText().toString())==true) {
                        Toast.makeText(getApplicationContext(),"هذه الأجازة مسجلة مسبقا",Toast.LENGTH_LONG).show();

                    }else{
                        //dayay(day+i,month,year)
                        for(int i=0;i<ndays;i++){
                            dbh.insertvacation(editText.getText().toString(), Vactyp, dayay(i), dayaym(i));
                        }
                        //dbh.insertvacation(editText.getText().toString(), Vactyp, datepicker.getText().toString(), sMonth);

                        Toast.makeText(getApplicationContext()," تم حفظ "+"\n"+ editText.getText() + "\n" + Vactyp + "\n" + datepicker.getText(), Toast.LENGTH_SHORT).show();
                    }
                }
                setoldnamesadabter();
                resetViewOb();
            }


        });
        if(dbh.getlistrecored().getCount()==0){
            Toast.makeText(this,"إدخل أول تسجيل",Toast.LENGTH_SHORT).show();
        }else {
            setoldnamesadabter();

        }
    }
    public void resetViewOb(){
        editText.setText("");
        datepicker.setText("حدد التاريخ");
        spinner.setSelection(0);
        nOfDays.setText("1");
    }
    //------------------------------
    public void setoldnamesadabter(){
        oldNames = removeDuplicates(dbh.getoldnames());
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, oldNames);
        editText.setAdapter(namesAdapter);
    }


    //------------------------------
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        Set<T> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }
        //--------------------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Vactyp =parent.getItemAtPosition(position).toString();
       // Toast.makeText(parent.getContext(),Vactyp,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
    public String dayay (int d , int m , int y ){
        String dd= d + " / "+m+" / " + y;
        return dd;
    }
    */

    public String dayay (int i){
        SimpleDateFormat sdf =new SimpleDateFormat("dd / MM / yyyy");

        Calendar calendar=new GregorianCalendar(dyear,dmonth,dday) ;
        calendar.add(Calendar.DAY_OF_MONTH,i);
        int d=calendar.get(Calendar.DAY_OF_MONTH);
        int m=calendar.get(Calendar.MONTH);
        int y=calendar.get(Calendar.YEAR);

        String dd = d +" /"+m+" /"+y;
        return sdf.format(calendar.getTime()).toString();
    }
    public String dayaym (int i){
        SimpleDateFormat sdf =new SimpleDateFormat("dd / MM / yyyy");

        Calendar calendar=new GregorianCalendar(dyear,dmonth,dday) ;
        calendar.add(Calendar.DAY_OF_MONTH,i);
        int d=calendar.get(Calendar.DAY_OF_MONTH);
        int m=calendar.get(Calendar.MONTH);
        int y=calendar.get(Calendar.YEAR);

        String dd = d +" /"+m+" /"+y;
        return m+"";
    }
    public String year (int i){
        SimpleDateFormat sdf =new SimpleDateFormat("dd / MM / yyyy");

        Calendar calendar=new GregorianCalendar(dyear,dmonth,dday) ;
        calendar.add(Calendar.DAY_OF_MONTH,i);
        int d=calendar.get(Calendar.DAY_OF_MONTH);
        int m=calendar.get(Calendar.MONTH);
        int y=calendar.get(Calendar.YEAR);

        String dd = d +" /"+m+" /"+y;
        return y+"";
    }



}
