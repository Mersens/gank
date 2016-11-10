package com.mersens.gank.utils;

import android.content.res.Resources;
import android.view.View;

public interface ColorUiInterface {
    View getView();

    void setTheme(Resources.Theme themeId);
}
