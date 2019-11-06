package alsayed.aly.vacationsrecordingfortechnicians;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class AllOldNames extends AppCompatActivity {

    ListView old_nams_listView;
    ArrayList<String> oldNames;
    DBHelper dbh=new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_old_names);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradiant));
        //actionBar.setIcon(R.mipmap.ic_launcher_round);
        actionBar.setTitle("إجازاتك");

        old_nams_listView=(ListView)findViewById(R.id.oldNamsListView);
        setoldnamesadabter();
        old_nams_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openSingleNameReport(oldNames.get(position).toString());
            }
        });

    }

    public void openSingleNameReport(String name){
        SinglNameReport.setSsnn(name);
        Intent intent =new Intent(this,SinglNameReport.class);
        startActivity(intent);
    }


    public void setoldnamesadabter(){

        if(dbh.getoldnames().size()!=0) {
            oldNames = removeDuplicates(dbh.getoldnames());
        }else{
            Toast.makeText(this," قاعدة البيانات فارغة ",Toast.LENGTH_LONG).show();}
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, oldNames);
        old_nams_listView.setAdapter(namesAdapter);
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

}
