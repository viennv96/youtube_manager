package com.example.youtubermanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.List;

public class TotalFragment extends Fragment {
    private ListView listView;
    private List<YoutubeChannel> listChannel;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.adapter_channel, container, false);

        listView = view.findViewById(R.id.listView_id);
        DBAccess db = DBAccess.getInstance(getContext());
        listChannel = db.getAllChannel();
        YoutubeChannelAdapter channelAdapter = new YoutubeChannelAdapter(this.getActivity(), listChannel);
        listView.setAdapter(channelAdapter);
        return view;
    }

    @Override
    public String toString() {
        String title="TOTAL";
        return title;
    }
}
