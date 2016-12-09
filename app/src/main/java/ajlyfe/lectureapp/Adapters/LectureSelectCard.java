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
}
