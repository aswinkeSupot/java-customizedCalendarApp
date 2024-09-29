package com.aswin.calender.utils.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aswin.calender.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LmtCalendarAdapter extends RecyclerView.Adapter<LmtCalendarAdapter.AdapterViewHolder> {

    private Context context;
    private RecyclerItemClickListener mListener;
    private List<String> monthsList;
    private String selectedDateGlobal;
    private String selectedMonthGlobal;

    public LmtCalendarAdapter(Context context, List<String> monthsList, String selectedMonth, String selectedDate, RecyclerItemClickListener mListener) {
        this.context = context;
        this.monthsList = monthsList;
        this.selectedDateGlobal = selectedDate;
        this.selectedMonthGlobal = selectedMonth;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_lmt_calendar, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tvMonth.setText(monthsList.get(position));
        List<String> dateList = new ArrayList<>();
        List<Boolean> dateSelectable = new ArrayList<>();
        dateList = getDates(position);
        dateSelectable = getDatesSelectable(position);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 7);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        LmtCalendarDetailsAdapter lmtCalendarAdapter = new LmtCalendarDetailsAdapter(context, dateList, dateSelectable, position == Integer.parseInt(selectedMonthGlobal) ? true : false, selectedDateGlobal, new LmtCalendarDetailsAdapter.RecyclerItemClickListener() {
            @Override
            public void onItemClick(String selectedDate) {
                selectedMonthGlobal = position + "";
                selectedDateGlobal = selectedDate;
                notifyDataSetChanged();
                mListener.onItemClick(selectedDate, selectedMonthGlobal + "");
            }
        });
        holder.recyclerView.setAdapter(lmtCalendarAdapter);
//        holder.llMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onItemClick(stationName.get(position), stationCode.get(position));
//            }
//        });
    }

    private List<Boolean> getDatesSelectable(int position) {
        List<Boolean> dateSelectable = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int emptySpace = calendar.get(Calendar.DAY_OF_WEEK);
        if (position == 0) {
            int firstSelectable = Calendar.getInstance().get(Calendar.DATE);
            int lastSelectable = Calendar.getInstance().getActualMaximum(Calendar.DATE);
            for (int i = 0; i < emptySpace - 1; i++) {
                dateSelectable.add(false);
            }
            for (int i = 0; i < firstSelectable - 1; i++) {
                dateSelectable.add(false);
            }
            for (int i = firstSelectable - 1; i <= lastSelectable; i++) {
                dateSelectable.add(true);
            }
        } else if (position == 1 || position == 2 || position == 3) {
            calendar.add(Calendar.MONTH, position);
            emptySpace = calendar.get(Calendar.DAY_OF_WEEK);
            for (int i = 0; i < emptySpace - 1; i++) {
                dateSelectable.add(false);
            }
            int lastSelectable = calendar.getActualMaximum(Calendar.DATE);
            for (int i = 0; i <= lastSelectable; i++) {
                dateSelectable.add(true);
            }
        } else {
            calendar.add(Calendar.MONTH, 4);
            emptySpace = calendar.get(Calendar.DAY_OF_WEEK);
            for (int i = 0; i < emptySpace - 1; i++) {
                dateSelectable.add(false);
            }
            int lastDate = calendar.getActualMaximum(Calendar.DATE);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.add(Calendar.DATE, 119);
            if(calendar.get(Calendar.MONTH)==calendar1.get(Calendar.MONTH)) {
                int lastSelectable = calendar1.get(Calendar.DATE);
                for (int i = 0; i < lastSelectable; i++) {
                    dateSelectable.add(true);
                }
                for (int i = lastSelectable; i < lastDate; i++) {
                    dateSelectable.add(false);
                }
            }else{
                for (int i = 0; i <= lastDate; i++) {
                    dateSelectable.add(false);
                }
            }
        }
        return dateSelectable;
    }

    private List<String> getDates(int pos) {
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int numDays = calendar.getActualMaximum(Calendar.DATE);
        int emptySpace = calendar.get(Calendar.DAY_OF_WEEK);
        if (pos == 0) {
            numDays = calendar.getActualMaximum(Calendar.DATE);
        } else if (pos == 1||pos==2||pos==3) {
            calendar.add(Calendar.MONTH, pos);
            numDays = calendar.getActualMaximum(Calendar.DATE);
            emptySpace = calendar.get(Calendar.DAY_OF_WEEK);
        } else {
            calendar.add(Calendar.MONTH, 4);
            numDays = calendar.getActualMaximum(Calendar.DATE);
            emptySpace = calendar.get(Calendar.DAY_OF_WEEK);
        }
        for (int i = 0; i < emptySpace - 1; i++) {
            dates.add("");
        }
        for (int i = 0; i < numDays; i++) {
            dates.add((i + 1) + "");
        }
        return dates;
    }


    @Override
    public int getItemCount() {
        return monthsList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvMonth;
        LinearLayout llMain;
        RecyclerView recyclerView;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            llMain = itemView.findViewById(R.id.llMain);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }

    public interface RecyclerItemClickListener {
        void onItemClick(String selectedDate, String selectedMonth);
    }

}
