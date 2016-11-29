package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import java.util.ArrayList;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class RegistrationScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button register = (Button) findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationScreen.this, LoginActivity.class));
            }
        });

        final ScrollView scrollView = (ScrollView) findViewById(R.id.registerScrollView);
        final EditText[] editTexts = {
                (EditText) findViewById(R.id.emailET),
                (EditText) findViewById(R.id.firstNameET),
                (EditText) findViewById(R.id.lastNameET),
                (EditText) findViewById(R.id.usernameET),
                (EditText) findViewById(R.id.passwordET),
                (EditText) findViewById(R.id.confirmPasswordET)
        };

        final int constant = 200;

        for (final EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() <= 1) {
                        if (editText.getId() != R.id.emailET) {
                            scrollView.smoothScrollTo(0, scrollView.getScrollY() + constant);
                        }
                    }
                }
            });
        }
    }
}