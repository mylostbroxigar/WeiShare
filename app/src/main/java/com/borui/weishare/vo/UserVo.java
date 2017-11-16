package com.borui.weishare.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by borui on 2017/9/29.
 */

public class UserVo extends BaseVo {

    /**
     * code : 0
     * data : {"id":3,"username":"boruiz2","password":"8cd645ef29e0b28acbe102aa0b0517df5c4e604a92d5df5b3d95c1bd11aec0f2b441e65d586cb625c923b185af8137c922947bcead7ce0a46299c29d","realname":"boruiZhu","personalid":"421023198808133478","sex":true,"personalPicture":"/weshare/upload/pic/20170928/user/boruiz2/20170928212646_7c19cddb82a375c7217ddfc0b83a94db.jpg","telphone":"15007167330","email":"borui_zhu@163.com","roles":"4","balance":null,"status":true}
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
         * id : 3
         * username : boruiz2
         * password : 8cd645ef29e0b28acbe102aa0b0517df5c4e604a92d5df5b3d95c1bd11aec0f2b441e65d586cb625c923b185af8137c922947bcead7ce0a46299c29d
         * realname : boruiZhu
         * personalid : 421023198808133478
         * sex : true
         * personalPicture : /weshare/upload/pic/20170928/user/boruiz2/20170928212646_7c19cddb82a375c7217ddfc0b83a94db.jpg
         * telphone : 15007167330
         * email : borui_zhu@163.com
         * roles : 4
         * balance : null
         * status : true
         */

        private int id;
        private String username;
        private String password;
        private String realname;
        private String personalid;
        private boolean sex;
        private String personalPicture;
        private String telphone;
        private String email;
        private String roles;
        private double balance;
        private boolean status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}
