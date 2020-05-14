package com.vivek.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

import java.util.logging.LogRecord;

import static com.vivek.myapplication.MainActivity.MSG;
import static com.vivek.myapplication.MainActivity.numberOfPlayer;


public class MyCanvas extends View {
   private Handler mhandler = new Handler();
    Paint paint;
    Paint linePaint;
    Paint mDrawPaint;
    Paint outPaint;
    Paint outCircle;
    Paint sqrPaint;
    Paint undoPaint;
    Paint borderUndoPaint;
    Paint textUndo;
    int num = MainActivity.getMSG();
    int disX=0;
    int disY=0;
    int dist;
    int dotX;
    int dotY;
    int startX;
    int startY;
    int stopY;
    int stopX;
    int radius=20;
    int clr;
    int touch1=0;
    int touch2=0;
    int typeOfLine;
    int b1;
    int b2;
    int compb1;
    int compb2;
    int w;
    int h;
    int a;
    int b;
    int c;
    int d;
    int oneUndo =0 ;
    int scoreOfPlayer1=0;
    int scoreOfPlayer2=0;
    int scoreOfPlayer3=0;
    int scoreOfPlayer4=0;
    int singlePlayer=MainActivity.getIfPlayerSingle();
    int compStartX ;
    int compStartY ;
    int compStopX;
    int compStopY;
    int repeat=0;
    int level = MainActivity.getLevel();
    int sqrmade =0;
    int oneOrZero=0;
    int screenActive=1;
    int boxNumber ;
    int side ;
    int winner;
    int check=1;
    int delayRun;
    MediaPlayer mpl = MediaPlayer.create(getContext(),R.raw.opppoint);
    MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.lose);
    MediaPlayer winning = MediaPlayer.create(getContext(),R.raw.winn);
    int NoOfPlayer=MainActivity.getNumberOfPlayer();
    String nameOfPlayer1 = MainActivity.getPlayer1();
    String nameOfPlayer2 = MainActivity.getPlayer2();
    String nameOfPlayer3 = MainActivity.getPlayer3();
    String nameOfPlayer4 = MainActivity.getPlayer4();
    String text = nameOfPlayer1+"'s Turn";
    Bitmap mDrawBitmap;
    Canvas mBitmapCanvas;
    Handler handler;
    Runnable mRunnable;
    int[][] squares = new int[num*num][4];
    LinearLayout layout;
    CountDownTimer timer;

    ArrayList<Integer> linesLeft2 = new ArrayList<>();    // ArrayLists which stores lines left in box
    ArrayList<Integer> linesLeft0 = new ArrayList<>();
    ArrayList<Integer> linesLeft1 = new ArrayList<>();
    ArrayList<Integer> linesLeft3 = new ArrayList<>();


    public TextView textView;
    public int getRandomNum(int num){                  //functions that returns random number
        Random random = new Random();
        return random.nextInt(num);
    }





    public MyCanvas(Context context) {
        super(context);
        if(singlePlayer==1) {                        //if it is played in single player mode
            nameOfPlayer2 = "computer";
            NoOfPlayer=2;
        }



        layout = new LinearLayout(context);
        textView = new TextView(context);
        textView.setText(text);
        textView.setTextColor(0XFF000000);
        textView.setTextSize(30);
        layout.addView(textView);
        paint =new Paint();
        paint.setAntiAlias(true);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor((Color.BLUE));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(20f);
        outPaint = new Paint();
        outPaint.setAntiAlias(true);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeWidth(5);
        outCircle = new Paint();
        outCircle.setAntiAlias(true);
        outCircle.setColor(Color.WHITE);
        outCircle.setStyle(Paint.Style.STROKE);
        outCircle.setStrokeWidth(5);
        sqrPaint = new Paint();
        sqrPaint.setAntiAlias(true);
        sqrPaint.setStyle(Paint.Style.FILL);
        sqrPaint.setColor(Color.LTGRAY);
        undoPaint = new Paint();
        undoPaint.setAntiAlias(true);
        undoPaint.setColor(Color.YELLOW);
        borderUndoPaint = new Paint();
        borderUndoPaint.setColor(Color.BLUE);
        borderUndoPaint.setStyle(Paint.Style.STROKE);
        borderUndoPaint.setStrokeWidth(4);
        borderUndoPaint.setAntiAlias(true);
        textUndo = new Paint();
        textUndo.setAntiAlias(true);
        textUndo.setColor(Color.BLACK);
        textUndo.setTextSize(50);

        mDrawPaint = new Paint();
        for(int p=0;p<num*num;p++){         //2D array which stores information of line is drawn or not
            for(int q=0;q<4;q++){
                squares[p][q]=0;
            }
        }
        Log.d("msg",Integer.toString(num));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawBitmap == null) {
            mDrawBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            mBitmapCanvas = new Canvas(mDrawBitmap);
        }

        w = mBitmapCanvas.getWidth();
        h = mBitmapCanvas.getHeight();
        textView.setVisibility(View.VISIBLE);

        float length = textView.getPaint().measureText(textView.getText().toString());
        textView.setX(w/2-length/2);
        textView.setY(3*h/4);
        layout.measure(canvas.getWidth(), canvas.getHeight());
        layout.layout(0, 0, canvas.getWidth(), canvas.getHeight());
