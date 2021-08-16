package com.example.uicaddressbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uicaddressbook.Adapter.SliderAdapter;
import com.example.uicnotifier.R;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button btnLetsGetStarted, btnSkip, btnNext;
    int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
//        getSupportActionBar().hide();

        viewPager = findViewById(R.id.onBoarding_slider);
        dotsLayout = findViewById(R.id.onBoarding_dots);
        btnLetsGetStarted = findViewById(R.id.btn_letsBegin);
        btnSkip = findViewById(R.id.skip_btn);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

    }

    public void setBtnSkip(View view){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void nextBtn(View view){
        if(currentPos==2){
            startActivity(new Intent(this, LoginActivity.class));
        finish();
        }
        viewPager.setCurrentItem(currentPos+1);
    }

    private void addDots(int position){
        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for(int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if(dots.length>0){
            dots[position].setTextSize(40);
            dots[position].setTextColor(getResources().getColor(R.color.black));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos = position;
            if(position < 2){
                btnLetsGetStarted.setVisibility(View.INVISIBLE);
            } else{
                btnLetsGetStarted.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}