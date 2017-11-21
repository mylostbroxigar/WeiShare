package com.borui.weishare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.util.SPUtil;
import com.borui.weishare.vo.BaseVo;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by borui on 2017/11/20.
 */

public class MineActivity extends BaseActivity {
    MineAdapter adapter;
    ArrayList<ViewItem> items = new ArrayList<>();
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.layout_userinfo)
    LinearLayout layoutUserinfo;
    @BindView(R.id.list_item)
    ListView listItem;
    @BindView(R.id.btn_logout)
    Button btnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine);
        ButterKnife.bind(this);
        initView();
    }



    private void initView() {
        Glide.with(this).load(APIAddress.SERVERADDRESS + Cache.currenUser.getData().getPersonalPicture()).placeholder(R.drawable.avtar_default).error(R.drawable.avtar_default).into(civHead);
        tvUsername.setText(Cache.currenUser.getData().getUsername());
        tvTelephone.setText(Cache.currenUser.getData().getTelphone());

        items.add(new ViewItem("密码管理", R.drawable.icon_mine_password, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MineActivity.this, PasswordActivity.class));
            }
        }));
        if (Cache.currenUser.getData().getRoles().equals(RegisterActivity.ROLE_USER)) {
            items.add(new ViewItem("实名认证", R.drawable.icon_mine_personalid, "", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }));
        } else {
            items.add(new ViewItem("商户认证", R.drawable.icon_mine_personalid, "", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MineActivity.this, MerchantRegisterActivity.class));
                }
            }));

            items.add(new ViewItem("商户信息", R.drawable.icon_mine_personalid, "", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MineActivity.this, MerchantInfoActivity.class));
                }
            }));
        }

        items.add(new ViewItem("我的收藏", R.drawable.icon_mine_collect, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }));
        items.add(new ViewItem("消息通知", R.drawable.icon_mine_message, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }));
        items.add(new ViewItem("关于我们", R.drawable.icon_mine_aboutus, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }));
        adapter = new MineAdapter(items);
        listItem.setAdapter(adapter);
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                items.get(position).onClickListener.onClick(view);
            }
        });
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo baseVo) {
    }

    @OnClick({R.id.iv_back, R.id.layout_userinfo, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.layout_userinfo:
                break;
            case R.id.btn_logout:
                SPUtil.insertBoolean(this, SPUtil.KEY_LOGINED, false);
                Cache.currenUser = null;
                setResult(501);
                finish();
                break;
        }
    }

    private class MineAdapter extends BaseAdapter {
        ArrayList<ViewItem> items;

        public MineAdapter(ArrayList<ViewItem> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(MineActivity.this).inflate(R.layout.layout_mine_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            ViewItem item = items.get(position);
            holder.ivIcon.setImageResource(item.icon);
            holder.tvTitle.setText(item.title);
            if (TextUtils.isEmpty(item.desc)) {
                holder.tvInfo.setVisibility(View.GONE);
            } else {
                holder.tvInfo.setText(item.desc);
            }
            return convertView;
        }


    }

    static class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_info)
        TextView tvInfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewItem {
        public ViewItem(String title, int icon, String desc, View.OnClickListener l) {
            this.title = title;
            this.icon = icon;
            this.desc = desc;
            this.onClickListener = l;
        }

        String title;
        int icon;
        String desc;
        View.OnClickListener onClickListener;
    }
}
