package com.example.bubble;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;
import com.txusballesteros.bubbles.OnInitializedCallback;

public class MainActivity extends AppCompatActivity {

//    private RecyclerView userRecyclerView;
//    private userAdapter usersAdapter;
//    private LinearLayoutManager linearLayoutManager;

    //private Button chatHead;

    //private BubblesManager bubblesManager;

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        chatHead = (Button) findViewById(R.id.chat_head);
//
//        userRecyclerView = (RecyclerView) findViewById(R.id.all_users_list);
//
//        //chatHead = (Button) findViewById(R.id.chat_head);
//
//        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        userRecyclerView.setLayoutManager(linearLayoutManager);
//
//        initializeBubblesManager();
//
//
//
//        chatHead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addNewBubble();
//            }
//        });
//
//         // tried the permission for the windowManager --- (chatHeadservice - added the condition for higher API )
//        int LAYOUT_FLAG;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//        } else {
//            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
//        }
//
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                LAYOUT_FLAG,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializeView();
        }


    }

    private void initializeView() {
        findViewById(R.id.chat_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, ChatHeadService.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

//    private void addNewBubble() {
//        BubbleLayout bubbleView = (BubbleLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.bubble_layout, null);
//        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
//            @Override
//            public void onBubbleRemoved(BubbleLayout bubble) { }
//        });
//        bubbleView.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {
//
//            @Override
//            public void onBubbleClick(BubbleLayout bubble) {
//                Toast.makeText(getApplicationContext(), "Clicked !",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        bubbleView.setShouldStickToWall(true);
//        bubblesManager.addBubble(bubbleView, 60, 20);
//    }
//
//    private void initializeBubblesManager() {
//
//        bubblesManager = new BubblesManager.Builder(this)
//                .setTrashLayout(R.layout.bubble_trash_layout)
//                .setInitializationCallback(new OnInitializedCallback() {
//                    @Override
//                    public void onInitialized() {
//                        addNewBubble();
//                    }
//                })
//                .build();
//        bubblesManager.initialize();
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //bubblesManager.recycle();
//    }
}
