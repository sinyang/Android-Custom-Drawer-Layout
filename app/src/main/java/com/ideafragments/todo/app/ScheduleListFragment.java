package com.ideafragments.todo.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ScheduleListAdapter adapter;

    public ScheduleListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new ScheduleListAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public static List<ScheduleInformation> getData() {
        List<ScheduleInformation> data = new ArrayList<ScheduleInformation>();
        String[] title = {"Go To School", "Do Your Homework", "Finish This App"};
        String[] body = {"Aliquam rutrum elementum dolor ac vestibulum. Praesent ut lectus ac nisi posuere posuere et eu justo.",
                "Praesent quis posuere libero. Proin vulputate suscipit lobortis. Cras imperdiet purus eget tortor rhoncus finibus. Maecenas sed eros egestas, iaculis dui sed, mattis diam. Proin vulputate suscipit lobortis.",
                "Proin vulputate suscipit lobortis."};
        String[] date = {"May 20", "May 20", "May 20"};
        String[] time = {"12:00 P", "3:00 P", "3:50 P"};
        int[] status = {1, 0, 0};

        for (int i = 0; i < 6; i++) {
            ScheduleInformation info = new ScheduleInformation();
            info.taskTitle = title[i%3];
            info.taskBody = body[i%3];
            info.taskDate = date[i%3];
            info.taskTime = time[i%3];
            info.status = status[i%3];

            data.add(info);
        }

        return data;
    }


}


/*interface
* void onViewSlide(View view, float value);
*
* void isSliding(View view, int finish) {
*   int current = view.currentPosition;
*
*   current++;
*
*   int percentChange = current/finish;
*
*   onViewSlide(view, percentChange);
*   if (current = finish) {
*       slideDone = true;
*   }
*
*
* }
*
*
* */
