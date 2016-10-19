package com.accessories.city.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import cn.jpush.android.api.JPushInterface;
import com.accessories.city.R;
import com.accessories.city.activity.login.LoginActivity;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.NetUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class SplashActivity extends BaseActivity {

	private static final String SP_FILE_NAME = "TEMPLATE";
	private static final String IS_FISRT = "is_first";

	private View m_obj_view = null;
	private ImageView imageView;
	private Bitmap bitmap = null;
	private File file;
	private int flag = 0;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		m_obj_view = LayoutInflater.from(SplashActivity.this).inflate(
				R.layout.splash, null);
		file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath()+getApplicationContext().getPackageName() + "/myFile", "splash.png");
		preferences = getSharedPreferences("splash", MODE_PRIVATE);
		initView(m_obj_view);
		setContentView(m_obj_view);
		
		UIFadeOut();
	}

	private void initView(View v) {
		imageView = (ImageView) v.findViewById(R.id.id_splash_iv1);

		if (NetUtils.isConnected(this)) {
			//flag = preferences.getInt("flag", 0);
			// getImg();
		} else {
			if (file.exists()) {
				imageView.setImageURI(Uri.fromFile(file));
			} else {
				imageView.setImageResource(R.drawable.logo_bg);
			}
		}

	}


	private void saveImag(final String url) {

		new Thread(new Runnable() {
			public void run() {

				InputStream is;
				try {
					is = new URL(url).openStream();
					bitmap = BitmapFactory.decodeStream(is);
					FileOutputStream outputStream = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
					outputStream.flush();
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}
	}


	private void UIFadeOut() {
		AlphaAnimation aa = new AlphaAnimation(0.7f, 1.0f);
		aa.setDuration(2000);
		m_obj_view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {

//				if(BaseApplication.isLogin()){
//					Intent intent = new Intent(SplashActivity.this,TeacherMainActivity.class);
//					startActivity(intent);
////            BaseApplication.getInstance().userId = BaseApplication.getUserInfo().getId();
////            toClassActivity(LoginFramgent.this, MainActivity.class.getName());//学生
//					JPushInterface.setAlias(BaseApplication.getInstance(),"t_"+BaseApplication.getUserInfo().getId(),null);
//				}else {
//					SplashActivity.this.startActivity(new Intent(
//							SplashActivity.this, LoginActivity.class));//
//				}
				if (!isFirst()) {//是否是第一次登录
//					SplashActivity.this.startActivity(new Intent(
//							SplashActivity.this, LoginActivity.class));
					if(BaseApplication.isLogin()){
					Intent intent = new Intent(SplashActivity.this,TeacherMainActivity.class);
					startActivity(intent);
//					JPushInterface.setAlias(BaseApplication.getInstance(),"t_"+BaseApplication.getUserInfo().getId(),null);
				}else {
					SplashActivity.this.startActivity(new Intent(
							SplashActivity.this, LoginActivity.class));//
				}

				} else {
					SplashActivity.this.startActivity(new Intent(
							SplashActivity.this, GuideActivity.class));
					saveIsFirst(false);
				}
                finish();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationStart(Animation animation) {

			}

		});
	}


	@Override
	public void onBackPressed() {
		return;
	}
	public Boolean isFirst() {
		if (preferences != null) {
			Boolean isDisplay = preferences.getBoolean(IS_FISRT, true);
			return isDisplay;
		}
		return null;
	}

	public void saveIsFirst(Boolean isDisplay) {
		if (preferences != null && isDisplay != null) {
			SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean(IS_FISRT, isDisplay);
			editor.commit();
		}
	}
}
