package com.music.free.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class EditTextHelvaticaMeidum extends androidx.appcompat.widget.AppCompatEditText {

    public EditTextHelvaticaMeidum(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextHelvaticaMeidum(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextHelvaticaMeidum(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            setTypeface(tf);
        }
    }

}