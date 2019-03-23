package com.example.youtubermanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LiveFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<YoutubeChannel> listChannel = new ArrayList<>();
    private final String TITLE = "LIVE";
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
        YoutubeChannelAdapter channelAdapter = new YoutubeChannelAdapter(this.getActivity(), listChannel, R.layout.adapter_channeldetail);
        recyclerView.setAdapter(channelAdapter);

        return view;
    }

    @Override
    public String toString() {
        String title=TITLE;
        return title;
    }
}
