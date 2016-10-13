package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.LectureCheckbox;
import ajlyfe.lectureapp.R;

public class FragmentFile extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.slide_upload, container, false);

        // Edit the layout
        doThings();

        // Inflate the layout
        return view;
    }

    public void doThings() {  // Wildly ineffecient but i don't want to make another RecyclerView :/

    }
}