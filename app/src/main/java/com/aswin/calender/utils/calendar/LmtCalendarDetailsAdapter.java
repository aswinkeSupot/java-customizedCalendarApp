package com.aswin.calender.utils.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.aswin.calender.R;
import java.util.List;

public class LmtCalendarDetailsAdapter extends RecyclerView.Adapter<LmtCalendarDetailsAdapter.AdapterViewHolder> {

    private Context context;
    private RecyclerItemClickListener mListener;
    private List<String> dateList;
    private List<Boolean> dateSelectable;
    private boolean isDateSelected;
    private String selectedDate;

    public LmtCalendarDetailsAdapter(Context context, List<String> dateList,List<Boolean> dateSelectable,boolean isDateSelected,String selectedDate, RecyclerItemClickListener mListener) {
        this.context = context;
        this.dateList = dateList;
        this.dateSelectable = dateSelectable;
        this.isDateSelected = isDateSelected;
        this.selectedDate = selectedDate;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_lmt_calendar_details, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tvDate.setTextColor(context.getResources().getColor(R.color.white));
        if(dateList.get(position).length()==0){
            holder.tvDate.setVisibility(View.INVISIBLE);
        }else {
            try {
                if(dateSelectable.get(position)){
                   holder.tvDate.setBackgroundResource(R.drawable.rounded_gray_dark);
                   if (isDateSelected){
                       if(dateList.get(position).equals(selectedDate+"")) {
                           holder.tvDate.setBackgroundResource(R.drawable.rounded_white);
                           holder.tvDate.setTextColor(context.getResources().getColor(R.color.black));
                       }
                   }
                }else{
                    holder.tvDate.setBackgroundResource(R.drawable.rounded_trans);
                }
            } catch (Exception e) {
                holder.tvDate.setBackgroundResource(R.drawable.rounded_accent);

            }
            holder.tvDate.setText(dateList.get(position));
        }
        holder.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateSelectable.get(position)) {
                    mListener.onItemClick(dateList.get(position));
                    selectedDate = dateList.get(position);
                    isDateSelected = true;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
//        LinearLayout llMain;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
//            llMain = itemView.findViewById(R.id.llMain);
        }
    }

    public interface RecyclerItemClickListener {
        void onItemClick(String selectedDate);
    }

}
