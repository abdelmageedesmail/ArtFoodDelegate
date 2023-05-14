package com.art4muslimt.artfooddelegate.ui.forgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.ui.ArtFood;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPassword extends AppCompatActivity implements ForgetPasswordView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @Inject
    ForgetPasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
    }

    @OnClick({R.id.ivBack, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnLogin:
                if (etPhone.getText().toString().isEmpty()) {
                    etPhone.setError(getString(R.string.empty));
                } else {
                    presenter.forgetPassword(this, etPhone.getText().toString());
                }
                break;
        }
    }

    @Override
    public void getCode(String code) {
        if (!code.isEmpty()) {
            Intent intent = new Intent(this, EnterCodeActivity.class);
            intent.putExtra("code", code);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.phoneNumberNotExist), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getId(String id) {

    }

    @Override
    public void isReset(boolean b) {

    }

}