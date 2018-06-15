package comethanl33.github.repetition;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;


/**
 * Created by Ethan on 6/6/2018.
 */

// 4 x 4 grid that will light up one grid block at a time
public class GameManager extends Activity{

    private int userGrid = -1;
    private int simulationGrid = -1;
    private int prevGrid = -1;
    private int gridIndex;
    private int yPos;

    private int numGridsToLightUp = 1;
    private int level = 1;
    private int numTouches = 0;

    private boolean win = false;
    private boolean lose = false;

    private boolean simulationFinished = false;
    private List<Integer> solutions = new ArrayList<>();

    private int numColumns, numRows;

    private int width = getScreenWidth();
    private int height = getScreenHeight();

    private Paint background = new Paint();
    private Paint gridColor = new Paint();
    private Paint warning = new Paint();
    private Paint paint = new Paint();

    private String text;

    private int r, g, b;

    private float[] coordinates = new float[4];


    public GameManager(int cols, int rows, int r, int g, int b) {
        numColumns = cols;
        numRows = rows;
        this.r = r;
        this.g = g;
        this.b = b;


        background.setColor(Color.rgb(255, 255, 255));
        gridColor.setColor(Color.rgb(255, 255, 255));
        warning.setColor(Color.rgb(255, 0, 0));
        paint.setColor(Color.rgb(255, 255, 255));
    }


    public void draw(Canvas canvas) throws InterruptedException {

        if(simulationFinished)
            canvas.drawColor(Color.BLACK); // resets the canvas to allow user to start #001933
        else
            gridColor.setColor(Color.rgb(255, 255, 255));

        // Vertical Lines
        for (int i = 1; i < numColumns; i++) {
            canvas.drawLine(width * i / numColumns, 0, width * i / numColumns, height, background);
        }

        // Horizontal Lines
        for (int i = 1; i < numRows; i++) {
            canvas.drawLine(0, height * i / numRows, width, height * i / numRows, background);
        }

        //drawing the score on the game screen
        background.setTextSize(37);
        canvas.drawText("Level:" + level,100,50, background);

    }


    public void drawSimulation(Canvas canvas) throws InterruptedException {

        // Lights up the grid at a certain index
        if (simulationGrid != -1) {

            coordinates = findGridCoordinates(simulationGrid);

            canvas.drawRect(coordinates[0], coordinates[1], coordinates[2], coordinates[3], gridColor);
            simulationGrid = -1;
        }
    }

    public void drawInput(Canvas canvas) throws InterruptedException {


        draw(canvas);
        // Lights up the grid at a certain index
        if (userGrid != -1) {

            coordinates = findGridCoordinates(userGrid);

            canvas.drawRect(coordinates[0], coordinates[1], coordinates[2], coordinates[3], gridColor);
            userGrid = -1;
        }

        if (win || lose){
            paint.setTextSize(100);
            paint.setTextAlign(Paint.Align.CENTER);

            if (win)
            {
                if (level <= 2)
                    text = "Beginner's Luck!";
                else if (level <= 4)
                    text = "Not Bad!";
                else if (level <= 6)
                    text = "Wow!";
                else if (level <= 8)
                    text = "Keep Going!";
                else if (level <= 10)
                    text = "Unbelievable!";
                else
                    text = "Legendary!";
            }
            if (lose)
                text = "Maybe Next Time!";

            yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
            canvas.drawText(text,canvas.getWidth() / 2, yPos, paint);

        }

        warning.setTextSize(200);
        warning.setTextAlign(Paint.Align.CENTER);
        yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
        if (!simulationFinished)
            canvas.drawText("WATCH!",canvas.getWidth() / 2, yPos, warning);
        userGrid = -1;
    }

    public void update()
    {
        if (isWinner())
        {
            level++;
            numGridsToLightUp = getLevel();
            simulationFinished = false;
            //win = false;
        }
        if (numGridsToLightUp == 0)
        {
            simulationFinished = true;
            numGridsToLightUp--;
        }
        if (numGridsToLightUp > 0)
        {
            simulationGrid = (int)((Math.random() * (numColumns * numRows)) + 1);
            while (prevGrid == simulationGrid)
                simulationGrid = (int)((Math.random() * (numColumns * numRows)) + 1);
            solutions.add(simulationGrid);
            numGridsToLightUp--;
            prevGrid = simulationGrid;
        }

    }

    public void validate()
    {
        if (numTouches > solutions.size()) {
            lose = true;
            gridColor.setColor(Color.rgb(255, 0, 0));
        }
        if (solutions.get(numTouches - 1) == gridIndex) {
            gridColor.setColor(Color.rgb(r, g, b));
            if (numTouches == solutions.size()) {
                win = true;
            }
        }
        else {
            lose = true;
            gridColor.setColor(Color.rgb(255, 0, 0));
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
            userGrid = gridIndex;
            numTouches++;
            validate();
        }

    }

