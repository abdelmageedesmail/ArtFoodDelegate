package com.art4muslimt.artfooddelegate.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.CityResponse;
import com.art4muslimt.artfooddelegate.internet.model.NationalitiesResponse;
import com.art4muslimt.artfooddelegate.internet.model.UserDataResponse;
import com.art4muslimt.artfooddelegate.internet.model.UserDetails;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.ui.home.HomeActivity;
import com.art4muslimt.artfooddelegate.ui.registeration.CitiesSpinnerAdapter;
import com.art4muslimt.artfooddelegate.ui.registeration.NationalitiesSpinnerAdapter;
import com.art4muslimt.artfooddelegate.ui.registeration.VehiclesSpinnerAdapter;
import com.art4muslimt.artfooddelegate.utils.GPSTracker;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;
import com.art4muslimt.artfooddelegate.utils.Utils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity implements ProfileView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.familyLogo)
    CircleImageView familyLogo;
    @BindView(R.id.btnLogo)
    Button btnLogo;
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
    @BindView(R.id.vehicleLogo)
    CircleImageView vehicleLogo;
    @BindView(R.id.btnUploadVechical)
    Button btnUploadVechical;
    @BindView(R.id.etVehicleNumber)
    EditText etVehicleNumber;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @Inject
    ProfilePresenter presenter;
    PrefrencesStorage storage;
    private UserDataResponse.DataEntity userData;
    private String vechileId, cityId, nationalityId;
    private String type;
    GPSTracker mGps;
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
    private File identityFile, vehicleFile;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        storage = new PrefrencesStorage(this);
        mGps = new GPSTracker(this);
        presenter.getUserData(this, storage.getId());

    }


    @OnClick({R.id.ivBack, R.id.btnLogo, R.id.btnIdentity, R.id.btnUploadVechical, R.id.btnEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
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
            case R.id.btnEdit:
                presenter.editUser(this, storage.getId(), etName.getText().toString(), etPhone.getText().toString(), etEmail.getText().toString(), imageFile,
                        "" + mGps.getLatitude(), "" + mGps.getLongitude(), identityFile, nationalityId, cityId, vechileId, vehicleFile, etVehicleNumber.getText().toString()
                        , etBank.getText().toString(), etIpan.getText().toString());

                break;
        }
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


    @Override
    public void getUserData(UserDataResponse.DataEntity userDataResponse) {
        userData = userDataResponse;
        etName.setText(userDataResponse.getName());

        etPhone.setText(userDataResponse.getMobile());
        etEmail.setText(userDataResponse.getEmail());
        if (!userDataResponse.getImage().isEmpty()) {
            Picasso.with(this).load(userDataResponse.getImage()).into(familyLogo);
        }
        if (!userDataResponse.getIdphoto().isEmpty()) {
            Picasso.with(this).load(userDataResponse.getIdphoto()).into(identityLogo);
        }
        if (!userDataResponse.getLicense().isEmpty()) {
            Picasso.with(this).load(userDataResponse.getLicense()).into(vehicleLogo);
        }

        etBank.setText(userDataResponse.getBankname());
        etIpan.setText(userDataResponse.getIban());
        etVehicleNumber.setText(userDataResponse.getVehicleplatenumber());

        presenter.getNationalities(this);
        presenter.getVicheles(this);
        presenter.getCities(this);

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
        for (int i = 0; i < data.size(); i++) {
            if (userData.getCity().equals(data.get(i).getId())) {
                spCities.setSelection(i);
            }
        }
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

        for (int i = 0; i < data.size(); i++) {
            if (userData.getNationality().equals(data.get(i).getId())) {
                spNationalities.setSelection(i);
            }
        }
    }

    @Override
    public void getVechilies(List<VechiliesResponse.DataEntity> data) {
        spVechilies.setAdapter(new VehiclesSpinnerAdapter(this, data));
        spVechilies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vechileId = data.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for (int i = 0; i < data.size(); i++) {
            if (userData.getTypevehicle().equals(data.get(i).getId())) {
                spVechilies.setSelection(i);
            }
        }
    }

    @Override
    public void getUpdatedData(UserDetails.User_detailsEntity data) {
        storage.storeKey("photo", data.getImage());
        startActivity(new Intent(this, HomeActivity.class));
        finish();

    }

    @Override
    public void passwordUpdated(boolean b) {

    }
}