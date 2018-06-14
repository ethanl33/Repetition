package comethanl33.github.repetition;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    TextView best, last;
    SharedPreferences sharedPreferences;
    RadioButton gameMode;
    String selectedGameMode;
    int rowsAndCols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        best = findViewById(R.id.bestScore);
        last = findViewById(R.id.lastScore);

        sharedPreferences  = getSharedPreferences("Repetition", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sharedPreferences.edit();

        //setting the values to the textViews
        //best.setTextColor(Color.rgb(238, 144, 137)); #ee9089

        best.setText("Best: " + sharedPreferences.getInt("best4x4",1));
        last.setText("Last: " + sharedPreferences.getInt("last4x4", 1) );

        selectedGameMode = sharedPreferences.getString("selectedGameMode", "");
        if (selectedGameMode.equals(""))
        {
            rowsAndCols = 4;
            best.setText("Best: " + sharedPreferences.getInt("best4x4",1));
            last.setText("Last: " + sharedPreferences.getInt("last4x4", 1) );
        }
        else
        {
            if (selectedGameMode.equals("2 x 2"))
            {
                rowsAndCols = 2;
                best.setText("Best: " + sharedPreferences.getInt("best2x2",1));
                last.setText("Last: " + sharedPreferences.getInt("last2x2", 1) );
            }

            else if (selectedGameMode.equals("3 x 3"))
            {
                rowsAndCols = 3;
                best.setText("Best: " + sharedPreferences.getInt("best3x3",1));
                last.setText("Last: " + sharedPreferences.getInt("last3x3", 1) );
            }

            else
            {
                rowsAndCols = 4;
                best.setText("Best: " + sharedPreferences.getInt("best4x4",1));
                last.setText("Last: " + sharedPreferences.getInt("last4x4", 1) );
            }

        }


        e.putInt("rowsAndCols", rowsAndCols);
        e.apply();
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

    public void chooseGameMode(View view)
    {
        Intent intent = new Intent(this, GameModeManager.class);
        startActivity(intent);
    }


}
