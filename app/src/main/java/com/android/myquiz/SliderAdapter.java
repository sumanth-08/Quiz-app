package com.android.myquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[] = {
            R.drawable.illustrator_image1,
            R.drawable.illustrator_image2,
            R.drawable.illustrator_image3,
            R.drawable.illustrator_image4

    };
    int headings[] = {
            R.string.heading1,
            R.string.heading2,
            R.string.heading3,
            R.string.heading4

    };
    int description[] = {
            R.string.desc1,
            R.string.desc2,
            R.string.desc3,
            R.string.desc4,
    };

    @Override
    public int getCount() {
        return headings.length;

    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((ConstraintLayout)object);

    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView imageView = view.findViewById(R.id.sliderImage);
        TextView heading = view.findViewById(R.id.sliderHeading);
        TextView desc = view.findViewById(R.id.sliderDesc);

        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        desc.setText(description[position]);

        container.addView(view);


        return view;

    }
}
