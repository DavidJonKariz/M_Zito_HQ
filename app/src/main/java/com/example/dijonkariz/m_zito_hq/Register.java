package com.example.dijonkariz.m_zito_hq;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.dijonkariz.m_zito_hq.Entities.AccessToken;
import com.example.dijonkariz.m_zito_hq.Entities.ApiError;
import com.example.dijonkariz.m_zito_hq.Network.Apiservice;
import com.example.dijonkariz.m_zito_hq.Network.RetrofitBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";

    private EditText inputName, inputEmail, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btnSignUp;
    private TextView to_login;

    //Bind The XML Details
    @BindView(R.id.txt_input_name)
    TextInputLayout txtInput_name;
    @BindView(R.id.txt_input_email)
    TextInputLayout txtInput_email;
    @BindView(R.id.txt_input_password)
    TextInputLayout txtInput_password;

    Apiservice service;
    Call<AccessToken> call;
    AwesomeValidation validator;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Input Layouts
        inputLayoutName = (TextInputLayout) findViewById(R.id.txt_input_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.txt_input_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.txt_input_password);
        //EditTexts
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        //Button
        btnSignUp = (Button) findViewById(R.id.btn_register);

        //TextViews
        to_login = (TextView) findViewById(R.id.to_login);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        //onClick Function
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submitForm();
//            }
//        });

        //To the Login
        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        //Bind Data To Database Process
        ButterKnife.bind(this);

        service = RetrofitBuilder.createService(Apiservice.class);
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        setupRules();
    }


    @OnClick(R.id.btn_register)
    void register()
    {
//        submitForm();
        String name = txtInput_name.getEditText().getText().toString();
        String email = txtInput_email.getEditText().getText().toString();
        String password = txtInput_password.getEditText().getText().toString();

        txtInput_name.setError(null);
        txtInput_email.setError(null);
        txtInput_password.setError(null);

        validator.clear();
        if (validator.validate())
        {
            call = service.register(name, email, password);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Log.w(TAG, "onResponse: "+ response);

                    if (response.isSuccessful())
                    {
                        Log.w(TAG, "onResponse: " + response.body());
                        tokenManager.saveToken(response.body());
                    }else{
                        handleErrors(response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " +t.getMessage());
                }
            });
        }
        Toast.makeText(this, "REGISTERED", Toast.LENGTH_SHORT).show();
    }

    private void handleErrors(ResponseBody response)
    {
        ApiError apiError = Utils.converErrors(response);

        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet())
        {
            if (error.getKey().equals("name"))
            {
                txtInput_name.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("email"))
            {
                txtInput_email.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("password"))
            {
                txtInput_password.setError(error.getValue().get(0));
            }
        }
    }

    public void setupRules()
    {
        validator.addValidation(this,  R.id.txt_input_name, RegexTemplate.NOT_EMPTY, R.string.err_msg_name);
        validator.addValidation(this,  R.id.txt_input_email, Patterns.EMAIL_ADDRESS, R.string.err_msg_email);
        validator.addValidation(this,  R.id.txt_input_password, "[a-zA-z0-9]{6,}", R.string.err_msg_password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null)
        {
            call.cancel();
            call = null;
        }
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_SHORT).show();
    }

    //Validate Username
    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    //Validate Email
    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    //Validate Password
    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email)
    {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view)
    {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        MyTextWatcher(View view) {
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
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}






