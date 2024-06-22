package Model;

public class admin {
    int admin_ID;
    String admin_FName;
    String admin_MName;
    String admin_LName;
    String admin_ContactNum;
    String admin_Username;
    String admin_Password;

public admin(int admin_ID, String admin_FName, String admin_MName, String admin_LName, String admin_ContactNum, String admin_Username, String admin_Password) {
    this.admin_ID = admin_ID;
    this.admin_FName = admin_FName;
    this.admin_MName = admin_MName;
    this.admin_LName = admin_LName;
    this.admin_ContactNum = admin_ContactNum;
    this.admin_Username = admin_Username;
    this.admin_Password = admin_Password;
    }

    public int getAdmin_ID() {
        return admin_ID;
    }

    public void setAdmin_ID(int admin_ID) {
        this.admin_ID = admin_ID;
    }

    public String getAdmin_FName() {
        return admin_FName;
    }

    public void setAdmin_FName(String admin_FName) {
        this.admin_FName = admin_FName;
    }

    public String getAdmin_MName() {
        return admin_MName;
    }

    public void setAdmin_MName(String admin_MName) {
        this.admin_MName = admin_MName;
    }

    public String getAdmin_LName() {
        return admin_LName;
    }

    public void setAdmin_LName(String admin_LName) {
        this.admin_LName = admin_LName;
    }

    public String getAdmin_ContactNum() {
        return admin_ContactNum;
    }

    public void setAdmin_ContactNum(String admin_ContactNum) {
        this.admin_ContactNum = admin_ContactNum;
    }

    public String getAdmin_Username() {
        return admin_Username;
    }

    public void setAdmin_Username(String admin_Username) {
        this.admin_Username = admin_Username;
    }

    public String getAdmin_Password() {
        return admin_Password;
    }

    public void setAdmin_Password(String admin_Password) {
        this.admin_Password = admin_Password;
    }


}
