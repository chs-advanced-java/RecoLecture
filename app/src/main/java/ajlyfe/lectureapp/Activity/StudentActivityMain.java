package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import ajlyfe.lectureapp.Adapters.ClassCardAdapter;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class StudentActivityMain extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fadeCircle();

        preferences = Utils.getPrefs(Utils.SHARED_PREFERENCES, this);
        editor = preferences.edit();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabStudent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentActivityMain.this, StudentEnterClassCode.class));
            }
        });

        RecyclerView recyclerViewStudentMain = (RecyclerView) findViewById(R.id.recyclerViewMainStudent);
        ClassCardAdapter adapter = new ClassCardAdapter(Utils.getStudentClassList(this), this, this);
        recyclerViewStudentMain.setAdapter(adapter);
        recyclerViewStudentMain.setLayoutManager(new LinearLayoutManager(this));
        OverScrollDecoratorHelper.setUpOverScroll(recyclerViewStudentMain, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }

    private void fadeCircle() {
        final ImageView colorBlock = (ImageView) findViewById(R.id.colorBlock);
        colorBlock.setVisibility(View.VISIBLE);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                colorBlock.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        colorBlock.startAnimation(fadeOut);
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
                startActivity(new Intent(StudentActivityMain.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
