package alsayed.aly.vacationsrecordingfortechnicians;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class allReport extends AppCompatActivity {
public ListView listView1;
public DBHelper mydb;
public String nas="",vaca="",datee="",smonth="";
public String nnn="";
public static int idd,vacPosition;

//*******
ArrayList<myArray> vacList;
    myArray marray;


    //*********

    public String getNnn() {
        return nnn;
    }
    public static boolean isActionMode;
    public static List<Integer> UserSelection = new ArrayList<>();
    public static List<Integer> selectedPosition = new ArrayList<>();
    public static ActionMode actionMode = null;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_report);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradiant));
        //actionBar.setIcon(R.mipmap.ic_launcher_round);
        actionBar.setTitle("إجازاتك");

        listView1 = (ListView) findViewById(R.id.listview1);
        listView1.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView1.setMultiChoiceModeListener(modeListener);


        showa();




        if (selectedPosition.size()!=0) {
            for (int i = 0; i < vacList.size(); i++) {

            }
        }
        //listView1.getChildAt(5).findViewById(R.id.allCont).setBackgroundColor(R.color.gray);
        //listView1.getAdapter().getItem(5).equals(R.id.allCont);


        /*
        listView1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Alert").setMessage("Do you really want to delete this record ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });
                return false;
            }
        });*/
    }



    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu2,menu);

            isActionMode=true;
            actionMode=mode;

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.del8:

                    AlertDialog.Builder dialog =new AlertDialog.Builder(allReport.this);
                    dialog.setTitle("حذف" +UserSelection.size()+" تسجيلاً                     ");

                    dialog.setMessage("سيتم حذف التسجيلات رقم : "+UserSelection);
                    dialog.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mydb=new DBHelper(allReport.this);
                            for(int iD:UserSelection){
                            mydb.deleteRecord(iD);
                            }
                            showa();
                            mode.finish();
                            Toast.makeText(getApplicationContext(),"  تم الحذف  ",Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.show();
                    return true;
                case R.id.search8:
                    if (UserSelection.size()!=1){
                        Toast.makeText(getApplicationContext(),"  رجاءً تحديد اختيار واحد فقط  ",Toast.LENGTH_LONG).show();
                    }else{
                        Intent intent=new Intent(getApplicationContext(),SinglNameReport.class);
                        startActivity(intent);
                        mode.finish();
                    }
                    return true;
                    default:
                        return false;

            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionMode=false;
            actionMode=null;
            UserSelection.clear();
        }
    };

/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu1,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.singlereporto:
                Intent intent=new Intent(getApplicationContext(),SinglNameReport.class);
                startActivity(intent);
                return true;
            case R.id.delet:
                AlertDialog.Builder dialog =new AlertDialog.Builder(allReport.this);
                dialog.setTitle("حذف تسجيل                     ");

                dialog.setMessage("سيتم حذف التسجيل رقم : "+idd);
                dialog.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mydb=new DBHelper(allReport.this);
                        mydb.deleteRecord(idd);
                        showa();
                        Toast.makeText(getApplicationContext(),"  تم الحذف  ",Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();

                return true;

            default:
                return super.onContextItemSelected(item);

        }

    }
*/
    public void showa(){
        mydb=new DBHelper(this);
        vacList = new ArrayList<>();
        Cursor data = mydb.getlistrecored();
        int numRows = data.getCount();
        if (numRows == 0){
            Toast.makeText(this," قاعدة البيانات فارغة ",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()) {
                marray = new myArray(data.getString(1), data.getString(2), data.getString(3), data.getString(0));
                vacList.add(marray);

            }
            final CustomAdapter adapter = new CustomAdapter(this, R.layout.adapter_view_layout, vacList);

            listView1.setAdapter(adapter);


        }
    }



}
