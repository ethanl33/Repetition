package comethanl33.github.repetition;

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
public class ComputerGraphics{

    private int grid = 0;
    private int prevGrid = -1;
    private int gridIndex;

    private int level;
    private int numTouches = 0;

    private boolean win;
    private boolean lose;

    private boolean simulationFinished = false;
    private List<Integer> solution = new ArrayList<>();

    private int numColumns, numRows;

    private int width = getScreenWidth();
    private int height = getScreenHeight();
    private Paint linePaint = new Paint();
    private Paint gridPaint = new Paint();


    public ComputerGraphics(int cols, int rows, int level) {
        this.level = level;
        numColumns = cols;
        numRows = rows;
        linePaint.setColor(Color.rgb(255, 255, 255));
        gridPaint.setColor(Color.rgb(255, 255, 255));
    }

    public void draw(Canvas canvas) {
        if (canvas != null)
        {

            if(simulationFinished)
            {
                canvas.drawColor(Color.BLACK); // resets the canvas to allow user to start
            }

            // Vertical Lines
            for (int i = 1; i < numColumns; i++) {
                canvas.drawLine(width * i / numColumns, 0, width * i / numColumns, height, linePaint);
            }

            // Horizontal Lines
            for (int i = 1; i < numRows; i++) {
                canvas.drawLine(0, height * i / numRows, width, height * i / numRows, linePaint);
            }

            // Lights up the grid at a certain index
            if (grid == 1)
                canvas.drawRect((float)0, (float)0, width / (float)numColumns, height / (float)numRows, gridPaint);
            else if (grid == 2)
                canvas.drawRect(width / (float)numColumns, (float)0, width * 2 / (float)numColumns, height / (float)numRows, gridPaint);
            else if (grid == 3)
                canvas.drawRect(width * 2 / (float)numColumns, (float)0, width * 3 / (float)numColumns, height / (float)numRows, gridPaint);
            else if (grid == 4)
                canvas.drawRect(width * 3 / (float)numColumns, (float)0, width * 4 / (float)numColumns, height / (float)numRows, gridPaint);
            else if (grid == 5)
                canvas.drawRect((float)0, height /(float)numRows, width / (float)numColumns, height * 2 / (float)numRows, gridPaint);
            else if (grid == 6)
                canvas.drawRect(width / (float)numColumns, height /(float)numRows, width * 2 / (float)numColumns, height * 2 / (float)numRows, gridPaint);
            else if (grid == 7)
                canvas.drawRect(width * 2 / (float)numColumns, height /(float)numRows, width * 3 / (float)numColumns, height * 2 / (float)numRows, gridPaint);
            else if (grid == 8)
                canvas.drawRect(width * 3 / (float)numColumns, height /(float)numRows, width * 4 / (float)numColumns, height * 2 / (float)numRows, gridPaint);
            else if (grid == 9)
                canvas.drawRect((float)0, height * 2 /(float)numRows, width / (float)numColumns, height * 3 / (float)numRows, gridPaint);
            else if (grid == 10)
                canvas.drawRect(width / (float)numColumns, height * 2 /(float)numRows, width * 2 / (float)numColumns, height * 3 / (float)numRows, gridPaint);
            else if (grid == 11)
                canvas.drawRect(width * 2 / (float)numColumns, height * 2 /(float)numRows, width * 3 / (float)numColumns, height * 3 / (float)numRows, gridPaint);
            else if (grid == 12)
                canvas.drawRect(width * 3 / (float)numColumns, height * 2 /(float)numRows, width * 4 / (float)numColumns, height * 3 / (float)numRows, gridPaint);
            else if (grid == 13)
                canvas.drawRect((float)0, height * 3 /(float)numRows, width / (float)numColumns, height * 4 / (float)numRows, gridPaint);
            else if (grid == 14)
                canvas.drawRect(width / (float)numColumns, height * 3 /(float)numRows, width * 2 / (float)numColumns, height * 4 / (float)numRows, gridPaint);
            else if (grid == 15)
                canvas.drawRect(width * 2 / (float)numColumns, height * 3 /(float)numRows, width * 3 / (float)numColumns, height * 4 / (float)numRows, gridPaint);
            else if (grid == 16)
                canvas.drawRect(width * 3 / (float)numColumns, height * 3 /(float)numRows, width * 4 / (float)numColumns, height * 4 / (float)numRows, gridPaint);

            grid = 0;
        }
    }

