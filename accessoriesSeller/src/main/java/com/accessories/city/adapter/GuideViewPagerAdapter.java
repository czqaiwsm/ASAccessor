package com.accessories.city.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.accessories.city.R;
import com.accessories.city.activity.center.SellerInfoActivity;
import com.accessories.city.activity.center.ServiceProtocolActivity;
import com.accessories.city.bean.BannerImgInfo;
import com.accessories.city.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页适配器
 */
public class GuideViewPagerAdapter extends PagerAdapter {

	private AutoPlayThread autoPlayThread = null;
	private Handler uiHadler = null;
	public static boolean isAutoPlay = false;
    private ArrayList<BannerImgInfo> bannerImgInfos;
	private  boolean isNeedDot = false;
	/**
	 * @param id 引导的图片数组
	 * @param view 引导页布局文件
	 * @param context
	 */
	public GuideViewPagerAdapter(int id[], View view, Context context) {
		this.context = context;
		m_obj_view = view;
		m_i_picId = id;
		initBaseView();
	}

	public GuideViewPagerAdapter(ArrayList<BannerImgInfo> bannerImgInfos, View view, Context context) {
		this.context = context;
		this.bannerImgInfos = bannerImgInfos;
		m_obj_view = view;
		initBaseView();
	}
	public GuideViewPagerAdapter(ArrayList<BannerImgInfo> bannerImgInfos, View view, Context context,boolean isNeedDot) {
		this.context = context;
		this.bannerImgInfos = bannerImgInfos;
		this.isNeedDot = isNeedDot;
		m_obj_view = view;
		initBaseView();
	}

	private void initBaseView(){
		initView();
		intGetView();
		initDot();
	}
//	public Wz_myViewPagerAdapter(int id[],Context context){
//		this(id,LayoutInflater.from(context).inflate(R.layout.guide,null),context);
//	}

