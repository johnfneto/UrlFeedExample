package com.optus.jneto.landmarksfeed.view_models;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.optus.jneto.landmarksfeed.R;
import com.optus.jneto.landmarksfeed.adapters.SpinnerAdapter;
import com.optus.jneto.landmarksfeed.controllers.NetworkController;
import com.optus.jneto.landmarksfeed.interfaces.TaskViewManager;
import com.optus.jneto.landmarksfeed.models.Item;
import com.optus.jneto.landmarksfeed.utils.UiUtils;
import com.optus.jneto.landmarksfeed.views.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jneto on 10/6/17.
 */

public class ViewModel implements TaskViewManager {
    private static final String TAG = "ViewModel";

    private MainActivity mActivity;
    private NetworkController mNetworkController;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private SpinnerAdapter mSpinnerAdapter;
    private Spinner mSpinner;
    private TextView mCarTextView;
    private TextView mTrainTextView;

    private List<Item> mItemsList = new ArrayList<>();

    public ViewModel(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void registerNetworkController() {
        mNetworkController = NetworkController.getInstance();
        mNetworkController.initialize(mActivity, mActivity.mDelegate);

        if (mItemsList.size() == 0) {
            UiUtils.setRefreshing(mSwipeRefreshLayout, true);
            mNetworkController.loadFeed();
        }
    }


    @Override
    public int getSpinnerSelection() {
        return mSpinner.getSelectedItemPosition();
    }

    @Override
    public void setSpinnerSelection(final int spinnerSelection) {
        mSpinner.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSpinner.setSelection(spinnerSelection, false);
            }
        }, 100);
    }

    @Override
    public void registerAdapter(Spinner spinner) {
        mSpinner = spinner;
        mSpinnerAdapter = new SpinnerAdapter(mActivity, android.R.layout.simple_spinner_item, mItemsList);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Item item = mSpinnerAdapter.getItem(position);
                if (item != null) {
                    if (item.getFromCentral().getCar() != null) {
                        mCarTextView.setText(mActivity.getString(R.string.time_by_car, item
                                .getFromCentral().getCar()));
                    } else {
                        mCarTextView.setText("");
                    }
                    if (item.getFromCentral().getTrain() != null) {
                        mTrainTextView.setText(mActivity.getString(R.string.time_by_train, item
                                .getFromCentral().getTrain()));
                    } else {
                        mTrainTextView.setText("");
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }

    @Override
    public void registerButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = (Item) mSpinner.getSelectedItem();

                Toast.makeText(mActivity, "Lng: " + item.getLocation().getLongitude() + "\nLat: "
                                + item.getLocation().getLatitude(), Toast.LENGTH_SHORT).show();

                String uri = "http://maps.google.com/maps?daddr=" + item.getLocation().getLatitude() + "," +
                        item.getLocation().getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                    mActivity.startActivity(intent);
                } else {
                    Toast.makeText(mActivity, mActivity.getString(R.string.no_maps), Toast
                            .LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void registerCarTextView(TextView carTextView) {
        mCarTextView = carTextView;
    }

    @Override
    public void registerTrainTextView(TextView trainTextView) {
        mTrainTextView = trainTextView;
    }

    @Override
    public void registerSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        mSwipeRefreshLayout = swipeRefreshLayout;
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNetworkController.loadFeed();
            }

        });
    }

    @Override
    public void notifyAdapter() {
        mSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void upDateList(List<Item> itemsList) {
        mItemsList.clear();
        mItemsList.addAll(itemsList);
    }

    @Override
    public void closeSwipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

}