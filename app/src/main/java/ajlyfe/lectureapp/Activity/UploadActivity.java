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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

import java.util.ArrayList;

public class UploadActivity extends AppIntro {

    private Context context;
    private Fragment fragmentUpload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // DO NOT WRITE -> setContentView(R.layout.activity_upload);

        Fragment fragmentFile = new FragmentFile();
        Fragment fragmentClass = new FragmentClass();
        Fragment fragmentStudents = new FragmentStudents();
        fragmentUpload = new FragmentUpload();
        Fragment fragmentResult = new FragmentResult();

        addSlide(fragmentFile);
        addSlide(fragmentClass);
        addSlide(fragmentStudents);
        addSlide(fragmentUpload);
        addSlide(fragmentResult);

        setSwipeLock(true);

        showSkipButton(false);
        setProgressButtonEnabled(true);     // Reveal the done button and next arrow
        setImageNextButton(null);           // Purge next button
        nextButton.setClickable(false);     // Banish next button
                                            // Done button still stays and works

        setColorDoneText(this.getResources().getColor(R.color.colorAccent));
        setSeparatorColor(Color.TRANSPARENT);
        setIndicatorColor(R.color.colorPrimary50, R.color.colorPrimary20);

        setVibrate(false);
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
            final int slideNumber = newFragment.getTag().charAt(newFragment.getTag().length() - 1) - 47;
            Button next;

            switch (slideNumber) {
                case 1:
                    final Activity activity1 = newFragment.getActivity();
                    next = (Button) activity1.findViewById(R.id.uploadFileButton);

                    final ArrayList<String> lectureCheckboxes = new ArrayList<>();
                    final CheckBox[] checkBoxes = new CheckBox[5];
                    final TextView[] lectureTitles = new TextView[5];

                    checkBoxes[0] = (CheckBox) activity1.findViewById(R.id.lectureCheck1);
                    checkBoxes[1] = (CheckBox) activity1.findViewById(R.id.lectureCheck2);
                    checkBoxes[2] = (CheckBox) activity1.findViewById(R.id.lectureCheck3);
                    checkBoxes[3] = (CheckBox) activity1.findViewById(R.id.lectureCheck4);
                    checkBoxes[4] = (CheckBox) activity1.findViewById(R.id.lectureCheck5);

                    lectureTitles[0] = (TextView) activity1.findViewById(R.id.checkText1);
                    lectureTitles[1] = (TextView) activity1.findViewById(R.id.checkText2);
                    lectureTitles[2] = (TextView) activity1.findViewById(R.id.checkText3);
                    lectureTitles[3] = (TextView) activity1.findViewById(R.id.checkText4);
                    lectureTitles[4] = (TextView) activity1.findViewById(R.id.checkText5);

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean checkedSomething = false;

                            for (int i = 0; i < checkBoxes.length; i++) {
                                if (checkBoxes[i].isChecked()) {
                                    checkedSomething = true;
                                    lectureCheckboxes.add(lectureTitles[i].getText().toString());
                                }
                            }

                            Bundle args = new Bundle();
                            args.putStringArrayList("lecturesCheckedOff", lectureCheckboxes);
                            fragmentUpload.setArguments(args);

                            if (checkedSomething) {
                                pager.setCurrentItem(1);
                            } else {
                                Toast.makeText(activity1, "Please select a lecture", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;

                case 2:
                    final Activity activity2 = newFragment.getActivity();
                    next = (Button) activity2.findViewById(R.id.uploadClassButton);

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CheckBox spanishOne = (CheckBox) findViewById(R.id.spanishOneCheck);
                            CheckBox spanishTwo = (CheckBox) findViewById(R.id.spanishTwoCheck);
                            CheckBox spanishThree = (CheckBox) findViewById(R.id.spanishThreeCheck);

                            if (spanishOne.isChecked() || spanishTwo.isChecked() || spanishThree.isChecked()) {
                                pager.setCurrentItem(2);
                            } else {
                                Toast.makeText(activity2, "Please select a class", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;

                case 3:
                    final Activity activity3 = newFragment.getActivity();
                    next = (Button) activity3.findViewById(R.id.uploadStudentButton);

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pager.setCurrentItem(3);
                        }
                    });
                    break;

                case 4:
                    final Activity activity4 = newFragment.getActivity();
                    Button finishButton = (Button) activity4.findViewById(R.id.finishButton);

                    finishButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO: Only checks if one of the shits is checked
                            CheckBox checkBox = (CheckBox) activity4.findViewById(R.id.lectureCheckboxCheckbox);

                            if (checkBox.isChecked()) {
                                pager.setCurrentItem(slideNumber);
                            } else {
                                Toast.makeText(activity4, "Please confirm all of the above", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;

                case 5:
                    final Activity activity5 = newFragment.getActivity();
                    // Nothing :)
                    break;
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
