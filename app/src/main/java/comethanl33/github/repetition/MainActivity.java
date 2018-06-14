package comethanl33.github.repetition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();  // optional depending on your needs
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
        finish();
    }
}
