package alsayed.aly.vacationsrecordingfortechnicians;

public class myArray {
    private String Name;
    private String Vac;
    private String Date;
    private String Idd;
    private boolean checked;

    public myArray(String dName,String dVac,String dDate,String dId){
        Name=dName;
        Vac=dVac;
        Date=dDate;
        Idd=dId;

    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return Name;
    }

    public String getVac() {
        return Vac;
    }

    public String getDate() {
        return Date;
    }

    public String getIdd() {
        return Idd;
    }
}
