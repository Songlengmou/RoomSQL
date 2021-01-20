package com.anningtex.roomsql.pop;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/**
 * @author Song
 */
public class PopupWindowUtils extends PopupWindow {
    public PopupWindowUtils(Context context) {
        super(context, null);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }
}
