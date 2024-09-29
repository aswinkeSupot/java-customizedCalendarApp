package com.aswin.calender;

import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.aswin.calender.databinding.ActivityMainBinding;
import com.aswin.calender.utils.utilities.CommonUtils;
import com.aswin.calender.utils.LMTBaseActivity;
import com.aswin.calender.utils.calendar.LmtCalendarFragment;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends LMTBaseActivity {
    private ActivityMainBinding binding;

    private Date dateSelected;
    private String dateStr;
    private String dateStrToApi;
    private String monthStr="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        dateStr = Calendar.getInstance().get(Calendar.DATE)+"";

        llTodayClick();

        binding.llToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llTodayClick();
            }
        });

        binding.llTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llTomorrowClick();
            }
        });

        binding.llDayAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llDayAfterClick();
            }
        });

        binding.llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        binding.imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        LmtCalendarFragment lmtCalendarFragment =new LmtCalendarFragment(monthStr, dateStr, new LmtCalendarFragment.OnDateSelected() {
            @Override
            public void onItemClick(String selectedDate, String selectedMonthPos) {
                dateStr=selectedDate;
                monthStr=selectedMonthPos;
                Calendar localCalendar = Calendar.getInstance();
                localCalendar.set(Calendar.DATE,Integer.parseInt(dateStr));
                localCalendar.add(Calendar.MONTH,Integer.parseInt(monthStr));
                System.out.println("HAHAH: "+dateStr + " " + localCalendar.get(Calendar.MONTH) + ", " + localCalendar.get(Calendar.YEAR));
                setDay(CommonUtils.dateCheck(Integer.parseInt(dateStr), localCalendar.get(Calendar.MONTH), localCalendar.get(Calendar.YEAR)));
                binding.tvDate.setText(dateStr + " " + CommonUtils.getEnglishMonthShort((localCalendar.get(Calendar.MONTH)+1)+"") + ", " + localCalendar.get(Calendar.YEAR));
                dateStrToApi=localCalendar.get(Calendar.YEAR)+"-"+formatedMonthOrDate(localCalendar.get(Calendar.MONTH)+1)+"-"+formatedMonthOrDate(localCalendar.get(Calendar.DATE));

            }
        });
        lmtCalendarFragment.show(getSupportFragmentManager(),"LmtCalendarFragment");
    }

    public void llTodayClick() {
        Calendar myCalendar = Calendar.getInstance();
        setDay(0);
        binding.tvDate.setText(myCalendar.get(Calendar.DATE) + " " + CommonUtils.getEnglishMonthShort((myCalendar.get(Calendar.MONTH)+1) + "") + ", " + myCalendar.get(Calendar.YEAR));
        dateSelected = myCalendar.getTime();
        dateStr=myCalendar.get(Calendar.DATE)+"";
        dateStrToApi=myCalendar.get(Calendar.YEAR)+"-"+formatedMonthOrDate(myCalendar.get(Calendar.MONTH)+1)+"-"+formatedMonthOrDate(myCalendar.get(Calendar.DATE));
        monthStr=(myCalendar.get(Calendar.MONTH)-Calendar.getInstance().get(Calendar.MONTH))+"";
    }

    public void llTomorrowClick() {
        Calendar myCalendar = Calendar.getInstance();
        setDay(1);
        myCalendar.add(Calendar.DATE, 1);
        binding.tvDate.setText(myCalendar.get(Calendar.DATE) + " " + CommonUtils.getEnglishMonthShort((myCalendar.get(Calendar.MONTH)+1) + "") + ", " + myCalendar.get(Calendar.YEAR));
        dateSelected = myCalendar.getTime();
        dateStr=myCalendar.get(Calendar.DATE)+"";
        dateStrToApi=myCalendar.get(Calendar.YEAR)+"-"+formatedMonthOrDate(myCalendar.get(Calendar.MONTH)+1)+"-"+formatedMonthOrDate(myCalendar.get(Calendar.DATE));
        monthStr=(myCalendar.get(Calendar.MONTH)-Calendar.getInstance().get(Calendar.MONTH))+"";
    }

    public void llDayAfterClick() {
        Calendar myCalendar = Calendar.getInstance();
        setDay(2);
        myCalendar.add(Calendar.DATE, 2);
        binding.tvDate.setText(myCalendar.get(Calendar.DATE) + " " + CommonUtils.getEnglishMonthShort((myCalendar.get(Calendar.MONTH)+1) + "") + ", " + myCalendar.get(Calendar.YEAR));
        dateSelected = myCalendar.getTime();
        dateStr=myCalendar.get(Calendar.DATE)+"";
        dateStrToApi=myCalendar.get(Calendar.YEAR)+"-"+formatedMonthOrDate(myCalendar.get(Calendar.MONTH)+1)+"-"+formatedMonthOrDate(myCalendar.get(Calendar.DATE));
        monthStr=(myCalendar.get(Calendar.MONTH)-Calendar.getInstance().get(Calendar.MONTH))+"";
    }

    private void setDay(long dateCheck) {
        binding.tvToday.setBackgroundResource(R.drawable.home_search_box_bg);
        binding.tvTomorrow.setBackgroundResource(R.drawable.home_search_box_bg);
        binding.tvDayAfter.setBackgroundResource(R.drawable.home_search_box_bg);
        switch ((int) dateCheck) {
            case 0:
                binding.tvToday.setBackgroundResource(R.drawable.home_search_box_selected_bg);
                break;
            case 1:
                binding.tvTomorrow.setBackgroundResource(R.drawable.home_search_box_selected_bg);
                break;
            case 2:
                binding.tvDayAfter.setBackgroundResource(R.drawable.home_search_box_selected_bg);
                break;
        }
    }
}