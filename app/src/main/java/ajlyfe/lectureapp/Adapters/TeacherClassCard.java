package ajlyfe.lectureapp.Adapters;

import java.util.ArrayList;

public class TeacherClassCard {
    private String name;
    private String description;

    public TeacherClassCard(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public TeacherClassCard setName(String cName) {
        this.name = cName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TeacherClassCard setClassDescription(String description) {
        this.description = description;
        return this;
    }

    public static ArrayList<TeacherClassCard> createList(int numClasses) {
        ArrayList<TeacherClassCard> classList = new ArrayList<>();

        //TODO: Pull from the database here::::
        for (int i = 1; i <= 3; i++) {
            TeacherClassCard tempCard = new TeacherClassCard("Spanish " + i, "Dummy description from a auto-generated class for testing purposes");
            classList.add(tempCard);
        }

        return classList;
    }
}
