package com.art4muslimt.artfooddelegate.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.notifications.FCMRegistrationService;
import com.art4muslimt.artfooddelegate.ui.bills.BillsActivity;
import com.art4muslimt.artfooddelegate.ui.contactUs.ContactUs;
import com.art4muslimt.artfooddelegate.ui.login.LoginActivity;
import com.art4muslimt.artfooddelegate.ui.orders.OrdersFragment;
import com.art4muslimt.artfooddelegate.ui.pages.PagesActivity;
import com.art4muslimt.artfooddelegate.ui.profile.ProfileFragment;
import com.art4muslimt.artfooddelegate.utils.HelperClass;
import com.art4muslimt.artfooddelegate.utils.Observer;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class HomeActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener, Observer {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.drawer)
    DuoDrawerLayout drawer;
    @BindView(R.id.tvDisable)
    TextView tvDisable;
    @BindView(R.id.frDisable)
    FrameLayout frDisable;
    @BindView(R.id.tvEnable)
    TextView tvEnable;
    @BindView(R.id.frEnable)
    FrameLayout frEnable;
    @BindView(R.id.liStatus)
    LinearLayout liStatus;
    public static AppCompatActivity appCompatActivity;

    private MenuAdapter mMenuAdapter;
    PrefrencesStorage storage;
    private ViewHolder mViewHolder;
    private ArrayList<String> mTitles;
    private String currentDate;
    private String currentTime;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        appCompatActivity = this;
        ButterKnife.bind(this);
        liStatus.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.homeAndStatus));
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.nav_raw)));
        startService(new Intent(this, FCMRegistrationService.class));

        storage = new PrefrencesStorage(this);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = df.format(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        currentTime = sdf.format(new Date());

        Log.e("currentDate", "" + storage.getKey("lastTime") + "..." + currentTime);
        if (storage.getKey("currentDate").equals(currentDate) &&
                ((LocalTime.parse(storage.getKey("lastTime")).isAfter(LocalTime.parse(currentTime))))) {
            // Date matches. User has already Launched the app once today. So do nothing.
            HelperClass.replaceFragmentWithoutBack(getSupportFragmentManager().beginTransaction(), new OrderMapFragment());
        } else {
            // Display dialog text here......
            // Do all other actions for first time launch in the day...
            // Set the last Launched date to today.
            HelperClass.replaceFragmentWithoutBack(getSupportFragmentManager().beginTransaction(), new HomeFragment());
        }
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else {
            Utils.chooseLang(this, "en");
        }
        mViewHolder = new ViewHolder();
        handleDrawer();
        handleMenu();
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("from").equals("details")) {
                HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), new OrdersFragment());
            }
        }
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
        };
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    private void handleDrawer() {
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, mViewHolder.mDuoDrawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewHolder.mDuoDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        drawerToggle.syncState();


    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(this, mTitles);
        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {

    }

    @Override
    public void onHeaderClicked() {

    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        Intent intent = new Intent(this, PagesActivity.class);
        switch (position) {

            case 0:
                tvTitle.setText(getString(R.string.editData));
                HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), new ProfileFragment());
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                break;
            case 1:
                tvTitle.setText(getString(R.string.myOrders));
                HelperClass.replaceFragment(getSupportFragmentManager().beginTransaction(), new OrdersFragment());
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                break;
            case 2:
                startActivity(new Intent(this, BillsActivity.class));
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                break;
            case 3:
                intent.putExtra("from", "terms");
                startActivity(intent);
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                break;
            case 4:
                intent.putExtra("from", "about");
                startActivity(intent);
                mViewHolder.mDuoDrawerLayout.closeDrawer();
                break;
            case 5:
                startActivity(new Intent(this, ContactUs.class));
                mViewHolder.mDuoDrawerLayout.closeDrawer();

                break;
        }

    }

    @OnClick({R.id.frDisable, R.id.frEnable})
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
        }
    }

    @Override
    public void changeView(String text) {
        tvTitle.setText(text);
        if (text.equals(getString(R.string.homeAndStatus))) {
            liStatus.setVisibility(View.GONE);
        } else {
            liStatus.setVisibility(View.VISIBLE);
        }


    }


    public class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;
        ImageView ivProfile;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = findViewById(R.id.toolbar);
//            TextView logout = mDuoMenuView.findViewById(R.id.duo_view_footer_text);
            TextView name = mDuoMenuView.findViewById(R.id.duo_view_header_text_title);
            Button tvLoginState = mDuoMenuView.findViewById(R.id.tvLoginState);
            TextView email = mDuoMenuView.findViewById(R.id.duo_view_header_text_sub_title);
            ImageView ivClear = mDuoMenuView.findViewById(R.id.ivClear);
            ivProfile = mDuoMenuView.findViewById(R.id.ivProfile);
            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                mDuoDrawerLayout.setGravity(Gravity.END);
            }

            ivClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDuoDrawerLayout.closeDrawer();
                }
            });

            tvLoginState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!storage.isFirstTimeLogin()) {
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        storage.setFirstTimeLogin(false);
//                        storage.clearSharedPref();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            });

            if (!storage.isFirstTimeLogin()) {
                name.setText(getString(R.string.welcome));
                tvLoginState.setText(getString(R.string.login));
//                ivProfile.setImageResource(R.drawable.ic_user);
            } else {
                tvLoginState.setText(getString(R.string.logout));
                name.setText(storage.getKey("name"));
                String image = storage.getKey("photo");
                Log.e("photo", "" + image);

                if (!image.isEmpty()) {
                    Picasso.with(HomeActivity.this).load("" + image).into(ivProfile);
                } else {
                    ivProfile.setImageResource(R.drawable.profimg);
                }
            }
        }
    }


}

