package comethanl33.github.repetition;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GameModeManager extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioGroup radioGroup2;
    private RadioButton radioButton2;
    private Intent intent;

    private String str = "";
    private int selectedId;
    private int selectedId2;
    private int num;
    private String paint;
    private String paintStr = "";
    SharedPreferences sharedPreferences;

    RadioButton b1, b2, b3, b4, b5, b6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode_manager);

        sharedPreferences  = getSharedPreferences("Repetition", Context.MODE_PRIVATE);
        b1 = findViewById(R.id.radioButton1);
        b2 = findViewById(R.id.radioButton2);
        b3 = findViewById(R.id.radioButton3);


        num = sharedPreferences.getInt("rowsAndCols", -1);
        if (num == 4)
        {
            b3.setChecked(true);
        }
        else if (num == 3)
        {
            b2.setChecked(true);
        }
        else
        {
            b1.setChecked(true);
        }


        b4 = findViewById(R.id.radioButton4);
        b5 = findViewById(R.id.radioButton5);
        b6 = findViewById(R.id.radioButton6);
        paint = sharedPreferences.getString("paint", "Blue");
        if (paint.equals("Pink"))
        {
            b6.setChecked(true);
        }
        else if (paint.equals("Green"))
        {
            b5.setChecked(true);
        }
        else
        {
            b4.setChecked(true);
        }

        addListenerOnButton();
    }

    public void returnToHomeScreen(View view)
    {
        intent = new Intent(this, HomeScreen.class);

        SharedPreferences.Editor e = sharedPreferences.edit();

        if (!str.equals(""))
            e.putString("selectedGameMode", str);
        else
        {
            if (b1.isChecked())
                e.putString("selectedGameMode", "2 x 2");
            else if (b2.isChecked())
                e.putString("selectedGameMode", "3 x 3");
            else
                e.putString("selectedGameMode", "4 x 4");
        }

        if(!paintStr.equals(""))
            e.putString("selectedPaint", paintStr);
        else
        {
            if (b4.isChecked())
                e.putString("selectedPaint", "Blue");
            else if (b5.isChecked())
                e.putString("selectedPaint", "Green");
            else
                e.putString("selectedPaint", "Pink");
        }

        e.apply();



        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();  // optional depending on your needs
        intent = new Intent(this, HomeScreen.class);

        SharedPreferences.Editor e = sharedPreferences.edit();

        if (!str.equals(""))
            e.putString("selectedGameMode", str);
        else
        {
            if (b1.isChecked())
                e.putString("selectedGameMode", "2 x 2");
            else if (b2.isChecked())
                e.putString("selectedGameMode", "3 x 3");
            else
                e.putString("selectedGameMode", "4 x 4");
        }


        if(!paintStr.equals(""))
            e.putString("selectedPaint", paintStr);
        else
        {
            if (b4.isChecked())
                e.putString("selectedPaint", "Blue");
            else if (b5.isChecked())
                e.putString("selectedPaint", "Green");
            else
                e.putString("selectedPaint", "Pink");
        }

        e.apply();
        startActivity(intent);
        finish();
    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);


        radioGroup.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                str = (String) radioButton.getText();

                //Toast.makeText(GameModeManager.this,
                        //radioButton.getText(), Toast.LENGTH_SHORT).show();

            }

        });

        radioGroup2.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                selectedId2 = radioGroup2.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton2 = (RadioButton) findViewById(selectedId2);

                paintStr = (String) radioButton2.getText();

            }

        });

}}
