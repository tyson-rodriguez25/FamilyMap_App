package com.example.familymap.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.familymap.MainActivity;
import com.example.familymap.R;
import com.example.familymap.server.loginTask;
import com.example.familymap.server.registerTask;

public class LoginFragment extends Fragment {

    //private MainViewModel mViewModel;
    private EditText hostText;
    private EditText portText;
    private EditText usernameText;
    private EditText passwordText;
    private EditText firstnameText;
    private EditText lastnameText;
    private EditText emailText;
    private RadioButton gendermaleButton;
    private RadioButton genderfemaleButton;



    private Button loginButton;
    private Button registerButton;



    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        hostText = (EditText)v.findViewById(R.id.serverhostEditText);
        portText = (EditText)v.findViewById(R.id.serverportEditText);
        usernameText = (EditText)v.findViewById(R.id.usernameEditText);
        passwordText = (EditText)v.findViewById(R.id.passwordEditText);
        firstnameText = (EditText)v.findViewById(R.id.firstnameEditText);
        lastnameText = (EditText)v.findViewById(R.id.lastnameEditText);
        emailText = (EditText)v.findViewById(R.id.emailEditText);
        gendermaleButton = (RadioButton) v.findViewById(R.id.genderbuttonmaleRadioButton);
        genderfemaleButton = (RadioButton) v.findViewById(R.id.genderbuttonfemaleRadioButton);


        loginButton = (Button) v.findViewById(R.id.signinButton);
        registerButton = (Button) v.findViewById(R.id.registerButton);
        loginButton.setEnabled(false);
        registerButton.setEnabled(false);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                buttonStuff();
            }
        };

        hostText.addTextChangedListener(tw);
        portText.addTextChangedListener(tw);
        usernameText.addTextChangedListener(tw);
        passwordText.addTextChangedListener(tw);
        firstnameText.addTextChangedListener(tw);
        lastnameText.addTextChangedListener(tw);
        emailText.addTextChangedListener(tw);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStuff();
            }
        };
        gendermaleButton.setOnClickListener(listener);
        genderfemaleButton.setOnClickListener(listener);


        return v;
    }

    public void buttonStuff(){
        if( checkEmpty(hostText )|| checkEmpty(portText) || checkEmpty(usernameText) || checkEmpty(passwordText)){
            loginButton.setEnabled(false);
        }
        else
            loginButton.setEnabled(true);

        if (checkEmpty(hostText) || checkEmpty(portText) || checkEmpty(usernameText) || checkEmpty(passwordText) || checkEmpty(firstnameText) || checkEmpty(lastnameText) || checkEmpty(emailText) || !(genderfemaleButton.isChecked() || gendermaleButton.isChecked())) {
            registerButton.setEnabled(false);
        }
        else
            registerButton.setEnabled(true);
    }

    public boolean checkEmpty(EditText edit){
        return edit.getText().toString().equals("");
    }


    public void doLogin(){
        //Toast.makeText(getActivity(), "You signed in!", Toast.LENGTH_SHORT).show();
        loginTask bl = new loginTask(getActivity());
        try {
            bl.execute(hostText.getText().toString(), portText.getText().toString(),
                    usernameText.getText().toString(), passwordText.getText().toString()).get();
            ((MainActivity)getActivity()).switchToMapFragment();
            //Intent main = new Intent(getActivity(), MainActivity.class);
            //startActivity(main);
        }
        catch (Exception e) {}

    }
    public  void doRegister(){
        //Toast.makeText(getActivity(), "You are registered!", Toast.LENGTH_SHORT).show();
        registerTask br = new registerTask(getActivity());
        String gender;
        if(gendermaleButton.isChecked()){
            gender = "m";
        }
        else{
            gender = "f";
        }
        try {
            br.execute(
                    hostText.getText().toString(),
                    portText.getText().toString(),
                    usernameText.getText().toString(),
                    passwordText.getText().toString(),
                    emailText.getText().toString(),
                    firstnameText.getText().toString(),
                    lastnameText.getText().toString(),
                    gender).get();
            Intent main = new Intent(getActivity(), MainActivity.class);
            startActivity(main);

        } catch (Exception e) {

        }
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        // TODO: Use the ViewModel
//    }

}
