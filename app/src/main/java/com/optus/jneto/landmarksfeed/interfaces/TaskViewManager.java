package com.optus.jneto.landmarksfeed.interfaces;

import android.support.v4.widget.SwipeRefreshLayout;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.optus.jneto.landmarksfeed.models.Item;

import java.util.List;

/**
 * Created by jneto on 10/6/17.
 */

public interface TaskViewManager {
    int getSpinnerSelection();
    void setSpinnerSelection(int spinnerSelection);

    //Through this interface the event logic is
    //passed off to the ViewModel.
    void registerAdapter(Spinner spinner);
    void registerButton(Button button);
    void registerCarTextView(TextView carTextView);
    void registerTrainTextView(TextView trainTextView);
    void registerSwipeRefreshLayout(SwipeRefreshLayout mSwipeRefreshLayout);

    void registerNetworkController();

    void notifyAdapter();

    void upDateList(List<Item> itemsList);
    void closeSwipeRefresh();
}
