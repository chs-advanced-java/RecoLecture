package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

import ajlyfe.lectureapp.Adapters.LectureCard;
import ajlyfe.lectureapp.Adapters.LectureCardAdapter;
import ajlyfe.lectureapp.Adapters.LectureCheckbox;
import ajlyfe.lectureapp.Adapters.LectureCheckboxAdapter;
import ajlyfe.lectureapp.R;

public class FragmentUpload extends Fragment {

    private View view;
    private CheckBox checkBox;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.slide_upload, container, false);

        // Edit the layout
        doThings();

        // Inflate the layout
        return view;
    }

    public void doThings() {
        ArrayList<LectureCheckbox> lecturesChecked = parseTemporaryArray(
                getArguments().getStringArrayList("lecturesCheckedOff"));

        RecyclerView recyclerViewLectures = (RecyclerView) view.findViewById(R.id.checkboxRecycler);
        LectureCheckboxAdapter adapter = new LectureCheckboxAdapter(lecturesChecked, view.getContext());
        recyclerViewLectures.setAdapter(adapter);
        recyclerViewLectures.setLayoutManager(new LinearLayoutManager(view.getContext()));

        /*
        RelativeLayout confirmBox = (RelativeLayout) view.findViewById(R.id.confirmBox);
        checkBox = (CheckBox) view.findViewById(R.id.lectureCheck);
        button = (Button) view.findViewById(R.id.finishButton);

        confirmBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
                button.setClickable(button.isClickable());
            }
        });
        */
    }

    private ArrayList<LectureCheckbox> parseTemporaryArray(ArrayList<String> tArray) {
        ArrayList<LectureCheckbox> returnMe = new ArrayList<>();

        for (int i = 0; i < tArray.size(); i++) {
            returnMe.add(i, new LectureCheckbox(tArray.get(i)));
        }

        return returnMe;
    }
}