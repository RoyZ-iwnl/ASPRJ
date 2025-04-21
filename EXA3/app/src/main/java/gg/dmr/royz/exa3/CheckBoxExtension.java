package gg.dmr.royz.exa3;

import android.widget.CheckBox;

public class CheckBoxExtension {

    // 为CheckBox设置顶部图标
    public static void setDrawableTopResource(CheckBox checkBox, int resourceId) {
        checkBox.setCompoundDrawablesWithIntrinsicBounds(0, resourceId, 0, 0);
    }
}