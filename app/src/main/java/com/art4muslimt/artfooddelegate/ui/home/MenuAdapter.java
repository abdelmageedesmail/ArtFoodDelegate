package com.art4muslimt.artfooddelegate.ui.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.NavDrawerItem;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;

import java.util.ArrayList;
import java.util.List;

import nl.psdcompany.duonavigationdrawer.views.DuoOptionView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by PSD on 13-04-17.
 */

public class MenuAdapter extends BaseAdapter {
    private ArrayList<String> mOptions = new ArrayList<>();
    private ArrayList<DuoOptionView> mOptionViews = new ArrayList<>();
    private static String[] titles = null;
    Context context;
    int[] pic;
    String isTransporter;

    public MenuAdapter(Context context, ArrayList<String> options) {
        mOptions = options;
        this.context = context;
        this.isTransporter = isTransporter;
    }


    @Override
    public int getCount() {
        return mOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return mOptions.get(position);
    }

    public void setViewSelected(int position, boolean selected) {

        // Looping through the options in the menu
        // Selecting the chosen option
        for (int i = 0; i < mOptionViews.size(); i++) {
            if (i == position) {
                mOptionViews.get(i).setSelected(selected);
            } else {
                mOptionViews.get(i).setSelected(!selected);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String option = mOptions.get(position);

        pic = new int[]{R.drawable.ic_home, R.drawable.ic_meals, R.drawable.ic_menu_cat, R.drawable.ic_terms, R.drawable.ic_about_app, R.drawable.contact_us};


        SharedPreferences sh = context.getSharedPreferences("userData", MODE_PRIVATE);
        String isVisit = sh.getString("isVisit", "null");
        PrefrencesStorage localeShared = new PrefrencesStorage(context);
        String userType = localeShared.getKey("type_user");
        final DuoOptionView optionView;
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_row, parent, false);
        if (convertView == null) {
            TextView title = (TextView) convertView.findViewById(R.id.textRow);
            ImageView im = (ImageView) convertView.findViewById(R.id.imageView);
            title.setText(mOptions.get(position));
            im.setImageResource(pic[position]);

            //   optionView = new DuoOptionView(parent.getContext());
        } else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_row, parent, false);
            TextView title = (TextView) convertView.findViewById(R.id.textRow);
            ImageView im = (ImageView) convertView.findViewById(R.id.imageView);

            title.setText(mOptions.get(position));
            im.setImageResource(pic[position]);
            //  RecyclerView drawerList=(RecyclerView) v.findViewById(R.id.drawerList);
            //drawerList.setAdapter(new NavigationDrawerAdapter(parent.getContext(),getData()));
//            optionView = (DuoOptionView) convertView;
        }

        // Using the DuoOptionView's default selectors
        //   optionView.bind(option, null, null);

        // Adding the views to an array list to handle view selection
        //  mOptionViews.add(optionView);

        return convertView;
    }


    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            data.add(navItem);
        }
        return data;
    }
}

