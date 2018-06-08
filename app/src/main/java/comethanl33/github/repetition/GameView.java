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
    private ComputerGraphics computerGraphics;

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
        computerGraphics = new ComputerGraphics(4, 4, 3);
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
        computerGraphics.update();
        if (computerGraphics.isWinner() || computerGraphics.isLoser())
        {
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
            computerGraphics.draw(canvas);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent e)
    {

        float x = e.getX();
        float y = e.getY();

        if(previousX != x || previousY != y)
            computerGraphics.onTouch(x, y);

        previousX = x;
        previousY = y;

        return true;
    }
}
