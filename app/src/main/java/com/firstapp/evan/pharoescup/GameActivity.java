package com.firstapp.evan.pharoescup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    int windowwidth;
    int screenCenter;
    int x_cord, y_cord;
    int Likes = 0;
    RelativeLayout parentView;
    float alphaValue = 0;
    Intent intent;
    int[] ppos;
    int[][] ids;
    ArrayList<String> rules;
    ArrayList<String> descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parentView = (RelativeLayout) findViewById(R.id.layoutView);
        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        screenCenter = windowwidth / 2;

        intent = getIntent();
        ppos = intent.getIntArrayExtra("com.example.evan.pharoescup.POSITIONS");
        rules = intent.getStringArrayListExtra("com.example.evan.pharoescup.RULES");
        descriptions = intent.getStringArrayListExtra("com.example.evan.pharoescup.DESCRIPTIONS");
        ids = new int[4][13];
        ids[0] = intent.getIntArrayExtra("com.example.evan.pharoescup.HEARTIDS");
        ids[1] = intent.getIntArrayExtra("com.example.evan.pharoescup.SPADEIDS");
        ids[2] = intent.getIntArrayExtra("com.example.evan.pharoescup.DIAMONDIDS");
        ids[3] = intent.getIntArrayExtra("com.example.evan.pharoescup.CLUBIDS");


        Deck deck = new Deck(rules,descriptions,ppos,ids);
        /*
        int w = 0;
        for(int a = 0;a < 13;a++) {
            for (int b = 0; b < 4; b++) {

                deck[w] = new Card(b, a);
                deck[w].setImg(ids[b][a]);
                deck[w].setRule(rules.get(ppos[a]), descriptions.get(ppos[a]));
                w++;
            }
        }
        */
        for(int i = 0; i < 52; i++){
            Card card = deck.drawFromDeck();
            final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View myRelView = inflater.inflate(R.layout.card_layout,parentView,false);
            TextView ruleTxt = (TextView) myRelView.findViewById(R.id.ruleTextView);
            ruleTxt.setText(card.getRule().getRule());
            ImageView img = (ImageView) myRelView.findViewById(R.id.cardImg);
            img.setImageResource(card.getImg());
            final float x = myRelView.getX();
            final float y = myRelView.getY();

            myRelView.setTag(i);
            myRelView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    x_cord = (int) event.getRawX();
                    y_cord = (int) event.getRawY();

                    myRelView.setX(x_cord - screenCenter + 40);
                    myRelView.setY(y_cord - 150);
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            x_cord = (int) event.getRawX();
                            y_cord = (int) event.getRawY();
                            myRelView.setX(x_cord - screenCenter + 40);
                            myRelView.setY(y_cord - 150);
                            if (x_cord >= screenCenter) {
                                myRelView
                                        .setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                                if (x_cord > (screenCenter + (screenCenter / 2))) {
                                    //imageLike.setAlpha(1);
                                    if (x_cord > (windowwidth - (screenCenter / 4))) {
                                        Likes = 2;
                                    } else {
                                        Likes = 0;
                                    }
                                } else {
                                    Likes = 0;
                                    // imageLike.setAlpha(0);
                                }
                                //imagePass.setAlpha(0);
                            } else {
                                // rotate
                                myRelView
                                        .setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                                if (x_cord < (screenCenter / 2)) {
                                    //imagePass.setAlpha(1);
                                    if (x_cord < screenCenter / 4) {
                                        Likes = 1;
                                    } else {
                                        Likes = 0;
                                    }
                                } else {
                                    Likes = 0;
                                    //imagePass.setAlpha(0);
                                }

                                // imageLike.setAlpha(0);
                            }

                            break;
                        case MotionEvent.ACTION_UP:
                            x_cord = (int) event.getRawX();
                            y_cord = (int) event.getRawY();

                            Log.e("X Point", "" + x_cord + " , Y " + y_cord);
                            //imagePass.setAlpha(0);
                            //imageLike.setAlpha(0);

                            if (Likes == 0) {
                                Log.e("Event Status", "Nothing");
                                myRelView.setX(x);
                                myRelView.setY(y);
                                myRelView.setRotation(0);
                            } else if (Likes == 1) {
                                Log.e("Event Status", "Passed");
                                parentView.removeView(myRelView);
                            } else if (Likes == 2) {

                                Log.e("Event Status", "Liked");
                                parentView.removeView(myRelView);
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
            parentView.addView(myRelView);
        }


    }

}
