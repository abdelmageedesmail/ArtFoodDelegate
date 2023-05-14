package com.art4muslimt.artfooddelegate.ui.registeration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.CityResponse;
import com.art4muslimt.artfooddelegate.internet.model.NationalitiesResponse;
import com.art4muslimt.artfooddelegate.utils.Utils;

import java.util.List;

/**
 * Created by abdelmageed on 20/09/17.
 */

public class NationalitiesSpinnerAdapter extends BaseAdapter {

    Context c;
    List<NationalitiesResponse.DataEntity> objects;

    public NationalitiesSpinnerAdapter(Context context, List<NationalitiesResponse.DataEntity> objects) {
        super();
        this.c = context;
        this.objects = objects;
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        NationalitiesResponse.DataEntity cur_obj = objects.get(position);
        //  LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = LayoutInflater.from(c).inflate(R.layout.custome_spinner, parent, false);
        TextView label = (TextView) row.findViewById(R.id.name);
        if (Utils.getLang(c).equals("ar")) {
            label.setText(cur_obj.getName());
        } else {
            label.setText(cur_obj.getNameen());
        }
        return row;
    }
}