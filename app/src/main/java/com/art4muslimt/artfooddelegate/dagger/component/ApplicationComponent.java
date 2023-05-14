package com.art4muslimt.artfooddelegate.dagger.component;


import com.art4muslimt.artfooddelegate.dagger.modules.ApplicationModule;
import com.art4muslimt.artfooddelegate.ui.bills.BillsActivity;
import com.art4muslimt.artfooddelegate.ui.contactUs.ContactUs;
import com.art4muslimt.artfooddelegate.ui.forgetPassword.EnterCodeActivity;
import com.art4muslimt.artfooddelegate.ui.forgetPassword.EnterNewPassword;
import com.art4muslimt.artfooddelegate.ui.forgetPassword.ForgetPassword;
import com.art4muslimt.artfooddelegate.ui.home.HomeActivity;
import com.art4muslimt.artfooddelegate.ui.home.HomeFragment;
import com.art4muslimt.artfooddelegate.ui.home.MapFragment;
import com.art4muslimt.artfooddelegate.ui.home.OrderMapFragment;
import com.art4muslimt.artfooddelegate.ui.home.UpdateUserLocationService;
import com.art4muslimt.artfooddelegate.ui.login.LoginActivity;
import com.art4muslimt.artfooddelegate.ui.orders.OrderDetailsActivity;
import com.art4muslimt.artfooddelegate.ui.orders.OrdersFragment;
import com.art4muslimt.artfooddelegate.ui.pages.PagesActivity;
import com.art4muslimt.artfooddelegate.ui.profile.EditPassword;
import com.art4muslimt.artfooddelegate.ui.profile.EditProfile;
import com.art4muslimt.artfooddelegate.ui.profile.ProfileFragment;
import com.art4muslimt.artfooddelegate.ui.registeration.RegisterationActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(HomeActivity homeActivity);

    void inject(RegisterationActivity registerationActivity);

    void inject(LoginActivity loginActivity);

    void inject(ProfileFragment profileFragment);

    void inject(EditProfile editProfile);

    void inject(EditPassword editPassword);

    void inject(PagesActivity pagesActivity);

    void inject(ContactUs contactUs);

    void inject(ForgetPassword forgetPassword);

    void inject(EnterCodeActivity enterCodeActivity);

    void inject(EnterNewPassword enterNewPassword);

    void inject(BillsActivity billsActivity);


    void inject(OrdersFragment ordersFragment);

    void inject(HomeFragment homeFragment);

    void inject(MapFragment mapFragment);

    void inject(OrderMapFragment orderMapFragment);

    void inject(OrderDetailsActivity orderDetailsActivity);

    void inject(UpdateUserLocationService updateUserLocationService);
}
