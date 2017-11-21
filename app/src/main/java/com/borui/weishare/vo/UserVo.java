package com.borui.weishare.vo;

import java.util.List;

/**
 * Created by borui on 2017/9/29.
 */

public class UserVo extends BaseVo {

    /**
     * data : {"personalPicture":"/weshare/upload/pic/20171109/user/boruim1/20171109202223_aHR0cDovL2NkbjJ3b2FzbmFwZ3JhbXdvYWNvL2ltZ3MvMjAxNS8wNy8wNy9pbWFnZTA1MndvYWpwZw==.jpg","sex":true,"auths":[{"id":5,"authenticationCode":"2088602002554514","authenticationNickname":null,"authenticationTime":"2017-11-19 00:00:00","authenticationType":"1","userId":9}],"personalid":"421023198808133478","telphone":"15007167330","id":9,"balance":0,"authenticationStatus":"1","username":"boruim1","email":"borui_zhu@163.com","roles":"3","realname":"boruiZhu"}
     */

    private UserBean data;

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public static class UserBean {
        /**
         * personalPicture : /weshare/upload/pic/20171109/user/boruim1/20171109202223_aHR0cDovL2NkbjJ3b2FzbmFwZ3JhbXdvYWNvL2ltZ3MvMjAxNS8wNy8wNy9pbWFnZTA1MndvYWpwZw==.jpg
         * sex : true
         * auths : [{"id":5,"authenticationCode":"2088602002554514","authenticationNickname":null,"authenticationTime":"2017-11-19 00:00:00","authenticationType":"1","userId":9}]
         * personalid : 421023198808133478
         * telphone : 15007167330
         * id : 9
         * balance : 0.0
         * authenticationStatus : 1
         * username : boruim1
         * email : borui_zhu@163.com
         * roles : 3
         * realname : boruiZhu
         */

        private String personalPicture;
        private boolean sex;
        private String personalid;
        private String telphone;
        private int id;
        private double balance;
        private String authenticationStatus;
        private String username;
        private String email;
        private String roles;
        private String realname;
        private List<AuthsBean> auths;

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

        public String getPersonalid() {
            return personalid;
        }

        public void setPersonalid(String personalid) {
            this.personalid = personalid;
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

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getAuthenticationStatus() {
            return authenticationStatus;
        }

        public void setAuthenticationStatus(String authenticationStatus) {
            this.authenticationStatus = authenticationStatus;
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

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public List<AuthsBean> getAuths() {
            return auths;
        }

        public void setAuths(List<AuthsBean> auths) {
            this.auths = auths;
        }

        public static class AuthsBean {
            /**
             * id : 5
             * authenticationCode : 2088602002554514
             * authenticationNickname : null
             * authenticationTime : 2017-11-19 00:00:00
             * authenticationType : 1
             * userId : 9
             */

            private int id;
            private String authenticationCode;
            private String authenticationNickname;
            private String authenticationTime;
            private String authenticationType;
            private int userId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAuthenticationCode() {
                return authenticationCode;
            }

            public void setAuthenticationCode(String authenticationCode) {
                this.authenticationCode = authenticationCode;
            }

            public String getAuthenticationNickname() {
                return authenticationNickname;
            }

            public void setAuthenticationNickname(String authenticationNickname) {
                this.authenticationNickname = authenticationNickname;
            }

            public String getAuthenticationTime() {
                return authenticationTime;
            }

            public void setAuthenticationTime(String authenticationTime) {
                this.authenticationTime = authenticationTime;
            }

            public String getAuthenticationType() {
                return authenticationType;
            }

            public void setAuthenticationType(String authenticationType) {
                this.authenticationType = authenticationType;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
