package com.art4muslimt.artfooddelegate.ui.home;

import com.art4muslimt.artfooddelegate.internet.model.OrderResponse;
import com.art4muslimt.artfooddelegate.internet.model.VechiliesResponse;

import java.util.List;

public interface HomeView {
    void getVechilies(List<VechiliesResponse.DataEntity> data);

    void isUpdated(boolean b);

    void getWaitingOrders(List<OrderResponse.DataEntity> data);

    void changeStatus(boolean b, String accept);
}
