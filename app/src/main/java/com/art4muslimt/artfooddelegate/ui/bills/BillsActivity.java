package com.art4muslimt.artfooddelegate.ui.bills;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.BillsModelResponse;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.utils.PrefrencesStorage;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BillsActivity extends AppCompatActivity implements BillsView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvBills)
    RecyclerView rvBills;
    @BindView(R.id.noData)
    View noData;
    @BindView(R.id.tvBalcnce)
    TextView tvBalcnce;
    @Inject
    BillsPresenter presenter;
    PrefrencesStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        ButterKnife.bind(this);
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);
        storage = new PrefrencesStorage(this);
        presenter.setView(this);
        presenter.getBills(this, storage.getId());
    }

    @OnClick(R.id.ivBack)
    public void onVBackViewClicked() {
        onBackPressed();
    }

    @Override
    public void getTransferBalance(List<BillsModelResponse.DataEntity> list, String balance) {
        tvBalcnce.setText("" + balance + " " + getString(R.string.currency));
        if (list.size() > 0) {
            noData.setVisibility(View.GONE);
            rvBills.setAdapter(new BillsAdapter(this, list));
            rvBills.setLayoutManager(new LinearLayoutManager(this));
            rvBills.setNestedScrollingEnabled(false);
        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    private void popUpSendRequest() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_charge_request);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        EditText etPrice = dialog.findViewById(R.id.etPrice);
        Button btnSend = dialog.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPrice.getText().toString().isEmpty()) {
                    etPrice.setError(getString(R.string.empty));
                } else {
                    dialog.dismiss();
                    presenter.addRequest(BillsActivity.this, storage.getId(), etPrice.getText().toString());
                }
            }
        });
        dialog.show();
    }

    @Override
    public void requestSent(boolean b) {
        if (b) {
            Toast.makeText(this, getString(R.string.requestSent), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.noAvaliableCharge), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnChargeRequest)
    public void onViewClicked() {
        popUpSendRequest();
    }
}