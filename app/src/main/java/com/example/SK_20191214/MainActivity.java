package com.example.SK_20191214;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import yuku.ambilwarna.AmbilWarnaDialog;

import static android.text.TextUtils.TruncateAt.MARQUEE;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button left, right, plus, minus, textColor, backColor, blinkButton, stopButton,startButton;
    EditText TextEditView;
    int TextSize = 3;
    int textColorValue, backColorValue;
    LinearLayout textViewLayout;
    Animation animationToLeft, animationToRight, animationToLeftFirst, animationToRightFirst;
    AnimationSet animationSetLeft, animationSetRight;
    Thread blinkThread;
    boolean stopFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewLayout = (LinearLayout) findViewById(R.id.textViewLinearLayout);
        textView = (TextView) findViewById(R.id.text);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);

        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);

        textColor = (Button) findViewById(R.id.text_color);
        backColor = (Button) findViewById(R.id.back_color);

        TextEditView = (EditText) findViewById(R.id.ChangeText);

        ButtonSizeChange ButtonSizeChange = new ButtonSizeChange();
        ButtonMove ButtonMove = new ButtonMove();
        TextChange TextChange = new TextChange();

        plus.setOnClickListener(ButtonSizeChange);
        minus.setOnClickListener(ButtonSizeChange);

        left.setOnClickListener(ButtonMove);
        right.setOnClickListener(ButtonMove);

        TextEditView.addTextChangedListener(TextChange);

        int locationX = textView.getWidth();
        int locationY = textView.getHeight();

        animationToLeftFirst = new TranslateAnimation(locationX / 2, -800, 0, 0);
        animationToLeftFirst.setDuration(6000);

        animationToRightFirst = new TranslateAnimation(locationX / 2, 1200, 0, 0);
        animationToRightFirst.setDuration(6000);

        animationToLeft = new TranslateAnimation(1200, -800, 0, 0);
        animationToLeft.setDuration(12000);
        animationToLeft.setRepeatMode(Animation.RESTART);
        animationToLeft.setRepeatCount(Animation.INFINITE);

        animationToRight = new TranslateAnimation(-800, 1200, 0, 0);
        animationToRight.setDuration(12000);
        animationToRight.setRepeatMode(Animation.RESTART);
        animationToRight.setRepeatCount(Animation.INFINITE);

        animationSetLeft = new AnimationSet(false);
        animationSetRight = new AnimationSet(false);
        animationSetLeft.addAnimation(animationToLeftFirst);
        animationSetLeft.addAnimation(animationToLeft);


        animationSetRight.addAnimation(animationToRightFirst);
        animationSetRight.addAnimation(animationToRight);

        ChangeColor ChangeColor = new ChangeColor();
        textColor.setOnClickListener(ChangeColor);
        backColor.setOnClickListener(ChangeColor);


        textColorValue = ContextCompat.getColor(MainActivity.this, R.color.colorPrimary);
        backColorValue = ContextCompat.getColor(MainActivity.this, R.color.colorPrimary);

        blinkButton = (Button) findViewById(R.id.blink);
        blinkButton.setOnClickListener(new blink());

        stopButton = (Button) findViewById(R.id.stop);
        stopButton.setOnClickListener(new BlinkStop());

        startButton = (Button)findViewById(R.id.start);
        startButton.setOnClickListener(new Start());

    }

    class BlinkStop implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            stopFlag = true;
            Log.d("",Boolean.toString(stopFlag));
            textView.clearAnimation();
            textView.setGravity(Gravity.CENTER);
        }
    }

    public void openColorPicker(final int id) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, textColorValue, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                if (id == R.id.text_color) {
                    textColorValue = color;
                    textView.setTextColor(textColorValue);
                } else if (id == R.id.back_color) {
                    backColorValue = color;
                    textViewLayout.setBackgroundColor(backColorValue);
                }
            }
        });
        colorPicker.show();
    }

    class ButtonMove implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.left:
                    textView.setGravity(Gravity.CENTER);
                    textView.startAnimation(animationSetLeft);
                    break;
                case R.id.right:
                    textView.setGravity(Gravity.CENTER);
                    textView.startAnimation(animationSetRight);
                    break;
            }
        }
    }

    class TextChange implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            textView.setText(TextEditView.getText());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }


    class ButtonSizeChange implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.plus:
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() + TextSize);
                    break;
                case R.id.minus:
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() - TextSize);
                    break;
            }
        }
    }

    class ChangeColor implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.text_color:
                    openColorPicker(id);
                    break;
                case R.id.back_color:
                    openColorPicker(id);
                    break;
            }
        }
    }

    class blink implements View.OnClickListener {
        private void blink() {
            final Handler handler = new Handler();
            Log.v("",Boolean.toString(stopFlag));
            if (!stopFlag) {
                blinkThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int timeToBlink = 500;
                        try {
                            blinkThread.sleep(timeToBlink);
                        } catch (Exception e) {
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (textView.getVisibility() == View.VISIBLE) {
                                    textView.setVisibility(View.INVISIBLE);
                                } else {
                                    textView.setVisibility(View.VISIBLE);
                                }
                                blink();
                            }
                        });
                    }
                });
                blinkThread.start();
            } else {
                Log.v("",Boolean.toString(stopFlag));
                blinkThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setVisibility(View.VISIBLE);
                    }
                });
                blinkThread.interrupt();
                textView.setVisibility(View.VISIBLE);
                stopFlag = false;
            }
        }

        @Override
        public void onClick(View view) {
            blink();
        }
    }
    class Start implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(),StartActivity.class);
            startActivity(intent);

        }
    }
}


