package com.accessories.city.activity.center;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.accessories.city.activity.BaseActivity;
import com.accessories.city.activity.login.LoginActivity;
import com.accessories.city.fragment.center.ResetPassFragment;

public class ResetPassActivity extends BaseActivity {
    private ResetPassFragment mFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        onInitContent();
        count++;

    }

    private void onInitContent() {
        mFragment = (ResetPassFragment) Fragment.instantiate(this,
                ResetPassFragment.class.getName());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, mFragment);
        ft.commit();
    }

    int count = 0;
    public static boolean exit = false;

    @Override
    public void finish() {
        super.finish();
        if(count == 1 && exit) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        exit = false;
    }
}
