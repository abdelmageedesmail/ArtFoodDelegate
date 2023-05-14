package com.art4muslimt.artfooddelegate.ui.registeration;


import com.art4muslimt.artfooddelegate.internet.model.CategoriesResponse;
import com.art4muslimt.artfooddelegate.internet.model.CityResponse;
import com.art4muslimt.artfooddelegate.internet.model.NationalitiesResponse;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;

import java.util.List;

public interface RegisterView {

    void getCities(List<CityResponse.DataEntity> data);

    void getNationalitieis(List<NationalitiesResponse.DataEntity> data);

    void getVechilies(List<VechiliesResponse.DataEntity> data);

    void isRegistered(boolean status);

}
