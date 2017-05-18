package edu.scu.calendermonthviewtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by lakshmimanasavelaga on 5/12/17.
 */

public class Fragment1 extends Fragment {
    private CompactCalendarView compactCalendarView;
  //  private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private ActionBar toolbar;
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private boolean shouldShow = false;

    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View v = inflator.inflate(R.layout.frag1,container,false);
        final List<String> mutableBookings = new ArrayList<>();
        mutableBookings.add("Hello");

        final ListView bookingsListView = (ListView) v.findViewById(R.id.bookings_listview);
        final Button showPreviousMonthBut = (Button) v.findViewById(R.id.prev_button);
        final Button showNextMonthBut = (Button) v.findViewById(R.id.next_button);
        final Button slideCalendarBut = (Button) v.findViewById(R.id.slide_calendar);
        final Button showCalendarWithAnimationBut = (Button) v.findViewById(R.id.expose_animation);

    //    final ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mutableBookings);
    //    bookingsListView.setAdapter(adapter);

        compactCalendarView = (CompactCalendarView) v.findViewById(R.id.compactcalendar_view);
       // compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

      //  toolbar = ((ActionBarActivity) getActivity()).getSupportActionBar();
      //  toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
     /*   compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                                            @Override
                                            public void onDayClick(Date dateClicked) {
                                                toolbar.setTitle(dateFormatForMonth.format(dateClicked));
                                                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                                                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                                                if (bookingsFromMap != null) {
                                                    Log.d(TAG, bookingsFromMap.toString());
                                                    mutableBookings.clear();
                                                    for (Event booking : bookingsFromMap) {
                                                        mutableBookings.add((String) booking.getData());
                                                    }
                           //                         adapter.notifyDataSetChanged();
                                                }

                                            }

                                            @Override
                                            public void onMonthScroll(Date firstDayOfNewMonth) {
                                                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
                                            }

                                        }); */
            //loadEvents();
//        loadEventsForYear(2017);
       // compactCalendarView.invalidate();

        //logEventsByMonth(compactCalendarView);



        showPreviousMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showPreviousMonth();
            }
        });

        showNextMonthBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showNextMonth();
            }
        });

    //    final View.OnClickListener showCalendarOnClickLis = getCalendarShowLis();
    //    slideCalendarBut.setOnClickListener(showCalendarOnClickLis);

        final View.OnClickListener exposeCalendarListener = getCalendarExposeLis();
        showCalendarWithAnimationBut.setOnClickListener(exposeCalendarListener);

        return v;


    }
    private View.OnClickListener getCalendarExposeLis() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compactCalendarView.isAnimating()) {
                    if (shouldShow) {
                        compactCalendarView.showCalendarWithAnimation();
                    } else {
                        compactCalendarView.hideCalendarWithAnimation();
                    }
                    shouldShow = !shouldShow;
                }
            }
        };
    }

    private View.OnClickListener getCalendarShowLis() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compactCalendarView.isAnimating()) {
                    if (shouldShow) {
                        compactCalendarView.showCalendar();
                    } else {
                        compactCalendarView.hideCalendar();
                    }
                    shouldShow = !shouldShow;
                }
            }
        };
    }

    @NonNull

    private void loadEvents() {
        addEvents(-1, -1);
        addEvents(Calendar.DECEMBER, -1);
        addEvents(Calendar.AUGUST, -1);
    }

    private void loadEventsForYear(int year) {
        addEvents(Calendar.DECEMBER, year);
        addEvents(Calendar.AUGUST, year);
    }

    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        currentCalender.set(Calendar.MONTH, Calendar.AUGUST);
        List<String> dates = new ArrayList<>();
        for (Event e : compactCalendarView.getEventsForMonth(new Date())) {
            dates.add(dateFormatForDisplaying.format(e.getTimeInMillis()));
        }
        Log.d(TAG, "Events for Aug with simple date formatter: " + dates);
        Log.d(TAG, "Events for Aug month using default local and timezone: " + compactCalendarView.getEventsForMonth(currentCalender.getTime()));
    }
    private void addEvents(int month, int year) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        for (int i = 0; i < 6; i++) {
            currentCalender.setTime(firstDayOfMonth);
            if (month > -1) {
                currentCalender.set(Calendar.MONTH, month);
            }
            if (year > -1) {
                currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
                currentCalender.set(Calendar.YEAR, year);
            }
            currentCalender.add(Calendar.DATE, i);
            setToMidnight(currentCalender);
            long timeInMillis = currentCalender.getTimeInMillis();

            List<Event> events = getEvents(timeInMillis, i);

            compactCalendarView.addEvents(events);
        }
    }

    private List<Event> getEvents(long timeInMillis, int day) {
        if (day < 2) {
            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)));
        } else if ( day > 2 && day <= 4) {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)));
        } else {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis) ),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 3 at " + new Date(timeInMillis)));
        }
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
