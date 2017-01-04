package ajlyfe.lectureapp.Adapters;

import android.support.annotation.Nullable;

public class ClassCard {
    private String className;
    private String classDescription;
    private String classCode;

    /** Class code can be null if it is a dummy class, otherwise we have a problem.**/
    public ClassCard(String className, String classDescription, @Nullable String classCode) {
        this.className = className;
        this.classDescription = classDescription;
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public String getClassCode() {
        return classCode;
    }

    public ClassCard setClassName(String cName) {
        this.className = cName;
        return this;
    }

    public ClassCard setClassDescription(String classDescription) {
        this.classDescription = classDescription;
        return this;
    }

    public ClassCard setClassCode(String classCode) {
        this.classCode = classCode;
        return this;
    }
}
