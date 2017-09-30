package com.borui.weishare.vo;

import java.util.List;

/**
 * Created by borui on 2017/6/30.
 */

public class Shares extends BaseVo {

    private List<ShareItem> data;

    public List<ShareItem> getData() {
        return data;
    }

    public void setData(List<ShareItem> data) {
        this.data = data;
    }

    public static class ShareItem {
        /**
         * shareId : 10001
         * cover : url
         * comment : 评论
         * uid : 10000001
         * nickname : 昵称
         * head : url
         * sign : 签名
         * collectnum : 35
         * likenum : 24
         * iscollect : 0
         * islike : 0
         * longitude : 314.554545
         * latitude : 135.155554
         */

        private String shareId;
        private String cover;
        private String comment;
        private String uid;
        private String nickname;
        private String head;
        private String sign;
        private int collectnum;
        private int likenum;
        private int iscollect;
        private int islike;
        private double longitude;
        private double latitude;

        public String getShareId() {
            return shareId;
        }

        public void setShareId(String shareId) {
            this.shareId = shareId;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getCollectnum() {
            return collectnum;
        }

        public void setCollectnum(int collectnum) {
            this.collectnum = collectnum;
        }

        public int getLikenum() {
            return likenum;
        }

        public void setLikenum(int likenum) {
            this.likenum = likenum;
        }

        public int getIscollect() {
            return iscollect;
        }

        public void setIscollect(int iscollect) {
            this.iscollect = iscollect;
        }

        public int getIslike() {
            return islike;
        }

        public void setIslike(int islike) {
            this.islike = islike;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }
}
