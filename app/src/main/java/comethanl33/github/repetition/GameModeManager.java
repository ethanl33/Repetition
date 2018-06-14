package comethanl33.github.repetition;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private Button confirmation;
    private Intent intent;

    private String str = "";
    private int selectedId;
    private int num;
    SharedPreferences sharedPreferences;

    RadioButton b1, b2, b3;


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
        e.apply();



        startActivity(intent);
    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        confirmation = (Button) findViewById(R.id.button);


        confirmation.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                str = (String) radioButton.getText();

                Toast.makeText(GameModeManager.this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show();

            }

        });
}}
