// package Model;

// import java.sql.Date;

// public class resolution {
//     private int resolution_ID;
//     private int compt_ID;
//     private int dept_ID;
//     private String complainant_ID;
//     private String resolution_Details;
//     private String resolution_Status;
//     private Date resolution_Date;
//     private String complainant_Name;
//     private String compt_Subject;
//     private String compt_Category;
//     private Date comp_CreatedDate;

//     private boolean isAUpdated;
//     private boolean isBUpdated;
//     private boolean isCUpdated;
//     private boolean isDUpdated;
//     private boolean isEUpdated;
//     private boolean isFUpdated;

//     // Constructor
//     public resolution(int resolution_ID, int compt_ID, int dept_ID, String complainant_ID, String resolution_Details, String resolution_Status, Date resolution_Date) {
//         this.resolution_ID = resolution_ID;
//         this.compt_ID = compt_ID;
//         this.dept_ID = dept_ID;
//         this.complainant_ID = complainant_ID;
//         this.resolution_Details = resolution_Details;
//         this.resolution_Status = resolution_Status;
//         this.resolution_Date = resolution_Date;
//     }

//     // Getters and Setters
//     public int getResolution_ID() {
//         return resolution_ID;
//     }

//     public void setResolution_ID(int resolution_ID) {
//         this.resolution_ID = resolution_ID;
//     }

//     public int getCompt_ID() {
//         return compt_ID;
//     }

//     public void setCompt_ID(int compt_ID) {
//         this.compt_ID = compt_ID;
//     }

//     public int getDept_ID() {
//         return dept_ID;
//     }

//     public void setDept_ID(int dept_ID) {
//         this.dept_ID = dept_ID;
//     }

//     public String getComplainant_ID() {
//         return complainant_ID;
//     }

//     public void setComplainant_ID(String complainant_ID) {
//         this.complainant_ID = complainant_ID;
//     }

//     public String getResolution_Details() {
//         return resolution_Details;
//     }

//     public void setResolution_Details(String resolution_Details) {
//         this.resolution_Details = resolution_Details;
//     }

//     public String getResolution_Status() {
//         return resolution_Status;
//     }

//     public void setResolution_Status(String resolution_Status) {
//         this.resolution_Status = resolution_Status;
//     }

//     public Date getResolution_Date() {
//         return resolution_Date;
//     }

//     public void setResolution_Date(Date resolution_Date) {
//         this.resolution_Date = resolution_Date;
//     }

//     public String getComplainant_Name() {
//         return complainant_Name;
//     }

//     public void setComplainant_Name(String complainant_Name) {
//         this.complainant_Name = complainant_Name;
//     }

//     public String getCompt_Subject() {
//         return compt_Subject;
//     }

//     public void setCompt_Subject(String compt_Subject) {
//         this.compt_Subject = compt_Subject;
//     }

//     public String getCompt_Category() {
//         return compt_Category;
//     }

//     public void setCompt_Category(String compt_Category) {
//         this.compt_Category = compt_Category;
//     }

//     public Date getComp_CreatedDate() {
//         return comp_CreatedDate;
//     }

//     public void setComp_CreatedDate(Date comp_CreatedDate) {
//         this.comp_CreatedDate = comp_CreatedDate;
//     }

//     public boolean isAUpdated() {
//         return isAUpdated;
//     }

//     public void setAUpdated(boolean isAUpdated) {
//         this.isAUpdated = isAUpdated;
//     }

//     public boolean isBUpdated() {
//         return isBUpdated;
//     }

//     public void setBUpdated(boolean isBUpdated) {
//         this.isBUpdated = isBUpdated;
//     }

//     public boolean isCUpdated() {
//         return isCUpdated;
//     }

//     public void setCUpdated(boolean isCUpdated) {
//         this.isCUpdated = isCUpdated;
//     }

//     public boolean isDUpdated() {
//         return isDUpdated;
//     }

//     public void setDUpdated(boolean isDUpdated) {
//         this.isDUpdated = isDUpdated;
//     }

//     public boolean isEUpdated() {
//         return isEUpdated;
//     }

//     public void setEUpdated(boolean isEUpdated) {
//         this.isEUpdated = isEUpdated;
//     }

//     public boolean isFUpdated() {
//         return isFUpdated;
//     }

//     public void setFUpdated(boolean isFUpdated) {
//         this.isFUpdated = isFUpdated;
//     }
// }

package Model;

import java.sql.Date;

