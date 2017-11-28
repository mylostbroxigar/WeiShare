package com.borui.weishare;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.net.VolleyUtil;
import com.borui.weishare.util.ImageUtil;
import com.borui.weishare.util.SPUtil;
import com.borui.weishare.view.ImageFragment;
import com.borui.weishare.view.ImageItemFragment;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.ShareDetailVo;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zhuborui on 2017/11/15.
 */

public class ShareDetailActivity extends BaseActivity {
    @BindView(R.id.layout_content)
    FrameLayout layoutContent;
    @BindView(R.id.tv_share_comment)
    TextView tvShareComment;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    String shareId;
    ShareDetailVo shareDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail);
        ButterKnife.bind(this);

        shareId = getIntent().getStringExtra("shareId");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", Cache.getInstance().getCurrenUser().getMsg());
        params.put("userId",Cache.getInstance().getCurrenUser().getData().getId() + "");
        params.put("shareId", shareId);
        params.put("longitude", SPUtil.getString(this, SPUtil.KEY_LONGITUDE));
        params.put("latitude", SPUtil.getString(this, SPUtil.KEY_LATITUDE));
        showProgress("");
        VolleyUtil.getInstance().doPost(APIAddress.LOCAL_SHARE_DETAIL, params, new TypeToken<ShareDetailVo>() {
        }.getType(), "shareDetail");
    }

    @OnClick({R.id.iv_like, R.id.iv_collect, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_like:
                like();
                break;
            case R.id.iv_collect:
                collect();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void like() {
        if (shareDetail == null)
            return;

        ivLike.setClickable(false);
        ivLike.setImageResource(R.drawable.icon_thumb_select);
        Map<String, String> params = new HashMap<>();
        params.put("token",Cache.getInstance().getCurrenUser().getMsg());
        params.put("shareId", shareId);
//        params.put("userId",Cache.currenUser.getData().getId()+"");
        VolleyUtil.getInstance().doPost(APIAddress.LIKE, params, new TypeToken<BaseVo>() {
        }.getType(), "like");
    }

    private void collect() {

        if (shareDetail == null)
            return;
        ivCollect.setClickable(false);
        ivCollect.setImageResource(R.drawable.icon_collect_select);
        Map<String, String> params = new HashMap<>();
        params.put("token",Cache.getInstance().getCurrenUser().getMsg());
        params.put("shareId", shareId);
        params.put("userId",Cache.getInstance().getCurrenUser().getData().getId() + "");
        VolleyUtil.getInstance().doPost(APIAddress.COLLECTION, params, new TypeToken<BaseVo>() {
        }.getType(), "collect");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo obj) {
        dismissProgress();
        if (obj instanceof ShareDetailVo) {
            onResult((ShareDetailVo) obj);
        } else {
            if (obj.getTag().equals("like")) {
                if (obj.getCode().equals("0")) {
                    Cache.getInstance().addLike(Integer.parseInt(shareId));
                    tvLike.setText((shareDetail.getData().getLiked() + 1) + "");
                    Toast.makeText(this, "已赞", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, obj.getMsg(), Toast.LENGTH_SHORT).show();
                    ivLike.setClickable(true);
                    ivLike.setImageResource(R.drawable.icon_thumb_normal);
                }
            }
            if (obj.getTag().equals("collect")) {
                if (obj.getCode().equals("0")) {
                    Cache.getInstance().addCollect(Integer.parseInt(shareId));
                    tvCollect.setText((shareDetail.getData().getCollections() + 1) + "");
                    Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, obj.getMsg(), Toast.LENGTH_SHORT).show();
                    ivCollect.setClickable(true);
                    ivCollect.setImageResource(R.drawable.icon_collect_normal);
                }
            }
        }
    }

    public void onResult(ShareDetailVo shareDetailVo) {

        if (shareDetailVo.getCode().equals("0")) {

            this.shareDetail = shareDetailVo;
//            Glide.with(context).load(APIAddress.IMAGEPATH +shareItem.getPics().get(0).getPicPath()).thumbnail(0.1f).error(ImageUtil.getErrorDrawable(context,layoutParams.width,layoutParams.height)).into(holder.ivShareThumb);
            Glide.with(this).load(APIAddress.IMAGEPATH + shareDetail.getData().getPersonalPicture()).placeholder(R.drawable.avtar_default).error(R.drawable.avtar_default).into(ivHead);


            tvShareComment.setText(shareDetail.getData().getTitle());
            tvLike.setText("" + shareDetail.getData().getThumbs());
            tvCollect.setText("" + shareDetail.getData().getCollections());
            tvName.setText(shareDetail.getData().getRealname());
            tvLocation.setText(getDistance(shareDetail.getData().getDistance()));
            ivLike.setClickable(!shareDetail.getData().isThumbed());
            ivLike.setImageResource(shareDetail.getData().isThumbed() ? R.drawable.icon_thumb_select : R.drawable.icon_thumb_normal);
            ivCollect.setClickable(!shareDetail.getData().isCollectioned());
            ivCollect.setImageResource(shareDetail.getData().isCollectioned() ? R.drawable.icon_collect_select : R.drawable.icon_collect_normal);
            ImageItemFragment.error_drawable = ImageUtil.getErrorDrawable(this, layoutContent.getWidth(), layoutContent.getHeight());
            ArrayList<String> imagePaths = new ArrayList<>();
            for (ShareDetailVo.DataBean.PicsBean pic : shareDetail.getData().getPics()) {
                imagePaths.add(pic.getPicPath());
            }
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("imagePaths", imagePaths);
            imageFragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.layout_content, imageFragment);
            ft.commit();
        }
    }

    private String getDistance(double distance) {
        if (distance <= 1d) {
            return "<1KM";
        } else {
            return (int) distance + "KM";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageItemFragment.error_drawable = null;
    }
}
