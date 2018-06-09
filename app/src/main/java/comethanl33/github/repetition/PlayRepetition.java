package comethanl33.github.repetition;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ethan on 6/6/2018.
 */

// 4 x 4 grid that will light up one grid block at a time
public class PlayRepetition extends Activity {

    private int grid = 0;
    private int prevGrid = -1;
    private int gridIndex;

    private int currentLevel = 1;
    private int LEVEL = 1;
    private int numTouches = 0;

    private boolean win = false;
    private boolean lose = false;

    private boolean simulationFinished = false;
    private boolean drawFinished = false;
    private List<Integer> solution = new ArrayList<>();

    private int numColumns, numRows;

    private int width = getScreenWidth();
    private int height = getScreenHeight();
    private Paint linePaint = new Paint();
    private Paint gridPaint = new Paint();


    public PlayRepetition(int cols, int rows) {
        numColumns = cols;
        numRows = rows;

        linePaint.setColor(Color.rgb(255, 255, 255));
        gridPaint.setColor(Color.rgb(255, 255, 255));
    }

    public void draw(Canvas canvas) {
        if (canvas != null)
        {

            if(simulationFinished)
                canvas.drawColor(Color.BLACK); // resets the canvas to allow user to start
            else
                gridPaint.setColor(Color.rgb(255, 255, 255));

            // Vertical Lines
            for (int i = 1; i < numColumns; i++) {
                canvas.drawLine(width * i / numColumns, 0, width * i / numColumns, height, linePaint);
            }

            // Horizontal Lines
            for (int i = 1; i < numRows; i++) {
                canvas.drawLine(0, height * i / numRows, width, height * i / numRows, linePaint);
            }
             drawFinished = false;

            // Lights up the grid at a certain index
            // TODO: how would you optimize this?  do we need this many if else statements?

            float left;
            float top;
            float right;
            float bottom;

            if (grid != -1) {
                if (grid >= 1 && grid <= 4) {
                    top = (float)0;
                    bottom = (height / (float)numRows);
                }
                else if (grid >= 5 && grid <= 8) {
                    top = (height /(float)numRows);
                    bottom = (height * 2 / (float)numRows);
                }
                else if (grid >= 9 && grid <= 12) {
                    top = (height * 2 /(float)numRows);
                    bottom = (height * 3 / (float)numRows);
                }
                else {
                    top = (height * 3 /(float)numRows);
                    bottom = (height * 4 / (float)numRows);
                }

                if (grid == 1 || grid == 5 || grid == 9 || grid == 13) {
                    left = (float)0;
                    right = (width / (float)numColumns);
                }
                else if (grid == 2 || grid == 6 || grid == 10 || grid == 14) {
                    left = (width / (float)numColumns);
                    right = (width * 2 / (float)numColumns);
                }
                else if (grid == 3 || grid == 7 || grid == 11 || grid == 15) {
                    left = (width * 2/ (float)numColumns);
                    right = (width * 3 / (float)numColumns);
                }
                else {
                    left = (width * 3/ (float)numColumns);
                    right = (width * 4 / (float)numColumns);
                }
                canvas.drawRect(left, top, right, bottom, gridPaint);
                drawFinished = true;
            }
            grid = -1;
        }
    }

    public void update()
    {
        if (isWinner() && drawFinished)
        {
            LEVEL++;
            currentLevel = LEVEL;
            simulationFinished = false;
            win = false;
        }
        if (currentLevel > 0)
        {
            grid = (int)((Math.random() * 16) + 1);
            while (prevGrid == grid)
                grid = (int)((Math.random() * 16) + 1);
            solution.add(grid);
            currentLevel--;
            prevGrid = grid;
        }
        if (currentLevel == 0)
        {
            simulationFinished = true;
            currentLevel--;
        }

    }

    public void validate()
    {
        grid = gridIndex;

        if (numTouches > solution.size()) {
            lose = true;
            gridPaint.setColor(Color.rgb(255, 0, 0));
        }
        if (solution.get(numTouches - 1) == gridIndex) {
            gridPaint.setColor(Color.rgb(0, 0, 255));
            if (numTouches == solution.size()) {
                win = true;
            }
        }
        else {
            lose = true;
            gridPaint.setColor(Color.rgb(255, 0, 0));
        }
    }

    public int findGridIndex(float x, float y, int numColumns, int numRows, int screenWidth, int screenHeight){
        int indexX = (int)(x / (screenWidth / numColumns)) + 1;
        int indexY = (int)(y / (screenHeight / numRows)) + 1;
        return indexX + ((indexY - 1) * numColumns);
    }

    public void onTouch(float x, float y)
    {
        gridIndex = findGridIndex(x, y, this.numColumns, this.numRows, this.width, this.height);

        if (simulationFinished) {
            numTouches++;
            validate();
        }

    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public boolean isWinner()
    {
        return win;
    }

    public boolean isLoser()
    {
        return lose;
    }

    public int getLEVEL()
    {
        return LEVEL;
    }

}
