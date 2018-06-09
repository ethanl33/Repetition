package comethanl33.github.repetition;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
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
    private MainActivity mainActivity;


    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        mainActivity = new MainActivity();
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        playRepetition = new PlayRepetition(4, 4, mainActivity.getLevel());
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

        if (playRepetition.isLoser()) {
            //mainActivity.enterCurrentScore(-1);
            //enter high score
            Context context = getContext();
            context.startActivity(new Intent(context, HomeScreen.class));
        }
        if (playRepetition.isWinner()) {
            //mainActivity.enterCurrentScore(mainActivity.getCurrentScore() + 1);
            Context context = getContext();
            context.startActivity(new Intent(context, WinScreen.class));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            playRepetition.draw(canvas);
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
                //Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                        //"of current screen element");
                return true;
            default:
                return super.onTouchEvent(e);
        }
    }
}
