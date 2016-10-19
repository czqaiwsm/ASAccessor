package com.accessories.city.activity.center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.accessories.city.activity.BaseActivity;
import com.accessories.city.fragment.center.PcenterModifyInfoIAFragment;

public class PCenterModifyInfoActivity extends BaseActivity {
    private PcenterModifyInfoIAFragment mFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
    }

    private void onInitContent() {
        mFragment = (PcenterModifyInfoIAFragment) Fragment.instantiate(this,
                PcenterModifyInfoIAFragment.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }
}
