package com.financecalculator.activity.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.financecalculator.NavDrawer.DrawerCallbacks;
import com.financecalculator.NavDrawer.Nav_DrawerFragment;
import com.financecalculator.R;
import com.financecalculator.app.Constant;
import com.financecalculator.app.app;
import com.financecalculator.base.BaseActivity;
import com.financecalculator.custom.CustomTextView;
import com.financecalculator.databinding.ActivityDashboardBinding;
import com.financecalculator.fragment.contact_us.ContactUsFragment;
import com.financecalculator.fragment.dashboard.DashboardFragment;

import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DashboardActivity extends BaseActivity implements DashboardView, DrawerCallbacks {

    private DashboardPresenter mPresenter;
    private ActivityDashboardBinding mBinding;
    private Nav_DrawerFragment mNavigationNavDrawerFragment;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_dashboard;
    }

    @Override
    public void init(ViewDataBinding dataBinding) {


        mPresenter = new DashboardPresenterImpl(this);
        mBinding = (ActivityDashboardBinding) dataBinding;

        setToolbar();
//        setToolbarTitle("Finance calculator");
        setToolbarTitle("");

//        getWindow().setStatusBarColor(getResources().getColor(R.color.DF6C6B));

        mNavigationNavDrawerFragment = (Nav_DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationNavDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_launcher_background));
        onNavigationDrawerItemSelected(0);

        ((CustomTextView) findViewById(R.id.txtFinance)).setTypeface(app.getBoldTypeFace());

//        new ContainerModel()
//                .setFragmentNumber(Constant.FRAGMENT_SIP)
//                .setShowToolbar(true)
//                .setTitle("SIP").startActivity(this);
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                onFragmentSelect(new DashboardFragment());
                break;
            case 1:
                onFragmentSelect(new ContactUsFragment());
                break;

            case 2:
                openPrivacyPolicy();
                break;

            case 3:
                finish();
                break;

            case 4:
                break;

            default:
        }

    }

    public void onFragmentSelect(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationNavDrawerFragment.isDrawerOpen())
            mNavigationNavDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    private void openPrivacyPolicy() {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Constant.Privacy_policy));
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
