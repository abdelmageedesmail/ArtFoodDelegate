package com.art4muslimt.artfooddelegate.ui.home;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.OrderResponse;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.ui.registeration.VehiclesSpinnerAdapter;
import com.art4muslimt.artfooddelegate.utils.HelperClass;
import com.art4muslimt.artfooddelegate.utils.Observer;
import com.art4muslimt.artfooddelegate.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment implements HomeView {

    @BindView(R.id.tvDisable)
    TextView tvDisable;
    @BindView(R.id.frDisable)
    FrameLayout frDisable;
    @BindView(R.id.tvEnable)
    TextView tvEnable;
    @BindView(R.id.frEnable)
    FrameLayout frEnable;
    @BindView(R.id.etFrom)
    EditText etFrom;
    @BindView(R.id.etToTime)
    EditText etToTime;
    @BindView(R.id.btnSetTime)
    Button btnSetTime;
    @BindView(R.id.tvFromTime)
    TextView tvFromTime;
    @BindView(R.id.tvToTime)
    TextView tvToTime;
    @BindView(R.id.frTime)
    FrameLayout frTime;
    Observer observer;
    @BindView(R.id.ivCloseTime)
    ImageView ivCloseTime;
    @BindView(R.id.spVechilies)
    Spinner spVechilies;
    @BindView(R.id.tvVehicleType)
    TextView tvVehicleType;
    @BindView(R.id.ivCloseVehicle)
    ImageView ivCloseVehicle;
    @BindView(R.id.frVehicle)
    FrameLayout frVehicle;
    @BindView(R.id.liMap)
    LinearLayout liMap;
    private String currentTime;
    private String currentDate;
    private String vehicleName;
    @Inject
    HomePresenter presenter;
    private String vehicleId;
    private String min;
    private String fromTime, toTime;
    private String selHour;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        ((ArtFood) getActivity().getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        currentTime = sdf.format(new Date());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        currentDate = df.format(new Date());
        presenter.getVicheles(getActivity());
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        observer = (Observer) context;
    }

    private void setTime(EditText etTime) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (selectedMinute < 10) {
                    min = "0" + selectedMinute;
                } else {
                    min = "" + selectedMinute;
                }
                if (selectedHour < 10) {
                    selHour = "0" + selectedHour;
                } else {
                    selHour = "" + selectedHour;
                }
                etTime.setText(selHour + ":" + min);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.frDisable, R.id.frEnable, R.id.etFrom, R.id.etToTime, R.id.btnSetTime, R.id.ivCloseTime, R.id.liMap, R.id.btnSetVehicle, R.id.ivCloseVehicle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.frDisable:
                frEnable.setBackgroundResource(0);
                tvEnable.setTextColor(Color.BLACK);
                frDisable.setBackgroundResource(R.drawable.shape_gold);
                tvDisable.setTextColor(Color.WHITE);
                break;
            case R.id.frEnable:
                frDisable.setBackgroundResource(0);
                tvDisable.setTextColor(Color.BLACK);
                frEnable.setBackgroundResource(R.drawable.shape_gold);
                tvEnable.setTextColor(Color.WHITE);
                break;
            case R.id.etFrom:
                setTime(etFrom);
                break;
            case R.id.etToTime:
                setTime(etToTime);
                break;
            case R.id.btnSetTime:
                if (etFrom.getText().toString().isEmpty()) {
                    etFrom.setError(getString(R.string.empty));
                } else if (etToTime.getText().toString().isEmpty()) {
                    etToTime.setError(getString(R.string.empty));
                } else {
                    fromTime = etFrom.getText().toString() + ":00";
                    toTime = etToTime.getText().toString() + ":00";
                    if (LocalTime.parse(fromTime)
                            .isAfter(LocalTime.parse(toTime))) {
                        Toast.makeText(getActivity(), getString(R.string.errorDateFormatFromTime), Toast.LENGTH_SHORT).show();
                    } else {
                        frTime.setVisibility(View.VISIBLE);
                        tvFromTime.setText(etFrom.getText().toString());
                        tvToTime.setText(etToTime.getText().toString());

                    }
                }
                break;
            case R.id.btnSetVehicle:
                frVehicle.setVisibility(View.VISIBLE);
                tvVehicleType.setText(vehicleName);

                break;

            case R.id.ivCloseTime:
                frTime.setVisibility(View.GONE);
                etFrom.setText("");
                etToTime.setText("");
            case R.id.ivCloseVehicle:
                frVehicle.setVisibility(View.GONE);
                tvVehicleType.setText("");
                break;
            case R.id.liMap:
                if (frTime.getVisibility() == View.VISIBLE && frVehicle.getVisibility() == View.VISIBLE){
                    observer.changeView(getString(R.string.home));
                    Fragment mapFragment = new MapFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("fromTime", etFrom.getText().toString());
                    bundle.putString("toTime", etToTime.getText().toString());
                    bundle.putString("toTimeHours", toTime);
                    bundle.putString("vehicleId", vehicleId);
                    mapFragment.setArguments(bundle);
                    HelperClass.replaceFragment(getActivity().getSupportFragmentManager().beginTransaction(), mapFragment);
                }else {
                    Toast.makeText(getActivity(), getString(R.string.pleaseSetTimeAndVechile), Toast.LENGTH_SHORT).show();
                }

        }
    }

    @Override
    public void getVechilies(List<VechiliesResponse.DataEntity> data) {
        spVechilies.setAdapter(new VehiclesSpinnerAdapter(getActivity(), data));
        spVechilies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicleId = data.get(position).getId();
                if (Utils.getLang(getActivity()).equals("ar")) {
                    vehicleName = data.get(position).getName();
                } else {
                    vehicleName = data.get(position).getNameen();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void isUpdated(boolean b) {

    }

    @Override
    public void getWaitingOrders(List<OrderResponse.DataEntity> data) {

    }

    @Override
    public void changeStatus(boolean b, String accept) {

    }
}
