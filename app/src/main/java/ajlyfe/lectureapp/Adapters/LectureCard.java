package ajlyfe.lectureapp.Adapters;

import android.content.Context;

import java.util.ArrayList;

import ajlyfe.lectureapp.R;

public class LectureCard {
    private String lectureName;
    private String teacherName;

    public LectureCard(String lName, String tName) {
        lectureName = lName;
        teacherName = tName;
    }

    public String getLectureName() {
        return lectureName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public LectureCard setLectureName(String lName) {
        this.lectureName = lName;
        return this;
    }

    public LectureCard setTeacherName(String tName) {
        this.teacherName = tName;
        return this;
    }

    public static ArrayList<LectureCard> createLectureList(Context context) {
        ArrayList<LectureCard> lectureList = new ArrayList<>();

        //TODO: Pull from the database here::::
        lectureList.add(0, new LectureCard(context.getString(R.string.lecture_sample_name_1), "Plumieri"));

        return lectureList;
    }
}