package com.example.felix.daboyzcalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by felix on 11/11/16.
 */

public class NoteAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Note> feedItems;
    View feedItemLayout;
    Application applicationContext;
    private String userId;
    private String username;
    String micropost_id;
    View linkPreviewView;
    Note item;

    public NoteAdapter(List<Note> feedItems,Activity activity) {
        this.feedItems = feedItems;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    public void setApplicationContext(Application application){
        this.applicationContext = application;
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.note_layout, null);
            //convertView = feedItemLayout;
        }



        TextView note = (TextView) convertView.findViewById(R.id.textViewNote);
        TextView them = (TextView) convertView.findViewById(R.id.textViewThem);
        TextView us = (TextView) convertView.findViewById(R.id.textViewUs);
        TextView calc = (TextView) convertView.findViewById(R.id.textViewCalc);
        //TextView total = (TextView) convertView.findViewById(R.id.textViewTotal);
        TextView percent = (TextView) convertView.findViewById(R.id.textViewPercentage);

        item = feedItems.get(position);
        convertView.setTag(note);

        note.setText(item.note);
        them.setText(item.them);
        us.setText(item.us);
        calc.setText(item.calculation);
        //total.setText(item.total);
        percent.setText("Rate: " + item.percentage);
        return convertView;
    }


}
