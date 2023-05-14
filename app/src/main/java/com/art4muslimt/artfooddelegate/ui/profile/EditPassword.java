package com.art4muslimt.artfooddelegate.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.CategoriesResponse;
import com.art4muslimt.artfooddelegate.internet.model.CityResponse;
import com.art4muslimt.artfooddelegate.internet.model.NationalitiesResponse;
import com.art4muslimt.artfooddelegate.internet.model.UserDataResponse;
import com.art4muslimt.artfooddelegate.internet.model.UserDetails;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPassword extends AppCompatActivity implements ProfileView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.btnEditPassword)
    Button btnEditPassword;
    @Inject
    ProfilePresenter presenter;
    PrefrencesStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        ButterKnife.bind(this);
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        storage = new PrefrencesStorage(this);
    }

    private void setUpValidation() {
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError(getString(R.string.empty));
        } else if (etNewPassword.getText().toString().isEmpty()) {
            etNewPassword.setError(getString(R.string.empty));
        } else if (etConfirmPassword.getText().toString().isEmpty()) {
            etConfirmPassword.setError(getString(R.string.empty));
        } else if (!etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            Toast.makeText(this, getString(R.string.passwordNotMatches), Toast.LENGTH_SHORT).show();
        } else {
            presenter.editPassword(this, storage.getId(), etPassword.getText().toString(), etNewPassword.getText().toString());
        }
    }

    @OnClick({R.id.ivBack, R.id.btnEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnEdit:
                setUpValidation();
                break;
        }
    }

    @Override
    public void getUserData(UserDataResponse.DataEntity userDataResponse) {

    }

    @Override
    public void getCities(List<CityResponse.DataEntity> data) {

    }

    @Override
    public void getNationalitieis(List<NationalitiesResponse.DataEntity> data) {

    }

    @Override
    public void getVechilies(List<VechiliesResponse.DataEntity> data) {

    }

    @Override
    public void getUpdatedData(UserDetails.User_detailsEntity data) {

    }

    @Override
    public void passwordUpdated(boolean b) {
        if (b) {
            Toast.makeText(this, getString(R.string.passwordUpdatedSuccessfully), Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else {
            Toast.makeText(this, getString(R.string.oldPassworInCorrect), Toast.LENGTH_SHORT).show();
        }
    }
}