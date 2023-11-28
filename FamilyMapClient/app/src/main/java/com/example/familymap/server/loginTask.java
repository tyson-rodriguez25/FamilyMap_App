package com.example.familymap.server;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.example.familymap.model.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.familymap.MainActivity;
import com.example.familymap.R;
import com.example.familymap.model.localStorage;
import com.example.familymap.request.LoginRequest;
import com.example.familymap.response.ErrorResponse;
import com.example.familymap.response.InheritResponse;
import com.example.familymap.response.LoginResponse;
import com.example.familymap.ui.main.MapFragment;


public class loginTask extends AsyncTask<String, Integer, InheritResponse> {

    private Context loginContext;
    //private Context context;
    private String host;
    private String port;

    public loginTask(Context loginContext) {
        this.loginContext = loginContext;
    }





    @Override
    protected InheritResponse doInBackground(String... strings) {
        host = strings[0];
        port = strings[1];
        proxy prox = new proxy(host, port);
        try {
            LoginRequest lr = new LoginRequest(strings[2], strings[3]);
            return prox.login(lr);

        }
        catch(Exception e){
            return new ErrorResponse("Login Failure: " + e.getMessage());
        }
        //return null;
    }

    @Override
    protected void onPostExecute(InheritResponse ir) {
        super.onPostExecute(ir);

        if(ir instanceof LoginResponse){
            LoginResponse lr = (LoginResponse)ir;
            //Toast.makeText(loginContext, lr.getPersonID(), Toast.LENGTH_SHORT).show();
            localStorage ls = localStorage.getInstance();
            ls.setAuthToken(lr.getAuthToken());
            infoTask bd = new infoTask(loginContext);
            bd.execute(host, port, ((LoginResponse) ir).getAuthToken(), ((LoginResponse) ir).getPersonID());
            //MainActivity activity = (MainActivity) context;
            //activity.switchToMapFragment();

        }
        else if(ir instanceof ErrorResponse){
            Toast.makeText(loginContext, ((ErrorResponse) ir).getMessage(), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(loginContext, "Wrong Username/Password", Toast.LENGTH_SHORT).show();
        }
    }


}
