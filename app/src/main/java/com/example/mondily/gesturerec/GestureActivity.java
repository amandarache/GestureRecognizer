package com.example.mondily.gesturerec;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

public class GestureActivity extends Activity {
    GestureLibrary gestureLib;
    String TAG;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_activity);

        // Library with all gestures in raw directory
        gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gestureLib.load()) {
            finish();
        }

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.setGestureColor(Color.GRAY);
        gestures.addOnGesturePerformedListener(handleGestureListener);
    }


    // Listen for gestures and perform action
    private OnGesturePerformedListener handleGestureListener = new OnGesturePerformedListener() {
        @Override
        public void onGesturePerformed(GestureOverlayView gestureView, Gesture gesture) {

            ArrayList<Prediction> predictions = gestureLib.recognize(gesture);

            // if a gesture is found
            if (predictions.size() > 0) {
                Prediction prediction = predictions.get(0);

                // if gesture exists in library
                if (prediction.score > 1.0) {
                        // Toast message pop-up
                        Toast.makeText(GestureActivity.this, prediction.name, Toast.LENGTH_SHORT).show();
                }
                // if gesture not found in library
                else {
                    // debug
                    Log.w(TAG, "----> score was less than 0 <----");
                    Toast.makeText(GestureActivity.this, "Gesture not found. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };
}