package com.example.user1.sb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class SB_MainActivity extends AppCompatActivity {
    private Button change_Btn,Start_btn;
    private ImageButton information_btn;
    private String informationText ="    遊戲玩法:    \n    在奶茶喝完之前  \n    盡力吸完所有珍珠  \n    按住吸按鍵可以開始吸    \n    移動吸管去吸珍珠";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb__main);
        change_Btn =(Button) findViewById(R.id.change_Btn);
        Start_btn = (Button) findViewById(R.id.Start_btn);
        information_btn = (ImageButton) findViewById(R.id.information_btn);
        //assign all varibles



        Start_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SB_MainActivity.this, SB_ModeChange.class);
                startActivity(intent);
                SB_MainActivity.this.finish();
            }
        });
        //開始遊戲的按鍵
        information_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), informationText, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();//information of game
                //Toast.makeText(v.getContext(), informationText, Toast.LENGTH_LONG).show();

            }
        });
        //遊戲規則講解的按鍵


        change_Btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SB_MainActivity.this, SB_Change.class);
                startActivity(intent);
                SB_MainActivity.this.finish();

            }
        });
        //跑到用料區的按鍵


    }


}
