package ajlyfe.lectureapp.Adapters;

import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

public class ClassSelectCard {
    private String className;


    public ClassSelectCard(String cName) {
        className = cName;


    }

    public String getClassName() {
        return className;
    }



    public ClassSelectCard setClassName(String cName) {
        this.className = cName;
        return this;
    }


    public static ArrayList<ClassSelectCard> createList(int numClasses) {
        ArrayList<ClassSelectCard> classList = new ArrayList<>();

        //TODO: Pull from the database here::::
        for (int i = 1; i <= numClasses; i++) {
            classList.add(new ClassSelectCard("Class Name"));
        }

        return classList;
    }
}

