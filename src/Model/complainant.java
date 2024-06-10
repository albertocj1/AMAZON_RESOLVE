package Model;

public class complainant {
    int complainant_ID;
    String complainant_FName;
    String complainant_MName;
    String complainant_LName;
    String complainant_ContactNum;
    String complainant_EmailAdd;
    String complainant_HouseNum;
    String complainant_Brgy;
    String complainant_Street;
    String complainant_City;

    public complainant(int complainant_ID, String complainant_FName, String complainant_MName, String complainant_LName, String complainant_ContactNum, String complainant_EmailAdd, String complainant_HouseNum, String complainant_Brgy, String complainant_Street, String complainant_City) {
        this.complainant_ID = complainant_ID;
        this.complainant_FName = complainant_FName;
        this.complainant_MName = complainant_MName;
        this.complainant_LName = complainant_LName;
        this.complainant_ContactNum = complainant_ContactNum;
        this.complainant_EmailAdd = complainant_EmailAdd;
        this.complainant_HouseNum = complainant_HouseNum;
        this.complainant_Brgy = complainant_Brgy;
        this.complainant_Street = complainant_Street;
        this.complainant_City = complainant_City;
    }

    public int getComplainant_ID() {
        return complainant_ID;
    }

    public void setComplainant_ID(int complainant_ID) {
        this.complainant_ID = complainant_ID;
    }

    public String getComplainant_FName() {
        return complainant_FName;
    }

    public void setComplainant_FName(String complainant_FName) {
        this.complainant_FName = complainant_FName;
    }

    public String getComplainant_MName() {
        return complainant_MName;
    }

    public void setComplainant_MName(String complainant_MName) {
        this.complainant_MName = complainant_MName;
    }

    public String getComplainant_LName() {
        return complainant_LName;
    }

    public void setComplainant_LName(String complainant_LName) {
        this.complainant_LName = complainant_LName;
    }

    public String getComplainant_ContactNum() {
        return complainant_ContactNum;
    }

    public void setComplainant_ContactNum(String complainant_ContactNum) {
        this.complainant_ContactNum = complainant_ContactNum;
    }

    public String getComplainant_EmailAdd() {
        return complainant_EmailAdd;
    }

    public void setComplainant_EmailAdd(String complainant_EmailAdd) {
        this.complainant_EmailAdd = complainant_EmailAdd;
    }

    public String getComplainant_HouseNum() {
        return complainant_HouseNum;
    }

    public void setComplainant_HouseNum(String complainant_HouseNum) {
        this.complainant_HouseNum = complainant_HouseNum;
    }

    public String getComplainant_Brgy() {
        return complainant_Brgy;
    }

    public void setComplainant_Brgy(String complainant_Brgy) {
        this.complainant_Brgy = complainant_Brgy;
    }

    public String getComplainant_Street() {
        return complainant_Street;
    }

    public void setComplainant_Street(String complainant_Street) {
        this.complainant_Street = complainant_Street;
    }

    public String getComplainant_City() {
        return complainant_City;
    }

    public void setComplainant_City(String complainant_City) {
        this.complainant_City = complainant_City;
    }
    
}
