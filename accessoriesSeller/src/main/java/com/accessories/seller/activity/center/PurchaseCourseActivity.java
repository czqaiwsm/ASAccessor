package com.accessories.seller.activity.center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.accessories.seller.activity.BaseActivity;
import com.accessories.seller.fragment.center.PurchaseCourseFragment;

public class PurchaseCourseActivity extends BaseActivity {
    private PurchaseCourseFragment mFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
    }

    private void onInitContent() {
        mFragment = (PurchaseCourseFragment) Fragment.instantiate(this,
                PurchaseCourseFragment.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }
}
