package com.art4muslimt.artfooddelegate.ui.pages;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.Urls;
import com.art4muslimt.artfooddelegate.ui.ArtFood;
import com.art4muslimt.artfooddelegate.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PagesActivity extends AppCompatActivity implements PagesView {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @Inject
    PagesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);
        ButterKnife.bind(this);
        ((ArtFood) getApplication()).getApplicationComponent().inject(this);
        presenter.setView(this);
        if (getIntent().getExtras().getString("from").equals("about")) {
            tvTitle.setText(getString(R.string.aboutUs));
            if (Utils.getLang(this).equals("ar")) {
                presenter.getPages(this, Urls.ABOUT_AR);
            } else {
                presenter.getPages(this, Urls.ABOUT_EN);
            }

        } else {
            tvTitle.setText(getString(R.string.termsAndCondition));
            if (Utils.getLang(this).equals("ar")) {
                presenter.getPages(this, Urls.TERMS_AR);
            } else {
                presenter.getPages(this, Urls.TERMS_EN);
            }
        }
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void getPages(String text) {
        tvMessage.setText(Html.fromHtml(text));
    }
}