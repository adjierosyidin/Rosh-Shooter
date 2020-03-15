package com.roshapp.roshshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import static com.roshapp.roshshooter.GameView.METEOR_DESTROYED;
import static com.roshapp.roshshooter.GameView.SCORE;

public class Meteor {
    private Bitmap mBitmap, mlives;
    private int mX;
    private int mY, mYl, mY2, mY3;
    private int mMaxX;
    private int mMinX;
    private int mMaxY;
    private int mMinY;

    private int mSpeed;
    private Rect mCollision;
    private int mScreenSizeX;
    private int mScreenSizeY;
    private int mHP;
    private SoundPlayer mSoundPlayer;

    public Meteor(Context context, int screenSizeX, int screenSizeY, SoundPlayer soundPlayer){
        mScreenSizeX = screenSizeX;
        mScreenSizeY = screenSizeY;
        mSoundPlayer = soundPlayer;

        mlives = BitmapFactory.decodeResource(context.getResources(), R.drawable.love);
        mlives = Bitmap.createScaledBitmap(mlives, mlives.getWidth() * 1/10, mlives.getHeight() * 1/10, false);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.meteor_1);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * 3/5, mBitmap.getHeight() * 3/5, false);

        mMaxX = screenSizeX - mBitmap.getWidth();
        mMaxY = screenSizeY - mBitmap.getHeight();
        mMinX = 0;
        mMinY = 0;
        mHP = 3;

        Random random = new Random();
        mSpeed = random.nextInt(3) + 1;

        mX = random.nextInt(mMaxX);
        mY = 0 - mBitmap.getHeight();
        mYl = 15;
        mY2 = 15;
        mY3 = 15;

        mCollision = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    public void update(){
        mY += 7 * mSpeed;

        mCollision.left = mX;
        mCollision.top = mY;
        mCollision.right = mX + mBitmap.getWidth();
        mCollision.bottom = mY + mBitmap.getHeight();
    }

    public Rect getCollision() {
        return mCollision;
    }

    public void hit(){
        if (--mHP ==0){
            SCORE += 20;
            METEOR_DESTROYED++;
            destroy();
        }else{
            mSoundPlayer.playExplode();
        }
    }

    public void destroy(){
        mY = mScreenSizeY + 1;
        if (GameView.lives==2){
            mYl = mScreenSizeY + 1;
        }
        if (GameView.lives==1){
            mY2 = mScreenSizeY + 1;
        }
        if (GameView.lives==0){
            mY3 = mScreenSizeY + 1;
        }
        mSoundPlayer.playCrash();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public Bitmap getMlives() {
        return mlives;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int getYl(){
        return mYl;
    }

    public int getY2(){
        return mY2;
    }

    public int getY3(){
        return mY3;
    }
}
