package ajlyfe.lectureapp.Adapters;

import java.util.ArrayList;

public class LectureSelectCard {
    private String fileName;
    private boolean checked;

    public LectureSelectCard(String fName) {
        fileName = fName;
        checked = false;

    }
  
    public String getFileName() {
        return fileName;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean c) {
        checked = c;
    }

    public LectureSelectCard setFileName(String fName) {
        this.fileName = fName;
        return this;
    }


    public static ArrayList<LectureSelectCard> createList(int numClasses) {
        ArrayList<LectureSelectCard> classList = new ArrayList<>();

        //TODO: Pull from the database here::::
        for (int i = 1; i <= numClasses; i++) {
            classList.add(new LectureSelectCard("File Name"));
        }

        return classList;
    }
}
