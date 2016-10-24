package ajlyfe.lectureapp.Adapters;

import java.util.ArrayList;

public class ClassCard {
    private String className;
    private String teacherName;

    public ClassCard(String cName, String tName) {
        className = cName;
        teacherName = tName;
    }

    public String getClassName() {
        return className;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public ClassCard setClassName(String cName) {
        this.className = cName;
        return this;
    }

    public ClassCard setTeacherName(String tName) {
        this.teacherName = tName;
        return this;
    }

    public static ArrayList<ClassCard> createList(int numClasses) {
        ArrayList<ClassCard> classList = new ArrayList<>();

        //TODO: Pull from the database here::::
        for (int i = 1; i <= numClasses; i++) {
            classList.add(new ClassCard("Spanish " + i, "Plumeri"));
        }

        return classList;
    }
}
