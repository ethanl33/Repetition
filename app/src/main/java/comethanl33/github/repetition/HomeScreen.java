package comethanl33.github.repetition;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    TextView textView,textView2,textView3,textView4;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        textView = (TextView) findViewById(R.id.textView);

        sharedPreferences  = getSharedPreferences("Repetition", Context.MODE_PRIVATE);

        //setting the values to the textViews
        textView.setText("Level " + sharedPreferences.getInt("score",1));
    }

    public void startGame(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
