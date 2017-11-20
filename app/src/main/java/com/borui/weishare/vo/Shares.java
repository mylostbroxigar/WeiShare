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

    public static class ShareItem{
        /**
         * thumbs : 1
         * personalPicture : /20171018/user/boruiz9/20171018130739_ic_default_share.png
         * id : 46
         * thumbed : false
         * pics : [{"picWidth":720,"picPath":"/20171116/share/local/user/boruiz9/20171116212418_weshare_1510838654418.jpg","id":19,"shareId":46,"picHeight":480},{"picWidth":720,"picPath":"/20171116/share/local/user/boruiz9/20171116212418_weshare_1510838654452.jpg","id":20,"shareId":46,"picHeight":224}]
         * distance : 0.0106847
         * title : uuuu
         * username : boruiz9
         * collectioned : false
         * realname : boruiZhu
         * collections : 0
         */

        private int thumbs;
        private String personalPicture;
        private int id;
        private boolean thumbed;
        private double distance;
        private String title;
        private String username;
        private boolean collectioned;
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

        public boolean isCollectioned() {
            return collectioned;
        }

        public void setCollectioned(boolean collectioned) {
            this.collectioned = collectioned;
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
             * picWidth : 720
             * picPath : /20171116/share/local/user/boruiz9/20171116212418_weshare_1510838654418.jpg
             * id : 19
             * shareId : 46
             * picHeight : 480
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
