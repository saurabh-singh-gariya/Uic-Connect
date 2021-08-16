package com.example.uicaddressbook.Adapter;

import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.example.uicnotifier.R;

public class SliderAdapter extends PagerAdapter {

    Context context;

    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int animations[] = {
            R.raw.splash_anim_one,
            R.raw.splash_anim_two,
            R.raw.splash_anim_three,
    };

    int headings[] = {
            R.string.onBoarding_one,
            R.string.onBoarding_two,
            R.string.onBoarding_three,
    };

    @Override
    public int getCount() {
        return animations.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container, false);

        LottieAnimationView animationView = view.findViewById(R.id.splash_animation);
        TextView onBoardingText = view. findViewById(R.id.onBoarding_text);

        if(position==3){
            animationView.setAnimation(animations[position]);
            onBoardingText.setVisibility(View.GONE);
        }
        else {
            animationView.setAnimation(animations[position]);
            onBoardingText.setText(headings[position]);
        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
