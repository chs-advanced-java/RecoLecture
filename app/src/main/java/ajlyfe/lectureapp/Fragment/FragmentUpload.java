package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import ajlyfe.lectureapp.R;

public class FragmentUpload extends Fragment {

    private View view;
    private CheckBox checkBox;

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
        RelativeLayout confirmBox = (RelativeLayout) view.findViewById(R.id.confirmBox);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        confirmBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
    }
}