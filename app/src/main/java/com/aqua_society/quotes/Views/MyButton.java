package com.aqua_society.quotes.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aqua_society.quotes.R;

/**
 * Created by MrCharif on 04/01/2017.
 */

public class MyButton extends ConstraintLayout {

    private ImageView button_pagination,button_icon;
    private TextView button_text;

    private String buttonText;
    private Drawable buttonIcon,paginationIcon;

    public MyButton(Context context){
        super(context);
        init();
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context,attrs);
        init();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getAttrs(context,attrs);
        init();
    }

    private void getAttrs(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyButton, 0, 0);

        try {
            buttonText = ta.getString(R.styleable.MyButton_text);
            buttonIcon = ta.getDrawable(R.styleable.MyButton_button_icon);
            paginationIcon = ta.getDrawable(R.styleable.MyButton_button_pagination);
        } finally {
            ta.recycle();
        }
    }

    private void init(){
        inflate(getContext(), R.layout.my_button, this);
        this.button_icon = (ImageView) findViewById(R.id.button_icon);
        this.button_icon.setImageDrawable(buttonIcon);



        this.button_pagination = (ImageView) findViewById(R.id.button_pagination);
        this.button_pagination.setImageDrawable(paginationIcon);

        this.button_text = (TextView) findViewById(R.id.button_text);
        this.button_text.setText(buttonText);
    }
}
