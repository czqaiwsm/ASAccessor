package com.accessories.seller.activity.center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.accessories.seller.activity.BaseActivity;
import com.accessories.seller.fragment.center.OrederDetailFragment;

public class OrderDetailActivity extends BaseActivity {
    private OrederDetailFragment mFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
    }

    private void onInitContent() {
        mFragment = (OrederDetailFragment) Fragment.instantiate(this,
                OrederDetailFragment.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }
}
