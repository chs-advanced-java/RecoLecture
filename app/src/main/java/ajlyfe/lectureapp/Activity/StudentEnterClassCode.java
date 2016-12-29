package ajlyfe.lectureapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.ClassCard;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class StudentEnterClassCode extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public final String DATA_URL = "http://www.chs.mcvsd.org/sandbox/getClassData.php?classCode=";
    public final String JSON_ARRAY = "result";

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enter_class_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = Utils.getPrefs(Utils.SHARED_PREFERENCES, this);
        editor = preferences.edit();

        Button joinClassButton = (Button) findViewById(R.id.JoinClassButton);
        joinClassButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final EditText enterClassCode =  (EditText) findViewById(R.id.editText3);
                String classCodeStudent = enterClassCode.getText().toString();
                getData(classCodeStudent);
            }
        });

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getData(final String classCode) {
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = DATA_URL + classCode;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response, classCode);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentEnterClassCode.this, "Invalid class code", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response, String classCode) {
        String classNameData;
        String classDescriptionData;
        String classCodeData;
        String teacherData;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject data = result.getJSONObject(0);

            classNameData = data.getString("className");
            classDescriptionData = data.getString("classDescription");
            classCodeData = data.getString("classCode");
            teacherData = data.getString("teacher");

            if (classCode.equals(classCodeData)) { // Class code is valid double-check
                ArrayList<ClassCard> classCards = Utils.getStudentClassList(StudentEnterClassCode.this);
                classCards.add(new ClassCard(classNameData, classDescriptionData));

                Utils.setStudentClassList(classCards, StudentEnterClassCode.this);

                startActivity(new Intent(StudentEnterClassCode.this, StudentActivityMain.class));
                startActivity(new Intent(StudentEnterClassCode.this, StudentClassPage.class));

                finish();
            } else {
                Toast.makeText(StudentEnterClassCode.this, "Invalid class code", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
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
                startActivity(new Intent(StudentEnterClassCode.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
