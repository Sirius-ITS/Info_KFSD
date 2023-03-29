package com.kuwaitKFF.untilities;

import android.content.Context;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

public class EnableDisableViewPager extends ViewPager {

    private boolean enabled = true;

    public EnableDisableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

  

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}