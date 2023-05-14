package com.art4muslimt.artfooddelegate.ui.contactUs;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.ui.ArtFood;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUs extends AppCompatActivity implements ContactUsView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @Inject
    ContactUsPresenter presenter;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etMessage)
    EditText etMessage;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.spType)
    Spinner spType;
    @BindView(R.id.etPhone)
    EditText etPhone;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    type = "2";
                } else if (position == 2) {
                    type = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpValidation() {
        if (etName.getText().toString().isEmpty()) {
            etName.setError(getString(R.string.empty));
        } else if (etPhone.getText().toString().isEmpty()) {
            etPhone.setError(getString(R.string.empty));
        } else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError(getString(R.string.empty));
        } else if (etMessage.getText().toString().isEmpty()) {
            etMessage.setError(getString(R.string.empty));
        } else if (!isValidEmail(etEmail.getText().toString())) {
            Toast.makeText(this, getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
        } else if (type == null) {
            Toast.makeText(this, getString(R.string.pleaseSelectContactType), Toast.LENGTH_SHORT).show();
        } else {
            presenter.contactUs(this, etName.getText().toString(), etMessage.getText().toString(), etEmail.getText().toString(), type, etPhone.getText().toString());
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void isAdded(boolean isAdded) {
        if (isAdded) {
            Toast.makeText(this, getString(R.string.messageSent), Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else {
            Toast.makeText(this, getString(R.string.errorPleaseTryAgain), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick({R.id.ivBack, R.id.btnSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnSend:
                setUpValidation();
                break;
        }
    }
}