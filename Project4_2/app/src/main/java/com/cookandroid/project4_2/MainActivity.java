package com.cookandroid.project4_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView text1, text2;
    CheckBox chkagree;
    RadioGroup rgroup1;
    RadioButton rbtdog, rbtcat, rbtrabbit;
    Button btnok;
    ImageView imgpet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("201735969 박진형");

        text1 = (TextView) findViewById(R.id.text1);
        chkagree = (CheckBox) findViewById(R.id.chkagree);
        text2 = (TextView) findViewById(R.id.text2);
        rgroup1 = (RadioGroup) findViewById(R.id.rgroup1);
        rbtdog = (RadioButton) findViewById(R.id.rbtdog);
        rbtcat = (RadioButton) findViewById(R.id.rbtcat);
        rbtrabbit = (RadioButton) findViewById(R.id.rbtrabbit);
        btnok = (Button) findViewById(R.id.btnok);
        imgpet = (ImageView) findViewById(R.id.imgpet);

        chkagree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkagree.isChecked())
                {
                    text2.setVisibility(View.VISIBLE);
                    rgroup1.setVisibility(View.VISIBLE);
                    btnok.setVisibility(View.VISIBLE);
                    imgpet.setVisibility(View.VISIBLE);
                }
                else
                {
                    text2.setVisibility(View.INVISIBLE);
                    rgroup1.setVisibility(View.INVISIBLE);
                    btnok.setVisibility(View.INVISIBLE);
                    imgpet.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rgroup1.getCheckedRadioButtonId())
                {
                    case R.id.rbtdog :
                        imgpet.setImageResource(R.drawable.dog);
                        break;
                    case R.id.rbtcat :
                        imgpet.setImageResource(R.drawable.cat);
                        break;
                    case R.id.rbtrabbit :
                        imgpet.setImageResource(R.drawable.rabbit);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"동물 먼저 선택하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}