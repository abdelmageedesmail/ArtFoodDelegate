package com.art4muslimt.artfooddelegate.ui.orders;

import com.art4muslimt.artfooddelegate.internet.model.OrderResponse;

import java.util.List;

public interface OrderView {
    void getAcceptedOrder(List<OrderResponse.DataEntity> data);

    void changeStatus(boolean b, String done);

    void getFinishedOrder(List<OrderResponse.DataEntity> data);
}
