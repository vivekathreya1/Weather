package com.vivek.weather.api;

import org.json.JSONObject;

import retrofit2.Response;

public class ApiError {

    private String cod;
    private String message;
    public String errorCode(final Response<?> response) {
        JSONObject obj = null;


        try {
            String errorbody = response.errorBody().string();
            obj = new JSONObject(errorbody);
            message = obj.getString("message");
            cod = obj.getString("cod");
            return cod;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


}
