package alsayed.aly.vacationsrecordingfortechnicians;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    ListView vNListView;
    Button addVac, delVac;
    EditText newVac;
    TextView delOldVacTV;
    LinearLayout linearLayout;
    ArrayList<String>vacationsNams;
    DBHelper dbh=new DBHelper(SettingActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        vNListView=(ListView)findViewById(R.id.vacation_list);
        addVac=(Button)findViewById(R.id.add_new_vac);
        delVac=(Button)findViewById(R.id.delete_vac);
        newVac=(EditText)findViewById(R.id.new_vac_edit);
        delOldVacTV=(TextView)findViewById(R.id.selected_vac);
        linearLayout=(LinearLayout)findViewById(R.id.lin_del);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.gray));

        vNListView.setAdapter(vacationsNamesAdapter());
        vNListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedVac= String.valueOf(parent.getItemAtPosition(position));
                delOldVacTV.setText(selectedVac);
                delVac.setEnabled(true);
                linearLayout.setBackgroundColor(getResources().getColor(R.color.backg));
                return true;
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delOldVacTV.setText("");
                delVac.setEnabled(false);
                linearLayout.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });
        addVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newV=newVac.getText().toString();
                if(!newV.equals("")) {
                    boolean res = dbh.insertNewVacation(newV);
                    if (res) {
                        vNListView.setAdapter(vacationsNamesAdapter());
                        Toast.makeText(getApplicationContext(), newVac.getText() + "تمت إضافة نوع إجازة جديدة بأسم : "
                                , Toast.LENGTH_SHORT).show();
                        newVac.setText("");
                    }
                }else{Toast.makeText(getApplicationContext(), "رجاء إدخال إسم لنوع الأجازة الجديدة"
                        , Toast.LENGTH_SHORT).show();}
            }
        });
        delVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog =new AlertDialog.Builder(SettingActivity.this);
                dialog.setTitle("رسالة تحذيرية");
                dialog.setMessage(delOldVacTV.getText()+" سيتم حذف نوع إجازة بأسم ");
                dialog.setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String delVacType=delOldVacTV.getText().toString();
                        dbh.deleteVacationType(delVacType);
                        delOldVacTV.setText("");
                        delVac.setEnabled(false);
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.gray));
                        vNListView.setAdapter(vacationsNamesAdapter());
                    }
                });
                dialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delOldVacTV.setText("");
                        delVac.setEnabled(false);
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.gray));
                        dialog.dismiss();
                    }
                });
                dialog.create();
                dialog.show();
            }
        });
    }
    public ArrayAdapter<String> vacationsNamesAdapter(){
        vacationsNams = dbh.getVacationsNames();
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, vacationsNams);
        return namesAdapter;
    }
}