    public void update()
    {
        if (level > 0)
        {
            grid = (int)((Math.random() * 16) + 1);
            while (prevGrid == grid)
                grid = (int)((Math.random() * 16) + 1);
            solution.add(grid);
            level--;
            prevGrid = grid;
        }
        if (level == 0)
            simulationFinished = true;

    }

    public boolean validate()
    {
        /*
        if (simulationFinished) {
            grid = gridIndex;
            if (numTouches == solution.size()) {
                win = true;
                gridPaint.setColor(Color.rgb(0, 255, 0));
            }
            else if (numTouches < solution.size()) {
                if (gridIndex == solution.get(solution.size() - numTouches)) {
                    gridPaint.setColor(Color.rgb(0, 0, 255));
                    //index++;
                    return true;
                }
                else {
                    lose = true;
                    gridPaint.setColor(Color.rgb(255, 0, 0));
                }
            }
            else {
                lose = true;
            }
        }Î
        return false;
        */
        grid = gridIndex;
        if (numTouches > solution.size()) {
            lose = true;
            gridPaint.setColor(Color.rgb(255, 0, 0));
            return false;
        }

        if (solution.get(numTouches - 1) == gridIndex) {
            if (numTouches == solution.size()) {
                win = true;
            }
            
            gridPaint.setColor(Color.rgb(0, 0, 255));
            return true;
        } else {
            lose = true;
            gridPaint.setColor(Color.rgb(255, 0, 0));
            return false;
        }
    }

    public void onTouch(float x, float y)
    {
        if (x >= 0 && x <= width / (float)numColumns  && y >= 0 && y <= height / (float)numRows){
            gridIndex = 1;
        }
        else if (x >= width / (float)numColumns && x <= width * 2 / (float)numColumns  && y >= 0 && y <= height / (float)numRows){
            gridIndex = 2;
        }
        else if (x >= width * 2 / (float)numColumns && x <= width * 3 / (float)numColumns  && y >= 0 && y <= height / (float)numRows){
            gridIndex = 3;
        }
        else if (x >= width * 3 / (float)numColumns && x <= width * 4 / (float)numColumns  && y >= 0 && y <= height / (float)numRows){
            gridIndex = 4;
        }
        else if (x >= 0 && x <= width / (float)numColumns  && y >= height /(float)numRows && y <= height * 2 / (float)numRows){
            gridIndex = 5;
        }
        else if (x >= width / (float)numColumns && x <= width * 2 / (float)numColumns  && y >= height /(float)numRows && y <= height * 2 / (float)numRows){
            gridIndex = 6;
        }
        else if (x >= width * 2 / (float)numColumns && x <= width * 3 / (float)numColumns  && y >= height /(float)numRows && y <= height * 2 / (float)numRows){
            gridIndex = 7;
        }
        else if (x >= width * 3 / (float)numColumns && x <= width * 4 / (float)numColumns  && y >= height /(float)numRows && y <= height * 2 / (float)numRows){
            gridIndex = 8;
        }
        else if (x >= 0 && x <= width / (float)numColumns  && y >= height * 2 /(float)numRows && y <= height * 3 / (float)numRows){
            gridIndex = 9;
        }
        else if (x >= width / (float)numColumns && x <= width * 2 / (float)numColumns  && y >= height * 2 /(float)numRows && y <= height * 3 / (float)numRows){
            gridIndex = 10;
        }
        else if (x >= width * 2 / (float)numColumns && x <= width * 3 / (float)numColumns  && y >= height * 2 /(float)numRows && y <= height * 3 / (float)numRows){
            gridIndex = 11;
        }
        else if (x >= width * 3 / (float)numColumns && x <= width * 4 / (float)numColumns  && y >= height * 2 /(float)numRows && y <= height * 3 / (float)numRows){
            gridIndex = 12;
        }
        else if (x >= 0 && x <= width / (float)numColumns  && y >= height * 3 /(float)numRows && y <= height * 4 / (float)numRows){
            gridIndex = 13;
        }
        else if (x >= width / (float)numColumns && x <= width * 2 / (float)numColumns  && y >= height * 3 /(float)numRows && y <= height * 4 / (float)numRows){
            gridIndex = 14;
        }
        else if (x >= width * 2 / (float)numColumns && x <= width * 3 / (float)numColumns  && y >= height * 3 /(float)numRows && y <= height * 4 / (float)numRows){
            gridIndex = 15;
        }
        else if (x >= width * 3 / (float)numColumns && x <= width * 4 / (float)numColumns  && y >= height * 3 /(float)numRows && y <= height * 4 / (float)numRows){
            gridIndex = 16;
        }
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
}
