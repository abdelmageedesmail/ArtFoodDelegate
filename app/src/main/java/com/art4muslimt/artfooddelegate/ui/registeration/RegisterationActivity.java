package com.art4muslimt.artfooddelegate.ui.registeration;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.CityResponse;
import com.art4muslimt.artfooddelegate.internet.model.NationalitiesResponse;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.ui.login.LoginActivity;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterationActivity extends AppCompatActivity implements RegisterView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btnLogo)
    Button btnLogo;
    @BindView(R.id.btnUploadVechical)
    Button btnUploadVechical;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    Runnable run;
    Handler handler = new Handler();
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.familyLogo)
    CircleImageView familyLogo;
    @BindView(R.id.identityLogo)
    CircleImageView identityLogo;
    @BindView(R.id.btnIdentity)
    Button btnIdentity;
    @BindView(R.id.spNationalities)
    Spinner spNationalities;
    @BindView(R.id.spCities)
    Spinner spCities;
    @BindView(R.id.etBank)
    EditText etBank;
    @BindView(R.id.etIpan)
    EditText etIpan;
    @BindView(R.id.spVechilies)
    Spinner spVechilies;
    @BindView(R.id.etVehicleNumber)
    EditText etVehicleNumber;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.radioAcceptTerrms)
    RadioButton radioAcceptTerrms;
    @BindView(R.id.vehicleLogo)
    CircleImageView vehicleLogo;
    private Uri imageUri;
    private static final int SELECT_FILE = 1;
    int REQUEST_CAMERA = 0;
    private String imageurl;
    private File imageFile;
    private Uri selectedImageUri;
    private String selectedImagePath;
    private Bitmap bm;
    private String substring, imagepath;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private String type;
    private File identityFile, vehicleFile;
    @Inject
    RegisterPresenter presenter;
    private String cityId, nationalityId, vehicleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        ButterKnife.bind(this);
        Utils.addPermissions(this);
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        presenter.getCities(this);
        presenter.getNationalities(this);
        presenter.getVicheles(this);
    }

    private void setUpValidation() {
        if (etName.getText().toString().isEmpty()) {
            etName.setError(getString(R.string.empty));
        } else if (etPhone.getText().toString().isEmpty()) {
            etPhone.setError(getString(R.string.empty));
        } else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError(getString(R.string.empty));
        } else if (etBank.getText().toString().isEmpty()) {
            etBank.setError(getString(R.string.empty));
        } else if (etIpan.getText().toString().isEmpty()) {
            etIpan.setError(getString(R.string.empty));
        } else if (etVehicleNumber.getText().toString().isEmpty()) {
            etVehicleNumber.setError(getString(R.string.empty));
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError(getString(R.string.empty));
        } else if (etConfirmPassword.getText().toString().isEmpty()) {
            etConfirmPassword.setError(getString(R.string.empty));
        } else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            Toast.makeText(this, getString(R.string.passwordNotMatches), Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(etEmail.getText().toString())) {
            Toast.makeText(this, getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
        } else if (etPhone.getText().toString().length() < 10) {
            Toast.makeText(this, getString(R.string.phoneError), Toast.LENGTH_SHORT).show();
        } else if (!radioAcceptTerrms.isChecked()) {
            Toast.makeText(this, getString(R.string.pleaseAcceptTerms), Toast.LENGTH_SHORT).show();
        } else {
            presenter.registerUser(this, etName.getText().toString(), etPhone.getText().toString(), etEmail.getText().toString(), imageFile
                    , "" + 0.0, "" + 0.0, etPassword.getText().toString(), identityFile, nationalityId, cityId, vehicleId, vehicleFile, etVehicleNumber.getText().toString(), etBank.getText().toString(), etIpan.getText().toString());
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @OnClick({R.id.ivBack, R.id.btnLogo, R.id.btnUploadVechical, R.id.btnRegister, R.id.btnLogin, R.id.btnIdentity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                break;
            case R.id.btnLogo:
                type = "logo";
                selectImage();
                break;
            case R.id.btnIdentity:
                type = "identity";
                selectImage();
                break;
            case R.id.btnUploadVechical:
                type = "vehicle";
                selectImage();
                break;
            case R.id.btnRegister:
                setUpValidation();
                break;
            case R.id.btnLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.forLanguageTag(Utils.getLang(this)));
        String loc = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                loc = city + " , " + country;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loc;
    }

    private void selectImage() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_FILE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            }

        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bmb = null;
        selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        selectedImagePath = cursor.getString(column_index);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        substring = selectedImagePath.substring(selectedImagePath.lastIndexOf(".") + 1);

        imagepath = selectedImageUri.getPath();
        if (type.equals("logo")) {
            imageFile = new File(selectedImagePath);
        } else if (type.equals("identity")) {
            identityFile = new File(selectedImagePath);
        } else {
            vehicleFile = new File(selectedImagePath);
        }

        Bitmap finalImage = null;
        try {
            ExifInterface ei = new ExifInterface(selectedImagePath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            Log.e("orientation", orientation + "");
            //finalImage = rotateImage(bm, 270);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    finalImage = rotateImage(bm, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    finalImage = rotateImage(bm, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    finalImage = rotateImage(bm, 270);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exeption", "" + e.getMessage());
        }

        if (finalImage != null) {
            if (type.equals("logo")) {
                familyLogo.setImageBitmap(finalImage);
            } else if (type.equals("identity")) {
                identityLogo.setImageBitmap(finalImage);
            } else {
                vehicleLogo.setImageBitmap(finalImage);
            }

        } else {
            if (type.equals("logo")) {
                familyLogo.setImageBitmap(bm);
            } else if (type.equals("identity")) {
                identityLogo.setImageBitmap(bm);
            } else {
                vehicleLogo.setImageBitmap(bm);
            }
        }

    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    private void popupAccountCreated() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_account_creates);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        run = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(RegisterationActivity.this, LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
            }
        }

        ;
        handler.postDelayed(run, 2000);
        dialog.show();
    }

    @Override
    public void getCities(List<CityResponse.DataEntity> data) {
        spCities.setAdapter(new CitiesSpinnerAdapter(this, data));
        spCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityId = data.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void getNationalitieis(List<NationalitiesResponse.DataEntity> data) {
        spNationalities.setAdapter(new NationalitiesSpinnerAdapter(this, data));
        spNationalities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nationalityId = data.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void getVechilies(List<VechiliesResponse.DataEntity> data) {
        spVechilies.setAdapter(new VehiclesSpinnerAdapter(this, data));
        spVechilies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicleId = data.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void isRegistered(boolean status) {
        if (status) {
            popupAccountCreated();
        }
    }
}