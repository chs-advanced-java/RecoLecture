package ajlyfe.lectureapp.Adapters;

import android.support.annotation.Nullable;

import ajlyfe.lectureapp.Activity.TeacherClassOverview;

public class TeacherClassCard {
    private String name;
    private String description;
    private String code;

    /** Class code can be null if it is a dummy class, otherwise we have a problem.**/
    public TeacherClassCard(String name, String description, @Nullable String code) {
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public TeacherClassCard setClassDescription(String description) {
        this.description = description;
        return this;
    }

    public TeacherClassCard setName(String cName) {
        this.name = cName;
        return this;
    }

    public boolean isDummy() {
        return description.equals(TeacherClassOverview.AUTO_DESCRIPTION);
    }
}
