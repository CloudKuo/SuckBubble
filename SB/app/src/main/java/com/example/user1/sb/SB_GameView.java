package com.example.user1.sb;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.Attributes;

/**
 * Created by user1 on 2017/1/5.
 */

class SB_GameView extends SurfaceView implements SurfaceHolder.Callback , Runnable {

    static int sec;
    static Boolean flagStart = true;
    Timer timer;
    int step = 0;

    private Resources resource;
    private Bitmap bubble;
    private Bitmap plane;
    private Bitmap bubble2;


    boolean condition = true , controlcondition =true;
    private Canvas canvas = null;
    private SurfaceHolder surholder;
    public   Thread bubble_thread;
    private ArrayList<gamebubbleview2> lv;
    private int planeX  , planeY;
    String numberstring;
    private GameStrawview planeview;
    private Bitmap scaledbubble;
    private Bitmap scaledplane;
    private Bitmap scaledbubble2;

    private TimerTask timeTask = new TimerTask()
    {
        public void run()
        {
            if(flagStart){
                sec++;


            }
        }
    };

    public SB_GameView(Context context){
        super(context);
    }

    public SB_GameView(Context context, AttributeSet attrs){
        //initial all the bitmap and timer
        super(context,attrs);
        getHolder().addCallback(this);
        surholder = getHolder();
        resource = getResources();
        bubble = BitmapFactory.decodeResource(resource,R.drawable.bubble);
        plane = BitmapFactory.decodeResource(resource,R.drawable.plane);
        //bubble2 = BitmapFactory.decodeResource(resource,R.drawable.home);

        scaledbubble = Bitmap.createScaledBitmap(bubble, 75, 75 , true);
        scaledplane = Bitmap.createScaledBitmap(plane, 100,100,true);
        //scaledbubble2 =  Bitmap.createScaledBitmap(bubble2, SB_GamePlay.Window_width/4, 20 , true);

        planeX = SB_MainActivity.Window_width/2;
        planeY = SB_MainActivity.Window_height/2;
        Initialbubble();
        setFocusable(true);
        bubble_thread = new Thread(this);
        timer = new Timer();  //Timer
        timer.schedule(timeTask , 0, 400);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);//將背景變透明的
    }
    //畫出線的整體架構 , 會重複呼叫
    public  void Initialbubble(){
        lv = new ArrayList<gamebubbleview2>();
        lv.clear();

        planeview = new GameStrawview(scaledplane);
        for(int i = 0 ; i < 30 ; i++ ){//幾顆珍珠
            gamebubbleview2 LV = new gamebubbleview2(scaledbubble, 0 , 0);
            lv.add(LV);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){//移動飛機的方式
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            planeX = (int)event.getX();
            planeY = (int)event.getY();
            if(planeX <= 0 +  planeview.bubble_width / 2  ){
                planeX = 0 +  planeview.bubble_width / 2;
            }
            if(planeX >= SB_MainActivity.Window_width - planeview.bubble_width/2){
                planeX = SB_MainActivity.Window_width - planeview.bubble_width/2 ;
            }
            if(planeY <= 0 + planeview.bubble_height/2){
                planeY =0 + planeview.bubble_height/2;
            }
            if(planeY >=SB_MainActivity.Window_height - planeview.bubble_height/2){
                planeY =SB_MainActivity.Window_height - planeview.bubble_height/2;
            }
        }

        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        bubble_thread.start();  //啟動執行緒
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        condition=false;
        bubble_thread.interrupt();
        bubble_thread=null;//stop thread
    }
    int i = 0;
    @Override
    public void run(){//bubble碰到plane的thread
        while(condition) {
            if (controlcondition) {
                try {
                    Thread.sleep(50);
                    canvas = surholder.lockCanvas();
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//將背景變透明
                    numberstring = Integer.toString(sec);

                    Paint mypaint = new Paint();
                    mypaint.setTextSize(50);//設定字體大小
                    mypaint.setColor(Color.BLACK);//設定字體顏色
                    canvas.drawText(numberstring, 0, 40, mypaint);//（顯示文字,顯示位置-X,顯示位置-Y, paint）

                    for (gamebubbleview2 a : lv) {
                        a.bubbleCanvas(canvas);
                        a.Touch(planeX + planeview.bubble_width / 2, planeY);
                        a.Touch(planeX - planeview.bubble_width / 2, planeY);
                        a.Touch(planeX, planeY + planeview.bubble_height / 2);
                        a.Touch(planeX, planeY - planeview.bubble_height / 2);
                    }




                    planeview.bubbleCanvas2(canvas,planeX - planeview.bubble_width/2,planeY - planeview.bubble_height/2);


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        surholder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
class gamebubbleview implements Runnable {
    static boolean condition = true, control = true;
    private int bubbleX, bubbleY;
    int bubblespeed = 0;
    static int bubble_width, bubble_height;
    private Bitmap bubble_bmp = null;
    private int bubbledirection;
    private int randomnumber = 0;
    Rect touch_rect = new Rect();
    public static Thread bubblethrea;
    private int initialbubbleX, initialbubbleY;
    private int random = 0;
    private int bubblemode;


    public gamebubbleview(Bitmap bmp, int initialx, int initialy, int mode) {
        bubble_bmp = bmp;
        initialbubbleX = initialx;
        initialbubbleY = initialy;
        bubblemode = mode;
        Initialbmp();
        bubblethrea = new Thread(this);
        bubblethrea.start();
    }

    public void Initialbmp() {
        bubble_width = bubble_bmp.getWidth();
        bubble_height = bubble_bmp.getHeight();
        bubbleX = initialbubbleX;
        bubbleY = initialbubbleY;


    }

    public void bubbleCanvas(Canvas canvas) {

        canvas.drawBitmap(this.bubble_bmp, bubbleX, bubbleY, null);
    }

    //碰撞判斷

    public void Touch(int plane_X, int plane_Y) {
        if (touch_rect.contains(plane_X, plane_Y)) {
            condition = false;


        }
    }


    @Override
    public void run() {
        while (condition) {
            if (control) {
                if (bubbleY < SB_MainActivity.Window_height - bubble_height / 2) {
                    bubbleY += bubblespeed;
                    touch_rect.set(bubbleX, bubbleY, bubbleX + bubble_width, bubbleY + bubble_height);
                } else {
                    bubbleY = initialbubbleY;
                    bubblespeed = 0;

                }
                if (bubblemode == 0) {
                    if (SB_GameView.sec % 60 == 49) {
                        bubblespeed = 5;
                    }
                } else if (bubblemode == 1) {
                    if (SB_GameView.sec % 60 == 35) {
                        bubblespeed = 5;
                    }

                } else if (bubblemode == 2) {
                    if (SB_GameView.sec % 60 == 59) {
                        bubblespeed = 5;
                    }
                }

                try {
                    bubblethrea.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
class GameStrawview{
    Bitmap bubble_bmp;
    static int bubble_width , bubble_height;

    public GameStrawview(Bitmap bmp){
        bubble_bmp = bmp;
        initialplane();
    }

    public void initialplane(){
        bubble_width = bubble_bmp.getWidth();
        bubble_height = bubble_bmp.getHeight();
    }

    public void bubbleCanvas2(Canvas canvas , int bx , int by){
        canvas.drawBitmap(this.bubble_bmp , bx , by , null);

    }
}
class gamebubbleview2 implements Runnable{
    static boolean condition = true , control = true;
    private int bubbleX , bubbleY;
    int bubblespeed2 = 3;

    static int bubble_width , bubble_height;
    private Bitmap bubble_bmp = null;
    private int bubbledirection = 0;
    private int randomnumber = 0;
    Rect touch_rect = new Rect();
    public static Thread bubblethrea;

    private int initialbubbleX , initialbubbleY;

    public gamebubbleview2(Bitmap bmp, int initialx , int initialy){
        bubble_bmp = bmp;
        Initialbmp();
        bubblethrea = new Thread(this);
        bubblethrea.start();
    }

    public void Initialbmp(){
        bubble_width = bubble_bmp.getWidth();
        bubble_height = bubble_bmp.getHeight();
        bubbleX = (int) (Math.random()*(SB_MainActivity.Window_width -bubble_width));
        bubbleY = -(int) (Math.random()*(SB_MainActivity.Window_height-bubble_height));
    }


    public void bubbleCanvas(Canvas canvas){

        canvas.drawBitmap(this.bubble_bmp , bubbleX , bubbleY , null);
    }

    //改變線的位置
    public void bubblePosition(){
        if (bubbledirection == 0){
            // y 值增加

            bubbleY +=bubblespeed2;
        }
        touch_rect.set(bubbleX, bubbleY ,bubbleX + bubble_width ,  bubbleY + bubble_height);

    }
    public void randomlocation(){
        if(randomnumber == 0){
            bubbleX = (int)(Math.random() * ( SB_MainActivity.Window_width -bubble_width) );
            bubbleY = -(int)(Math.random() * ( SB_MainActivity.Window_height - bubble_height))*2;
        }
    }

    //碰撞判斷
    public void Touch(int plane_X , int plane_Y ){
        if(touch_rect.contains(plane_X,plane_Y)){
            condition = false;
        }
    }


    @Override
    public void run(){
        while(condition) {
            if (control) {

                bubblePosition();
                if (bubbleY >= SB_MainActivity.Window_height - bubble_height / 2) {

                    randomlocation();
                    bubblespeed2 = 0;

                }
                if(SB_GameView.sec % 30 == 19){
                    bubblespeed2 =  7;
                }

                try {
                    bubblethrea.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


