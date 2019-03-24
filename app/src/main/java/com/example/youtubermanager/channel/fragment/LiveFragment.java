package com.example.youtubermanager.channel.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.youtubermanager.sqlitedb.DBAccess;
import com.example.youtubermanager.channel.YoutubeChannelAdapter;
import com.example.youtubermanager.R;
import com.example.youtubermanager.entity.YoutubeChannel;
import com.example.youtubermanager.youtubeparser.ChannelParser;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LiveFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<YoutubeChannel> listChannel = new ArrayList<>();
    private final String TITLE = "LIVE";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    YoutubeChannelAdapter channelAdapter;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.adapter_channel, container, false);

        DBAccess db = DBAccess.getInstance(getContext());
        listChannel = db.getChannel(TITLE);
        recyclerView = view.findViewById(R.id.listView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        channelAdapter = new YoutubeChannelAdapter(this.getActivity(), listChannel, R.layout.adapter_channeldetail);
        recyclerView.setAdapter(channelAdapter);
        channelAdapter.setOnItemClickListener(new YoutubeChannelAdapter.OnItemClickListener() {
            @Override
            public void onClickOpenItem(int position) {
                channelAdapter.openItem(position);
            }

            @Override
            public void onClickRemoveItem(int position) {
                DBAccess dbAccess = DBAccess.getInstance(getContext());
                dbAccess.deleteChannel(listChannel.get(position).getUrlChannel());
                listChannel.remove(position);
                channelAdapter.notifyDataSetChanged();
            }
        });

        mSwipeRefreshLayout = view.findViewById(R.id.container);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.canChildScrollUp();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listChannel.clear();
                channelAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(true);
                reloadChannel(TITLE);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void reloadChannel(String status) {
        DBAccess db2 = DBAccess.getInstance(getContext());
        ArrayList<String> urls = db2.getAllUrlChannel(status);
        for(String item:urls){
            ChannelParser parser = new ChannelParser();
            String url = parser.generateRequest(item);
            parser.execute(url);
            parser.onFinish(new ChannelParser.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(YoutubeChannel channel) {
                    DBAccess db3 = DBAccess.getInstance(getContext());
                    db3.updateChannel(channel);
                    channelAdapter.addChannelToList(channel);
                    channelAdapter.notifyDataSetChanged();
                }
                @Override
                public void onError() {
                    Toast.makeText(getActivity(), "Error while loading data. Please retry", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public String toString() {
        String title= TITLE;
        return title;
    }
}
