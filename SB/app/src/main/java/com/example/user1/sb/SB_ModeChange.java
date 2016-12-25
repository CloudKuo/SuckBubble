package com.example.user1.sb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by user1 on 2016/12/18.
 */
//mode page
public class SB_ModeChange extends AppCompatActivity {
    private Button Home_Btn;
    private  Button information_btn;
    private  String informationText ="    遊戲玩法:    \n    在奶茶喝完之前  \n    盡力吸完所有珍珠  \n    按住吸按鍵可以開始吸    \n    移動吸管去吸珍珠";
    private Button normal_Btn,limit_Btn,random_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sb_mode);
        Home_Btn = (Button) findViewById(R.id.home);
        information_btn = (Button) findViewById(R.id.information);
        normal_Btn =(Button)findViewById(R.id.normal_Btn);




        Home_Btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SB_ModeChange.this, SB_MainActivity.class);
                startActivity(intent);
                SB_ModeChange.this.finish();
            }
        });
        information_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), informationText, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();//information of game
                //Toast.makeText(v.getContext(), informationText, Toast.LENGTH_LONG).show();

            }
        });
        normal_Btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SB_ModeChange.this, SB_GamePlay.class);
                startActivity(intent);
                SB_ModeChange.this.finish();
            }
        });
        limit_Btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SB_ModeChange.this, SB_GamePlay.class);
                startActivity(intent);
                SB_ModeChange.this.finish();
            }
        });
        random_Btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SB_ModeChange.this, SB_GamePlay.class);
                startActivity(intent);
                SB_ModeChange.this.finish();
            }
        });

    }

}
