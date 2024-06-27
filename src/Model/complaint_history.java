package Model;
import java.time.LocalDate;

public class complaint_history {
    int history_ID;
    int resolution_ID;
    LocalDate history_Date;
    String history_Status;

    public complaint_history(int history_ID, int resolution_ID, LocalDate history_Date, String history_Status) {
        this.history_ID = history_ID;
        this.resolution_ID = resolution_ID;
        this.history_Date = history_Date;
        this.history_Status = history_Status;
    }

    public int getHistory_ID() {
        return history_ID;
    }

    public void setHistory_ID(int history_ID) {
        this.history_ID = history_ID;
    }

    public int getResolution_ID() {
        return resolution_ID;
    }

    public void setResolution_ID(int resolution_ID) {
        this.resolution_ID = resolution_ID;
    }

    public LocalDate getHistory_Date() {
        return history_Date;
    }

    public void setHistory_Date(LocalDate history_Date) {
        this.history_Date = history_Date;
    }

    public String getHistory_Status() {
        return history_Status;
    }

    public void setHistory_Status(String history_Status) {
        this.history_Status = history_Status;
    }
}
