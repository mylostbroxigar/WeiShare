package com.borui.weishare.vo;

/**
 * Created by borui on 2017/11/13.
 */

public class ImagePath {
    String path;
    String dataname;

    public ImagePath(String path, String dataname) {
        this.path = path;
        this.dataname = dataname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDataname() {
        return dataname;
    }

    public void setDataname(String dataname) {
        this.dataname = dataname;
    }
}
