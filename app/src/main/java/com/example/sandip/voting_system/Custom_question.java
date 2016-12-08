package com.example.sandip.voting_system;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by sandip on 7/15/2016.
 */

    public class Custom_question extends ArrayAdapter<String> {
        private String[] ids;
        private String[] question;
        private Activity context;

        public Custom_question(Activity context,String[] ids, String[] question) {
            super(context, R.layout.question_page, ids);
            this.context = context;
            this.ids=ids;
            this.question = question;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.question_page, null, true);
            TextView txt= (TextView) listViewItem.findViewById(R.id.id);
            TextView txt1 = (TextView) listViewItem.findViewById(R.id.questionView);
            txt.setText(ids[position]);
            txt1.setText(question[position]);
            return listViewItem;
        }
    }
