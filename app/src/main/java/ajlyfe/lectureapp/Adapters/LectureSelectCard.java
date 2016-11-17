package ajlyfe.lectureapp.Adapters;

import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

public class LectureSelectCard {
    private String fileName;


    public LectureSelectCard(String fName) {
        fileName = fName;


    }
  
    public String getClassName() {
        return fileName;
    }



    public LectureSelectCard setClassName(String fName) {
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
