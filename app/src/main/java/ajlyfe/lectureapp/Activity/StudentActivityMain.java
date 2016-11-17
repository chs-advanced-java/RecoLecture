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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ajlyfe.lectureapp.Adapters.ClassCard;
import ajlyfe.lectureapp.Adapters.ClassCardAdapter;
import ajlyfe.lectureapp.Adapters.LectureCard;
import ajlyfe.lectureapp.Adapters.TeacherClassCard;
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

        preferences = Utils.getPrefs(Utils.PREFS_CLASSES, this);
        editor = preferences.edit();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabStudent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentActivityMain.this, StudentEnterClassCode.class));
            }
        });

        RecyclerView recyclerViewStudentMain = (RecyclerView) findViewById(R.id.recyclerViewMainStudent);
        ClassCardAdapter adapter = new ClassCardAdapter(getClasses(), this, this);
        recyclerViewStudentMain.setAdapter(adapter);
        recyclerViewStudentMain.setLayoutManager(new LinearLayoutManager(this));
        OverScrollDecoratorHelper.setUpOverScroll(recyclerViewStudentMain, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }

    public ArrayList<ClassCard> getClasses() {
        Set<String> tempClassList;
        tempClassList = preferences.getStringSet("KeyStudent", null);
        if (tempClassList == null) {
            Set<String> errorSet = new HashSet<>();
            errorSet.add("Spanish 1");
            errorSet.add("Spanish 2");
            errorSet.add("Spanish 3");
            tempClassList = errorSet;
            editor.putStringSet("KeyStudent", tempClassList);
            editor.commit();
        }
        int listSize = tempClassList.size();
        String[] tempClassArray = tempClassList.toArray(new String[tempClassList.size()]);
        final ArrayList<ClassCard> classes = ClassCard.createList(listSize);
        for(int y = 0; y < listSize; y++) {
            classes.get(y).setClassName(tempClassArray[y]);
        }

        return classes;
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
