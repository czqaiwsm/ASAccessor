package com.accessories.seller.activity.center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.accessories.seller.activity.BaseActivity;
import com.accessories.seller.fragment.center.EditFragment;
import com.accessories.seller.fragment.center.WidthDrawFragment;
import com.accessories.seller.fragment.center.WidthdrawInfoFragment;

public class WidthdrawInfoActivity extends BaseActivity {
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
    }

    private void onInitContent() {
        mFragment =  Fragment.instantiate(this,
                WidthDrawFragment.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }
}