package com.builderfly.parsing_app.network;

import com.builderfly.parsing_app.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestParam {

    private static String TAG = RequestParam.class.getSimpleName();

    public static Map<String, String> getNull() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("temp", "");
        return param;
    }

    public static Map<String, String> getCommonHeaderWithContentType() {
        Map<String, String> param = new HashMap<String, String>();
        //param.put("Authorization", "" + mPreferenceManager.getAccessToken());
        param.put("Content-Type", "application/json");
        return param;
    }

    public static Map<String, String> getCommonHeader() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("Authorization", "");
        return param;
    }

    public static JSONObject allCategories() {
        JSONObject jsonRootObject = new JSONObject();
        try {
            jsonRootObject.put("api_key", Constants.API_KEY);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonRootObject;
    }

   /*public static Map<String, String> userLogin(String apiKey, String emailAddress, String password) {
      Map<String, String> requestBody = new HashMap<>();
      requestBody.put("api_key", apiKey.trim());
      requestBody.put("email", emailAddress.trim());
      requestBody.put("pass", password.trim());
      return requestBody;
   }*/

    public static JSONObject userLogin(String apiKey, String emailAddress, String password) {
        JSONObject jsonRootObject = new JSONObject();
        try {
            jsonRootObject.put("api_key", apiKey.trim());
            jsonRootObject.put("email", emailAddress.trim());
            jsonRootObject.put("pass", password.trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonRootObject;
    }

    public static Map<String, String> userRegister(String apiKey, String emailAddress, String firstName,
                                                   String lastName, String password) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("api_key", apiKey.trim());
        requestBody.put("email", emailAddress.trim());
        requestBody.put("fname", firstName.trim());
        requestBody.put("lname", lastName.trim());
        requestBody.put("pass", password.trim());
        return requestBody;
    }


}