package com.example.user1.sb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by user1 on 2016/12/18.
 */
//mode page
public class SB_ModeChange extends AppCompatActivity {
    private Button Home_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);
        //Home_Btn = (Button) findViewById(R.id.);




        Home_Btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SB_ModeChange.this, SB_MainActivity.class);
                startActivity(intent);
                SB_ModeChange.this.finish();
            }
        });
    }

}
