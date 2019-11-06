package alsayed.aly.vacationsrecordingfortechnicians;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<myArray>
{
    private LayoutInflater minflater;
    private ArrayList<myArray> marrays;
    private int mViewResourceId;





    public CustomAdapter(Context context, int textViewReasourceId, ArrayList<myArray> marrays)
    {
        super(context, textViewReasourceId,marrays);

        this.marrays = marrays;
        minflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewReasourceId;
    }

    static class ViewHolder {
        public TextView name;
        public TextView vacation;
        public TextView date;
        public TextView idd;
        public TextView pos;
        public CheckBox checkbox ;
        public LinearLayout itemV;
    }

    @SuppressLint("ResourceAsColor")
    public View getView(final int position, View converterView, final ViewGroup parents){
        final ViewHolder holder;


        if (converterView==null){
            holder=new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            converterView = inflater.inflate(R.layout.adapter_view_layout,null);
            holder.name=(TextView)converterView.findViewById(R.id.TextView1);
            holder.vacation=(TextView)converterView.findViewById(R.id.TextView2);
            holder.date=(TextView)converterView.findViewById(R.id.TextView3);
            holder.idd=(TextView)converterView.findViewById(R.id.TextView4);
            holder.pos=(TextView)converterView.findViewById(R.id.TextView5);
            holder.checkbox=(CheckBox) converterView.findViewById(R.id.checkbox);
            holder.itemV=(LinearLayout)converterView.findViewById(R.id.allCont);
            converterView.setTag(holder);
        }else{
            holder=(ViewHolder) converterView.getTag();
        }
        final myArray marray = marrays.get(position);
        if(marray!=null) {
            holder.name.setText(marray.getName());
            holder.vacation.setText(marray.getVac());
            holder.date.setText(marray.getDate());
            holder.idd.setText(marray.getIdd());
            holder.pos.setText((position+1)+"");
            //holder.checkbox.setTag(position);
            holder.checkbox.setChecked(marray.isChecked());


        }


            if(allReport.isActionMode){
                holder.checkbox.setVisibility(View.VISIBLE);
            }else{
                holder.checkbox.setVisibility(View.GONE);
            }

            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {


                    if (allReport.UserSelection.contains(Integer.parseInt(marrays.get(position).getIdd())))
                    {
                        marray.setChecked(false);
                        allReport.UserSelection.remove(new Integer(Integer.parseInt(marrays.get(position).getIdd())));
                    }else{

                        allReport.UserSelection.add(Integer.parseInt(marrays.get(position).getIdd()));
                        marray.setChecked(true);
                    }


                    if(allReport.UserSelection.size()==0){allReport.actionMode.setTitle("حدد إختياراتك ");}
                    if(allReport.UserSelection.size()==1){
                        allReport.actionMode.setTitle("تم تحديد إختيار واحد");
                        SinglNameReport.setSsnn(marrays.get(position).getName());
                    }
                    if(allReport.UserSelection.size()==2){allReport.actionMode.setTitle("تم تحديد إختيارين");}
                    if(allReport.UserSelection.size()>2) {
                        allReport.actionMode.setTitle("تم تحديد "+allReport.UserSelection.size() + "  إختيارات  ");
                    }
                }
            });




        return converterView;
    }



}
