package ajlyfe.lectureapp.Adapters;

import android.widget.CheckBox;

public class StudentSelectCard {
    private String studentName;
    private boolean checked;
    private CheckBox checkBox;


    public StudentSelectCard(String sName, CheckBox check) {
        studentName = sName;
        checked = false;
        checkBox = check;
    }

    public String getClassName() {
        return studentName;
    }

    public StudentSelectCard setClassName(String sName) {
        this.studentName = sName;
        return this;
    }

    public void setChecked(boolean c){
        checked = c;
        checkBox.setChecked(c);
    }

    public boolean getChecked(){
        return checked;
    }

}

