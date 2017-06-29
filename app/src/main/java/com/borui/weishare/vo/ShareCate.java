package com.borui.weishare.vo;

import java.util.List;

/**
 * Created by borui on 2017/6/29.
 */

public class ShareCate extends BaseVo{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * catename : 美食
         * catecode : 1
         */

        private String catename;
        private int catecode;

        public String getCatename() {
            return catename;
        }

        public void setCatename(String catename) {
            this.catename = catename;
        }

        public int getCatecode() {
            return catecode;
        }

        public void setCatecode(int catecode) {
            this.catecode = catecode;
        }
    }
}
