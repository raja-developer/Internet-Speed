package com.raja.internetspeed;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.raja.internetspeed.Support.Connectivity;

public class History extends AppCompatActivity {

    ImageView search;
    EditText mobile_num;

    TextView mob_num_tv;
    TextView speed_tv;
    TextView state_tv;
    TextView timestamp_tv;

    LinearLayout details_ll;
    CardView details_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();
    }

    public void init() {

        try {

            initializeLayouts();

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Connectivity.isConnected(getApplicationContext())) {

                        if (mobile_num.getText().toString().trim().length() == 10) {
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                            Query phoneQuery = database.child("Details").child(mobile_num.getText().toString());

                            phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                                        details_cv.setVisibility(View.VISIBLE);
                                        mob_num_tv.setText("Mobile Number : " + mobile_num.getText().toString());
                                        state_tv.setText("State : " + dataSnapshot.child("State").getValue());
                                        speed_tv.setText("Speed : " + dataSnapshot.child("Speed").getValue());
                                        timestamp_tv.setText("TimeStamp : " + dataSnapshot.child("timestamp").getValue());

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.e("", "onCancelled", databaseError.toException());
                                    details_cv.setVisibility(View.GONE);
                                }
                            });

                        } else {

                            Toast.makeText(getApplicationContext(), "Please enter 10 digit Mobile Number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Turn ON Internet", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } catch (Exception exception) {
            Log.d("History__", "" + exception.getMessage());
        }

    }

    public void initializeLayouts() {

        try {

            search = (ImageView) findViewById(R.id.search);
            mobile_num = (EditText) findViewById(R.id.mobile_num);

            mob_num_tv = (TextView) findViewById(R.id.mob_num_tv);
            speed_tv = (TextView) findViewById(R.id.speed_tv);
            state_tv = (TextView) findViewById(R.id.state_tv);
            timestamp_tv = (TextView) findViewById(R.id.timestamp_tv);

            details_ll = (LinearLayout) findViewById(R.id.details_ll);
            details_cv = (CardView) findViewById(R.id.details_cv);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}