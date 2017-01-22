package com.example.felix.daboyzcalculator;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

/**
 * Created by felix on 11/11/16.
 */

public class SavePopup extends Dialog{

    public Activity c;
    public Dialog d;
    Note data;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();



    public SavePopup(Activity a, Note note) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.data = note;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup);
        Button save = (Button) findViewById(R.id.save);
        final TextView note = (TextView) findViewById(R.id.note);
        d = this;

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = database.push();
                data.note = note.getText().toString();
                db.setValue(data);
                d.dismiss();
            }

        });
    }


}