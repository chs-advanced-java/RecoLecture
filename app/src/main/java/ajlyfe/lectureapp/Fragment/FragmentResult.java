package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import ajlyfe.lectureapp.R;

public class FragmentResult extends Fragment {

    LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;
    private CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup mContainer,
                             Bundle mSavedInstanceState) {
        // Save the things
        inflater = mInflater;
        container = mContainer;
        savedInstanceState = mSavedInstanceState;

        // Edit the layout
        doThings();

        // Inflate the layout
        return inflater.inflate(R.layout.slide_result, container, false);
    }

    public void doThings() {
        RelativeLayout confirmBox = (RelativeLayout) container.findViewById(R.id.confirmBox);
        checkBox = (CheckBox) container.findViewById(R.id.checkBox);

        confirmBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
    }

    public boolean getCheckedOne() {
        return checkBox.isChecked();
    }
}