package com.o3sa.mobipugapp.uicomponents;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.o3sa.mobipugapp.R;
import com.o3sa.mobipugapp.dumpdata.DumpData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kiran on 19-09-2018.
 */

public class RecylerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private final int HEADER_ITEM = 2;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    Activity activity;
    ArrayList<DumpData> datalist;  //ArrayList<HashMap<String, String>> datalist;
    String formtype;
    int list_itemview;

    public RecylerviewAdapter(Activity activity2, ArrayList<DumpData> data_list, String string, RecyclerView recyclerView, int recylerviewlistitem) { //ArrayList<HashMap<String, String>> data_list

        this.datalist = data_list;
        this.activity = activity2;
        formtype = string;
        list_itemview=recylerviewlistitem;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {

        return datalist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(list_itemview, parent, false);
            return new ViewHolder(view,formtype,activity);

        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = null;
            view = LayoutInflater.from(activity).inflate(R.layout.item_loading, parent, false);


            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        if (holder1 instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) holder1;
            holder.assign_data(datalist,position,formtype);


        } else if (holder1 instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder1;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return datalist == null ? 0 : datalist.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar_new);
        }
    }





}