public class resolution {
    private int resolution_ID;
    private int compt_ID;
    private int dept_ID;
    private String complainant_ID;
    private String resolution_Details;
    private String resolution_Status;
    private Date resolution_Date;
    private String complainant_Name;
    private String compt_Subject;
    private String compt_Category;
    private Date comp_CreatedDate;

    private int admin_ID;
    private String compt_Desc;
    private String compt_OrderID;
    private String compt_ProdInfo;
    private int compt_CustServRate;
    private String compt_Status;
    private String compt_Dept;

    private boolean isAUpdated;
    private boolean isBUpdated;
    private boolean isCUpdated;
    private boolean isDUpdated;
    private boolean isEUpdated;
    private boolean isFUpdated;

    // Constructor
    public resolution(int resolution_ID, int compt_ID, int dept_ID, String complainant_ID, String resolution_Details, String resolution_Status, Date resolution_Date) {
        this.resolution_ID = resolution_ID;
        this.compt_ID = compt_ID;
        this.dept_ID = dept_ID;
        this.complainant_ID = complainant_ID;
        this.resolution_Details = resolution_Details;
        this.resolution_Status = resolution_Status;
        this.resolution_Date = resolution_Date;
    }

    // Getters and Setters
    public int getResolution_ID() {
        return resolution_ID;
    }

    public void setResolution_ID(int resolution_ID) {
        this.resolution_ID = resolution_ID;
    }

    public int getCompt_ID() {
        return compt_ID;
    }

    public void setCompt_ID(int compt_ID) {
        this.compt_ID = compt_ID;
    }

    public int getDept_ID() {
        return dept_ID;
    }

    public void setDept_ID(int dept_ID) {
        this.dept_ID = dept_ID;
    }

    public String getComplainant_ID() {
        return complainant_ID;
    }

    public void setComplainant_ID(String complainant_ID) {
        this.complainant_ID = complainant_ID;
    }

    public String getResolution_Details() {
        return resolution_Details;
    }

    public void setResolution_Details(String resolution_Details) {
        this.resolution_Details = resolution_Details;
    }

    public String getResolution_Status() {
        return resolution_Status;
    }

    public void setResolution_Status(String resolution_Status) {
        this.resolution_Status = resolution_Status;
    }

    public Date getResolution_Date() {
        return resolution_Date;
    }

    public void setResolution_Date(Date resolution_Date) {
        this.resolution_Date = resolution_Date;
    }

    public String getComplainant_Name() {
        return complainant_Name;
    }

    public void setComplainant_Name(String complainant_Name) {
        this.complainant_Name = complainant_Name;
    }

    public String getCompt_Subject() {
        return compt_Subject;
    }

    public void setCompt_Subject(String compt_Subject) {
        this.compt_Subject = compt_Subject;
    }

    public String getCompt_Category() {
        return compt_Category;
    }

    public void setCompt_Category(String compt_Category) {
        this.compt_Category = compt_Category;
    }

    public Date getComp_CreatedDate() {
        return comp_CreatedDate;
    }

    public void setComp_CreatedDate(Date comp_CreatedDate) {
        this.comp_CreatedDate = comp_CreatedDate;
    }

    public int getAdmin_ID() {
        return admin_ID;
    }

    public void setAdmin_ID(int admin_ID) {
        this.admin_ID = admin_ID;
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

    public String getCompt_Dept() {
        return compt_Dept;
    }

    public void setCompt_Dept(String compt_Dept) {
        this.compt_Dept = compt_Dept;
    }

    public boolean isAUpdated() {
        return isAUpdated;
    }

    public void setAUpdated(boolean isAUpdated) {
        this.isAUpdated = isAUpdated;
    }

    public boolean isBUpdated() {
        return isBUpdated;
    }

    public void setBUpdated(boolean isBUpdated) {
        this.isBUpdated = isBUpdated;
    }

    public boolean isCUpdated() {
        return isCUpdated;
    }

    public void setCUpdated(boolean isCUpdated) {
        this.isCUpdated = isCUpdated;
    }

    public boolean isDUpdated() {
        return isDUpdated;
    }

    public void setDUpdated(boolean isDUpdated) {
        this.isDUpdated = isDUpdated;
    }

    public boolean isEUpdated() {
        return isEUpdated;
    }

    public void setEUpdated(boolean isEUpdated) {
        this.isEUpdated = isEUpdated;
    }

    public boolean isFUpdated() {
        return isFUpdated;
    }

    public void setFUpdated(boolean isFUpdated) {
        this.isFUpdated = isFUpdated;
    }
}
