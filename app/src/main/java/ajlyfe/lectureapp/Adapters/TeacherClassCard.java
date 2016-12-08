package ajlyfe.lectureapp.Adapters;

import ajlyfe.lectureapp.Activity.TeacherClassOverview;

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

    public boolean isDummy() {
        return description.equals(TeacherClassOverview.AUTO_DESCRIPTION);
    }
}
