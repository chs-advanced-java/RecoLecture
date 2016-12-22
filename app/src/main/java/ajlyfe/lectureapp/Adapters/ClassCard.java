package ajlyfe.lectureapp.Adapters;

public class ClassCard {
    private String className;
    private String classDescription;

    public ClassCard(String className, String classDescription) {
        this.className = className;
        this.classDescription = classDescription;
    }

    public String getClassName() {
        return className;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public ClassCard setClassName(String cName) {
        this.className = cName;
        return this;
    }

    public ClassCard setClassDescription(String classDescription) {
        this.classDescription = classDescription;
        return this;
    }
}
