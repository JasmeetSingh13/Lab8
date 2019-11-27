package ca.davidpellegrini.scorekeepersolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//needed to create variables to collect information from later
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
//needed to check for events
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        // we can't add listeners without implementing them first
        // once we do, we have to include the required methods (onClick and onCheckedChanged)
        implements OnClickListener, OnCheckedChangeListener {

    // here we store the global variables (and Views) we'll need access to
    private Button decrease_TeamA, decrease_TeamB, increase_TeamA, increase_TeamB;
    private TextView teamA_score_textView, teamB_score_textView;
    private RadioGroup changeRadioGroup;
    private RadioButton radio_score_1, radio_score_2, radio_score_3;
    private int changeValues, scoreA, scoreB;
    private SharedPreferences prefs;
    private boolean rememberScore = true;
    private int incrementValue = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        //set up our variables for use later on
        //  Buttons
        decrease_TeamA = (Button) findViewById(R.id.decrease_TeamA);
        decrease_TeamB = (Button) findViewById(R.id.decrease_TeamB);
        increase_TeamA = (Button) findViewById(R.id.increase_TeamA);
        increase_TeamB = (Button) findViewById(R.id.increase_TeamB);
        //  TextViews
        teamA_score_textView = (TextView) findViewById(R.id.teamA_score_textview);
        teamB_score_textView = (TextView) findViewById(R.id.teamB_score_textview);
        // radio buttons and group
        radio_score_1 = (RadioButton) findViewById(R.id.radio_score_1);
        radio_score_2 = (RadioButton) findViewById(R.id.radio_score_2);
        radio_score_3 = (RadioButton) findViewById(R.id.radio_score_3);
        changeRadioGroup = (RadioGroup) findViewById(R.id.changeRadioGroup);

        //Set listeners so the buttons know what's happening
        decrease_TeamA.setOnClickListener(this);
        decrease_TeamB.setOnClickListener(this);
        increase_TeamA.setOnClickListener(this);
        increase_TeamB.setOnClickListener(this);
        //We only need a listener for the radio group, not each button
        changeRadioGroup.setOnCheckedChangeListener(this);

        /*
        Let's write fun Java code!

        Here we're just setting variables for us to use later
         */
        changeValues = 1;


        //Since our layout set the text to start at 10, we'll set it here to be the scores
        //set earlier
        teamA_score_textView.setText(Integer.toString(scoreA)); //Integer.toString will take our int
        // and make it a String for the
        // TextView
        teamB_score_textView.setText(Integer.toString(scoreB));

        //We didn't set our RadioGroup to have a set value
        radio_score_1.setChecked(true);
        //Instead of manually setting the score increment each time, we can get the String values
        //  The text returned from getText is a CharSequence so we need to turn it into a String
        //  and then an int
        changeValues = Integer.parseInt(radio_score_1.getText().toString());
    }

    @Override
    public void onClick(View view) {
        /*
        instead of calling methods to do this work for us, we can do the work in the onClick method

        With the score variables we set earlier, we can do easy math (+ and -), and then store
        the value in the TextViews
         */
        switch (view.getId()) {
            case R.id.decrease_TeamA:
                scoreA -= changeValues;
                teamA_score_textView.setText(Integer.toString(scoreA));
                break;
            case R.id.decrease_TeamB:
                scoreB -= changeValues;
                teamB_score_textView.setText(Integer.toString(scoreB));
                break;
            case R.id.increase_TeamA:
                scoreA += changeValues;
                teamA_score_textView.setText(Integer.toString(scoreA));
                break;
            case R.id.increase_TeamB:
                scoreB += changeValues;
                teamB_score_textView.setText(Integer.toString(scoreB));
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
        /*
        Again we don't need extra methods for this, we can do each part in the onCheckedChanged
         */
        switch (checkedID) {
            case R.id.radio_score_1:

                incrementValue = 1;
                // all we have to do here is get the text from the radio button (getText) and change
                //  it to a String, then int. As an int we can set our changeValues variable from
                //  earlier
             //   changeValues = Integer.parseInt(radio_score_1.getText().toString());
                break;
            case R.id.radio_score_2:
                incrementValue = 2;
               // changeValues = Integer.parseInt(radio_score_2.getText().toString());
                break;
            case R.id.radio_score_3:
                incrementValue = 3;
               // changeValues = Integer.parseInt(radio_score_3.getText().toString());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.menu, menu
        );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(
                        getApplicationContext(), SettingsActivity.class
                ));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        if (prefs.contains("scoreA")) {
            teamA_score_textView.setText(Integer.toString(prefs.getInt("scoreA", 0)));
            scoreA = prefs.getInt("scoreA", 0);
            Toast.makeText(this, scoreA + "", Toast.LENGTH_SHORT).show();
        }
        if (prefs.contains(("scoreB"))) {
            teamB_score_textView.setText(Integer.toString(prefs.getInt("scoreB",0)));
            scoreB = prefs.getInt("scoreB", 0);
            Toast.makeText(this, scoreB + "",Toast.LENGTH_SHORT).show();
        }
        if(prefs.contains("radioincrement")){
            RadioButton radioButton;
            switch (prefs.getInt("radioincrement",1)){
                case 1 :
                    radioButton = findViewById(R.id.radio_score_1);
                    radioButton.setChecked(true);
                    break;

                case 2 :
                    radioButton = findViewById(R.id.radio_score_2);
                    radioButton.setChecked(true);
                    break;
                case 3 :

                    radioButton = findViewById(R.id.radio_score_3);
                    radioButton.setChecked(true);
                    break;
            }
        }

    }
 

    @Override
    protected void onPause() {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("scoreA", scoreA);
        editor.putInt("scoreB", scoreB);
        editor.putInt("radioincrement", incrementValue);

        editor.apply();

        super.onPause();
    }
}