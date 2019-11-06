package alsayed.aly.vacationsrecordingfortechnicians;

public class BalanceArray {
    int id;
    String p_name;
    int ordinary;
    int sudden;

    public BalanceArray(String p_name, int ordinary, int sudden) {
        this.p_name = p_name;
        this.ordinary = ordinary;
        this.sudden = sudden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public int getOrdinary() {
        return ordinary;
    }

    public void setOrdinary(int ordinary) {
        this.ordinary = ordinary;
    }

    public int getSudden() {
        return sudden;
    }

    public void setSudden(int sudden) {
        this.sudden = sudden;
    }
}
