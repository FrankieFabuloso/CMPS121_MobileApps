package com.frocha.assn5;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;


/**
 * TODO: document your custom view class.
 */
public class MyScreenView extends View {
    private Tomato tom = new Tomato();
    public float accelx = 0;
    public float accely = 0;
    public float width;

    //hard coded variables
    //drain
    public float drainx = 500;
    public float drainy = 1000;
    public float drainr = 75;

    //line1
    public float line1H;
    public float line1W;

    public float line2H;
    public float line2W;
    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            postDelayed(this, 20);
            invalidate();
            //how the ball will behave in the game
            physics();
            /*for testing purposes

            if (tom.x == getWidth()) {
                tom.x = 0;
            } else
                tom.x++;
                */
        }

    };

    public MyScreenView(Context context) {
        super(context);
        init(null, 0);
    }

    public MyScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyScreenView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        post(animator);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = canvas.getWidth();
        //paints to be used on the canvas
        Paint g = new Paint();
        Paint r = new Paint();
        Paint b = new Paint();

        b.setColor(Color.BLACK);
        g.setColor(Color.BLUE);
        r.setColor(Color.RED);

        //shapes to be drawn on screen
        canvas.drawCircle(drainx, drainy, drainr, g);
        canvas.drawCircle(tom.x, tom.y, tom.r, r);
        canvas.drawLine(0, getHeight() * (float) .2, getWidth() * (float) .6, getHeight() * (float) .2, b);
        canvas.drawLine(getWidth(), getHeight() * (float) .7, getWidth() * (float) .3, getHeight() * (float) .7, b);
        //canvas.drawRect(0, 200, 500, 170, b);
        //canvas.drawRect(250, 700, getWidth(), 720, b);


    }

    public void physics() {
        tom.vx += accelx * .01;
        tom.x += tom.vx * 1.5;
        tom.vy += accely * .01;
        tom.y += tom.vy * 1.5;
        //line 1 position
        line1W = getWidth() * (float) .6;
        line1H = getHeight() * (float) .2;
        //line 2 position
        line2W = getWidth() * (float) .3;
        line2H = getHeight() * (float) .7;

        //checking for win
        float diffx = tom.x - drainx;
        float diffy = tom.y - drainy;

        double diff = Math.sqrt((diffx * diffx) + (diffy * diffy));
        if (diff + 60 < tom.r + drainr) {
            win();
            //reset coordinates to original position
            tom.x = 50;
            tom.y = 50;
        }

        //checking for walls
        //top wall
        if (tom.y - tom.r < 0) {
            tom.vy = tom.vy * -(float) .5;
            tom.y = 0 + tom.r;
        }

        //right wall
        if (tom.x + tom.r > getWidth()) {
            tom.vx = tom.vx * -(float) .5;
            tom.x = getWidth() - tom.r;
        }

        //bottom wall
        if (tom.y + tom.r > getHeight()) {
            tom.vy = tom.vy * -(float) .5;
            tom.y = getHeight() - tom.r;
        }
        //left wall
        if (tom.x - tom.r < 0) {
            tom.vx = tom.vx * -(float) .5;
            tom.x = 0 + tom.r;
        }


        //checking for obstacles
        //top line
        //bottom line
        if (tom.y - tom.r < line1H && tom.y + tom.r > line1H){
            if(tom.x > line1W) {

            }else if(tom.y < line1H){
                tom.y = line1H - tom.r;
                tom.vy = tom.vy * -(float) .5;
            }
            else if(tom.y > line1H){
                tom.y = line1H + tom.r;
                tom.vy = tom.vy * -(float) .5;
            }
        }
        //bottom line
        if (tom.y - tom.r < line2H && tom.y + tom.r > line2H){
            if(tom.x < line2W) {

            }else if(tom.y < line2H){
                tom.y = line2H - tom.r;
                tom.vy = tom.vy * -(float) .5;
            }
            else if(tom.y > line2H){
                tom.y = line2H + tom.r;
                tom.vy = tom.vy * -(float) .5;
            }
        }
    }

    public void win(){
        Context contex = getContext();
        CharSequence text = "You Win";
        Toast.makeText(contex, text, Toast.LENGTH_SHORT);
    }

}

