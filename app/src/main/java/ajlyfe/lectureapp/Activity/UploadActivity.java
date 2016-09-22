package ajlyfe.lectureapp.Activity;

import ajlyfe.lectureapp.Fragment.*;
import ajlyfe.lectureapp.R;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class UploadActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DO NOT WRITE -> setContentView(R.layout.activity_upload);

        Fragment fragmentClass = new FragmentClass();
        Fragment fragmentFile = new FragmentFile();
        Fragment fragmentUpload = new FragmentUpload();
        Fragment fragmentResult = new FragmentResult();

        addSlide(fragmentFile);
        addSlide(fragmentClass);
        addSlide(fragmentUpload);
        addSlide(fragmentResult);

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(false);

        setIndicatorColor(R.color.colorPrimary50, R.color.colorPrimary20);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        int slideNumber = newFragment.getTag().charAt(newFragment.getTag().length() - 1) - 47;
    }
}
