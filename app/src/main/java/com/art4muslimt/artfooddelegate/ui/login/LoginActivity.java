package com.art4muslimt.artfooddelegate.ui.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.LoginResponse;
import com.art4muslimt.artfooddelegate.notifications.FCMRegistrationService;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.ui.forgetPassword.ForgetPassword;
import com.art4muslimt.artfooddelegate.ui.home.HomeActivity;
import com.art4muslimt.artfooddelegate.ui.registeration.RegisterationActivity;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;
import com.art4muslimt.artfooddelegate.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.liForgetPass)
    LinearLayout liForgetPass;

    @Inject
    LoginPresenter presenter;
    private PrefrencesStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        tvForgetPassword.setPaintFlags(tvForgetPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);
        startService(new Intent(this, FCMRegistrationService.class));

        presenter.setView(this);
        storage = new PrefrencesStorage(this);
        tvForgetPassword.setPaintFlags(tvForgetPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Utils.addPermissions(this);
    }

    private void setUpValidation() {
        if (etPhone.getText().toString().isEmpty()) {
            etPhone.setError(getString(R.string.empty));
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError(getString(R.string.empty));
        } else {
            presenter.login(this, etPhone.getText().toString(), etPassword.getText().toString());
        }
    }

    @OnClick({R.id.btnLogin, R.id.btnRegister, R.id.liForgetPass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                setUpValidation();
                break;
            case R.id.liForgetPass:
                startActivity(new Intent(this, ForgetPassword.class));
                break;
            case R.id.btnRegister:
                startActivity(new Intent(this, RegisterationActivity.class));
                break;
        }
    }

    @Override
    public void getUserData(LoginResponse loginResponse) {
        if (loginResponse.getStatus()) {
            storage.storeId(loginResponse.getIduser());
            storage.storeKey("name", loginResponse.getNameuser());
            storage.storeKey("phone", loginResponse.getMobile());
            storage.storeKey("email", loginResponse.getEmail());
            storage.storeKey("photo", loginResponse.getImage());
            storage.setFirstTimeLogin(true);
            startActivity(new Intent(this, HomeActivity.class));
        }

    }
}