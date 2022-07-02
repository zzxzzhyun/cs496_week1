package com.example.week_1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context context;

    public Integer[] images = {
            R.mipmap.beach2, R.mipmap.beach3,
            R.mipmap.beach4, R.mipmap.beach4,
            R.mipmap.beach4, R.mipmap.beach4,
            R.mipmap.beach4, R.mipmap.beach4

    };

    public ImageAdapter(Context c) {
        context = c;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(images[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(240, 240));
        return imageView;
    }
}
