package com.example.bubble;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ChatHeadService extends Service {

    private WindowManager mWindowManager;
    private View mChatHeadView;

    private RecyclerView recyclerView;
    private View mv;

    private LinearLayoutManager linearLayoutManager;
    private userAdapter userAdapter;

    int data=0;

    WindowManager.LayoutParams params;

    public ChatHeadService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Inflate the chat head layout we created
        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.layout_chat_head, null);

        recyclerView = (RecyclerView) mChatHeadView.findViewById(R.id.bubble_list_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        userAdapter = new userAdapter(data, ChatHeadService.this);
        recyclerView.setAdapter(userAdapter);


        //linearLayout = new LinearLayout(R.id.chat_head_fragment).

        //Add the view to the window.

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    PixelFormat.TRANSLUCENT);

            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
            params.x = 0;
            params.y = 100;
            mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            mWindowManager.addView(mChatHeadView, params);

        } else {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    PixelFormat.TRANSLUCENT);


            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
            params.x = 0;
            params.y = 100;
            mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            mWindowManager.addView(mChatHeadView, params);
        }

//        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_PHONE,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT);
//
//        //Specify the chat head position
//        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
//        params.x = 0;
//        params.y = 100;
//
//        //Add the view to the window
//        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        mWindowManager.addView(mChatHeadView, params);

        //Set the close button.
        ImageView closeButton = (ImageView) mChatHeadView.findViewById(R.id.close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close the service and remove the chat head from the window
                stopSelf();
            }
        });
        ImageView addButton = (ImageView) mChatHeadView.findViewById(R.id.add_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data += 1;
                userAdapter = new userAdapter(data, ChatHeadService.this);
                recyclerView.setAdapter(userAdapter);
            }
        });

        //Drag and move chat head using user's touch action.
//        ImageView chatHeadImage = (ImageView) mChatHeadView.findViewById(R.id.chat_head_profile_iv);
//        chatHeadImage.setOnTouchListener(new View.OnTouchListener() {
//            private int lastAction;
//            private int initialX;
//            private int initialY;
//            private float initialTouchX;
//            private float initialTouchY;
//
//            private Long touchDownTime;
//            private Long touchUpTime;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        //records the time of the touch down
//                        touchDownTime = System.currentTimeMillis()/1000;
//
//                        //remember the initial position.
//                        initialX = params.x;
//                        initialY = params.y;
//
//                        //get the touch location
//                        initialTouchX = event.getRawX();
//                        initialTouchY = event.getRawY();
//
//                        lastAction = event.getAction();
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        //record the time of the touch up
//                        touchUpTime = System.currentTimeMillis()/1000;
//
//                        //As we implemented on touch listener with ACTION_MOVE,
//                        //we have to check if the previous action was ACTION_DOWN
//                        //to identify if the user clicked the view or not.
//                        if ((lastAction == MotionEvent.ACTION_DOWN) || (touchUpTime-touchDownTime<0.8)) {
//                            //Open the chat conversation click.
//                            Intent intent = new Intent(ChatHeadService.this, popup.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//
//                            //close the service and remove the chat heads
//                            //stopSelf();
//                        }
//                        lastAction = event.getAction();
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        //Calculate the X and Y coordinates of the view.
//                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
//                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
//
//                        //Update the layout with new X & Y coordinate
//                        mWindowManager.updateViewLayout(mChatHeadView, params);
//                        lastAction = event.getAction();
//                        return true;
//                }
//                return false;
//            }
//        });

        ImageView chatHeadDefaultImage = (ImageView) mChatHeadView.findViewById(R.id.chat_head_default);
        chatHeadDefaultImage.setOnTouchListener(new View.OnTouchListener() {
            private int lastAction;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            private Long touchDownTime;
            private Long touchUpTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //records the time of the touch down
                        touchDownTime = System.currentTimeMillis()/1000;

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_UP:
                        //record the time of the touch up
                        touchUpTime = System.currentTimeMillis()/1000;

                        //As we implemented on touch listener with ACTION_MOVE,
                        //we have to check if the previous action was ACTION_DOWN
                        //to identify if the user clicked the view or not.
                        if ((lastAction == MotionEvent.ACTION_DOWN) || (touchUpTime-touchDownTime<0.8)) {
                            //Open the chat conversation click.
                            Intent intent = new Intent(ChatHeadService.this, popup.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            //close the service and remove the chat heads
                            //stopSelf();
                        }
                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mChatHeadView, params);
                        lastAction = event.getAction();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatHeadView != null) mWindowManager.removeView(mChatHeadView);
    }
}
