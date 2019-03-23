package com.example.youtubermanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LiveFragment extends Fragment {
    private ListView listView;
    private List<YoutubeChannel> listChannel = new ArrayList<>();
    private final String TITLE = "LIVE";
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.adapter_channel, container, false);

        listView = view.findViewById(R.id.listView_id);
        DBAccess db = DBAccess.getInstance(getContext());
        listChannel = db.getChannel(TITLE);
        YoutubeChannelAdapter channelAdapter = new YoutubeChannelAdapter(this.getActivity(), listChannel);
        listView.setAdapter(channelAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra("urlChannel", listChannel.get(position).getUrlChannel());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public String toString() {
        String title=TITLE;
        return title;
    }
}
