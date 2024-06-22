package Model;
import java.sql.Date;

public class resolution {
    int resolution_ID;
    int compt_ID;
    int dept_ID;
    String resolution_Details;
    String resolution_Status;
    Date resolution_Date;

    public resolution(int resolution_ID, int compt_ID, int dept_ID, String resolution_Details, String resolution_Status, Date resolution_Date) {
        this.resolution_ID = resolution_ID;
        this.compt_ID = compt_ID;
        this.dept_ID = dept_ID;
        this.resolution_Details = resolution_Details;
        this.resolution_Status = resolution_Status;
        this.resolution_Date = resolution_Date;
    }

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
    
}