    public float[] findGridCoordinates(int gridToLightUp) {
        float coordinates[] = new float[4]; // 0 = left, 1 = top, 2 = right, 3 = bottom

        if (numColumns == 4 && numRows == 4)
        {
            if (gridToLightUp >= 1 && gridToLightUp <= 4) {
                //top = (float)0;
                //bottom = (height / (float)numRows);
                coordinates[1] = (float)0;
                coordinates[3] = (height / (float)numRows);
            }
            else if (gridToLightUp >= 5 && gridToLightUp <= 8) {
                //top = (height /(float)numRows);
                //bottom = (height * 2 / (float)numRows);
                coordinates[1] = (height /(float)numRows);
                coordinates[3] = (height * 2 / (float)numRows);
            }
            else if (gridToLightUp >= 9 && gridToLightUp <= 12) {
                //top = (height * 2 /(float)numRows);
                //bottom = (height * 3 / (float)numRows);
                coordinates[1] = (height * 2 /(float)numRows);
                coordinates[3] = (height * 3 / (float)numRows);
            }
            else {
                //top = (height * 3 /(float)numRows);
                //bottom = (height * 4 / (float)numRows);
                coordinates[1] = (height * 3 /(float)numRows);
                coordinates[3] = (height * 4 / (float)numRows);
            }

            if (gridToLightUp == 1 || gridToLightUp == 5 || gridToLightUp == 9 || gridToLightUp == 13) {
                //left = (float)0;
                //right = (width / (float)numColumns);
                coordinates[0] = (float)0;
                coordinates[2] = (width / (float)numColumns);
            }
            else if (gridToLightUp == 2 || gridToLightUp == 6 || gridToLightUp == 10 || gridToLightUp == 14) {
                //left = (width / (float)numColumns);
                //right = (width * 2 / (float)numColumns);
                coordinates[0] = (width / (float)numColumns);
                coordinates[2] = (width * 2 / (float)numColumns);
            }
            else if (gridToLightUp == 3 || gridToLightUp == 7 || gridToLightUp == 11 || gridToLightUp == 15) {
                //left = (width * 2/ (float)numColumns);
                //right = (width * 3 / (float)numColumns);
                coordinates[0] = (width * 2/ (float)numColumns);
                coordinates[2] = (width * 3 / (float)numColumns);
            }
            else {
                //left = (width * 3/ (float)numColumns);
                //right = (width * 4 / (float)numColumns);
                coordinates[0] = (width * 3/ (float)numColumns);
                coordinates[2] = (width * 4 / (float)numColumns);
            }
        }
        else if (numColumns == 3 && numRows == 3)
        {
            if (gridToLightUp >= 1 && gridToLightUp <= 3) {
                coordinates[1] = (float) 0;
                coordinates[3] = (height / (float) numRows);
            }
            else if (gridToLightUp >= 4 && gridToLightUp <= 6) {
                coordinates[1] = (height /(float)numRows);
                coordinates[3] = (height * 2 / (float)numRows);
            }
            else {
                coordinates[1] = (height * 2 /(float)numRows);
                coordinates[3] = (height * 3 / (float)numRows);
            }

            if (gridToLightUp == 1 || gridToLightUp == 4 || gridToLightUp == 7) {
                coordinates[0] = (float)0;
                coordinates[2] = (width / (float)numColumns);
            }
            else if (gridToLightUp == 2 || gridToLightUp == 5 || gridToLightUp == 8) {
                coordinates[0] = (width / (float)numColumns);
                coordinates[2] = (width * 2 / (float)numColumns);
            }
            else {
                coordinates[0] = (width  * 2/ (float)numColumns);
                coordinates[2] = (width * 3 / (float)numColumns);
            }
        }
        else
        {
            if (gridToLightUp >= 1 && gridToLightUp <= 2) {
                coordinates[1] = (float) 0;
                coordinates[3] = (height / (float) numRows);
            }
            else {
                coordinates[1] = (height /(float)numRows);
                coordinates[3] = (height * 2 / (float)numRows);
            }

            if (gridToLightUp == 1 || gridToLightUp == 3) {
                coordinates[0] = (float)0;
                coordinates[2] = (width / (float)numColumns);
            }
            else {
                coordinates[0] = (width / (float)numColumns);
                coordinates[2] = (width * 2 / (float)numColumns);
            }
        }

        //canvas.drawRect(left, top, right, bottom, gridColor);
        return coordinates;

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

    public int getLevel()
    {
        return level;
    }

    public void setWinToFalse()
    {
        win = false;
    }

}
