package ajlyfe.lectureapp.Activity;

import ajlyfe.lectureapp.Fragment.*;
import ajlyfe.lectureapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.github.paolorotolo.appintro.AppIntro;

public class UploadActivity extends AppIntro {

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        setProgressButtonEnabled(true);
        setColorDoneText(this.getResources().getColor(R.color.colorAccent));
        setImageNextButton(this.getResources().getDrawable(R.drawable.ic_next));
        setSeparatorColor(Color.TRANSPARENT);

        setIndicatorColor(R.color.colorPrimary50, R.color.colorPrimary20);

        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, TeacherMainActivity.class));
    }

    @Override
    public void onSlideChanged(@Nullable final Fragment oldFragment, @Nullable final Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        if (newFragment != null) {
            int slideNumber = newFragment.getTag().charAt(newFragment.getTag().length() - 1) - 47;

            if (slideNumber == 4) {
                setSwipeLock(true);
            }

            if (slideNumber == 3) {
                setNextPageSwipeLock(true);

                final Activity activity = newFragment.getActivity();
                Button finishButton = (Button) activity.findViewById(R.id.finishButton);

                finishButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox = (CheckBox) activity.findViewById(R.id.lectureCheck);
                        if (checkBox.isChecked()) {
                            pager.setCurrentItem(4);
                        }
                    }
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
