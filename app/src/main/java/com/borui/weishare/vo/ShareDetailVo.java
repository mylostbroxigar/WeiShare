package com.borui.weishare.vo;

import java.util.List;

/**
 * Created by borui on 2017/11/16.
 */

public class ShareDetailVo extends BaseVo {

    /**
     * data : {"thumbs":0,"personalPicture":"/20171018/user/boruiz9/20171018130739_ic_default_share.png","sex":true,"remark":"","liked":0,"shareTime":"2017-11-14 20:35:55","personalid":"421023198808133478","sharePictures":"","telphone":"15007167330","id":44,"thumbed":false,"pics":[{"picWidth":240,"picPath":"/20171114/share/local/user/boruiz9/20171114203555_weshare_1510662949185.jpg","id":12,"shareId":44,"picHeight":360},{"picWidth":null,"picPath":"/20171114/share/local/user/boruiz9/20171114203555_weshare_1510662949229.jpg","id":13,"shareId":44,"picHeight":null}],"distance":2.2133017,"title":"ejje","username":"boruiz9","email":"borui_zhu@163.com","merchantType":"2","userId":2,"collectioned":false,"longitude":"114.40323","latitude":"30.492043","realname":"boruiZhu","collections":1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * thumbs : 0
         * personalPicture : /20171018/user/boruiz9/20171018130739_ic_default_share.png
         * sex : true
         * remark :
         * liked : 0
         * shareTime : 2017-11-14 20:35:55
         * personalid : 421023198808133478
         * sharePictures :
         * telphone : 15007167330
         * id : 44
         * thumbed : false
         * pics : [{"picWidth":240,"picPath":"/20171114/share/local/user/boruiz9/20171114203555_weshare_1510662949185.jpg","id":12,"shareId":44,"picHeight":360},{"picWidth":null,"picPath":"/20171114/share/local/user/boruiz9/20171114203555_weshare_1510662949229.jpg","id":13,"shareId":44,"picHeight":null}]
         * distance : 2.2133017
         * title : ejje
         * username : boruiz9
         * email : borui_zhu@163.com
         * merchantType : 2
         * userId : 2
         * collectioned : false
         * longitude : 114.40323
         * latitude : 30.492043
         * realname : boruiZhu
         * collections : 1
         */

        private int thumbs;
        private String personalPicture;
        private boolean sex;
        private String remark;
        private int liked;
        private String shareTime;
        private String personalid;
        private String sharePictures;
        private String telphone;
        private int id;
        private boolean thumbed;
        private double distance;
        private String title;
        private String username;
        private String email;
        private String merchantType;
        private int userId;
        private boolean collectioned;
        private String longitude;
        private String latitude;
        private String realname;
        private int collections;
        private List<PicsBean> pics;

        public int getThumbs() {
            return thumbs;
        }

        public void setThumbs(int thumbs) {
            this.thumbs = thumbs;
        }

        public String getPersonalPicture() {
            return personalPicture;
        }

        public void setPersonalPicture(String personalPicture) {
            this.personalPicture = personalPicture;
        }

        public boolean isSex() {
            return sex;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getLiked() {
            return liked;
        }

        public void setLiked(int liked) {
            this.liked = liked;
        }

        public String getShareTime() {
            return shareTime;
        }

        public void setShareTime(String shareTime) {
            this.shareTime = shareTime;
        }

        public String getPersonalid() {
            return personalid;
        }

        public void setPersonalid(String personalid) {
            this.personalid = personalid;
        }

        public String getSharePictures() {
            return sharePictures;
        }

        public void setSharePictures(String sharePictures) {
            this.sharePictures = sharePictures;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isThumbed() {
            return thumbed;
        }

        public void setThumbed(boolean thumbed) {
            this.thumbed = thumbed;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMerchantType() {
            return merchantType;
        }

        public void setMerchantType(String merchantType) {
            this.merchantType = merchantType;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public boolean isCollectioned() {
            return collectioned;
        }

        public void setCollectioned(boolean collectioned) {
            this.collectioned = collectioned;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
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
             * picWidth : 240
             * picPath : /20171114/share/local/user/boruiz9/20171114203555_weshare_1510662949185.jpg
             * id : 12
             * shareId : 44
             * picHeight : 360
             */

            private int picWidth;
            private String picPath;
            private int id;
            private int shareId;
            private int picHeight;

            public int getPicWidth() {
                return picWidth;
            }

            public void setPicWidth(int picWidth) {
                this.picWidth = picWidth;
            }

            public String getPicPath() {
                return picPath;
            }

            public void setPicPath(String picPath) {
                this.picPath = picPath;
            }

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

            public int getPicHeight() {
                return picHeight;
            }

            public void setPicHeight(int picHeight) {
                this.picHeight = picHeight;
            }
        }
    }
}
