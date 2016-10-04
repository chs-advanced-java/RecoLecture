package ajlyfe.lectureapp.Adapters;

import java.util.ArrayList;

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

    public static ArrayList<LectureCard> createLectureList(int numLectures) {
        ArrayList<LectureCard> lectureList = new ArrayList<LectureCard>();

        for (int i = 1; i <= numLectures; i++) {
            lectureList.add(new LectureCard("Spanish" + i, "Plumezzzzz"));
        }

        return lectureList;
    }
}