package com.yanxin.store.entity;

import java.io.File;

public class PicEntity {
    private String mPath;
    private File mPicFile;
    private boolean isDefault;
    private boolean isNetWork;

    public boolean isNetWork() {
        return isNetWork;
    }

    public void setNetWork(boolean netWork) {
        isNetWork = netWork;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }

    public File getPicFile() {
        return mPicFile;
    }

    public void setPicFile(File mPicFile) {
        this.mPicFile = mPicFile;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
