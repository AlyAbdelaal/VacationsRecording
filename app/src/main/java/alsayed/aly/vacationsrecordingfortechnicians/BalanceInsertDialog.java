package alsayed.aly.vacationsrecordingfortechnicians;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BalanceInsertDialog extends AppCompatDialogFragment {
    TextView personName;
    EditText ordinaryBalance;
    EditText suddenBalance;
    Button saveBalance;
    Button cancel;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.balance_dialog_layout,null);

        suddenBalance=view.findViewById(R.id.sudden_balance_edit);
        ordinaryBalance=view.findViewById(R.id.ordinary_balance_edit);
        Cursor data =new DBHelper(getActivity()).getBalance(SinglNameReport.ssnn);
        if (data.getCount()!=0) {
            String o, s;
            if (data.moveToFirst()) {
                o = data.getString(data.getColumnIndex("ordinary"));
                s = data.getString(data.getColumnIndex("sudden"));
                ordinaryBalance.setText(o);
                suddenBalance.setText(s);
            }
        }

        personName=view.findViewById(R.id.p_name_view);
        personName.setText(SinglNameReport.ssnn);

        cancel=view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        saveBalance=view.findViewById(R.id.save_balance);
        saveBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveBalance(ordinaryBalance.getText().toString(),suddenBalance.getText().toString());
            }
        });

        builder.setView(view)
        .setTitle("الرصيد");

        return builder.create();
    }

    private void SaveBalance(String ordinary, String sudden) {

        int o = Integer.parseInt(ordinary);
        int s = Integer.parseInt(sudden);
        DBHelper db =new DBHelper(getActivity());
        if (db.getBalance(SinglNameReport.ssnn).getCount()==0) {
            db.insertVacationsBalance(SinglNameReport.ssnn, o, s);
        }else{db.updateBalance(SinglNameReport.ssnn,o,s);}
        //SinglNameReport.balanceTextView.setText(new SinglNameReport().GetBalance());
        dismiss();
    }
}
