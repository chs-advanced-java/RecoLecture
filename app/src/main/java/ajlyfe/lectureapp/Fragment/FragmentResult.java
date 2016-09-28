package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import ajlyfe.lectureapp.R;

public class FragmentResult extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.slide_result, container, false);

        /*
        ImageView space = (ImageView) view.findViewById(R.id.greenSpace);

        Animation animSlide = AnimationUtils.loadAnimation(view.getContext(), R.anim.squeeze_right);

        space.startAnimation(animSlide);

*/
        return view;
    }
}