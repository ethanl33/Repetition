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

/**
 * Created by Ethan on 6/6/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private PlayRepetition playRepetition;
    private SurfaceHolder surfaceHolder;
    private Paint paint = new Paint();
    private String text;

    private int score; //score holder
    private int highScore;
    SharedPreferences sharedPreferences;

    private static int ROWS = 4;
    private static int COLS = 4;


    public GameView(Context context) {
        super(context);

        score = 1;
        sharedPreferences = context.getSharedPreferences("Repetition", Context.MODE_PRIVATE);
        highScore = sharedPreferences.getInt("score",1);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        paint.setColor(Color.rgb(255, 255, 255));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        playRepetition = new PlayRepetition(ROWS, COLS);
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
        playRepetition.update();
        score = playRepetition.getLEVEL();

        if (playRepetition.isLoser() || playRepetition.isWinner()) {
            //enter high score
            if (highScore < score)
                highScore = score;

            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putInt("score", highScore);
            e.putInt("lastScore", score);
            e.apply();

            if (playRepetition.isLoser()) {
                Context context = getContext();
                context.startActivity(new Intent(context, HomeScreen.class));
            }

        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            try {
                thread.sleep(430);// 1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                thread.currentThread().interrupt();
            }
            try {
                playRepetition.draw(canvas);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (playRepetition.isWinner() || playRepetition.isLoser()){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                if (playRepetition.isWinner())
                    text = "Not Bad!";
                if (playRepetition.isLoser())
                    text = "You Suck!";

                int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText(text,canvas.getWidth() / 2, yPos, paint);
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
                //Log.d(DEBUG_TAG, "Action was DOWN");
                playRepetition.onTouch(x, y);
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

}
