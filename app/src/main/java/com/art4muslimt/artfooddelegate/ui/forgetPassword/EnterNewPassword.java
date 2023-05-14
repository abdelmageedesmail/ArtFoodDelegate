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
import com.art4muslimt.artfooddelegate.ui.login.LoginActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EnterNewPassword extends AppCompatActivity implements ForgetPasswordView {

    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @Inject
    ForgetPasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_new_password);
        ButterKnife.bind(this);
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
    }

    private void setUpValidation() {
        if (etNewPassword.getText().toString().isEmpty()) {
            etNewPassword.setError(getString(R.string.empty));
        } else if (etConfirmPassword.getText().toString().isEmpty()) {
            etConfirmPassword.setError(getString(R.string.empty));
        } else if (!etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            Toast.makeText(this, getString(R.string.passwordNotMatches), Toast.LENGTH_SHORT).show();
        } else {
            presenter.enterNewPassword(this, etNewPassword.getText().toString(), getIntent().getExtras().getString("id"));
        }
    }

    @OnClick(R.id.btnConfirm)
    public void onViewClicked() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick({R.id.ivBack, R.id.btnConfirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnConfirm:
                break;
        }
    }

    @Override
    public void getCode(String code) {

    }

    @Override
    public void getId(String code) {

    }

    @Override
    public void isReset(boolean b) {
        if (b) {
            Toast.makeText(this, getString(R.string.passwordUpdatedSuccessfully), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}