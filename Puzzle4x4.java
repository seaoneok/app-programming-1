package edu.skku.map.personalassignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class Puzzle4x4 extends AppCompatActivity {

    private int newEmptyX, newEmptyY;
    private RelativeLayout puzzle;

    private ImageButton[][] buttons;
    private Button buttonShuffle;
    private Button puzzle3;
    private Bitmap temp;
    private Bitmap blank;

    private Bitmap[] puzzle4 = new Bitmap[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle4x4);

        buttonView();
        slice();
        puzzleView();

        puzzle3 = findViewById(R.id.button3x3);
        puzzle3.setOnClickListener(new View .OnClickListener(){
            @Override
            public void onClick (View v){
                startActivity(new Intent(Puzzle4x4.this, Puzzle3x3.class));
            }
        });

    }

    private void puzzleView(){
        for(int i =0; i<16;i++){
            buttons[i/4][i%4].setImageBitmap(puzzle4[i]);
            buttons[i/4][i%4].setBackgroundResource(android.R.drawable.btn_default);
            buttons[i/4][i%4].setBackgroundColor(ContextCompat.getColor(this,R.color.white));
        }
    }

    private void shuffle() {
        int n=16 ;
        Random random=new Random();
        while(n>1){
            int randomNum = random.nextInt(n--);
            Bitmap temp = puzzle4[randomNum];
            puzzle4[randomNum]=puzzle4[n];
            puzzle4[n]=temp;
        }
        findEmpty();
    }

    private void findEmpty(){
        for(int i =0; i<15;i++){
            if(puzzle4[i].equals(blank)){
                newEmptyX = buttons[i/4][i%4].getTag().toString().charAt(0)-'0';
                newEmptyY = buttons[i/4][i%4].getTag().toString().charAt(1)-'0';
            }
        }
    }

    private void slice(){
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.elite);
        Bitmap white = Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);

        int OrH=bitmap.getHeight();
        int OrW=bitmap.getWidth();

        int PicW=OrW/4;
        int PicH=OrH/4;

        for(int i=0; i <4; i++) {
            for (int j = 0; j < 4; j++) {
                puzzle4[i*4+j] = Bitmap.createBitmap(bitmap, j * PicW, i * PicH, PicW, PicH);
                puzzle4[i*4+j] = Bitmap.createScaledBitmap(puzzle4[i*4+j],150,150,false);
            }
        }
        puzzle4[15]=Bitmap.createBitmap(white,0,0,1,1);
        puzzle4[15]=Bitmap.createScaledBitmap(puzzle4[15],150,150,false);
        blank = puzzle4[15];
    }

    private void buttonView(){
        puzzle=findViewById(R.id.puzzle);
        buttonShuffle = findViewById(R.id.button_shuffle);

        buttons = new ImageButton[4][4];
        for(int i = 0; i<16;i++){
            buttons[i/4][i%4]=(ImageButton)puzzle.getChildAt(i);
        }

        buttonShuffle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                slice();
                shuffle();
                puzzleView();
            }
        });

    }

    public void movePuzzle(View v){
        ImageButton button = (ImageButton) v;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';

        if((Math.abs(newEmptyX-x)==1&&newEmptyY==y)||(Math.abs(newEmptyY-y)==1&&newEmptyX==x)){

            buttons[newEmptyX][newEmptyY].setImageBitmap(puzzle4[x*4+y]);
            buttons[newEmptyX][newEmptyY].setBackgroundResource(android.R.drawable.btn_default);

            puzzle4[newEmptyX*4+newEmptyY] = puzzle4[x*4+y];
            puzzle4[x*4+y] = temp;
            buttons[x][y].setImageBitmap(temp);

            button.setImageBitmap(blank);
            button.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            newEmptyX=x; newEmptyY=y;
            checkDone();
        }
    }

    private void checkDone(){
        boolean isDone = false;
        if(newEmptyX==3&&newEmptyY==3) {
            for(int i=0;i<15;i++){
                if(buttons[i/4][i%4].getTag().toString().equals(String.valueOf((i/4)*10+(i%4)))){
                    isDone=true;
                }else{
                    isDone=false;
                    break;
                }
            }
        }
        if(isDone){
            Toast.makeText(this,"Finish!!",Toast.LENGTH_LONG).show();
            for(int i =0; i<16;i++){
                buttons[i/4][i%4].setClickable(false);
            }
            buttonShuffle.setClickable(false);
        }
    }
}
