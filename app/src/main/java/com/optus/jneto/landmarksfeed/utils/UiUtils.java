package com.optus.jneto.landmarksfeed.utils;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by jneto on 10/6/17.
 */

public class UiUtils {

    public static void setRefreshing(final SwipeRefreshLayout swipeRefreshLayout, final boolean isRefreshing)
    {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(isRefreshing);
                }
            });
        }
    }
}
