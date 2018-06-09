package comethanl33.github.repetition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WinScreen extends AppCompatActivity {


    private int level;
    private MainActivity mainActivity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        level = mainActivity.getLevel() + 1;
    }

    public void NextLevel(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("currentScore", level);
        startActivity(intent);
    }
}
