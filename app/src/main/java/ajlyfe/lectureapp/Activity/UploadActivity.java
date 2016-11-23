package ajlyfe.lectureapp.Activity;

import ajlyfe.lectureapp.Adapters.ClassSelectCard;
import ajlyfe.lectureapp.Adapters.LectureSelectCard;
import ajlyfe.lectureapp.Fragment.*;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

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
    FragmentFile file;
    FragmentClass classes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        context = this;

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // DO NOT WRITE -> setContentView(R.layout.activity_upload);
        file = new FragmentFile();
        classes = new FragmentClass();
        Fragment fragmentFile = file;
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

                    final ArrayList<LectureSelectCard> lectureCheckboxes = file.getAdapterArrayList();
                    final ArrayList<String> lecturesChecked = new ArrayList<>();


                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean checkedSomething = false;

                            for (int i = 0; i < lectureCheckboxes.size(); i++) {
                                if (lectureCheckboxes.get(i).getChecked()) {
                                    checkedSomething = true;
                                    lecturesChecked.add(lectureCheckboxes.get(i).getFileName());
                                }
                            }

                            Bundle args = new Bundle();
                            args.putStringArrayList("lecturesCheckedOff", lecturesChecked);
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

                    final ArrayList<ClassSelectCard> classCheckboxes = classes.getAdapterArrayList();
                    final ArrayList<String> classesChecked = new ArrayList<>();

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean checkedSomething = false;

                            for (int i = 0; i < classCheckboxes.size(); i++) {
                                if (classCheckboxes.get(i).getChecked()) {
                                    checkedSomething = true;
                                    classesChecked.add(classCheckboxes.get(i).getClassName());
                                }
                            }

                            Bundle args = new Bundle();
                            args.putStringArrayList("classesCheckedOff", classesChecked);
                            fragmentUpload.setArguments(args);

                            if (checkedSomething) {
                                pager.setCurrentItem(1);
                            } else {
                                Toast.makeText(activity2, "Please select a class.", Toast.LENGTH_SHORT).show();
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
