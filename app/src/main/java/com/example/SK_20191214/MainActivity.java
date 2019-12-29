package com.example.SK_20191214;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.text.TextUtils.TruncateAt.MARQUEE;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button left,right,plus,minus;
    EditText TextEditView;
    int TextSize = 3;
    Animation animationToLeft,animationToRight,animationToLeftFirst,animationToRightFirst;
    AnimationSet animationSetLeft,animationSetRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text);
        plus = (Button)findViewById(R.id.plus);
        minus = (Button)findViewById(R.id.minus);

        left = (Button)findViewById(R.id.left);
        right = (Button)findViewById(R.id.right);

        TextEditView = (EditText)findViewById(R.id.ChangeText);

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

        animationToLeftFirst = new TranslateAnimation(locationX/2,-800,0,0);
        animationToLeftFirst.setDuration(6000);

        animationToRightFirst = new TranslateAnimation(locationX/2,1200,0,0);
        animationToRightFirst.setDuration(6000);

        animationToLeft = new TranslateAnimation(1200,-800,0,0);
        animationToLeft.setDuration(12000);
        animationToLeft.setRepeatMode(Animation.RESTART);
        animationToLeft.setRepeatCount(Animation.INFINITE);

        animationToRight = new TranslateAnimation(-800,1200, 0, 0);
        animationToRight.setDuration(12000);
        animationToRight.setRepeatMode(Animation.RESTART);
        animationToRight.setRepeatCount(Animation.INFINITE);

        animationSetLeft = new AnimationSet(false);
        animationSetRight = new AnimationSet(false);
        animationSetLeft.addAnimation(animationToLeftFirst);
        animationSetLeft.addAnimation(animationToLeft);




        animationSetRight.addAnimation(animationToRightFirst);
        animationSetRight.addAnimation(animationToRight);

    }

    class ButtonMove implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
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



    class ButtonSizeChange implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.plus:
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textView.getTextSize()+TextSize);
                    break;
                case R.id.minus:
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textView.getTextSize()-TextSize);
                    break;
            }
        }
    }
}

