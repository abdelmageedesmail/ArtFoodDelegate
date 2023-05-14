package com.art4muslimt.artfooddelegate.ui.bills;


import com.art4muslimt.artfooddelegate.internet.model.BillsModelResponse;

import java.util.List;

public interface BillsView {

    void getTransferBalance(List<BillsModelResponse.DataEntity> list,String balance);


    void requestSent(boolean b);
}
