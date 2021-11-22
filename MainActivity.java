package edu.skku.map.personalassignment1;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button puzzle3;
    private Button puzzle4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        puzzle3 = findViewById(R.id.button3x3);
        puzzle3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                startActivity(new Intent(MainActivity.this, Puzzle3x3.class));
            }
        });

        puzzle4 = findViewById(R.id.button4x4);
        puzzle4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                startActivity(new Intent(MainActivity.this, Puzzle4x4.class));
            }
        });
    }
}