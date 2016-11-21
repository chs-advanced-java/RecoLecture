package ajlyfe.lectureapp.Adapters;

import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

public class StudentSelectCard {
    private String studentName;


    public StudentSelectCard(String sName) {
        studentName = sName;


    }

    public String getClassName() {
        return studentName;
    }



    public StudentSelectCard setClassName(String sName) {
        this.studentName = sName;
        return this;
    }


    public static ArrayList<StudentSelectCard> createList(int numClasses) {
        ArrayList<StudentSelectCard> classList = new ArrayList<>();

        //TODO: Pull from the database here::::
        for (int i = 1; i <= numClasses; i++) {
            classList.add(new StudentSelectCard("Student Name"));
        }

        return classList;
    }
}

