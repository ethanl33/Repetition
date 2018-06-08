package comethanl33.github.repetition;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

/**
 * Created by Ethan on 6/6/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private PlayRepetition playRepetition;

    private float previousX = -1;
    private float previousY = -1;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        playRepetition = new PlayRepetition(4, 4, 3);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
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
        ((HomeScreen)getContext()).finish();
    }

    public void update()
    {
        playRepetition.update();
        if (playRepetition.isWinner() || playRepetition.isLoser()) {
            Context context = getContext();
            context.startActivity(new Intent(context, HomeScreen.class));
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        if (canvas != null)
        {
            playRepetition.draw(canvas);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent e)
    {

        float x = e.getX();
        float y = e.getY();

        if(previousX != x || previousY != y)
            playRepetition.onTouch(x, y);

        previousX = x;
        previousY = y;

        return true;
    }
}
