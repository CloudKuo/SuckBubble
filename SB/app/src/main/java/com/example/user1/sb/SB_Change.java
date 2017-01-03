package com.example.user1.sb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by user1 on 2016/12/18.
 */

public class SB_Change extends AppCompatActivity {
    private Button Home_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sb_change);
        Home_Btn = (Button) findViewById(R.id.home);
        Home_Btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SB_Change.this, SB_MainActivity.class);
                startActivity(intent);
                SB_Change.this.finish();
            }
        });
    }

}
