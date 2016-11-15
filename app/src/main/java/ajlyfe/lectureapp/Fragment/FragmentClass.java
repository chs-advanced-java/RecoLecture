package ajlyfe.lectureapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class FragmentClass extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.slide_class, container, false);
    }
}