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

public class Puzzle3x3 extends AppCompatActivity {

    private int newEmptyX, newEmptyY;
    private RelativeLayout puzzle;

    private ImageButton[][] buttons;
    private Button buttonShuffle;
    private Button puzzle4;
    private Bitmap temp;
    private Bitmap blank;
    private Bitmap white = Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);

    private Bitmap[] puzzle3 = new Bitmap[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle3x3);
        buttonView();
        slice();
        puzzleView();

        puzzle4 = findViewById(R.id.button4x4);
        puzzle4.setOnClickListener(new View .OnClickListener(){
            @Override
            public void onClick (View v){
                startActivity(new Intent(Puzzle3x3.this, Puzzle4x4.class));
            }
        });
    }

    private void puzzleView(){
        for(int i =0; i<9;i++){
            buttons[i/3][i%3].setImageBitmap(puzzle3[i]);
            buttons[i/3][i%3].setBackgroundResource(android.R.drawable.btn_default);
            buttons[i/3][i%3].setBackgroundColor(ContextCompat.getColor(this,R.color.white));
        }
    }

    private void shuffle() {
        int n=9 ;
        Random random=new Random();
        while(n>1){
            int randomNum = random.nextInt(n--);
            Bitmap temp = puzzle3[randomNum];
            puzzle3[randomNum]=puzzle3[n];
            puzzle3[n]=temp;
        }
        findEmpty();
    }

    private void findEmpty(){
        for(int i = 0; i<9;i++){
            if(puzzle3[i].equals(blank)) {
                newEmptyX = buttons[i/3][i%3].getTag().toString().charAt(0)-'0';
                newEmptyY = buttons[i/3][i%3].getTag().toString().charAt(1)-'0';
            }
        }
    }

    private void slice(){
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.elite);
        Bitmap white = Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);
        int OrH=bitmap.getHeight();
        int OrW=bitmap.getWidth();

        int PicW=OrW/3;
        int PicH=OrH/3;

        for(int i=0; i <3; i++) {
            for (int j = 0; j < 3; j++) {
                puzzle3[i*3+j] = Bitmap.createBitmap(bitmap, j * PicW, i * PicH, PicW, PicH);
                puzzle3[i*3+j] = Bitmap.createScaledBitmap(puzzle3[i*3+j],200,200,false);
            }
        }

        puzzle3[8]=Bitmap.createBitmap(white ,0,0,1,1);
        puzzle3[8]=Bitmap.createScaledBitmap(puzzle3[8],200,200,false);
        blank = puzzle3[8];
    }

    private void buttonView(){
        puzzle=findViewById(R.id.puzzle);
        buttonShuffle = findViewById(R.id.button_shuffle);

        buttons = new ImageButton[3][3];
        for(int i = 0; i<9;i++){
            buttons[i/3][i%3]=(ImageButton)puzzle.getChildAt(i);
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

            buttons[newEmptyX][newEmptyY].setImageBitmap(puzzle3[x*3+y]);
            buttons[newEmptyX][newEmptyY].setBackgroundResource(android.R.drawable.btn_default);

            puzzle3[newEmptyX*3+newEmptyY] = puzzle3[x*3+y];
            puzzle3[x*3+y] = temp;
            buttons[x][y].setImageBitmap(temp);


            button.setImageBitmap(blank);
            button.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            newEmptyX=x; newEmptyY=y;
            checkDone();
        }
    }

    private void checkDone(){
        boolean isWin = false;
        if(newEmptyX==2&&newEmptyY==2) {
            for(int i=0;i<8;i++){
                if(buttons[i/3][i%3].getTag().toString().equals(String.valueOf((i/3)*10+(i%3)))){
                    isWin=true;
                }else{
                    isWin=false;
                    break;
                }
            }
        }
        if(isWin){
            Toast.makeText(this,"Finish!!",Toast.LENGTH_LONG).show();
            for(int i =0; i<9;i++){
                buttons[i/3][i%3].setClickable(false);
            }
            buttonShuffle.setClickable(false);
        }
    }


}
