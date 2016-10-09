package ajlyfe.lectureapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import ajlyfe.lectureapp.*;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;
import io.codetail.widget.RevealLinearLayout;

public class TeacherClassOverview extends AppCompatActivity {

    private boolean creatingClass = false;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        /** HARDCODE! **/
        CardView classOneCard = (CardView) findViewById(R.id.classOne);
        CardView classTwoCard = (CardView) findViewById(R.id.classTwo);

        RelativeLayout classTwoLayout = (RelativeLayout) classTwoCard.getChildAt(0);
        TextView classTwoTitle = (TextView) classTwoLayout.getChildAt(0);

        classTwoTitle.setText("Class Two");

        classOneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherClassOverview.this, TeacherClassView.class);
                intent.putExtra("CLASS_CLICKED", 1);
                startActivity(intent);

            }
        });

        classTwoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherClassOverview.this, TeacherClassView.class);
                intent.putExtra("CLASS_CLICKED", 2);
                startActivity(intent);
            }
        });

        /** END HARDCODE! **/
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
            //TODO: ANIMATE ME!
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
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}