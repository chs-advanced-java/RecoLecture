package ajlyfe.lectureapp.Adapters;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

import ajlyfe.lectureapp.R;

public class LectureCard {
    private String lectureName;
    private String teacherName;
    private File lectureFile;

    public LectureCard(String lectureName, String teacherName, File lectureFile) {
        this.lectureName = lectureName;
        this.teacherName = teacherName;
        this.lectureFile = lectureFile;
    }

    public String getLectureName() {
        return lectureName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public File getLectureFile() {
        return lectureFile;
    }

    public LectureCard setLectureName(String lName) {
        this.lectureName = lName;
        return this;
    }

    public LectureCard setTeacherName(String tName) {
        this.teacherName = tName;
        return this;
    }

    public LectureCard setLectureFile(File lectureFile) {
        this.lectureFile = lectureFile;
        return this;
    }
}