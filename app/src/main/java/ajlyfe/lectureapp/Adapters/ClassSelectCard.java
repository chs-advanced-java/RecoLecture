package ajlyfe.lectureapp.Adapters;

import java.util.ArrayList;

public class ClassSelectCard {
    private String className;
    private boolean checked;


    public ClassSelectCard(String cName) {
        className = cName;
        checked = false;
    }

    public String getClassName() {
        return className;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean c) {
        checked = c;
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

