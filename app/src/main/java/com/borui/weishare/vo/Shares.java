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
         * id : 1
         * userId : 2
         * username : boruiz
         * realname : boruiZhu
         * personalid : 421023198808133478
         * sex : true
         * personalPicture :
         * telphone : 15007167330
         * email : borui_zhu@163.com
         * longitude : 21.23
         * latitude : 24.23
         * title : 2
         * shareTime :
         * sharePictures :
         * remark : 2
         * merchantType : 2
         * liked : 1
         * distance : 10446.721
         * collections : 0
         * pics : [{"id":1,"shareId":1,"picPath":"\\20171011\\share\\local\\user\\admin123\\20171011231316_avatar.png"}]
         */

        private int id;
        private int userId;
        private String username;
        private String realname;
        private String personalid;
        private boolean sex;
        private String personalPicture;
        private String telphone;
        private String email;
        private double longitude;
        private double latitude;
        private String title;
        private String shareTime;
        private String sharePictures;
        private String remark;
        private int merchantType;
        private int liked;
        private double distance;
        private int collections;
        private List<PicsBean> pics;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getPersonalid() {
            return personalid;
        }

        public void setPersonalid(String personalid) {
            this.personalid = personalid;
        }

        public boolean isSex() {
            return sex;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }

        public String getPersonalPicture() {
            return personalPicture;
        }

        public void setPersonalPicture(String personalPicture) {
            this.personalPicture = personalPicture;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShareTime() {
            return shareTime;
        }

        public void setShareTime(String shareTime) {
            this.shareTime = shareTime;
        }

        public String getSharePictures() {
            return sharePictures;
        }

        public void setSharePictures(String sharePictures) {
            this.sharePictures = sharePictures;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getMerchantType() {
            return merchantType;
        }

        public void setMerchantType(int merchantType) {
            this.merchantType = merchantType;
        }

        public int getLiked() {
            return liked;
        }

        public void setLiked(int liked) {
            this.liked = liked;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getCollections() {
            return collections;
        }

        public void setCollections(int collections) {
            this.collections = collections;
        }

        public List<PicsBean> getPics() {
            return pics;
        }

        public void setPics(List<PicsBean> pics) {
            this.pics = pics;
        }

        public static class PicsBean {
            /**
             * id : 1
             * shareId : 1
             * picPath :
             */

            private int id;
            private int shareId;
            private String picPath;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getShareId() {
                return shareId;
            }

            public void setShareId(int shareId) {
                this.shareId = shareId;
            }

            public String getPicPath() {
                return picPath;
            }

            public void setPicPath(String picPath) {
                this.picPath = picPath;
            }
        }
    }
}
