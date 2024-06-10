package Model;
import java.sql.Date;

public class complaint_history {
    int history_ID;
    int compt_ID;
    Date history_Date;
    String history_Status;

    public complaint_history(int history_ID, int compt_ID, Date history_Date, String history_Status) {
        this.history_ID = history_ID;
        this.compt_ID = compt_ID;
        this.history_Date = history_Date;
        this.history_Status = history_Status;
    }

    public int getHistory_ID() {
        return history_ID;
    }

    public void setHistory_ID(int history_ID) {
        this.history_ID = history_ID;
    }

    public int getCompt_ID() {
        return compt_ID;
    }

    public void setCompt_ID(int compt_ID) {
        this.compt_ID = compt_ID;
    }

    public Date getHistory_Date() {
        return history_Date;
    }

    public void setHistory_Date(Date history_Date) {
        this.history_Date = history_Date;
    }

    public String getHistory_Status() {
        return history_Status;
    }

    public void setHistory_Status(String history_Status) {
        this.history_Status = history_Status;
    }
    
}
