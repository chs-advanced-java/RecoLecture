package ajlyfe.lectureapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.HashMap;

import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.Adapters.TeacherClassCardAdapter;
import ajlyfe.lectureapp.CodeGenerator;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import ajlyfe.lectureapp.WriteToDatabase;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class TeacherClassOverview extends AppCompatActivity {

    private boolean creatingClass = false;
    private FloatingActionButton fab;
    private ArrayList<TeacherClassCard> classes = new ArrayList<>();
    public static final String NULL_CLASS = "Header Name (NULL)";
    public static final String AUTO_DESCRIPTION = "Dummy description for an auto-generated\nclass for testing purposes";

    private static final String CLASSES_URL = "http://www.chs.mcvsd.org/sandbox/insert-classdb.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boolean[] creatingClassArr = {creatingClass};
        fab = (FloatingActionButton) findViewById(R.id.fabTeacher);
        fab.setOnClickListener(onFABClick(TeacherClassOverview.this, creatingClassArr, fab));
        creatingClass = creatingClassArr[0];

        classes = initializeClassList();

        RecyclerView recyclerViewMainTeacher = (RecyclerView) findViewById(R.id.recyclerViewMainTeacher);
        final TeacherClassCardAdapter adapter = new TeacherClassCardAdapter(classes, this, this);
        recyclerViewMainTeacher.setAdapter(adapter);
        recyclerViewMainTeacher.setLayoutManager(new LinearLayoutManager(this));
        OverScrollDecoratorHelper.setUpOverScroll(recyclerViewMainTeacher, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        findViewById(R.id.teacherCreateClassButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText classNameET = (EditText) findViewById(R.id.newClassNameET);
                final EditText classDescriptionET = (EditText) findViewById(R.id.newClassDescriptionET);
                final String className = classNameET.getText().toString();
                final String classDescription = classDescriptionET.getText().toString();
                String code = "";

                if (!className.equals("") && !classDescription.equals("") && (classDescription.length() <= 80)) { // Proceed
                    CodeGenerator gen = new CodeGenerator();
                    boolean generating = true;
                    while (generating) {
                        code = gen.generate();

                        generating = false;
                    }
                    final String usedCode = code;

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(TeacherClassOverview.this);
                    builder.title("Attention!")
                            .customView(R.layout.create_class_dialog, true)
                            .positiveText("OK!")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    classes.add(new TeacherClassCard(className, classDescription));

                                    for (int i = 0; i < classes.size(); i++) {
                                        if (classes.get(i).getName().equals(getString(R.string.no_classes_title))) {
                                            classes.remove(i);
                                            Utils.setTeacherClassList(classes, TeacherClassOverview.this);
                                        }
                                    }

                                    adapter.setClassList(classes);
                                    Utils.setTeacherClassList(classes, TeacherClassOverview.this);

                                    String email = Utils.getPrefs(Utils.SHARED_PREFERENCES, TeacherClassOverview.this).getString(Utils.PREF_EMAIL, null);
                                    pushClass(className, classDescription, usedCode, email);

                                    onBackPressed();
                                }
                            })
                            .negativeText("CANCEL")
                            .canceledOnTouchOutside(false);

                    MaterialDialog dialog = builder.build();

                    View dialogView = dialog.getCustomView();

                    if (dialogView != null) {
                        TextView text = (TextView) dialogView.findViewById(R.id.message);
                        text.setText("Your class code is: " + code + ".\nDistribute this to your students.");

                        RelativeLayout actionMessage = (RelativeLayout) dialogView.findViewById(R.id.actionMessage);
                        actionMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("message/rfc822");
                                i.putExtra(Intent.EXTRA_SUBJECT, "Join my new class on RecoLecture!");
                                i.putExtra(Intent.EXTRA_TEXT, "Use the code " + usedCode + " to join my class \""
                                        + className + ".\"");
                                try {
                                    startActivity(Intent.createChooser(i, "Send mail..."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(TeacherClassOverview.this, "There are no email clients installed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    dialog.show();

                } else { // One of the fields is empty
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

                    if (classDescription.length() > 80) {
                        TextInputLayout classDescriptionTIL = (TextInputLayout) findViewById(R.id.inputLayoutClassDescription);
                        classDescriptionTIL.setErrorEnabled(true);
                        classDescriptionTIL.setError("Must be less than 80 characters");
                    }
                }
            }
        });
    }

    public static View.OnClickListener onFABClick(final Activity activity, final boolean[] creatingClass,
                                                  final FloatingActionButton fab) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.findViewById(R.id.createClassLayout).setVisibility(View.VISIBLE);
                RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.classOverviewLayout);

                creatingClass[0] = true;
                animateButton(fab, activity.findViewById(R.id.revealLayout));

                layout.setEnabled(false);
                layout.setClickable(false);
                layout.setVisibility(View.GONE);
            }
        };
    }

    private ArrayList<TeacherClassCard> initializeClassList() {
        ArrayList<TeacherClassCard> classList = Utils.getTeacherClassList(this);

        if (classList.size() == 1) { // User has no classes
            classList.add(new TeacherClassCard(getString(R.string.no_classes_title), getString(R.string.no_classes_description)));
        }

        return classList;
    }

    private static void animateButton(final ImageButton mFloatingButton, final View revealLayout) {
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

    private static void animateReveal(int dx, int dy, final ImageButton mFloatingButton, final View revealLayout) {
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
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {
            }
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

    private void pushClass(String className, String classDescription, String classCode, String teacher) {
        class PushClass extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;
            private WriteToDatabase ruc = new WriteToDatabase();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TeacherClassOverview.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("className", params[0]);
                data.put("classDescription", params[1]);
                data.put("classCode", params[2]);
                data.put("teacher", params[3]);

                return ruc.sendPostRequest(CLASSES_URL, data);
            }
        }

        PushClass pc = new PushClass();
        pc.execute(className, classDescription, classCode, teacher);
    }
}