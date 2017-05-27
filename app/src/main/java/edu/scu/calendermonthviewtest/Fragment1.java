package edu.scu.calendermonthviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static edu.scu.calendermonthviewtest.R.id.bookings_listview;
import static edu.scu.calendermonthviewtest.R.id.editText;

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

        View v = inflator.inflate(R.layout.frag1, container, false);

        final EditText text3 = (EditText) v.findViewById(editText);
        final ArrayList<String> mobileArray = new ArrayList<>();
      //  mobileArray.add("Event1 : At SCU at 7 am");
      //  mobileArray.add("Events2 : At Bannan Center");
      //  mobileArray.add("Events3 : At Library");
      //  final String[] mobileArray = {"Event1 : At SCU", "Events2 : At Bannan Center", "Event3 : At library"};

        final ListView bookingsListView = (ListView) v.findViewById(bookings_listview);
        final Button showPreviousMonthBut = (Button) v.findViewById(R.id.prev_button);
        final Button showNextMonthBut = (Button) v.findViewById(R.id.next_button);


        final Button showCalendarWithAnimationBut = (Button) v.findViewById(R.id.expose_animation);

        final ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mobileArray);
        bookingsListView.setAdapter(adapter);

        compactCalendarView = (CompactCalendarView) v.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                                            public void onDayClick(Date dateClicked) {
                                                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                                                Log.d(TAG, "inside onclick " + dateFormatForMonth.format(dateClicked));
                                                text3.setText(dateFormatForMonth.format(dateClicked));
                                                if(dateFormatForDisplaying.format(dateClicked).equals("28-5-2017 12:00:00 AM")) {
                                                    mobileArray.clear();
                                                    mobileArray.add(0,"Fitness event near gym");
                                                    mobileArray.add(1,"Movie opposite to library");
                                                    mobileArray.add(2,"Concert near Benson");
                                                } else if(dateFormatForDisplaying.format(dateClicked).equals("30-5-2017 12:00:00 AM")){
                                                    mobileArray.clear();
                                                    mobileArray.add(0,"My event");
                                                    mobileArray.add(1,"My event2");
                                                    mobileArray.add(2,"My event3");
                                                } else {
                                                    mobileArray.clear();
                                                }
                                               // List<Event> bookingsFromMap =
                                            //    mobileArray.set(1,"EventA : Pool");
                                                adapter.notifyDataSetChanged();
                                            }
            public void onMonthScroll(Date firstDayOfNewMonth) {
                text3.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        bookingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),rsvpActivity.class);
                intent.putExtra("EventDetails",bookingsListView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
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
}
