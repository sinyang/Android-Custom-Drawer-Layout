package com.ideafragments.todo.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sam.I on 3/9/2015.
 */
public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ScheduleListViewHolder> {
    private LayoutInflater inflater;
    private List<ScheduleInformation> data = Collections.emptyList();
    Context context;

    public ScheduleListAdapter(Context context, List<ScheduleInformation> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public ScheduleListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.custom_row_schedule, viewGroup, false);
        ScheduleListViewHolder scheduleListViewHolder = new ScheduleListViewHolder(view);
        return scheduleListViewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleListViewHolder scheduleListViewHolder, int position) {
        ScheduleInformation currentItem = data.get(position);
        scheduleListViewHolder.taskTitle.setText(currentItem.taskTitle);
        scheduleListViewHolder.date.setText(currentItem.taskDate);
        scheduleListViewHolder.taskBody.setText(currentItem.taskBody);
        scheduleListViewHolder.time.setText(currentItem.taskTime);
        if (currentItem.status == 1) {
            scheduleListViewHolder.status.setText("Completed");
            scheduleListViewHolder.status.setTextColor(context.getResources().getColor(R.color.quaternaryLight));
        } else if (currentItem.status == 0) {
            scheduleListViewHolder.status.setText("Not Completed");
            scheduleListViewHolder.status.setTextColor(context.getResources().getColor(R.color.secondaryLight));
        } else {
            scheduleListViewHolder.status.setText("In Progress");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ScheduleListViewHolder extends RecyclerView.ViewHolder {

        private TextView taskTitle;
        private TextView taskBody;
        private TextView status;
        private TextView date;
        private TextView time;

        public ScheduleListViewHolder(View itemView) {
            super(itemView);

            taskTitle = (TextView) itemView.findViewById(R.id.taskTitle);
            taskBody = (TextView) itemView.findViewById(R.id.taskBody);
            status = (TextView) itemView.findViewById(R.id.status);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);


        }
    }
}


