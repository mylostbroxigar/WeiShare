package com.borui.weishare.net;

import android.support.annotation.NonNull;

import com.borui.weishare.vo.ShareCate;
import com.borui.weishare.vo.Shares;
import com.borui.weishare.vo.UserVo;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by borui on 2017/9/29.
 */

public class Cache {
    public static UserVo currenUser;
    public static ShareCate shareCate;
    public static Map<Integer,List<Shares.ShareItem>> shareCache=new HashMap<Integer, List<Shares.ShareItem>>();
}
