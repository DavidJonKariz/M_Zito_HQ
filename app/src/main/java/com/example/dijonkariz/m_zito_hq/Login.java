package com.example.dijonkariz.m_zito_hq;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText inputUserame, inputUpassword;
    private TextInputLayout inputLayoutUname, inputLayoutUpassword;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Input Layouts
        inputLayoutUname = (TextInputLayout) findViewById(R.id.login_txt_username);
        inputLayoutUpassword = (TextInputLayout) findViewById(R.id.login_txt_password);
        //EditTexts
        inputUserame = (EditText) findViewById(R.id.login_username);
        inputUpassword = (EditText) findViewById(R.id.login_password);
        //Button
        btnSignIn = (Button) findViewById(R.id.btn_login);

        inputUserame.addTextChangedListener(new Login.MyLoginTextWatcher(inputUserame));
        inputUpassword.addTextChangedListener(new Login.MyLoginTextWatcher(inputUpassword));

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        if (!validateUname()) {
            return;
        }

        if (!validateUpassword()) {
            return;
        }
        Toast.makeText(getApplicationContext(), "Signed In!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Login.this, landing_page.class);
        startActivity(intent);
    }

    private boolean validateUname() {
        if (inputUserame.getText().toString().trim().isEmpty()) {
            inputLayoutUname.setError(getString(R.string.err_msg_name));
            requestFocus(inputUserame);
            return false;
        } else {
            inputLayoutUname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateUpassword() {
        if (inputUpassword.getText().toString().trim().isEmpty()) {
            inputLayoutUpassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputUpassword);
            return false;
        } else {
            inputLayoutUpassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view)
    {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyLoginTextWatcher implements TextWatcher {
        private View view;

        MyLoginTextWatcher(View view) {
            this.view = view;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.login_username:
                    validateUname();
                    break;
                case R.id.login_password:
                    validateUpassword();
                    break;
            }
        }
    }
}
