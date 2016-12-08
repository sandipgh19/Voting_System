package com.example.sandip.voting_system;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sandip on 9/19/2016.
 */

public class ParseJSON_follow {

        public static String[] name;
        public static String[] gender;
        public static String[] phn;

        public static final String JSON_ARRAY = "result";
        public static final String KEY_NAME = "name";
        public static final String KEY_GENDER = "gender";
        public static final String KEY_PHN = "phn";

        private JSONArray users = null;

        private String json;

        public ParseJSON_follow(String json){
            this.json = json;
        }

        protected void parseJSON_follow(){
            JSONObject jsonObject=null;
            try {
                jsonObject = new JSONObject(json);
                users = jsonObject.getJSONArray(JSON_ARRAY);

                name = new String[users.length()];
                gender = new String[users.length()];
                phn = new String[users.length()];

                for(int i=0;i<users.length();i++){
                    JSONObject jo = users.getJSONObject(i);
                    name[i] = jo.getString(KEY_NAME);
                    gender[i] = jo.getString(KEY_GENDER);
                    phn[i] = jo.getString(KEY_PHN);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