	/**
	 * 自动播放
	 * @param viewPager
	 * @param isAutoPlay 是否自动播放
	 */
	public void setAutoPlay(final ViewPager viewPager,boolean isAutoPlay) {
		GuideViewPagerAdapter.isAutoPlay = isAutoPlay;
		if (uiHadler == null) {
			uiHadler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					if(viewPager != null){
						if (viewPager.getCurrentItem() < (getCount() - 1)) {
							viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
						}else {
							viewPager.setCurrentItem(0);
						}
					}
				}
			};
		}
        if (autoPlayThread == null){
			autoPlayThread =new AutoPlayThread("autoPlayThread");
			autoPlayThread.start();
		}

	}

	/**
	 * 是否已经开始
	 * */
	public void haveStart() {
	}

	public void changeDotPos() {

	}

	/******************************** private method and param ********************************************/
	private int m_i_picId[];
    /*引导页 的个数*/
	private List<View> views = new ArrayList<View>();

	private Context context = null;

	private View m_obj_view = null;

	private ImageView m_obj_start = null;

	/*移动点，移动到哪里，当前就是那个点*/
	private ImageView m_obj_curDot = null;

	/*动点的容器*/
	private LinearLayout m_obj_dotContain = null;

	private int m_i_offset;

	private int m_i_curPos = 0;

	private  int LENGTH = 0;

	private void initView() {

//		if (LENGTH == m_i_picId.length) {
//			return;
//		}
		views.clear();
		boolean isId = false;
		if(bannerImgInfos != null && bannerImgInfos.size()>0){
			LENGTH = bannerImgInfos.size();
		}else if(m_i_picId != null){
			LENGTH = m_i_picId.length;
			isId = true;
		}
		if (LENGTH == 0){
			return;
		}
		for (int i = 0; i < LENGTH; i++) {
			if(isId){
				views.add(buildImageView(m_i_picId[i]));
			}else {
				views.add(buildImageView(bannerImgInfos.get(i).getUrl(),i));

			}
		}
	}

	private boolean initDot() {
		m_obj_dotContain.setVisibility(View.GONE);
		if(isNeedDot){
			m_obj_dotContain.setVisibility(View.VISIBLE);

		}
		m_obj_dotContain.removeAllViews();
		if (LENGTH > 0) {
			ImageView dotView;
			for (int i = 0; i < LENGTH; i++) {
				dotView = new ImageView(this.context);
				dotView.setImageResource(R.drawable.dot1_w);
				dotView.setLayoutParams(new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

				m_obj_dotContain.addView(dotView);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 初始化视图
	 */
	private void intGetView() {
		m_obj_dotContain = (LinearLayout) m_obj_view
				.findViewById(R.id.dot_contain);
		m_obj_curDot = (ImageView) m_obj_view.findViewById(R.id.cur_dot);
		m_obj_start = (ImageView) m_obj_view.findViewById(R.id.open);
		m_obj_start.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

			}
		});

		m_obj_curDot.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					public boolean onPreDraw() {
						m_i_offset = m_obj_curDot.getWidth();
						return true;
					}
				});

		m_obj_curDot.setVisibility(View.GONE);

		if(isNeedDot){
			m_obj_curDot.setVisibility(View.VISIBLE);
		}
	}

	private ImageView buildImageView(int id) {
		ImageView iv = new ImageView(context);
		buildImgLayout(iv);
		iv.setImageResource(id);
		return iv;
	}


	private ImageView buildImageView(String url,int position){
		ImageView iv = new ImageView(context);
		if(url != null){
//			todo 加载网络图片，用ImgeLoader或其他框架
			ImageLoader.getInstance().displayImage(url,iv,ImageLoaderUtil.mHallIconLoaderOptions);
			if(!TextUtils.isEmpty(bannerImgInfos.get(position).getRedirect())){
				iv.setTag(bannerImgInfos.get(position).getRedirect());
				iv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(TextUtils.isEmpty(v.getTag().toString())) return;
						Intent intent = new Intent(context, SellerInfoActivity.class);
						intent.putExtra("shopId", v.getTag().toString());
//						Intent intent = new Intent(context, ServiceProtocolActivity.class);
//						intent.setFlags(11);
//						intent.putExtra("url",v.getTag().toString());
						context.startActivity(intent);
					}
				});
			}

		}
		buildImgLayout(iv);
		return iv;
	}

	private ImageView buildImgLayout(ImageView img){

		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		img.setLayoutParams(params);
		img.setScaleType(ScaleType.FIT_XY);
		return img;
	}
	/**
	 * 导航点移动动画
	 * @param position
	 */
	public void moveCursorTo(int position) {
		position = position % LENGTH;
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation tAnim = new TranslateAnimation(m_i_offset
				* m_i_curPos, m_i_offset * position, 0, 0);

		animationSet.addAnimation(tAnim);
		animationSet.setDuration(300);
		animationSet.setFillAfter(true);
		m_obj_curDot.startAnimation(animationSet);
		m_i_curPos = position;
	}

	@Override
	public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
//		 arg0.removeView(views.get(arg1 % views.size()));

	}

	@Override
	public void finishUpdate(ViewGroup arg0) {

	}

	@Override
	public int getCount() {
		if (bannerImgInfos != null && bannerImgInfos.size() > 1) {
			return Integer.MAX_VALUE;
		}
		return views.size();

	}

	@Override
	public Object instantiateItem(ViewGroup arg0, int arg1) {
		View view = views.get(arg1 % views.size());
		arg0.removeView(view);
		((ViewPager) arg0).addView(view, 0);
		return view;

	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(ViewGroup arg0) {

	}

   public void setDotAlignBottom(int px){
	   FrameLayout dot_frame = (FrameLayout) m_obj_view.findViewById(R.id.dot_frame);

	   dot_frame.setVisibility(View.GONE);
	   if(isNeedDot){
		   dot_frame.setVisibility(View.VISIBLE);
	   }
	   /*****因为FrameLayout的父布局是RelativeLayout 所以类型为 RelativeLayout.LayoutParams****/
	   RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dot_frame.getLayoutParams());
	   /****设置布局位置****/
	   layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	   layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

	   layoutParams.bottomMargin = px;//or 	layoutParams.setMargins(0,0,0,px);
	   dot_frame.setLayoutParams(layoutParams);
   }

   class  AutoPlayThread extends HandlerThread{

	   public AutoPlayThread(String name) {
		   super(name);
	   }

	   @Override
	   protected void onLooperPrepared() {
		   super.onLooperPrepared();
		   while(isAutoPlay){
			   try {
				   Thread.sleep(3000);
				   uiHadler.removeMessages(0);
				   uiHadler.sendEmptyMessage(0);
			   } catch (InterruptedException e) {
				   e.printStackTrace();
			   }

		   }
	   }
   }

}
