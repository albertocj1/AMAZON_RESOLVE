package Model;

public class department {
    int dept_ID;
    String dept_Head;
    String dept_Name;
    String dept_Description;

    public department(int dept_ID, String dept_Head, String dept_Name, String dept_Description) {
        this.dept_ID = dept_ID;
        this.dept_Head = dept_Head;
        this.dept_Name = dept_Name;
        this.dept_Description = dept_Description;
    }

    public int getDept_ID() {
        return dept_ID;
    }

    public void setDept_ID(int dept_ID) {
        this.dept_ID = dept_ID;
    }

    public String getDept_Head() {
        return dept_Head;
    }

    public void setDept_Head(String dept_Head) {
        this.dept_Head = dept_Head;
    }

    public String getDept_Name() {
        return dept_Name;
    }

    public void setDept_Name(String dept_Name) {
        this.dept_Name = dept_Name;
    }

    public String getDept_Description() {
        return dept_Description;
    }

    public void setDept_Description(String dept_Description) {
        this.dept_Description = dept_Description;
    }

    
}
