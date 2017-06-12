package com.example.guest.bopit;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    @Bind(R.id.commandTextView) TextView commandTextView;
    @Bind(R.id.newCommand) Button newCommandButton;
    @Bind(R.id.counterTextView) TextView counterTextView;
    @Bind(R.id.commandCard) CardView commandCardView;

    String[] commands = {"Double Tap", "Longclick", "Pinch to Zoom", "Fling"};
    int randomNum;

    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        newGame();

        gestureDetector = new GestureDetector(this, new GestureListener());
        newCommandButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                newGame();
            }
        });
        scaleGestureDetector = new ScaleGestureDetector(this, new PinchListener());
        commandCardView.setOnTouchListener(this);
    }

    public void setRandomCommand() {
        randomNum = (int)(Math.random() * commands.length);
        commandTextView.setText(commands[randomNum]);
    }

    public void newGame() {
        score = 0;
        counterTextView.setText(String.format("Score: %d", score));
        setRandomCommand();
    }


    public void increaseCounter() {
        score++;
        counterTextView.setText(String.format("Score: %d", score));
        setRandomCommand();
    }


    public void processGesture(String gesture) {
        if (commandTextView.getText().toString().equals(gesture)) {
            increaseCounter();
        } else {
            newGame();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            v.animate().alpha(0.7f).scaleX(0.9f).scaleY(0.9f);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            v.animate().alpha(1f).scaleX(1f).scaleY(1f);
        }
        return gestureDetector.onTouchEvent(event);
    }



    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            MainActivity.this.processGesture("Longclick");
            commandCardView.animate().alpha(1f).scaleX(1f).scaleY(1f);
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            MainActivity.this.processGesture("Fling");
            commandCardView.animate().alpha(1f).scaleX(1f).scaleY(1f);
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            MainActivity.this.processGesture("Double Tap");
            commandCardView.animate().alpha(1f).scaleX(1f).scaleY(1f);
            return super.onDoubleTap(e);
        }
    }



    public class PinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            processGesture("Pinch to Zoom");
            commandCardView.animate().alpha(1f).scaleX(1f).scaleY(1f);
            super.onScaleEnd(detector);
        }
    }

}
