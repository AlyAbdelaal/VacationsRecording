package alsayed.aly.vacationsrecordingfortechnicians;

import android.app.Dialog;
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
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.balance_dialog_layout,null);

        ordinaryBalance=view.findViewById(R.id.ordinary_balance_edit);
        personName=view.findViewById(R.id.p_name_view);
        personName.setText(SinglNameReport.ssnn);
        suddenBalance=view.findViewById(R.id.sudden_balance_edit);
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
        db.insertVacationsBalance(SinglNameReport.ssnn,o,s);
        dismiss();
    }
}
