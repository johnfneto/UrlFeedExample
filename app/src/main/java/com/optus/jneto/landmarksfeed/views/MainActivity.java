package com.optus.jneto.landmarksfeed.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.optus.jneto.landmarksfeed.R;
import com.optus.jneto.landmarksfeed.interfaces.TaskViewManager;
import com.optus.jneto.landmarksfeed.view_models.ViewModel;

/**
 * Created by jneto on 10/6/17.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SPINNER_SELECTION = "spinner_selection";

    public TaskViewManager mDelegate;

    private int mSpinnerSelection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Spinner spinner;
        Button button;
        TextView carTextView;
        TextView trainTextView;
        SwipeRefreshLayout swipeRefreshLayout;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);
        carTextView = (TextView) findViewById(R.id.carTextView);
        trainTextView = (TextView) findViewById(R.id.trainTextView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        mDelegate = new ViewModel(MainActivity.this);
        mDelegate.registerAdapter(spinner);
        mDelegate.registerButton(button);
        mDelegate.registerCarTextView(carTextView);
        mDelegate.registerTrainTextView(trainTextView);
        mDelegate.registerSwipeRefreshLayout(swipeRefreshLayout);

        mDelegate.registerNetworkController();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mSpinnerSelection = mDelegate.getSpinnerSelection();
        outState.putInt(SPINNER_SELECTION, mSpinnerSelection);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSpinnerSelection = savedInstanceState.getInt(SPINNER_SELECTION);
        mDelegate.setSpinnerSelection(mSpinnerSelection);
    }
}
