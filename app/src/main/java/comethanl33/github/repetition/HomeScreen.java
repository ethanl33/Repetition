package comethanl33.github.repetition;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    TextView best, last;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        best = findViewById(R.id.bestScore);
        last = findViewById(R.id.lastScore);

        sharedPreferences  = getSharedPreferences("Repetition", Context.MODE_PRIVATE);

        //setting the values to the textViews
        best.setText("Best: " + sharedPreferences.getInt("score",1));
        last.setText("Last: " + sharedPreferences.getInt("lastScore", 1) );
        //textView.setTextColor(Color.parseColor("#FFFFFF"));
        //title.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        int action = MotionEventCompat.getActionMasked(e);

        switch (action) {
            case (MotionEvent.ACTION_DOWN): // Called when the user first presses the touchscreen.
                //Log.d(DEBUG_TAG, "Action was DOWN");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case (MotionEvent.ACTION_MOVE):
                //Log.d(DEBUG_TAG, "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                //Log.d(DEBUG_TAG, "Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                //Log.d(DEBUG_TAG, "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                //Log.d(DEBUG_TAG, "Movement occurred outside bounds " + "of current screen element");
                return true;
            default:
                return super.onTouchEvent(e);
        }
    }

    public void startGame(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
