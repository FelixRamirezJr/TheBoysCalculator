package com.example.felix.daboyzcalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SavedData extends AppCompatActivity {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    List<Note> notes = new ArrayList<>();
    NoteAdapter noteAdapter;
    ListView listView;
    Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ProgressBar load = (ProgressBar) findViewById(R.id.loading);
        load.setVisibility(View.VISIBLE);
        act = this;

        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                listView = (ListView) findViewById(R.id.list);
                noteAdapter = new NoteAdapter(notes, act);
                // View header = inflater.inflate(R.layout.feed_user_post,null);
                //listView.addHeaderView(header);
                listView.setAdapter(noteAdapter);
                noteAdapter.notifyDataSetChanged();
                load.setVisibility(View.GONE);

                listView.setOnItemClickListener( listener() );
                listView.setItemsCanFocus(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            public void onCancelled(FirebaseDatabase firebaseError) {
            }
        });

        mRootRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Log.d("TEST", "TEEEESS");
                String d = dataSnapshot.getValue(Note.class).note;
                notes.add(dataSnapshot.getValue(Note.class));
                Log.d("NOte: ", d);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public AdapterView.OnItemClickListener listener() {
        AdapterView.OnItemClickListener listt =  new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String noteToDelete = ((TextView)view.findViewById(R.id.textViewNote)).getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setTitle("Delete Note?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final String[] keyToDelete = new String[1];
                        final DatabaseReference dbf = FirebaseDatabase.getInstance().getReference();
                        dbf.orderByKey().addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                                if (noteToDelete.equalsIgnoreCase(dataSnapshot.getValue(Note.class).note)) {
                                    keyToDelete[0] = dataSnapshot.getKey().toString();
                                    Log.d("Key: ", keyToDelete[0]);
                                    Log.d(noteToDelete, noteToDelete);
                                    DatabaseReference delete = FirebaseDatabase.getInstance().getReference().child(keyToDelete[0]);
                                    delete.removeValue();
                                    notes.remove(position);
                                    noteAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        };
        return listt;
    }
}

