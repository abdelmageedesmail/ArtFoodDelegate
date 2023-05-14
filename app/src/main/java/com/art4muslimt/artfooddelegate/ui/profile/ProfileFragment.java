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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.CityResponse;
import com.art4muslimt.artfooddelegate.internet.model.NationalitiesResponse;
import com.art4muslimt.artfooddelegate.internet.model.UserDataResponse;
import com.art4muslimt.artfooddelegate.internet.model.UserDetails;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.ui.home.HomeActivity;
import com.art4muslimt.artfooddelegate.ui.registeration.CitiesSpinnerAdapter;
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

public class ProfileFragment extends Fragment implements ProfileView {

    @BindView(R.id.btnEditData)
    Button btnEditData;
    @BindView(R.id.btnEditPassword)
    Button btnEditPassword;
    @BindView(R.id.ivFamilyImage)
    CircleImageView ivFamilyImage;
    @BindView(R.id.ivEdit)
    ImageView ivEdit;
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
    @BindView(R.id.spNationalities)
    TextView spNationalities;
    @BindView(R.id.spCities)
    TextView spCities;
    @BindView(R.id.etBank)
    EditText etBank;
    @BindView(R.id.etIpan)
    EditText etIpan;
    @BindView(R.id.spVechilies)
    TextView spVechilies;
    @BindView(R.id.vehicleLogo)
    CircleImageView vehicleLogo;
    @BindView(R.id.etVehicleNumber)
    EditText etVehicleNumber;
    @Inject
    ProfilePresenter presenter;
    PrefrencesStorage storage;
    private UserDataResponse.DataEntity data;
    private String cityId, nationalityId, vehicleId;


    private Uri imageUri;
    private static final int SELECT_FILE = 1;
    int REQUEST_CAMERA = 0;
    private String imageurl;
    private File imageFile;
    private Uri selectedImageUri;
    private String selectedImagePath;
    private Bitmap bm;
    private String substring, imagepath;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        ((ArtFood) getActivity().getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        storage = new PrefrencesStorage(getActivity());
        presenter.getUserData(getActivity(), storage.getId());

        return view;
    }

    @OnClick({R.id.btnEditData, R.id.btnEditPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEditData:
                startActivity(new Intent(getActivity(), EditProfile.class));
                break;
            case R.id.btnEditPassword:
                startActivity(new Intent(getActivity(), EditPassword.class));
                break;
        }
    }

    @OnClick(R.id.ivEdit)
    public void onViewClicked() {
        selectImage();
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
    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(dataIntent);
                presenter.editUser(getActivity(), storage.getId(), etName.getText().toString(), etPhone.getText().toString(), etEmail.getText().toString(), imageFile,
                        "" + data.getLat(), "" + data.getLng(), null, nationalityId, cityId, vehicleId, null, etVehicleNumber.getText().toString()
                        , etBank.getText().toString(), etIpan.getText().toString());

            }

        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bmb = null;
        selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null,
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
        imageFile = new File(selectedImagePath);

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
            familyLogo.setImageBitmap(finalImage);
            ivFamilyImage.setImageBitmap(finalImage);

        } else {
            familyLogo.setImageBitmap(bm);
            ivFamilyImage.setImageBitmap(bm);
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
        data = userDataResponse;


        etName.setText(userDataResponse.getName());

        etPhone.setText(userDataResponse.getMobile());
        etEmail.setText(userDataResponse.getEmail());
        if (!userDataResponse.getImage().isEmpty()) {
            Picasso.with(getActivity()).load(userDataResponse.getImage()).into(ivFamilyImage);
        }
        if (!userDataResponse.getImage().isEmpty()) {
            Picasso.with(getActivity()).load(userDataResponse.getImage()).into(familyLogo);
        }
        if (!userDataResponse.getIdphoto().isEmpty()) {
            Picasso.with(getActivity()).load(userDataResponse.getIdphoto()).into(identityLogo);
        }
        if (!userDataResponse.getLicense().isEmpty()) {
            Picasso.with(getActivity()).load(userDataResponse.getLicense()).into(vehicleLogo);
        }

        etBank.setText(userDataResponse.getBankname());
        etIpan.setText(userDataResponse.getIban());

        etVehicleNumber.setText(userDataResponse.getVehicleplatenumber());
        if (Utils.getLang(getActivity()).equals("ar")) {
            spCities.setText(userDataResponse.getCity_name());
            spNationalities.setText(userDataResponse.getNationality_name());
            spVechilies.setText(userDataResponse.getTypevehicle_name());
        } else {
            spCities.setText(userDataResponse.getCity_nameen());
            spNationalities.setText(userDataResponse.getNationality_nameen());
            spVechilies.setText(userDataResponse.getTypevehicle_nameen());
        }

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
        storage.storeKey("photo", data.getImage());
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
    }

    @Override
    public void passwordUpdated(boolean b) {

    }
}
