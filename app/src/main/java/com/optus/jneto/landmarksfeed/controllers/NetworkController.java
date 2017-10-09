package com.optus.jneto.landmarksfeed.controllers;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.optus.jneto.landmarksfeed.R;
import com.optus.jneto.landmarksfeed.interfaces.TaskViewManager;
import com.optus.jneto.landmarksfeed.models.Item;
import com.optus.jneto.landmarksfeed.utils.ConnectivityInfo;
import com.optus.jneto.landmarksfeed.views.MainActivity;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jneto on 10/6/17.
 */

public class NetworkController {
    protected static final String TAG = "NetworkController";

    private static NetworkController mNetworkController;
    private static MainActivity mActivity;
    private static TaskViewManager mDelegate;


    public static NetworkController getInstance() {
        if (mNetworkController == null) {
            mNetworkController = new NetworkController(); }
        return mNetworkController;
    }

    public static void initialize(MainActivity ctx, TaskViewManager delegate) {
        mActivity = ctx;
        mDelegate = delegate;
    }

    public static void loadFeed() {
        if (ConnectivityInfo.isConnectedToInternet(mActivity)) {
            new LoadFeedTask().execute();
        }
        else {
            mDelegate.closeSwipeRefresh();
        }
    }

    private static class LoadFeedTask extends AsyncTask<Void, Integer, List<Item>> {
        protected List<Item> doInBackground(Void... params) {

            Item items;
            List<Item> itemsList = new ArrayList<>();
            String url = mActivity.getString(R.string.url);

            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response responses;

                try {
                    responses = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

                Gson gson = new Gson();
                JSONArray jsonArray = new JSONArray(responses.body().string());

                for(int i=0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    items = gson.fromJson(obj.toString(), Item.class);
                    itemsList.add(items);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return itemsList;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(List<Item> itemsList) {
            if (itemsList == null) {
                Log.d(TAG, mActivity.getString(R.string.server_error));
                Toast.makeText(mActivity, mActivity.getString(R.string.server_error), Toast
                        .LENGTH_LONG).show();
            } else {
                mDelegate.upDateList(itemsList);
                mDelegate.notifyAdapter();
            }
            mDelegate.closeSwipeRefresh();
        }
    }
}

