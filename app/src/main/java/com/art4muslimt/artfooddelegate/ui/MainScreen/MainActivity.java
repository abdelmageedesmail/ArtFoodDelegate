package com.art4muslimt.artfooddelegate.ui.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.notifications.FCMRegistrationService;
import com.art4muslimt.artfooddelegate.ui.home.HomeActivity;
import com.art4muslimt.artfooddelegate.ui.login.LoginActivity;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;
import com.art4muslimt.artfooddelegate.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnEnglish)
    Button btnEnglish;
    @BindView(R.id.btnArabic)
    Button btnArabic;
    PrefrencesStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        storage = new PrefrencesStorage(this);
        startService(new Intent(this, FCMRegistrationService.class));

    }

    @OnClick({R.id.btnEnglish, R.id.btnArabic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEnglish:
                startActivity("en");
                break;
            case R.id.btnArabic:
                startActivity("ar");
                break;
        }
    }

    private void startActivity(String lang) {
        Utils.chooseLang(this, lang);
        if ((!storage.isFirstTimeLogin())) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startActivity(new Intent(this, HomeActivity.class));
        }
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
    }
}