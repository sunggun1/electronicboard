package com.example.SK_20191214;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.text.TextUtils.TruncateAt.MARQUEE;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button left,right,plus,minus;
    EditText TextEditView;
    int TextSize = 3;
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
    }

    class ButtonMove implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.left:
                    textView.setSingleLine();
                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    textView.setSelected(true);
                    break;
                case R.id.right:
                    textView.setSingleLine();
                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    textView.setSelected(true);
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