// To place the text view somewhere specific:
//canvas.translate(0, 0);
        layout.draw(canvas);

        if(singlePlayer==0) {                                        //undo button in multiplayer mode
            mBitmapCanvas.drawRect(w - 300, 350, w - 100, 450, undoPaint);
            mBitmapCanvas.drawRect(w - 300, 350, w - 100, 450, borderUndoPaint);
            mBitmapCanvas.drawText("UNDO", w - 300 + 35, 350 + 65, textUndo);
        }

        paint.setColor(Color.BLACK);
        dist = 150;
        dotX = (mBitmapCanvas.getWidth() - dist * num) / 2;
        dotY = (mBitmapCanvas.getHeight() - dist * num) / 2;
        int i, j;

        j = (mBitmapCanvas.getHeight() - dist * num) / 2;


        for (int p = 0; p < num + 1; p++) {                         //Drawing dots
            i = (mBitmapCanvas.getWidth() - dist * num) / 2;
            for (int q = 0; q < num + 1; q++) {
                if(touch1==1) {
                    if (disX - i < 40 && disX - i > -40 && disY - j < 40 && disY - j > -40) {
                        startX = i;
                        startY = j;
                        mBitmapCanvas.drawCircle(i,j,25,outPaint);
                    }

                }
                mBitmapCanvas.drawCircle(i, j, radius, paint);
                i = i + dist;
            }
            j = j + dist;
        }

        if(touch2==1&&stopY!=0&&stopX!=0){      // if dot is selected
            repeat=1;
            a =startX;
            b =startY;
            c =stopX;
            d =stopY;
            winner=0;
            for(int p=0;p<num*num;p++){
                for(int q=0;q<4;q++){
                    if(squares[p][q]==0){
                        winner=1;
                    }
                }
            }
                                                      //execution based on number players
            if(NoOfPlayer==2) {
                if (linePaint.getColor() == Color.BLUE) {
                    linePaint.setColor(Color.RED);
                    text = nameOfPlayer2 + "'s Turn";
                    textView.setText(text);
                } else {
                    linePaint.setColor(Color.BLUE);
                    text = nameOfPlayer1 + "'s Turn";
                    textView.setText(text);
                }
            }
            else if(NoOfPlayer==3){
                if (linePaint.getColor() == Color.BLUE) {
                    linePaint.setColor(Color.RED);
                    text = nameOfPlayer2 + "'s Turn";
                    textView.setText(text);
                }
                else if(linePaint.getColor()==Color.RED){
                    linePaint.setColor(Color.GREEN);
                    text = nameOfPlayer3 + "'s Turn";
                    textView.setText(text);
                }
                else{
                    linePaint.setColor(Color.BLUE);
                    text = nameOfPlayer1 + "'s Turn";
                    textView.setText(text);
                }
            }
            else if(NoOfPlayer==4){
                if (linePaint.getColor() == Color.BLUE) {
                    linePaint.setColor(Color.RED);
                    text = nameOfPlayer2 + "'s Turn";
                    textView.setText(text);
                }
                else if(linePaint.getColor()==Color.RED){
                    linePaint.setColor(Color.GREEN);
                    text = nameOfPlayer3 + "'s Turn";
                    textView.setText(text);
                }
                else if(linePaint.getColor()==Color.GREEN){
                    linePaint.setColor(Color.YELLOW);
                    text = nameOfPlayer4 + "'s Turn";
                    textView.setText(text);
                }
                else {
                    linePaint.setColor(Color.BLUE);
                    text = nameOfPlayer1 + "'s Turn";
                    textView.setText(text);
                }
            }
            mBitmapCanvas.drawLine(a,b,c,d,linePaint);     //drawing line

            mBitmapCanvas.drawCircle(startX,startY,25,outCircle);    // white circle over highlited circle

            if(NoOfPlayer==2) {                               //selecting paint of a box
                if (linePaint.getColor() == Color.BLUE) {
                    sqrPaint.setColor(0XFFA5A7FF);

                } else {
                    sqrPaint.setColor(0xFFFF7980);
                }
            }
            else if(NoOfPlayer==3){
                if (linePaint.getColor() == Color.BLUE) {
                    sqrPaint.setColor(0XFFA5A7FF);
                }
                else if(linePaint.getColor()==Color.RED){
                    sqrPaint.setColor(0xFFFF7980);
                }
                else{
                    sqrPaint.setColor(0xFF31FF5A);
                }
            }
            else if(NoOfPlayer==4){
                if (linePaint.getColor() == Color.BLUE) {
                    sqrPaint.setColor(0XFFA5A7FF);
                }
                else if(linePaint.getColor()==Color.RED){
                    sqrPaint.setColor(0xFFFF7980);
                }
                else if(linePaint.getColor()==Color.GREEN){
                   sqrPaint.setColor(0xFF31FF5A);
                }
                else {
                    sqrPaint.setColor(0XFFFFF570);
                }
            }
            sqrmade=0;
            /*
            There is three types of lines
            1- lines on left side or top side of grid
            2- lines on right side or bottom side of grid
            3- rest others(contact with two boxes)
             */


            if(typeOfLine==1){         //if any box is completed
                if(squares[b1][0]==1&&squares[b1][1]==1&&squares[b1][2]==1&&squares[b1][3]==1){
                    sqrmade=1;
                    Log.d("sqr","square completed");
                    MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.positivepoint);
                    mp.start();
                    mBitmapCanvas.drawRect((+10f)+dotX+(b1%num)*dist,(+10f)+dotY+(b1/num)*dist,(-10f)+dotX+(b1%num)*dist+dist,(-10f)+dotY+(b1/num)*dist+dist,sqrPaint);
                    if(NoOfPlayer==2) {
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else {
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                    if(NoOfPlayer==3){
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else if(sqrPaint.getColor()==0xFF31FF5A){
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                        else{
                            scoreOfPlayer3++;
                            linePaint.setColor(Color.GREEN);
                            text = nameOfPlayer3 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                    if(NoOfPlayer==4){
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else if(sqrPaint.getColor()==0xFF31FF5A){
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                        else if(sqrPaint.getColor()==0xFFFFF570){
                            scoreOfPlayer3++;
                            linePaint.setColor(Color.GREEN);
                            text = nameOfPlayer3 + "'s Turn";
                            textView.setText(text);
                        }
                        else{
                            scoreOfPlayer4++;
                            linePaint.setColor(Color.YELLOW);
                            text = nameOfPlayer4 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                }
            }
            else if(typeOfLine==2){
                if(squares[b2][0]==1&&squares[b2][1]==1&&squares[b2][2]==1&&squares[b2][3]==1){
                    sqrmade=1;
                    Log.d("sqr","square completed");
                    MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.positivepoint);
                    mp.start();
                    mBitmapCanvas.drawRect((+10f)+dotX+(b2%num)*dist,(+10f)+dotY+(b2/num)*dist,(-10f)+dotX+(b2%num)*dist+dist,(-10f)+dotY+(b2/num)*dist+dist,sqrPaint);
                    if(NoOfPlayer==2) {
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else {
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                    if(NoOfPlayer==3){
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else if(sqrPaint.getColor()==0xFF31FF5A){
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                        else{
                            scoreOfPlayer3++;
                            linePaint.setColor(Color.GREEN);
                            text = nameOfPlayer3 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                    if(NoOfPlayer==4){
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else if(sqrPaint.getColor()==0xFF31FF5A){
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                        else if(sqrPaint.getColor()==0xFFFFF570){
                            scoreOfPlayer3++;
                            linePaint.setColor(Color.GREEN);
                            text = nameOfPlayer3 + "'s Turn";
                            textView.setText(text);
                        }
                        else{
                            scoreOfPlayer4++;
                            linePaint.setColor(Color.YELLOW);
                            text = nameOfPlayer4 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                }
            }
            else{
                if(squares[b1][0]==1&&squares[b1][1]==1&&squares[b1][2]==1&&squares[b1][3]==1){
                    sqrmade=1;
                    Log.d("sqr","square completed");
                    MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.positivepoint);
                    mp.start();
                    mBitmapCanvas.drawRect((+10f)+dotX+(b1%num)*dist,(+10f)+dotY+(b1/num)*dist,(-10f)+dotX+(b1%num)*dist+dist,(-10f)+dotY+(b1/num)*dist+dist,sqrPaint);
                    if(NoOfPlayer==2) {
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else {
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                    if(NoOfPlayer==3){
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else if(sqrPaint.getColor()==0xFF31FF5A){
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                        else{
                            scoreOfPlayer3++;
                            linePaint.setColor(Color.GREEN);
                            text = nameOfPlayer3 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                    if(NoOfPlayer==4){
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else if(sqrPaint.getColor()==0xFF31FF5A){
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                        else if(sqrPaint.getColor()==0xFFFFF570){
                            scoreOfPlayer3++;
                            linePaint.setColor(Color.GREEN);
                            text = nameOfPlayer3 + "'s Turn";
                            textView.setText(text);
                        }
                        else{
                            scoreOfPlayer4++;
                            linePaint.setColor(Color.YELLOW);
                            text = nameOfPlayer4 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                }
                if(squares[b2][0]==1&&squares[b2][1]==1&&squares[b2][2]==1&&squares[b2][3]==1){
                    sqrmade=1;
                    Log.d("sqr","square completed");
                    MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.positivepoint);
                    mp.start();
                    mBitmapCanvas.drawRect((+10f)+dotX+(b2%num)*dist,(+10f)+dotY+(b2/num)*dist,(-10f)+dotX+(b2%num)*dist+dist,(-10f)+dotY+(b2/num)*dist+dist,sqrPaint);
                    if(NoOfPlayer==2) {
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else {
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                    if(NoOfPlayer==3){
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else if(sqrPaint.getColor()==0xFF31FF5A){
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                        else{
                            scoreOfPlayer3++;
                            linePaint.setColor(Color.GREEN);
                            text = nameOfPlayer3 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                    if(NoOfPlayer==4){
                        if (sqrPaint.getColor() == 0xFFFF7980) {
                            scoreOfPlayer1++;
                            linePaint.setColor(Color.BLUE);
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        } else if(sqrPaint.getColor()==0xFF31FF5A){
                            scoreOfPlayer2++;
                            linePaint.setColor(Color.RED);
                            text = nameOfPlayer2 + "'s Turn";
                            textView.setText(text);
                        }
                        else if(sqrPaint.getColor()==0xFFFFF570){
                            scoreOfPlayer3++;
                            linePaint.setColor(Color.GREEN);
                            text = nameOfPlayer3 + "'s Turn";
                            textView.setText(text);
                        }
                        else{
                            scoreOfPlayer4++;
                            linePaint.setColor(Color.YELLOW);
                            text = nameOfPlayer4 + "'s Turn";
                            textView.setText(text);
                        }
                    }
                }
            }


            radius=20;
            touch2=0;
            touch1=0;
            startX=0;
            startY=0;
            stopX=0;
            stopY=0;
            if(singlePlayer==1&&winner!=0&&sqrmade==0){   //if it is single player mode
                text = nameOfPlayer2 + "'s Turn";
                textView.setText(text);
                screenActive=0;
                 mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        mpl.reset();
                        mp.reset();
                    linesLeft0.clear();
                    linesLeft1.clear();
                    linesLeft2.clear();
                    linesLeft3.clear();
                    repeat = 0;
                    linePaint.setColor(Color.BLUE);
                    sqrPaint.setColor(0xFFA5A7FF);

                    if(level==1) {
                        do {
                            boxNumber = getRandomNum(num * num);
                            side = getRandomNum(4);
                        }while (squares[boxNumber][side]==1);
                    }
                    else {
                        int flag;


                        for (int p = 0; p < num * num; p++) {
                            flag = 0;
                            for (int q = 0; q < 4; q++) {
                                if (squares[p][q] == 1) {
                                    flag++;
                                }
                            }
                            if (flag == 3) {
                                linesLeft3.add(p);
                            }
                        }
                        for (int p = 0; p < num * num; p++) {
                            flag = 0;
                            for (int q = 0; q < 4; q++) {
                                if (squares[p][q] == 1) {
                                    flag++;
                                }
                            }
                            if (flag == 2) {
                                linesLeft2.add(p);
                            }
                        }
                        for (int p = 0; p < num * num; p++) {
                            flag = 0;
                            for (int q = 0; q < 4; q++) {
                                if (squares[p][q] == 1) {
                                    flag++;
                                }
                            }
                            if (flag == 1) {

                                linesLeft1.add(p);
                            }
                        }
                        for (int p = 0; p < num * num; p++) {
                            flag = 0;
                            for (int q = 0; q < 4; q++) {
                                if (squares[p][q] == 1) {
                                    flag++;
                                }
                            }
                            if (flag == 0) {
                                linesLeft0.add(p);
                            }
                        }
                        oneOrZero=0;
                        if (linesLeft3.size() != 0) {

                            do {
                                boxNumber =linesLeft3.get(getRandomNum(linesLeft3.size()));
                                side = getRandomNum(4);
                            }while (squares[boxNumber][side] == 1);
                        } else if (linesLeft0.size() != 0) {

                            do {
                                boxNumber =linesLeft0.get(getRandomNum(linesLeft0.size()));
                                side = getRandomNum(4);
                            }while (squares[boxNumber][side] == 1);
                            oneOrZero=1;
                        } else if (linesLeft1.size() != 0) {

                            do {
                                boxNumber =linesLeft1.get(getRandomNum(linesLeft1.size()));
                                side = getRandomNum(4);
                            }while (squares[boxNumber][side] == 1);
                            oneOrZero=1;
                        } else {

                            do {
                                boxNumber =linesLeft2.get(getRandomNum(linesLeft2.size()));
                                side = getRandomNum(4);
                            }while (squares[boxNumber][side] == 1);
                        }

                    }



                    if (side == 0) {
                        compStartX = dotX + dist * (boxNumber % num);
                        compStartY = dotY + dist * (boxNumber / num);
                        compStopX = compStartX + dist;
                        compStopY = compStartY;
                    } else if (side == 1) {
                        compStartX = dotX + dist * (boxNumber % num);
                        compStartY = dotY + dist * (boxNumber / num);
                        compStopX = compStartX;
                        compStopY = compStartY + dist;
                    } else if (side == 2) {
                        compStartX = dotX + dist * (boxNumber % num) + dist;
                        compStartY = dotY + dist * (boxNumber / num);
                        compStopX = compStartX;
                        compStopY = compStartY + dist;
                    } else if (side == 3) {
                        compStartX = dotX + dist * (boxNumber % num);
                        compStartY = dotY + dist * (boxNumber / num) + dist;
                        compStopX = compStartX + dist;
                        compStopY = compStartY;
                    }
                    if ((compStartX == dotX && compStartX == compStopX) || (compStartY == dotY && compStartY == compStopY)) {
                        typeOfLine = 1;
                        squares[boxNumber][side] = 1;
                        compb1 = boxNumber;
                    } else if (compStartX == dotX + num * dist || compStartY == dotY + num * dist) {
                        typeOfLine = 2;
                        squares[boxNumber][side] = 1;
                        compb2 = boxNumber;
                    } else {
                        typeOfLine = 3;
                        squares[boxNumber][side] = 1;
                        compb1 = boxNumber;
                        if (side == 0) {
                            squares[boxNumber - num][3] = 1;
                            compb2 = boxNumber - num;
                        } else if (side == 1) {
                            squares[boxNumber - 1][2] = 1;
                            compb2 = boxNumber - 1;
                        } else if (side == 2) {
                            squares[boxNumber + 1][1] = 1;
                            compb2 = boxNumber + 1;
                        } else {
                            squares[boxNumber + num][0] = 1;
                            compb2 = boxNumber + num;
                        }
                    }


                            mBitmapCanvas.drawLine(compStartX, compStartY, compStopX, compStopY, linePaint);
                    mpl = MediaPlayer.create(getContext(),R.raw.opppoint);
                    mpl.start();

                            if (typeOfLine == 1) {
                                if (squares[compb1][0] == 1 && squares[compb1][1] == 1 && squares[compb1][2] == 1 && squares[compb1][3] == 1) {
                                    repeat = 1;
                                    Log.d("sqrtype1", "square completed");
                                    Log.d("singleby1", Integer.toString(compb1));
                                    mBitmapCanvas.drawRect((+10f) + dotX + (compb1 % num) * dist, (+10f) + dotY + (compb1 / num) * dist, (-10f) + dotX + (compb1 % num) * dist + dist, (-10f) + dotY + (compb1 / num) * dist + dist, sqrPaint);
                                    mp = MediaPlayer.create(getContext(),R.raw.lose);
                                    mp.start();
                                    scoreOfPlayer2++;
                                    linePaint.setColor(Color.RED);
                                }

                            } else if (typeOfLine == 2) {
                                if (squares[compb2][0] == 1 && squares[compb2][1] == 1 && squares[compb2][2] == 1 && squares[compb2][3] == 1) {
                                    mp = MediaPlayer.create(getContext(),R.raw.lose);
                                    mp.start();
                                    repeat = 1;
                                    Log.d("sqrtype2", "square completed");
                                    Log.d("singleby2", Integer.toString(compb2));
                                    mBitmapCanvas.drawRect((+10f) + dotX + (compb2 % num) * dist, (+10f) + dotY + (compb2 / num) * dist, (-10f) + dotX + (compb2 % num) * dist + dist, (-10f) + dotY + (compb2 / num) * dist + dist, sqrPaint);
                                    if (NoOfPlayer == 2) {
                                        scoreOfPlayer2++;
                                        linePaint.setColor(Color.RED);
                                    }

                                }


                            } else if (typeOfLine == 3) {
                                if (squares[compb1][0] == 1 && squares[compb1][1] == 1 && squares[compb1][2] == 1 && squares[compb1][3] == 1) {
                                    mp = MediaPlayer.create(getContext(),R.raw.lose);
                                    mp.start();
                                    repeat = 1;
                                    Log.d("sqrtype3", "square completed");
                                    Log.d("singleby1", Integer.toString(compb1));
                                    mBitmapCanvas.drawRect((+10f) + dotX + (compb1 % num) * dist, (+10f) + dotY + (compb1 / num) * dist, (-10f) + dotX + (compb1 % num) * dist + dist, (-10f) + dotY + (compb1 / num) * dist + dist, sqrPaint);
                                    if (NoOfPlayer == 2) {
                                        scoreOfPlayer2++;
                                        linePaint.setColor(Color.RED);
                                    }

                                }
                                if (squares[compb2][0] == 1 && squares[compb2][1] == 1 && squares[compb2][2] == 1 && squares[compb2][3] == 1) {
                                    mp = MediaPlayer.create(getContext(),R.raw.lose);
                                    mp.start();
                                    repeat = 1;
                                    Log.d("sqrtype3.0", "square completed");
                                    Log.d("singleby2", Integer.toString(compb2));
                                    mBitmapCanvas.drawRect((+10f) + dotX + (compb2 % num) * dist, (+10f) + dotY + (compb2 / num) * dist, (-10f) + dotX + (compb2 % num) * dist + dist, (-10f) + dotY + (compb2 / num) * dist + dist, sqrPaint);
                                    if (NoOfPlayer == 2) {
                                        scoreOfPlayer2++;
                                        linePaint.setColor(Color.RED);
                                    }

                                }
                            }
                            invalidate();

                            Log.d("singletypeof", Integer.toString(typeOfLine));

                            Log.d("singleb2", Integer.toString(compb2));
                            Log.d("singleside", Integer.toString(side));
                            Log.d("singleboxnumber", Integer.toString(boxNumber));

                            Log.d("repeat",Integer.toString(repeat));
                            int test=0;
                            for(int p=0;p<num*num;p++){
                                for(int q=0;q<4;q++){
                                    if(squares[p][q]==0){
                                        test=1;
                                    }

                                }
                            }

                            if(test==0){
                                repeat=0;
                                if(scoreOfPlayer1>scoreOfPlayer2) {
                                    text = nameOfPlayer1 + " WINS";
                                    textView.setText(text);
                                }
                                else{
                                    text = nameOfPlayer2 + " WINS";
                                    textView.setText(text);
                                }
                            }

                        if(repeat==1){
                            mhandler.postDelayed(mRunnable,1000);
                        }

                        if(repeat==0) {
                            screenActive = 1;
                            text = nameOfPlayer1 + "'s Turn";
                            textView.setText(text);
                        }
                        winner=0;
                        for(int p=0;p<num*num;p++){
                            for(int q=0;q<4;q++){
                                if(squares[p][q]==0){
                                    winner=1;
                                }
                            }
                        }
                        if(winner==0){
                            if(scoreOfPlayer1>scoreOfPlayer4&&scoreOfPlayer1>scoreOfPlayer3&&scoreOfPlayer1>scoreOfPlayer2){
                                text = nameOfPlayer1 + "  WINS";
                                textView.setText(text);
                                winning.start();
                            }
                            else if(scoreOfPlayer2>scoreOfPlayer1&&scoreOfPlayer2>scoreOfPlayer3&&scoreOfPlayer2>scoreOfPlayer4){
                                text = nameOfPlayer2 + "  WINS";
                                textView.setText(text);
                                winning.start();
                            }
                            else if(scoreOfPlayer3>scoreOfPlayer1&&scoreOfPlayer3>scoreOfPlayer2&&scoreOfPlayer3>scoreOfPlayer4){
                                text = nameOfPlayer3 + "  WINS";
                                textView.setText(text);
                                winning.start();
                            }
                            else{
                                text = nameOfPlayer4 + "  WINS";
                                textView.setText(text);
                                winning.start();
                            }
                        }
                        }

                    };

                    if(repeat==1){
                        mhandler.postDelayed(mRunnable,1000);

                    }


            }
            winner=0;
            for(int p=0;p<num*num;p++){
                for(int q=0;q<4;q++){
                    if(squares[p][q]==0){
                        winner=1;
                    }
                }
            }
            if(winner==0){
                if(scoreOfPlayer1>scoreOfPlayer4&&scoreOfPlayer1>scoreOfPlayer3&&scoreOfPlayer1>scoreOfPlayer2){
                    text = nameOfPlayer1 + "  WINS";
                    textView.setText(text);
                    winning.start();
                }
                else if(scoreOfPlayer2>scoreOfPlayer1&&scoreOfPlayer2>scoreOfPlayer3&&scoreOfPlayer2>scoreOfPlayer4){
                    text = nameOfPlayer2 + "  WINS";
                    textView.setText(text);
                    winning.start();
                }
                else if(scoreOfPlayer3>scoreOfPlayer1&&scoreOfPlayer3>scoreOfPlayer2&&scoreOfPlayer3>scoreOfPlayer4){
                    text = nameOfPlayer3 + "  WINS";
                    textView.setText(text);
                    winning.start();
                }
                else{
                    text = nameOfPlayer4 + "  WINS";
                    textView.setText(text);
                    winning.start();
                }
            }

            invalidate();

        }

//        Log.d("sqrcolor0",Integer.toString(squares[8][0]));
//        Log.d("sqrcolor1",Integer.toString(squares[8][1]));
//        Log.d("sqrcolor2",Integer.toString(squares[8][2]));
//        Log.d("sqrcolor3",Integer.toString(squares[8][3]));
//        Log.d("typeofline",Integer.toString(typeOfLine));
//        Log.d("box1",Integer.toString(b1));
//        Log.d("box2",Integer.toString(b2));
        Log.d("checkundoPLayer1",Integer.toString(scoreOfPlayer1));
        Log.d("checkundoPlayer2",Integer.toString(scoreOfPlayer2));
        Log.d("checkundoPlauer3",Integer.toString(scoreOfPlayer3));
        Log.d("checkundoPlauer4",Integer.toString(scoreOfPlayer4));

        canvas.drawBitmap(mDrawBitmap, 0, 0, mDrawPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d("xtouch1", Integer.toString(touch1));
        Log.d("xtouch2", Integer.toString(touch2));
        Log.d("xdotx", Integer.toString(dotX));
        Log.d("xdoty", Integer.toString(dotY));
        int dX = (int) event.getX();
        int dY = (int) event.getY();
        /*if(Math.abs(dX-startX)<40&&Math.abs(dY-startY)<40){
            Log.d("xnum","andar aa gaya bhai");
            touch1=1;
            return true;

        }*/
        if (dX > w - 300 && dX < w - 100 && dY > 350 && dY < 450&&singlePlayer==0&&screenActive==1) {
            Log.d("heyhey", "undopressed");
            Log.d("heyheyColleundopressed", Integer.toString(linePaint.getColor()));
            Log.d("noofplayer", Integer.toString(NoOfPlayer));
            if (oneUndo == 1) {
                oneUndo=0;
                clr = linePaint.getColor();
                if (typeOfLine == 1) {
                    if (squares[b1][0] == 1 && squares[b1][1] == 1 && squares[b1][2] == 1 && squares[b1][3] == 1) {
                        Log.d("sqr", "square completed");
                        sqrPaint.setColor(Color.WHITE);
                        mBitmapCanvas.drawRect((+10f) + dotX + (b1 % num) * dist, (+10f) + dotY + (b1 / num) * dist, (-10f) + dotX + (b1 % num) * dist + dist, (-10f) + dotY + (b1 / num) * dist + dist, sqrPaint);
                        if (NoOfPlayer == 2) {
                            if (clr == Color.RED) {
                                scoreOfPlayer2--;
                                clr = Color.BLUE;
                            } else {
                                scoreOfPlayer1--;
                                clr = Color.RED;
                            }
                        } else if (NoOfPlayer == 3) {
                            if (clr == Color.RED) {
                                scoreOfPlayer2--;
                                clr = Color.GREEN;
                            } else if (clr == Color.BLUE) {
                                scoreOfPlayer1--;
                                clr = Color.RED;
                            } else {
                                scoreOfPlayer3--;
                                clr = Color.BLUE;
                            }
                        } else {
                            if (clr == Color.GREEN) {
                                scoreOfPlayer3--;
                                clr = Color.YELLOW;
                            } else if (clr == Color.RED) {
                                scoreOfPlayer2--;
                                clr = Color.GREEN;
                            } else if (clr == Color.YELLOW) {
                                scoreOfPlayer4--;
                                clr = Color.BLUE;
                            } else {
                                scoreOfPlayer1--;
                                clr = Color.RED;
                            }
                        }
                    }
                } else if (typeOfLine == 2) {
                    if (squares[b2][0] == 1 && squares[b2][1] == 1 && squares[b2][2] == 1 && squares[b2][3] == 1) {
                        Log.d("sqr", "square completed");
                        sqrPaint.setColor(Color.WHITE);
                        mBitmapCanvas.drawRect((+10f) + dotX + (b2 % num) * dist, (+10f) + dotY + (b2 / num) * dist, (-10f) + dotX + (b2 % num) * dist + dist, (-10f) + dotY + (b2 / num) * dist + dist, sqrPaint);
                        if (NoOfPlayer == 2) {
                            if (clr == Color.RED) {
                                scoreOfPlayer2--;
                                clr = Color.BLUE;
                            } else {
                                scoreOfPlayer1--;
                                clr = Color.RED;
                            }
                        } else if (NoOfPlayer == 3) {
                            if (clr == Color.RED) {
                                scoreOfPlayer2--;
                                clr = Color.GREEN;
                            } else if (clr == Color.BLUE) {
                                scoreOfPlayer1--;
                                clr = Color.RED;
                            } else {
                                scoreOfPlayer3--;
                                clr = Color.BLUE;
                            }
                        } else {
                            if (clr == Color.GREEN) {
                                scoreOfPlayer3--;
                                clr = Color.YELLOW;
                            } else if (clr == Color.RED) {
                                scoreOfPlayer2--;
                                clr = Color.GREEN;
                            } else if (clr == Color.YELLOW) {
                                scoreOfPlayer4--;
                                clr = Color.BLUE;
                            } else {
                                scoreOfPlayer1--;
                                clr = Color.RED;
                            }
                        }
                    }
                } else {
                    int flag = 0;
                    if (squares[b1][0] == 1 && squares[b1][1] == 1 && squares[b1][2] == 1 && squares[b1][3] == 1) {
                        flag = 1;
                        Log.d("sqr", "square completed");
                        sqrPaint.setColor(Color.WHITE);
                        mBitmapCanvas.drawRect((+10f) + dotX + (b1 % num) * dist, (+10f) + dotY + (b1 / num) * dist, (-10f) + dotX + (b1 % num) * dist + dist, (-10f) + dotY + (b1 / num) * dist + dist, sqrPaint);
                        if (NoOfPlayer == 2) {
                            if (clr == Color.RED) {
                                scoreOfPlayer2--;
                                clr = Color.BLUE;
                            } else {
                                scoreOfPlayer1--;
                                clr = Color.RED;
                            }
                        } else if (NoOfPlayer == 3) {
                            if (clr == Color.RED) {
                                scoreOfPlayer2--;
                                clr = Color.GREEN;
                            } else if (clr == Color.BLUE) {
                                scoreOfPlayer1--;
                                clr = Color.RED;
                            } else {
                                scoreOfPlayer3--;
                                clr = Color.BLUE;
                            }
                        } else {
                            if (clr == Color.GREEN) {
                                scoreOfPlayer3--;
                                clr = Color.YELLOW;
                            } else if (clr == Color.RED) {
                                scoreOfPlayer2--;
                                clr = Color.GREEN;
                            } else if (clr == Color.YELLOW) {
                                scoreOfPlayer4--;
                                clr = Color.BLUE;
                            } else {
                                scoreOfPlayer1--;
                                clr = Color.RED;
                            }
                        }
                    }
                    if (squares[b2][0] == 1 && squares[b2][1] == 1 && squares[b2][2] == 1 && squares[b2][3] == 1) {
                        Log.d("sqr", "square completed");
                        sqrPaint.setColor(Color.WHITE);
                        mBitmapCanvas.drawRect((+10f) + dotX + (b2 % num) * dist, (+10f) + dotY + (b2 / num) * dist, (-10f) + dotX + (b2 % num) * dist + dist, (-10f) + dotY + (b2 / num) * dist + dist, sqrPaint);
                        if (flag == 0) {
                            if (NoOfPlayer == 2) {
                                if (clr == Color.RED) {
                                    scoreOfPlayer2--;
                                    clr = Color.BLUE;
                                } else {
                                    scoreOfPlayer1--;
                                    clr = Color.RED;
                                }
                            } else if (NoOfPlayer == 3) {
                                if (clr == Color.RED) {
                                    scoreOfPlayer2--;
                                    clr = Color.GREEN;
                                } else if (clr == Color.BLUE) {
                                    scoreOfPlayer1--;
                                    clr = Color.RED;
                                } else {
                                    scoreOfPlayer3--;
                                    clr = Color.BLUE;
                                }
                            } else {
                                if (clr == Color.GREEN) {
                                    scoreOfPlayer3--;
                                    clr = Color.YELLOW;
                                } else if (clr == Color.RED) {
                                    scoreOfPlayer2--;
                                    clr = Color.GREEN;
                                } else if (clr == Color.YELLOW) {
                                    scoreOfPlayer4--;
                                    clr = Color.BLUE;
                                } else {
                                    scoreOfPlayer1--;
                                    clr = Color.RED;
                                }
                            }
                        } else {
                            if (NoOfPlayer == 2) {
                                if (clr == Color.RED) {
                                    scoreOfPlayer1--;
                                } else {
                                    scoreOfPlayer2--;
                                }
                            } else if (NoOfPlayer == 3) {
                                if (clr == Color.RED) {
                                    scoreOfPlayer1--;
                                } else if (clr == Color.BLUE) {
                                    scoreOfPlayer3--;
                                } else {
                                    scoreOfPlayer2--;
                                }
                            } else {
                                if (clr == Color.GREEN) {
                                    scoreOfPlayer2--;
                                } else if (clr == Color.RED) {
                                    scoreOfPlayer1--;
                                } else if (clr == Color.YELLOW) {
                                    scoreOfPlayer3--;
                                } else {
                                    scoreOfPlayer4--;
                                }
                            }
                        }

                    }
                }


                if (b == d) {
                    if (typeOfLine == 1) {
                        squares[b1][0] = 0;
                    } else if (typeOfLine == 2) {
                        squares[b2][3] = 0;
                    } else {
                        squares[b1][0] = 0;
                        squares[b2][3] = 0;
                    }
                } else if (a == c) {
                    if (typeOfLine == 1) {
                        squares[b1][1] = 0;
                    } else if (typeOfLine == 2) {
                        squares[b2][2] = 0;
                    } else {
                        squares[b1][1] = 0;
                        squares[b2][2] = 0;
                    }
                }
                linePaint.setColor(Color.WHITE);
                Log.d("xa", Integer.toString(a));
                Log.d("xb", Integer.toString(b));
                Log.d("xc", Integer.toString(c));
                Log.d("xd", Integer.toString(d));
                mBitmapCanvas.drawLine(a, b, c, d, linePaint);
                if (NoOfPlayer == 2) {
                    if (clr == Color.RED) {
                        linePaint.setColor(Color.BLUE);
                        text = nameOfPlayer1 + "'s Turn";
                        textView.setText(text);
                    } else {
                        linePaint.setColor(Color.RED);
                        text = nameOfPlayer2 + "'s Turn";
                        textView.setText(text);
                    }
                } else if (NoOfPlayer == 3) {
                    if (clr == Color.RED) {
                        linePaint.setColor(Color.BLUE);
                        text = nameOfPlayer1 + "'s Turn";
                        textView.setText(text);
                    } else if (clr == Color.BLUE) {
                        linePaint.setColor(Color.GREEN);
                        text = nameOfPlayer3 + "'s Turn";
                        textView.setText(text);
                    } else {
                        linePaint.setColor(Color.RED);
                        text = nameOfPlayer2 + "'s Turn";
                        textView.setText(text);
                    }
                } else {
                    if (clr == Color.GREEN) {
                        linePaint.setColor(Color.RED);
                        text = nameOfPlayer2 + "'s Turn";
                        textView.setText(text);
                    } else if (clr == Color.RED) {
                        linePaint.setColor(Color.BLUE);
                        text = nameOfPlayer1 + "'s Turn";
                        textView.setText(text);
                    } else if (clr == Color.YELLOW) {
                        text = nameOfPlayer3 + "'s Turn";
                        textView.setText(text);
                        linePaint.setColor(Color.GREEN);
                    } else {
                        linePaint.setColor(Color.YELLOW);
                        text = nameOfPlayer4 + "'s Turn";
                        textView.setText(text);
                    }
                }






            }
            invalidate();
            return super.onTouchEvent(event);
        }


            if (touch1 == 1&&screenActive==1) {
                oneUndo =1;
                MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.puttt);
                mp.start();

                int sX = (int) event.getX();
                int sY = (int) event.getY();
                if (Math.abs(startX + dist - sX) < 40 && Math.abs(startY - sY) < 40 && sX > dotX - 40 && sX < dotX + num * dist + 40 && sY > dotY - 40 && sY < dotY + num * dist + 40) {
                    stopX = startX + dist;
                    stopY = startY;

                    if (startY == dotY) {
                        int box1 = (startX - dotX) / dist;
                        if (squares[box1][0] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box1][0] = 1;
                        typeOfLine = 1;
                        b1 = (startX - dotX) / dist;
                    } else if (startY == dotY + num * dist) {
                        int box2 = (startX - dotX) / dist + num * (num - 1);
                        if (squares[box2][3] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box2][3] = 1;
                        typeOfLine = 2;
                        b2 = (startX - dotX) / dist + num * (num - 1);
                    } else {
                        int box1 = (startX - dotX) / dist + num * (startY - dotY) / dist;
                        int box2 = (startX - dotX) / dist + num * (startY - dotY) / dist - num;
                        if (squares[box1][0] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box1][0] = 1;
                        squares[box2][3] = 1;
                        typeOfLine = 3;
                        b1 = (startX - dotX) / dist + num * (startY - dotY) / dist;
                        b2 = (startX - dotX) / dist + num * (startY - dotY) / dist - num;
                    }
                    touch2 = 1;
                    touch1 = 0;
                    invalidate();
                } else if (Math.abs(startX - dist - sX) < 40 && Math.abs(startY - sY) < 40 && sX > dotX - 40 && sX < dotX + num * dist + 40 && sY > dotY - 40 && sY < dotY + num * dist + 40) {

                    stopX = startX - dist;
                    stopY = startY;
                    if (startY == dotY) {
                        int box1 = (stopX - dotX) / dist;
                        if (squares[box1][0] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box1][0] = 1;
                        typeOfLine = 1;
                        b1 = (stopX - dotX) / dist;
                    } else if (startY == dotY + num * dist) {
                        int box2 = (stopX - dotX) / dist + num * (num - 1);
                        if (squares[box2][3] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box2][3] = 1;
                        typeOfLine = 2;
                        b2 = (stopX - dotX) / dist + num * (num - 1);
                    } else {
                        int box1 = (stopX - dotX) / dist + num * (stopY - dotY) / dist;
                        int box2 = (stopX - dotX) / dist + num * (stopY - dotY) / dist - num;
                        if (squares[box1][0] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box1][0] = 1;
                        squares[box2][3] = 1;
                        typeOfLine = 3;
                        b1 = (stopX - dotX) / dist + num * (stopY - dotY) / dist;
                        b2 = (stopX - dotX) / dist + num * (stopY - dotY) / dist - num;
                    }
                    touch2 = 1;
                    touch1 = 0;
                    invalidate();
                } else if (Math.abs(startY + dist - sY) < 40 && Math.abs(startX - sX) < 40 && sX > dotX - 40 && sX < dotX + num * dist + 40 && sY > dotY - 40 && sY < dotY + num * dist + 40) {
                    stopX = startX;
                    stopY = startY + dist;


                    if (startX == dotX) {
                        int box1 = (startY - dotY) / dist * num;
                        if (squares[box1][1] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box1][1] = 1;
                        typeOfLine = 1;
                        b1 = (startY - dotY) / dist * num;
                    } else if (startX == dotX + num * dist) {
                        int box2 = num - 1 + num * (startY - dotY) / dist;
                        if (squares[box2][2] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box2][2] = 1;
                        typeOfLine = 2;
                        b2 = num - 1 + num * (startY - dotY) / dist;
                    } else {
                        int box1 = (startX - dotX) / dist + num * (startY - dotY) / dist;
                        int box2 = (startX - dotX) / dist + num * (startY - dotY) / dist - 1;
                        if (squares[box1][1] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box1][1] = 1;
                        squares[box2][2] = 1;
                        typeOfLine = 3;
                        b1 = (startX - dotX) / dist + num * (startY - dotY) / dist;
                        b2 = (startX - dotX) / dist + num * (startY - dotY) / dist - 1;
                    }
                    touch2 = 1;
                    touch1 = 0;
                    invalidate();
                } else if (Math.abs(startY - dist - sY) < 40 && Math.abs(startX - sX) < 40 && sX > dotX - 40 && sX < dotX + num * dist + 40 && sY > dotY - 40 && sY < dotY + num * dist + 40) {
                    stopX = startX;
                    stopY = startY - dist;

                    if (startX == dotX) {
                        int box1 = (stopY - dotY) / dist * num;
                        if (squares[box1][1] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box1][1] = 1;
                        typeOfLine = 1;
                        b1 = (stopY - dotY) / dist * num;
                    } else if (startX == dotX + num * dist) {
                        int box2 = num - 1 + num * (stopY - dotY) / dist;
                        if (squares[box2][2] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box2][2] = 1;
                        typeOfLine = 2;
                        b2 = num - 1 + num * (stopY - dotY) / dist;
                    } else {
                        int box1 = (stopX - dotX) / dist + num * (stopY - dotY) / dist;
                        int box2 = (stopX - dotX) / dist + num * (stopY - dotY) / dist - 1;
                        if (squares[box1][1] == 1) {
                            mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                            touch1 = 0;
                            invalidate();
                            return super.onTouchEvent(event);
                        }
                        squares[box1][1] = 1;
                        squares[box2][2] = 1;
                        typeOfLine = 3;
                        b1 = (stopX - dotX) / dist + num * (stopY - dotY) / dist;
                        b2 = (stopX - dotX) / dist + num * (stopY - dotY) / dist - 1;
                    }
                    touch2 = 1;
                    touch1 = 0;
                    invalidate();
                } else {
                    int k, x, y;
                    k = 0;
                    x = 0;
                    y = 0;
                    for (int p = 0; p < num + 1; p++) {
                        if (Math.abs(dX - dotX - k) < 40) {
                            x = 1;
                        }
                        if (Math.abs(dY - dotY - k) < 40) {
                            y = 1;
                        }
                        k = k + dist;
                    }
                    if (x == 1 && y == 1) {

                        disX = (int) event.getX();
                        disY = (int) event.getY();
                        touch1 = 1;
                        mBitmapCanvas.drawCircle(startX, startY, 25, outCircle);
                        invalidate();
                    }
                }
                return super.onTouchEvent(event);

            } else if(screenActive==1){
                int k, x, y;
                k = 0;
                x = 0;
                y = 0;
                for (int p = 0; p < num + 1; p++) {
                    if (Math.abs(dX - dotX - k) < 40) {
                        x = 1;
                    }
                    if (Math.abs(dY - dotY - k) < 40) {
                        y = 1;
                    }
                    k = k + dist;
                }
                if (x == 1 && y == 1) {
                    MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.puttt);
                    mp.start();
                    disX = (int) event.getX();
                    disY = (int) event.getY();
                    touch1 = 1;
                    invalidate();
                }
                return super.onTouchEvent(event);

            }
        return super.onTouchEvent(event);
    }
}
