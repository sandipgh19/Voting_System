package com.example.sandip.voting_system;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by sandip on 9/19/2016.
 */

public class Custom_follow extends ArrayAdapter<String>{

        private String[] name;
        private String[] gender;
        private String[] phn;
        private Activity context;

        public Custom_follow(Activity context, String[] name, String[] gender, String[] phn) {
            super(context, R.layout.list_view_layout, name);
            this.context = context;
            this.name = name;
            this.gender = gender;
            this.phn = phn;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);
            TextView textView_name = (TextView) listViewItem.findViewById(R.id.name);
            TextView textView_gender = (TextView) listViewItem.findViewById(R.id.gender);
            TextView textView_phn = (TextView) listViewItem.findViewById(R.id.phn);

            textView_name.setText(name[position]);
            textView_gender.setText(gender[position]);
            textView_phn.setText(phn[position]);

            return listViewItem;
        }
    }
