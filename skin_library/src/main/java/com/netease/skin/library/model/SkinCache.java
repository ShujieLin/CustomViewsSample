package com.netease.skin.library.model;

import android.content.res.Resources;

public class SkinCache {

    /**
     * 用于加载皮肤资源
     */
    private Resources skinResource;

    /**
     * 皮肤资源所在包名（住：皮肤包不在app内，也不限定包名）
     */
    private String skinPackageName;

    public SkinCache(Resources skinResource,String skinPackageName){
        this.skinResource = skinResource;
        this.skinPackageName = skinPackageName;
    }

    public Resources getSkinResource() {
        return skinResource;
    }

    public String getSkinPackageName() {
        return skinPackageName;
    }


}
