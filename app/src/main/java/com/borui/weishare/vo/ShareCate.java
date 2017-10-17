package com.borui.weishare.vo;

import java.util.List;

/**
 * Created by borui on 2017/6/29.
 */

public class ShareCate extends BaseVo{

    private List<Dict> data;

    public List<Dict> getData() {
        return data;
    }

    public void setData(List<Dict> data) {
        this.data = data;
    }

    public static class Dict {
        /**
         * id : 2
         * pid : 1
         * dictName : 衣服
         * dictValue : YF
         * dictOrder : 1
         * dictRemark : null
         * dictStatus : 1
         */

        private int id;
        private int pid;
        private String dictName;
        private String dictValue;
        private int dictOrder;
        private Object dictRemark;
        private int dictStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getDictName() {
            return dictName;
        }

        public void setDictName(String dictName) {
            this.dictName = dictName;
        }

        public String getDictValue() {
            return dictValue;
        }

        public void setDictValue(String dictValue) {
            this.dictValue = dictValue;
        }

        public int getDictOrder() {
            return dictOrder;
        }

        public void setDictOrder(int dictOrder) {
            this.dictOrder = dictOrder;
        }

        public Object getDictRemark() {
            return dictRemark;
        }

        public void setDictRemark(Object dictRemark) {
            this.dictRemark = dictRemark;
        }

        public int getDictStatus() {
            return dictStatus;
        }

        public void setDictStatus(int dictStatus) {
            this.dictStatus = dictStatus;
        }

        @Override
        public String toString() {
            return dictName;
        }
    }
}
