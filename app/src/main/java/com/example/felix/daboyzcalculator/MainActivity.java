package com.example.felix.daboyzcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView others;
    TextView us;
    EditText total;
    EditText rate;
    double tot;
    double rat;
    double divided;
    double other;
    double me;
    double otherRounded;
    double meRounded;
    boolean dataEnter = false;
    Activity myActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        others = (TextView) findViewById(R.id.others);
        us = (TextView) findViewById(R.id.us);
        rate = (EditText) findViewById(R.id.rate);
        total = (EditText) findViewById(R.id.total);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            calculate();
            }
        });

        FloatingActionButton save = (FloatingActionButton) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        FloatingActionButton list = (FloatingActionButton) findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),SavedData.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void calculate ()
    {
        if(total.getText().length() > 0 && rate.getText().length() > 0) {
            tot = Double.parseDouble(total.getText().toString());
            rat = Double.parseDouble(rate.getText().toString());
            divided = tot / 6;
            other = divided + (divided * (rat / 100));
            me = (tot - (other * 4)) / 2;
            otherRounded = Math.round(other * 100.0) / 100.0;
            meRounded = Math.round(me * 100.0) / 100.0;
            others.setText("Others will pay: $" + otherRounded + " each");
            us.setText("We will pay: $" + meRounded + " each\n\n" + otherRounded + " x 4 = " + (otherRounded * 4) + "\n" + meRounded + " x 2 = " + (2 * meRounded) + "\nTotal: " + tot);
            dataEnter = true;
        }else{
            Toast.makeText(this,"Fill in Total and Rate",Toast.LENGTH_LONG);
        }
    }

    void save()
    {
        if(dataEnter){
            Note n = new Note();
            n.them = others.getText().toString();
            n.us = "We will pay: $" + meRounded + " each";
            n.calculation = otherRounded + " x 4 = " + (otherRounded * 4) + "\n" + meRounded + " x 2 = " + (2 * meRounded) + "\nTotal: " + tot;
            n.total = String.valueOf(tot);
            n.percentage = String.valueOf(rat / 100);
            SavePopup editNameDialog = new SavePopup(this, n);
            editNameDialog.show();
        }else{
            Toast.makeText(this,"Need to calculate first",Toast.LENGTH_LONG);
        }
    }

}
