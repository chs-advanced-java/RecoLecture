package ajlyfe.lectureapp.Adapters;

import java.util.ArrayList;

public class TeacherClassCard {
    private String className;

    public TeacherClassCard(String cName) {
        className = cName;
    }

    public String getClassName() {
        return className;
    }

    public TeacherClassCard setClassName(String cName) {
        this.className = cName;
        return this;
    }

    public static ArrayList<TeacherClassCard> createList(int numClasses) {
        ArrayList<TeacherClassCard> classList = new ArrayList<>();

        //TODO: Pull from the database here::::
        for (int i = 1; i <= numClasses; i++) {
            TeacherClassCard tempCard = new TeacherClassCard("Spanish" + i);
            classList.add(tempCard);
        }

        return classList;
    }
}
