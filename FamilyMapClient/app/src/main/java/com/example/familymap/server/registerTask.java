package com.example.familymap.server;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.familymap.model.localStorage;
import com.example.familymap.response.RegisterResponse;
import com.example.familymap.response.ErrorResponse;
import com.example.familymap.response.InheritResponse;
import com.example.familymap.request.RegisterRequest;


public class registerTask extends AsyncTask<String, Integer, InheritResponse> {

    private Context registerContext;
    private String host;
    private String port;
    public registerTask(Context registerContext) {
        this.registerContext = registerContext;
    }


    @Override
    protected InheritResponse doInBackground(String... strings) {
        host = strings[0];
        port = strings[1];
        proxy prox = new proxy(host, port);
        try {
            RegisterRequest rr = new RegisterRequest(strings[2], strings[3], strings[4], strings[5], strings[6],strings[7]);
            return prox.register(rr);

        }
        catch(Exception e){
            return new ErrorResponse("Register Failure: " + e.getMessage());
        }
        //return null;
    }

    @Override
    protected void onPostExecute(InheritResponse ir) {
        super.onPostExecute(ir);

        if(ir instanceof RegisterResponse){
            RegisterResponse rr = (RegisterResponse)ir;
            //Toast.makeText(registerContext, rr.getTokenID(), Toast.LENGTH_SHORT).show();
            localStorage ls = localStorage.getInstance();
            ls.setAuthToken(rr.getAuthToken());
            infoTask bd = new infoTask(registerContext);
            bd.execute(host, port, ((RegisterResponse) ir).getAuthToken(), ((RegisterResponse) ir).getTokenID());
        }
        else if(ir instanceof ErrorResponse){
            Toast.makeText(registerContext, ((ErrorResponse) ir).getMessage(), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(registerContext, "User already registered", Toast.LENGTH_SHORT).show();
        }
    }
}
