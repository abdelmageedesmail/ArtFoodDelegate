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

public class EnterCodeActivity extends AppCompatActivity implements ForgetPasswordView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnResend)
    Button btnResend;
    @BindView(R.id.etCode)
    EditText etCode;
    @Inject
    ForgetPasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
        ButterKnife.bind(this);
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
    }

    @OnClick({R.id.ivBack, R.id.btnLogin, R.id.btnResend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnLogin:
                if (etCode.getText().toString().isEmpty()) {
                    etCode.setError(getString(R.string.empty));
                } else {
                    presenter.checkCode(this, etCode.getText().toString());

                }
                break;
            case R.id.btnResend:
                break;
        }
    }

    @Override
    public void getCode(String code) {

    }

    @Override
    public void getId(String id) {
        if (!id.isEmpty()) {
            Intent intent = new Intent(this, EnterNewPassword.class);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.pleaseEnterCorrectCode), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void isReset(boolean b) {

    }
}