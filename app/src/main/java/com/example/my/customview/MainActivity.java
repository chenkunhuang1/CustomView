package com.example.my.customview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private QQStepView mQQStepView;
    private EditText mMax_EditText;
    private EditText mCur_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQQStepView = findViewById(R.id.step_View);
        mMax_EditText = findViewById(R.id.max_edit_text);
        mCur_EditText = findViewById(R.id.current_edit_text);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMax_EditText != null && mCur_EditText != null){
                    mQQStepView.setMaxStep(Integer.parseInt(mMax_EditText.getText().toString()));
                    //属性动画
                    ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0,Integer.parseInt(mCur_EditText.getText().toString()));
                    valueAnimator.setDuration(3000);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float currentStep = (float) animation.getAnimatedValue();
                            mQQStepView.setCurrentStep((int) currentStep);
                        }
                    });
                    valueAnimator.start();
                }

            }
        });

    }
}

