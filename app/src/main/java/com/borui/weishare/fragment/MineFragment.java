package com.borui.weishare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.borui.weishare.MainActivity;
import com.borui.weishare.R;
import com.borui.weishare.net.APIAddress;
import com.borui.weishare.net.Cache;
import com.borui.weishare.util.SPUtil;
import com.borui.weishare.vo.BaseVo;
import com.borui.weishare.vo.UserVo;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by borui on 2017/6/29.
 */

public class MineFragment extends BaseFragment {

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
    Unbinder unbinder;

    MineAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, view);
        adapter=new MineAdapter();
        listItem.setAdapter(adapter);
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"tap "+position,Toast.LENGTH_SHORT).show();
            }
        });

        if (checkLogin()) {
            initView();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        Glide.with(getActivity()).load(APIAddress.SERVERADDRESS + Cache.currenUser.getData().getPersonalPicture()).into(civHead);
        tvUsername.setText(Cache.currenUser.getData().getUsername());
        tvTelephone.setText(Cache.currenUser.getData().getTelphone());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.layout_userinfo, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_userinfo:
                Toast.makeText(getContext(),"tap userinfo",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_logout:
                SPUtil.insertBoolean(getContext(),SPUtil.KEY_LOGINED,false);
                MainActivity activity=(MainActivity)getActivity();
                activity.checkMenu(0);
                Cache.currenUser=null;
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(BaseVo baseVo) {
    }

    private class MineAdapter extends BaseAdapter {
        int[] icons = new int[]{
                R.drawable.icon_mine_password,
                R.drawable.icon_mine_personalid,
                R.drawable.icon_mine_contact,
                R.drawable.icon_mine_suggest,
                R.drawable.icon_mine_verison,
                R.drawable.icon_mine_msg};
        String[] titles = new String[]{
                "密码管理",
                "实名认证",
                "联系客服",
                "宝贵建议",
                "版本信息",
                "消息通知"
        };
        String[] infos = new String[]{
                "",
                "认证通过",
                "",
                "",
                "",
                ""
        };

        @Override
        public int getCount() {
            return 6;
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_mine_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder.ivIcon.setImageResource(icons[position]);
            holder.tvTitle.setText(titles[position]);
            if(TextUtils.isEmpty(infos[position])){
                holder.tvInfo.setVisibility(View.GONE);
            }else{
                holder.tvInfo.setText(infos[position]);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_LOGIN){
            if(Cache.currenUser==null){
                MainActivity activity=(MainActivity)getActivity();
                activity.checkMenu(0);
            }else{
                initView();
            }
        }
    }
}
