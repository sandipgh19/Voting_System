package com.example.sandip.voting_system;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sandip on 7/15/2016.
 */

public class ParseJSON_question {
    public static String[] ids;
    public static String[] question;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_ID="id";
    public static final String KEY_QUESTION = "question";
    private JSONArray users = null;

    private String json;

    public ParseJSON_question(String json){
        this.json = json;
    }

    protected void parseJSON_question(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

             ids=new String[users.length()];
             question= new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                ids[i]=jo.getString(KEY_ID);
                question[i] = jo.getString(KEY_QUESTION);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
