package ajlyfe.lectureapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ajlyfe.lectureapp.*;
import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.Adapters.TeacherClassCardAdapter;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class TeacherClassOverview extends AppCompatActivity {

    private boolean creatingClass = false;
    private FloatingActionButton fab;
    private ArrayList<TeacherClassCard> classes = new ArrayList<>();
    public static final String NULL_CLASS = "Header Name (NULL)";
    public static final String AUTO_DESCRIPTION = "Dummy description for an auto-generated\nclass for testing purposes";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = Utils.getPrefs(Utils.SHARED_PREFERENCES, this);
        editor = preferences.edit();

        fab = (FloatingActionButton) findViewById(R.id.fabTeacher);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.createClassLayout).setVisibility(View.VISIBLE);
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.classOverviewLayout);

                creatingClass = true;
                animateButton(fab, findViewById(R.id.revealLayout));

                layout.setEnabled(false);
                layout.setClickable(false);
                layout.setVisibility(View.GONE);
            }
        });

        classes = initializeClassList();

        RecyclerView recyclerViewMainTeacher = (RecyclerView) findViewById(R.id.recyclerViewMainTeacher);
        final TeacherClassCardAdapter adapter = new TeacherClassCardAdapter(classes, this, this);
        recyclerViewMainTeacher.setAdapter(adapter);
        recyclerViewMainTeacher.setLayoutManager(new LinearLayoutManager(this));
        OverScrollDecoratorHelper.setUpOverScroll(recyclerViewMainTeacher, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        final EditText classNameET =  (EditText) findViewById(R.id.className);
        final EditText classDescriptionET = (EditText) findViewById(R.id.classDescription);

        findViewById(R.id.teacherCreateClassButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String className =  classNameET.getText().toString();
                final String classDescription = classDescriptionET.getText().toString();

                if (!className.equals("") || !classDescription.equals("")) { // Proceed
                    classes.add(new TeacherClassCard(className, classDescription));
                    adapter.setClassList(classes);
                    Utils.setClassList(classes, TeacherClassOverview.this);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(TeacherClassOverview.this);
                    View dialogView = View.inflate(getApplicationContext(), R.layout.create_class_dialog, null);
                    RelativeLayout actionMessage = (RelativeLayout) dialogView.findViewById(R.id.actionMessage);
                    actionMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_SUBJECT, "Join my new class on RecoLecture");
                            i.putExtra(Intent.EXTRA_TEXT, "Use the code 3zb8c27n to join my class \""
                                    + className + ".\"");
                            try {
                                startActivity(Intent.createChooser(i, "Send mail..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Snackbar.make(view, "There are no email clients installed", Snackbar.LENGTH_SHORT);
                            }
                        }
                    });
                    dialog.setTitle("Attention!")
                            .setView(dialogView)
                            .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int idk) {
                                    onBackPressed();

                                }
                            }).show();
                }
                else { // One of the fields is empty
                    if (className.equals("")) {
                        TextInputLayout classNameTIL = (TextInputLayout) findViewById(R.id.inputLayoutClassName);
                        classNameTIL.setErrorEnabled(true);
                        classNameTIL.setError("This field cannot be empty");
                    }

                    if (classDescription.equals("")) {
                        TextInputLayout classDescriptionTIL = (TextInputLayout) findViewById(R.id.inputLayoutClassDescription);
                        classDescriptionTIL.setErrorEnabled(true);
                        classDescriptionTIL.setError("This field cannot be empty");
                    }
                }
            }
        });
    }

    private ArrayList<TeacherClassCard> initializeClassList() {
        return Utils.getClassList(this);
    }

    private void animateButton(final ImageButton mFloatingButton, final View revealLayout) {
        mFloatingButton.animate()
                .translationXBy(0)
                .translationY(0)
                .translationXBy(0)
                .translationX(0)
                .setDuration(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        animateReveal(revealLayout.getWidth(), revealLayout.getHeight(), mFloatingButton, revealLayout);
                    }
                });
    }

    private void animateReveal(int dx, int dy, final ImageButton mFloatingButton, final View revealLayout) {
        float finalRadius = hypo(revealLayout.getWidth(), revealLayout.getHeight());

        SupportAnimator animator =
                ViewAnimationUtils.createCircularReveal(revealLayout, dx, dy, 0, finalRadius);

        animator.addListener(new SupportAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart() {
                mFloatingButton.setVisibility(View.INVISIBLE);
                revealLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd() {}

            @Override
            public void onAnimationCancel() {}

            @Override
            public void onAnimationRepeat() {}
        });

        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(250);
        animator.start();
    }

    static float hypo(int a, int b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    @Override
    public void onBackPressed() {
        if (creatingClass) {
            //TODO: ANIMATE ME! no thx
            Log.d("Animations", "Closing create class view.");

            View createClassView = findViewById(R.id.createClassLayout);
            View overviewView = findViewById(R.id.classOverviewLayout);

            createClassView.setEnabled(false);
            createClassView.setClickable(false);
            createClassView.setVisibility(View.GONE);

            overviewView.setEnabled(true);
            overviewView.setClickable(true);
            overviewView.setVisibility(View.VISIBLE);

            fab.setVisibility(View.VISIBLE);

            creatingClass = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(TeacherClassOverview.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}