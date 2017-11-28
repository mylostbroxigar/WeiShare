package com.borui.weishare.net;

import android.support.annotation.NonNull;

import com.borui.weishare.vo.ShareCate;
import com.borui.weishare.vo.Shares;
import com.borui.weishare.vo.UserVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by borui on 2017/9/29.
 */

public class Cache {
    private static Cache thisCache;
    private UserVo currenUser;
    private ShareCate shareCate;
    private Map<Integer,List<Shares.ShareItem>> shareCache;

    private Cache(){

    }
    public static Cache getInstance(){
        if(thisCache==null)
            thisCache=new Cache();
        return thisCache;
    }

    public UserVo getCurrenUser() {
        return currenUser;
    }

    public void setCurrenUser(UserVo currenUser) {
        this.currenUser = currenUser;
    }

    public ShareCate getShareCate() {
        return shareCate;
    }

    public void setShareCate(ShareCate shareCate) {
        this.shareCate = shareCate;
        if(shareCache!=null)
            shareCache.clear();
        shareCache=new HashMap<>();
        for (ShareCate.Dict data : shareCate.getData()) {
            shareCache.put(data.getId(), new ArrayList<Shares.ShareItem>());
        }
    }

//    public Map<Integer, List<Shares.ShareItem>> getShareCache() {
//        return shareCache;
//    }
//
//    public void setShareCache(Map<Integer, List<Shares.ShareItem>> shareCache) {
//        this.shareCache = shareCache;
//    }

    public void inputShareCache(int cate,List<Shares.ShareItem> shareCacheList,boolean replace){
        if(replace){
            this.shareCache.get(cate).clear();
            if(shareCacheList.size()>=2){
                //保证第1个元素高度小于第2个元素
                Shares.ShareItem.PicsBean p1=shareCacheList.get(0).getPics().get(0);
                Shares.ShareItem.PicsBean p2=shareCacheList.get(1).getPics().get(0);
                if(p1.getPicHeight()*1000/p1.getPicWidth()>p2.getPicHeight()*1000/p2.getPicWidth()){

                    Shares.ShareItem tmp=shareCacheList.get(0);
                    shareCacheList.set(0,shareCacheList.get(1));
                    shareCacheList.set(1,tmp);
                }
            }
        }
        this.shareCache.get(cate).addAll(shareCacheList);
    }

    public List<Shares.ShareItem> getShareCache(int cate){
        return this.shareCache.get(cate);
    }
    public void addLike(int shareId){

        for (Map.Entry<Integer, List<Shares.ShareItem>> entry : shareCache.entrySet()) {

            for (Shares.ShareItem shareItem:entry.getValue()) {
                if(shareItem.getId()==shareId){
                    shareItem.setThumbs(shareItem.getThumbs()+1);
                    return;
                }
            }

        }
    }
    public void addCollect(int shareId){

        for (Map.Entry<Integer, List<Shares.ShareItem>> entry : shareCache.entrySet()) {

            for (Shares.ShareItem shareItem:entry.getValue()) {
                if(shareItem.getId()==shareId){
                    shareItem.setCollections(shareItem.getCollections()+1);
                    return;
                }
            }

        }
    }
}
