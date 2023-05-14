package com.art4muslimt.artfooddelegate.dagger.modules;

import com.art4muslimt.artfooddelegate.ui.bills.BillsPresenter;
import com.art4muslimt.artfooddelegate.ui.bills.BillsView;
import com.art4muslimt.artfooddelegate.ui.contactUs.ContactUsPresenter;
import com.art4muslimt.artfooddelegate.ui.forgetPassword.ForgetPasswordPresenter;
import com.art4muslimt.artfooddelegate.ui.home.HomePresenter;
import com.art4muslimt.artfooddelegate.ui.login.LoginPresenter;
import com.art4muslimt.artfooddelegate.ui.orders.OrderPresenter;
import com.art4muslimt.artfooddelegate.ui.pages.PagesPresenter;
import com.art4muslimt.artfooddelegate.ui.profile.ProfilePresenter;
import com.art4muslimt.artfooddelegate.ui.registeration.RegisterPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {


    @Singleton
    @Provides
    public HomePresenter homePresenter() {
        return new HomePresenter();
    }

    @Singleton
    @Provides
    public LoginPresenter loginPresenter() {
        return new LoginPresenter();
    }

    @Singleton
    @Provides
    public RegisterPresenter registerPresenter() {
        return new RegisterPresenter();
    }

    @Singleton
    @Provides
    public ContactUsPresenter contactUsPresenter() {
        return new ContactUsPresenter();
    }

    @Singleton
    @Provides
    public PagesPresenter pagesPresenter() {
        return new PagesPresenter();
    }

    @Singleton
    @Provides
    public ForgetPasswordPresenter forgetPasswordPresenter() {
        return new ForgetPasswordPresenter();
    }

    @Singleton
    @Provides
    public ProfilePresenter profilePresenter() {
        return new ProfilePresenter();
    }

    @Singleton
    @Provides
    public OrderPresenter orderPresenter() {
        return new OrderPresenter();
    }

    @Singleton
    @Provides
    public BillsPresenter billsPresenter() {
        return new BillsPresenter();
    }

}
