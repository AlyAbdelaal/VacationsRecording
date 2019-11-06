package alsayed.aly.vacationsrecordingfortechnicians;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import hotchemi.android.rate.AppRate;


public class MainActivity extends AppCompatActivity {

    Button addrecord;
    Button allrecords;
    TextView oldn;
    static boolean showDialog=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        actionBar.setTitle("إجازاتك");
        AppRate.with(this).setInstallDays(0)
                .setLaunchTimes(2).setRemindInterval(1).monitor();
        AppRate.showRateDialogIfMeetsConditions(this);

        addrecord=(Button)findViewById(R.id.addrecord);
        addrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddActivity();
            }
        });
        allrecords=(Button)findViewById(R.id.allreport);
        allrecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,allReport.class));
            }
        });

        oldn=(TextView)findViewById(R.id.reNams);
        oldn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opennamsActivity();
            }
        });

        Calendar calendar=Calendar.getInstance();
        int c_Year = calendar.get(Calendar.YEAR);
        int r_year=Integer.parseInt(new DBHelper(this).recordYear());
        Toast.makeText(this,r_year+"",Toast.LENGTH_SHORT).show();
        if (c_Year!=r_year){detectNewYear();}
    }

    private void detectNewYear() {
        new DBHelper(this).insertToOld();
        new AlertDialog.Builder(this)
                .setTitle(" السنة الجديدة "  )
                .setMessage( "تم الآن تنظيف السجلات بمناسبة حلول السنة الجديدة ")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDialog=false;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void openAddActivity(){
        Intent intent=new Intent(this,add.class);
        startActivity(intent);

    }

    public void opennamsActivity(){
        Intent intent=new Intent(this,AllOldNames.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.settingmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.setVacation:
                Intent intent =new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }

    }


}
