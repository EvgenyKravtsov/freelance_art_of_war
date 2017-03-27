package com.tesseractumstudios.warhammer_artofwar.util.font.roboto;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public final class TextViewRobotoRegular extends TextView {

    public TextViewRobotoRegular(Context context) {
        super(context);
        setRobotoFont(context);
    }

    public TextViewRobotoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRobotoFont(context);
    }

    public TextViewRobotoRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setRobotoFont(context);
    }

    ////

    private void setRobotoFont(Context context) {
        setTypeface(new RobotoFont(context).getTypefaceRegular());
    }
}
