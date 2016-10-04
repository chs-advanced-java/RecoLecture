package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import ajlyfe.lectureapp.R;

public class FragmentStudents extends Fragment {

    private boolean selectAllSelection;
    private View view;
    private Button selectAll;
    private CheckBox studentOne;
    private CheckBox studentTwo;
    private CheckBox studentThree;
    private CheckBox studentFour;
    private CheckBox studentFive;
    private CheckBox studentSix;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.slide_students, container, false);

        method();

        return view;
    }

    public void method() {
        selectAll = (Button) view.findViewById(R.id.selectAllButton);
        studentOne = (CheckBox) view.findViewById(R.id.studentBox1);
        studentTwo = (CheckBox) view.findViewById(R.id.studentBox2);
        studentThree = (CheckBox) view.findViewById(R.id.studentBox3);
        studentFour = (CheckBox) view.findViewById(R.id.studentBox4);
        studentFive = (CheckBox) view.findViewById(R.id.studentBox5);
        studentSix = (CheckBox) view.findViewById(R.id.studentBox6);

        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectAllSelection) {
                    studentOne.setChecked(true);
                    studentTwo.setChecked(true);
                    studentThree.setChecked(true);
                    studentFour.setChecked(true);
                    studentFive.setChecked(true);
                    studentSix.setChecked(true);
                    selectAllSelection = true;
                }
                else{
                    studentOne.setChecked(false);
                    studentTwo.setChecked(false);
                    studentThree.setChecked(false);
                    studentFour.setChecked(false);
                    studentFive.setChecked(false);
                    studentSix.setChecked(false);
                    selectAllSelection = false;
                }
            }
        });
    }

}