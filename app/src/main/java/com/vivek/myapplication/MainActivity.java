package com.vivek.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    public static int MSG = 0;
    public static int numberOfPlayer = 0;
    public  static  String nameOfPlayer1 = "player1";
    public  static String nameOfPlayer2 = "player2";
    public  static String nameOfPlayer3 = "player3";
    public  static String nameOfPlayer4 = "player4";
    public static int singlePlay=0;
    public static int level;

    Button enterPlayer;
    Button play;
    Button b3;
    Button b4;
    Button b5;
    Button player2;
    Button player3;
    Button player4;
    Button singlePlayer;
    Button multiPlayer;
    Button begin;
    Button expert;
    EditText Name1;
    EditText Name2;
    EditText Name3;
    EditText Name4;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    Drawable back;
    MediaPlayer mp;
    int selectPlayer = 0;
    int selectGrid = 0;
    int t = 0;



    public static int getMSG() {
        return MSG;
    }

    public static int getNumberOfPlayer() {
        return numberOfPlayer;
    }
    public static String getPlayer1(){
        return  nameOfPlayer1;
    }
    public static String getPlayer2(){
        return  nameOfPlayer2;
    }
    public static String getPlayer3(){
        return  nameOfPlayer3;
    }
    public static String getPlayer4(){
        return  nameOfPlayer4;

    }
    public  static int getIfPlayerSingle(){
        return  singlePlay;
    }
    public static int getLevel(){
        return level;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        back = getResources().getDrawable(R.drawable.dotback);
        back.setAlpha(50);
        mp = MediaPlayer.create(this,R.raw.onutton);
        singlePlayer =findViewById(R.id.singlePlayer);
        multiPlayer =findViewById(R.id.multiPlayer);
        play = findViewById(R.id.play);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        begin = findViewById(R.id.beginner);
        expert = findViewById(R.id.expert);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        player4 = findViewById(R.id.player4);
        Name1 = findViewById(R.id.Nameplayer1);
        Name2 = findViewById(R.id.Nameplayer2);
        Name3 = findViewById(R.id.Nameplayer3);
        Name4 = findViewById(R.id.Nameplayer4);
        enterPlayer = findViewById(R.id.enterPlayer);
        enterPlayer.setVisibility(View.INVISIBLE);
        text1=findViewById(R.id.textView1);
        text2 =findViewById(R.id.text2);
        text3=findViewById(R.id.text3);
        text4=findViewById(R.id.text4);
        text1.setVisibility(View.INVISIBLE);
        text2.setVisibility(View.INVISIBLE);
        text3.setVisibility(View.INVISIBLE);
        text4.setVisibility(View.INVISIBLE);
        player2.setEnabled(false);
        player3.setEnabled(false);
        player4.setEnabled(false);
        player2.setVisibility(View.INVISIBLE);
        player3.setVisibility(View.INVISIBLE);
        player4.setVisibility(View.INVISIBLE);
        begin.setEnabled(false);
        expert.setEnabled(false);
        begin.setVisibility(View.INVISIBLE);
        expert.setVisibility(View.INVISIBLE);
        Name1.setVisibility(View.INVISIBLE);
        Name2.setVisibility(View.INVISIBLE);
        Name3.setVisibility(View.INVISIBLE);
        Name4.setVisibility(View.INVISIBLE);
        Name1.setEnabled(false);
        Name2.setEnabled(false);
        Name3.setEnabled(false);
        Name4.setEnabled(false);
        play.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);
        b5.setEnabled(false);
        b3.setVisibility(View.INVISIBLE);
        b4.setVisibility(View.INVISIBLE);
        b5.setVisibility(View.INVISIBLE);
        play.setVisibility(View.INVISIBLE);

        final Intent intent = new Intent(this, gamePage.class);
        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setVisibility(View.VISIBLE);
                text1.setText("Please Select Level");
                singlePlay=1;
                singlePlayer.setEnabled(false);
                multiPlayer.setEnabled(false);
                begin.setEnabled(true);
                expert.setEnabled(true);
                begin.setVisibility(View.VISIBLE);
                expert.setVisibility(View.VISIBLE);
                mp.start();

            }
        });
        multiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setVisibility(View.VISIBLE);
                player2.setEnabled(true);
                player3.setEnabled(true);
                player4.setEnabled(true);
                player2.setVisibility(View.VISIBLE);
                player3.setVisibility(View.VISIBLE);
                player4.setVisibility(View.VISIBLE);
                singlePlayer.setEnabled(false);
                multiPlayer.setEnabled(false);
                mp.start();
            }
        });

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level = 1;
                expert.setEnabled(false);
                Name1.setVisibility(View.VISIBLE);
                Name1.setEnabled(true);
                enterPlayer.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                mp.start();
                YoYo.with(Techniques.Pulse)
                        .duration(700)
                        .playOn(begin);

            }
        });

        expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level=2;
                begin.setEnabled(false);
                Name1.setVisibility(View.VISIBLE);
                Name1.setEnabled(true);
                enterPlayer.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                mp.start();
                YoYo.with(Techniques.Pulse)
                        .duration(700)
                        .playOn(expert);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
                mp.start();
                YoYo.with(Techniques.BounceInRight)
                        .duration(700)
                        .playOn(play);
                finish();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MSG = 3;
                b4.setEnabled(false);
                b5.setEnabled(false);
                selectGrid = 1;
                play.setEnabled(true);
                mp.start();
                play.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .duration(700)
                        .playOn(b3);

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MSG = 4;
                b3.setEnabled(false);
                b5.setEnabled(false);
                selectGrid = 1;
                play.setEnabled(true);
                mp.start();
                play.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .duration(700)
                        .playOn(b4);

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MSG = 5;
                b3.setEnabled(false);
                b4.setEnabled(false);
                selectGrid = 1;
                play.setEnabled(true);
                mp.start();
                play.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .duration(700)
                        .playOn(b4);

            }
        });
        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfPlayer = 2;
                player2.setEnabled(false);
                player3.setEnabled(false);
                player4.setEnabled(false);
                selectPlayer = 1;
                Name1.setVisibility(View.VISIBLE);
                Name1.setEnabled(true);
                mp.start();
                enterPlayer.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Pulse)
                        .duration(700)
                        .playOn(player2);


            }
        });
        player3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfPlayer = 3;
                player3.setEnabled(false);
                player2.setEnabled(false);
                player4.setEnabled(false);
                selectPlayer = 1;
                Name1.setVisibility(View.VISIBLE);
                Name1.setEnabled(true);
                mp.start();
                enterPlayer.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Pulse)
                        .duration(700)
                        .playOn(player3);

            }
        });
        player4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfPlayer = 4;
                player3.setEnabled(false);
                player2.setEnabled(false);
                player4.setEnabled(false);
                selectPlayer = 1;
                Name1.setVisibility(View.VISIBLE);
                Name1.setEnabled(true);
                mp.start();
                enterPlayer.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Pulse)
                        .duration(700)
                        .playOn(player4);

            }
        });

        Name1.addTextChangedListener(textWatcher1);
        if (Name1.getText().toString().isEmpty()) {
            enterPlayer.setEnabled(false);
        } else {
            enterPlayer.setEnabled(true);
        }
        Name2.addTextChangedListener(textWatcher2);
        if (Name2.getText().toString().isEmpty()) {
            enterPlayer.setEnabled(false);
        } else {
            enterPlayer.setEnabled(true);
        }
        Name3.addTextChangedListener(textWatcher3);
        if (Name3.getText().toString().isEmpty()) {
            enterPlayer.setEnabled(false);
        } else {
            enterPlayer.setEnabled(true);
        }
        Name4.addTextChangedListener(textWatcher4);
        if (Name4.getText().toString().isEmpty()) {
            enterPlayer.setEnabled(false);
        } else {
            enterPlayer.setEnabled(true);
        }

        enterPlayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mp.start();
                if(singlePlay==1){
                    nameOfPlayer1 = Name1.getText().toString();
                    enterPlayer.setEnabled(false);
                    b3.setEnabled(true);
                    b4.setEnabled(true);
                    b5.setEnabled(true);
                    Name1.setEnabled(false);
                    Name1.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.VISIBLE);
                    b4.setVisibility(View.VISIBLE);
                    b5.setVisibility(View.VISIBLE);
                    text3.setVisibility(View.VISIBLE);
                    text4.setVisibility(View.VISIBLE);
                }
                if (t < numberOfPlayer) {
                    t++;
                    if (Name1.isEnabled() == true) {
                        nameOfPlayer1 = Name1.getText().toString();
                        Name1.setEnabled(false);
                        Name1.setVisibility(View.INVISIBLE);
                        if (t == numberOfPlayer) {
                            enterPlayer.setEnabled(false);
                            b3.setEnabled(true);
                            b4.setEnabled(true);
                            b5.setEnabled(true);
                            b3.setVisibility(View.VISIBLE);
                            b4.setVisibility(View.VISIBLE);
                            b5.setVisibility(View.VISIBLE);
                            text3.setVisibility(View.VISIBLE);
                            text4.setVisibility(View.VISIBLE);
                        }
                        else {
                            Name2.setEnabled(true);
                            Name2.setVisibility(View.VISIBLE);
                        }
                    } else if (Name2.isEnabled() == true) {

                        nameOfPlayer2 = Name2.getText().toString();
                        Name2.setEnabled(false);

                        Name2.setVisibility(View.INVISIBLE);

                        if (t == numberOfPlayer) {
                            enterPlayer.setEnabled(false);
                            b3.setEnabled(true);
                            b4.setEnabled(true);
                            b5.setEnabled(true);
                            b3.setVisibility(View.VISIBLE);
                            b4.setVisibility(View.VISIBLE);
                            b5.setVisibility(View.VISIBLE);
                            text3.setVisibility(View.VISIBLE);
                            text4.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            Name3.setEnabled(true);
                            Name3.setVisibility(View.VISIBLE);
                        }

                    } else if (Name3.isEnabled() == true) {

                        nameOfPlayer3 = Name3.getText().toString();
                        Name3.setEnabled(false);
                        Name3.setVisibility(View.INVISIBLE);
                        if (t == numberOfPlayer) {
                            enterPlayer.setEnabled(false);
                            b3.setEnabled(true);
                            b4.setEnabled(true);
                            b5.setEnabled(true);
                            b3.setVisibility(View.VISIBLE);
                            b4.setVisibility(View.VISIBLE);
                            b5.setVisibility(View.VISIBLE);
                            text3.setVisibility(View.VISIBLE);
                            text4.setVisibility(View.VISIBLE);
                        }
                        else {
                            Name4.setEnabled(true);
                            Name4.setVisibility(View.VISIBLE);
                        }
                    } else if (Name4.isEnabled() == true) {

                        nameOfPlayer4 = Name4.getText().toString();
                        Name4.setEnabled(false);
                        Name4.setVisibility(View.INVISIBLE);
                        if (t == numberOfPlayer) {
                            enterPlayer.setEnabled(false);
                            b3.setEnabled(true);
                            b4.setEnabled(true);
                            b5.setEnabled(true);
                            b3.setVisibility(View.VISIBLE);
                            b4.setVisibility(View.VISIBLE);
                            b5.setVisibility(View.VISIBLE);
                            text3.setVisibility(View.VISIBLE);
                            text4.setVisibility(View.VISIBLE);
                        }
                    }

                } else {
                    enterPlayer.setEnabled(false);
                }
                enterPlayer.setEnabled(false);
            }
        });


    }

    private TextWatcher textWatcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text1 = Name1.getText().toString();
            enterPlayer.setEnabled(!text1.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher textWatcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String text2 = Name2.getText().toString();
            enterPlayer.setEnabled(!text2.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher textWatcher3 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String text3 = Name3.getText().toString();
            enterPlayer.setEnabled(!text3.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher textWatcher4 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text4 = Name4.getText().toString();
            enterPlayer.setEnabled(!text4.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
