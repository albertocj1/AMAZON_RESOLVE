package Model;

import java.sql.Date;

public class complaint_ticket {
    int compt_ID;
    int complainant_ID;
    int admin_ID;
    String compt_Subject;
    String compt_Desc;
    String compt_OrderID;
    String compt_Category;
    String compt_ProdInfo;
    int compt_CustServRate;
    String compt_Status;
    boolean compt_DropOffSched;
    Date compt_CreatedDate;
    String compt_Dept;

    public complaint_ticket(int compt_ID, int complainant_ID, int admin_ID, String compt_Subject, String compt_Desc, String compt_OrderID, String compt_Category, String compt_ProdInfo, int compt_CustServRate, String compt_Status, boolean compt_DropOffSched, Date compt_CreatedDate, String compt_Dept) {
        this.compt_ID = compt_ID;
        this.complainant_ID = complainant_ID;
        this.admin_ID = admin_ID;
        this.compt_Subject = compt_Subject;
        this.compt_Desc = compt_Desc;
        this.compt_OrderID = compt_OrderID;
        this.compt_Category = compt_Category;
        this.compt_ProdInfo = compt_ProdInfo;
        this.compt_CustServRate = compt_CustServRate;
        this.compt_Status = compt_Status;
        this.compt_DropOffSched = compt_DropOffSched;
        this.compt_CreatedDate = compt_CreatedDate;
        this.compt_Dept = compt_Dept;
    }

    public int getCompt_ID() {
        return compt_ID;
    }

    public void setCompt_ID(int compt_ID) {
        this.compt_ID = compt_ID;
    }

    public int getComplainant_ID() {
        return complainant_ID;
    }

    public void setComplainant_ID(int complainant_ID) {
        this.complainant_ID = complainant_ID;
    }

    public int getAdmin_ID() {
        return admin_ID;
    }

    public void setAdmin_ID(int admin_ID) {
        this.admin_ID = admin_ID;
    }

    public String getCompt_Subject() {
        return compt_Subject;
    }

    public void setCompt_Subject(String compt_Subject) {
        this.compt_Subject = compt_Subject;
    }

    public String getCompt_Desc() {
        return compt_Desc;
    }

    public void setCompt_Desc(String compt_Desc) {
        this.compt_Desc = compt_Desc;
    }

    public String getCompt_OrderID() {
        return compt_OrderID;
    }

    public void setCompt_OrderID(String compt_OrderID) {
        this.compt_OrderID = compt_OrderID;
    }

    public String getCompt_Category() {
        return compt_Category;
    }

    public void setCompt_Category(String compt_Category) {
        this.compt_Category = compt_Category;
    }

    public String getCompt_ProdInfo() {
        return compt_ProdInfo;
    }

    public void setCompt_ProdInfo(String compt_ProdInfo) {
        this.compt_ProdInfo = compt_ProdInfo;
    }

    public int getCompt_CustServRate() {
        return compt_CustServRate;
    }

    public void setCompt_CustServRate(int compt_CustServRate) {
        this.compt_CustServRate = compt_CustServRate;
    }

    public String getCompt_Status() {
        return compt_Status;
    }

    public void setCompt_Status(String compt_Status) {
        this.compt_Status = compt_Status;
    }

    public boolean isCompt_DropOffSched() {
        return compt_DropOffSched;
    }

    public void setCompt_DropOffSched(boolean compt_DropOffSched) {
        this.compt_DropOffSched = compt_DropOffSched;
    }

    public Date getCompt_CreatedDate() {
        return compt_CreatedDate;
    }

    public void setCompt_CreatedDate(Date compt_CreatedDate) {
        this.compt_CreatedDate = compt_CreatedDate;
    }

    public String getCompt_Dept() {
        return compt_Dept;
    }

    public void setCompt_Dept(String compt_Dept) {
        this.compt_Dept = compt_Dept;
    }

}
