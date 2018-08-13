package comethanl33.github.repetition;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ethan on 6/6/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private GameManager gameManager;
    private SurfaceHolder surfaceHolder;
    private Paint paint = new Paint();
    private String paintStr;
    private int r, g, b;

    private int score; //score holder
    private int highScore;
    SharedPreferences sharedPreferences;

    private int rows;
    private int cols;

    private int rowsAndCols;



    public GameView(Context context) {
        super(context);

        score = 1;
        sharedPreferences = context.getSharedPreferences("Repetition", Context.MODE_PRIVATE);

        rows = sharedPreferences.getInt("rowsAndCols", 4);
        cols = sharedPreferences.getInt("rowsAndCols", 4);
        rowsAndCols = sharedPreferences.getInt("rowsAndCols", 4);

        if (rowsAndCols == 4)
            highScore = sharedPreferences.getInt("best4x4",1);
        else if (rowsAndCols == 3)
            highScore = sharedPreferences.getInt("best3x3",1);
        else
            highScore = sharedPreferences.getInt("best2x2",1);

        getHolder().addCallback(this);

        paintStr = sharedPreferences.getString("paint", "Blue");
        if (paintStr.equals("Pink"))
        {
            r = 255;
            g = 20;
            b = 147;
        }
        else if (paintStr.equals("Green"))
        {
            r = 0;
            g = 255;
            b = 0;
        }
        else
        {
            r = 0;
            g = 0;
            b = 255;
        }

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        paint.setColor(Color.rgb(255, 255, 255));
        getHolder().setFormat(0x00000004);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameManager = new GameManager(cols, rows, r, g, b);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
        ((HomeScreen) getContext()).finish();
    }

    public void update() {
        gameManager.update();
        score = gameManager.getLevel();

        if (gameManager.isLoser() || gameManager.isWinner()) {
            //enter high score
            if (highScore < score)
                highScore = score;

            SharedPreferences.Editor e = sharedPreferences.edit();
            if (rowsAndCols == 4)
            {
                e.putInt("best4x4", highScore);
                e.putInt("last4x4", score);
            }
            else if (rowsAndCols == 3)
            {
                e.putInt("best3x3", highScore);
                e.putInt("last3x3", score);
            }
            else
            {
                e.putInt("best2x2", highScore);
                e.putInt("last2x2", score);
            }
            e.apply();

            if (gameManager.isWinner())
            {
                gameManager.setWinToFalse();
                try {
                    thread.sleep(800);// 1000 milliseconds is one second.
                } catch (InterruptedException ex) {
                    thread.currentThread().interrupt();
                }
            }

            if (gameManager.isLoser()) {
                Context context = getContext();
                context.startActivity(new Intent(context, HomeScreen.class));
            }

        }
    }

    //TODO: Lag after user touch is caused by this sleep statement, figure out a way to oly call sleep when the user is watching the simulation
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            try {
                thread.sleep(270);// 1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                thread.currentThread().interrupt();
            }


            try {
                gameManager.draw(canvas);
                gameManager.drawSimulation(canvas);
                gameManager.drawInput(canvas);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();

        int action = MotionEventCompat.getActionMasked(e);

        switch (action) {
            case (MotionEvent.ACTION_DOWN): // Called when the user first presses the touchscreen.
            {
                //Log.d(DEBUG_TAG, "Action was DOWN");
                gameManager.onTouch(x, y);
                return true;
            }

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


}
