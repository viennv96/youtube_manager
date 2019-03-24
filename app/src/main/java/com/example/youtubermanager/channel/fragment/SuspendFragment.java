package com.example.youtubermanager.channel.fragment;

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
import android.widget.Toast;

import com.example.youtubermanager.sqlitedb.DBAccess;
import com.example.youtubermanager.channel.YoutubeChannelAdapter;
import com.example.youtubermanager.R;
import com.example.youtubermanager.entity.YoutubeChannel;

import java.util.ArrayList;

public class SuspendFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<YoutubeChannel> listChannel = new ArrayList<>();
    private final String TITLE = "DIE";
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
        final YoutubeChannelAdapter channelAdapter = new YoutubeChannelAdapter(this.getActivity(), listChannel, R.layout.adapter_channeldetail);
        recyclerView.setAdapter(channelAdapter);
        channelAdapter.setOnItemClickListener(new YoutubeChannelAdapter.OnItemClickListener() {
            @Override
            public void onClickOpenItem(int position) {
                Toast.makeText(getContext(), "Can not show videos of suspend channel!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickRemoveItem(int position) {
                DBAccess dbAccess = DBAccess.getInstance(getContext());
                dbAccess.deleteChannel(listChannel.get(position).getUrlChannel());
                listChannel.remove(position);
                channelAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public String toString() {
        String title="SUSPEND";
        return title;
    }
}
