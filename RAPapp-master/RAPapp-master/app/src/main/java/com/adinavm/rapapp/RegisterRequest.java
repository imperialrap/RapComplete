package com.adinavm.rapapp;

/**
 * Created by elisa on 15/02/17.
 */

import com.android.volley.toolbox.StringRequest;
import java.util.*;
import com.android.volley.Response;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://imperialrap.000webhostapp.com/register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, String email, String password, float weight, int height, int age, String gender, Response.Listener<String> Listener){
        super(Method.POST, REGISTER_REQUEST_URL, Listener, null);

        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);
        params.put("weight", weight + "");
        params.put("height", height + "");
        params.put("age", age + "");
        params.put("gender", gender);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
