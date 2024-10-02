package com.aswin.calender.utils.calendar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aswin.calender.R;
import com.aswin.calender.databinding.FragmentLmtCalendarBinding;
import com.aswin.calender.utils.LMTBaseBottomSheet;
import com.aswin.calender.utils.utilities.CommonUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LmtCalendarFragment extends LMTBaseBottomSheet {

    FragmentLmtCalendarBinding binding;
    private String date;
    private String monthPos;

    private OnDateSelected onDateSelected;
    private Handler handler;

    public LmtCalendarFragment(String monthPos,String date,OnDateSelected onDateSelected){
        this.date = date;

        this.monthPos = monthPos;
        this.onDateSelected = onDateSelected;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                BottomSheetBehavior.from(bottomSheet).setHideable(false);
                BottomSheetBehavior.from(bottomSheet).setDraggable(false);
            }
        });
        return dialog;
    }


    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lmt_calendar, container, false);
        handler=new Handler();
        setCalendarAdapter();

        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return binding.getRoot();
    }

    private int setMonth(int month) {
        int updatedMonth = 0;
        if(month <= 12) {
            updatedMonth = month;
        } else{
            updatedMonth = month - 12;
        }
        return updatedMonth;
    }

    private int setYear(int currentMonth) {
        int year = 0;
        if(currentMonth <= 12 ) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        } else {
            year = Calendar.getInstance().get(Calendar.YEAR) + 1;
        }

        return year;
    }

    private boolean isBefore(int lastDayMonth, int lastDayYear, int currentMonth, int currentYear){
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        // Set the first date (month is 0-based, so subtract 1)
        date1.set(lastDayYear, lastDayMonth - 1, 1);

        // Set the second date (month is 0-based, so subtract 1)
        date2.set(currentYear, currentMonth - 1, 1);

        // Compare the two dates
        return date1.before(date2);
    }

    private void setCalendarAdapter() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 119);  // Adding 119 days to the current day
        int lastDay = calendar.get(Calendar.DAY_OF_MONTH);
        int lastMonth = calendar.get(Calendar.MONTH) + 1;  // Month is 0-based, so add 1
        int lastYear = calendar.get(Calendar.YEAR);
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM", Locale.ENGLISH);
        // Format the date from the calendar instance
        String uptoInfo = dateFormat.format(calendar.getTime());
        binding.tvBookingUptoInfo.setText("Bookings open for travel upto " + uptoInfo);

        List<String> monthsList = new ArrayList<>();

        int currentMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1);

        String month1 = (setMonth(currentMonth)) + "";
        String year1 = setYear(currentMonth) + "";

        String month2 = (setMonth(currentMonth + 1)) + "";
        String year2 = setYear(currentMonth + 1) + "";

        String month3 = (setMonth(currentMonth + 2)) + "";
        String year3 = setYear(currentMonth + 2) + "";

        String month4 = (setMonth(currentMonth + 3)) + "";
        String year4 = setYear(currentMonth + 3) + "";

        String month5 = (setMonth(currentMonth + 4)) + "";
        String year5 = setYear(currentMonth + 4) + "";

        monthsList.add(CommonUtils.getEnglishMonth(month1) + " " + year1);
        monthsList.add(CommonUtils.getEnglishMonth(month2) + " " + year2);
        monthsList.add(CommonUtils.getEnglishMonth(month3) + " " + year3);
        monthsList.add(CommonUtils.getEnglishMonth(month4) + " " + year4);
        if(!isBefore(lastMonth,lastYear,Integer.parseInt(month5),Integer.parseInt(year5))){
            monthsList.add(CommonUtils.getEnglishMonth(month5) + " " + year5);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        LmtCalendarAdapter lmtCalendarAdapter = new LmtCalendarAdapter(getContext(), monthsList,monthPos,date, new LmtCalendarAdapter.RecyclerItemClickListener() {
            @Override
            public void onItemClick(String selectedDate, String selectedMonth) {
                onDateSelected.onItemClick(selectedDate,selectedMonth);
                try {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    },250);
                } catch (Exception e) {
                }
            }
        });
        binding.recyclerView.setItemViewCacheSize(5);
        binding.recyclerView.setAdapter(lmtCalendarAdapter);
    }

    public interface OnDateSelected{
        void onItemClick(String selectedDate, String selectedMonthPos);
    }
}
