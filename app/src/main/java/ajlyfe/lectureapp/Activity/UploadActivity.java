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
        setProgressButtonEnabled(false);

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

                    final CheckBox lectureOne = (CheckBox) activity1.findViewById(R.id.lectureCheck1);
                    final TextView lectureOneTitle = (TextView) activity1.findViewById(R.id.checkText1);

                    final CheckBox lectureTwo = (CheckBox) activity1.findViewById(R.id.lectureCheck2);
                    final TextView lectureTwoTitle = (TextView) activity1.findViewById(R.id.checkText2);

                    final CheckBox lectureThree = (CheckBox) activity1.findViewById(R.id.lectureCheck3);
                    final TextView lectureThreeTitle = (TextView) activity1.findViewById(R.id.checkText3);

                    final CheckBox lectureFour = (CheckBox) activity1.findViewById(R.id.lectureCheck4);
                    final TextView lectureFourTitle = (TextView) activity1.findViewById(R.id.checkText4);

                    final CheckBox lectureFive = (CheckBox) activity1.findViewById(R.id.lectureCheck5);
                    final TextView lectureFiveTitle = (TextView) activity1.findViewById(R.id.checkText5);

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (lectureOne.isChecked()) lectureCheckboxes.add(lectureOneTitle.getText().toString());
                            if (lectureTwo.isChecked()) lectureCheckboxes.add(lectureTwoTitle.getText().toString());
                            if (lectureThree.isChecked()) lectureCheckboxes.add(lectureThreeTitle.getText().toString());
                            if (lectureFour.isChecked()) lectureCheckboxes.add(lectureFourTitle.getText().toString());
                            if (lectureFive.isChecked()) lectureCheckboxes.add(lectureFiveTitle.getText().toString());

                            Bundle args = new Bundle();
                            args.putStringArrayList("lecturesCheckedOff", lectureCheckboxes);
                            fragmentUpload.setArguments(args);

                            pager.setCurrentItem(1);
                        }
                    });
                    break;

                case 2:
                    final Activity activity2 = newFragment.getActivity();
                    next = (Button) activity2.findViewById(R.id.uploadClassButton);

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO: Make sure stupid user checked something
                            pager.setCurrentItem(2);
                        }
                    });
                    break;

                case 3:
                    final Activity activity3 = newFragment.getActivity();
                    next = (Button) activity3.findViewById(R.id.uploadStudentButton);

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO: Make sure stupid user checked something
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
                            //Define any of the checkboxes
                            CheckBox checkBox = (CheckBox) activity4.findViewById(R.id.lectureCheckboxCheckbox);
                            if (checkBox.isChecked()) {
                                pager.setCurrentItem(slideNumber);
                            }
                        }
                    });
                    break;

                case 5:
                    final Activity activity5 = newFragment.getActivity();
                    TextView done = (TextView) activity5.findViewById(R.id.DoneNextToPurpleCheck);

                        done.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                              startActivity(new Intent(UploadActivity.this, TeacherMainActivity.class));
                            }
                        });
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
