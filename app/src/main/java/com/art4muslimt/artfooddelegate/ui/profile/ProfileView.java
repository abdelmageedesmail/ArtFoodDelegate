package com.art4muslimt.artfooddelegate.ui.profile;


import com.art4muslimt.artfooddelegate.internet.model.CityResponse;
import com.art4muslimt.artfooddelegate.internet.model.NationalitiesResponse;
import com.art4muslimt.artfooddelegate.internet.model.UserDataResponse;
import com.art4muslimt.artfooddelegate.internet.model.UserDetails;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;

import java.util.List;

public interface ProfileView {

    void getUserData(UserDataResponse.DataEntity userDataResponse);

    void getCities(List<CityResponse.DataEntity> data);

    void getNationalitieis(List<NationalitiesResponse.DataEntity> data);

    void getVechilies(List<VechiliesResponse.DataEntity> data);

    void getUpdatedData(UserDetails.User_detailsEntity data);

    void passwordUpdated(boolean b);
}
