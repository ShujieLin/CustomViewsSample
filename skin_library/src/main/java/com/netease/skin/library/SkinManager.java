package com.netease.skin.library;

import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import com.netease.skin.library.model.SkinCache;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
public class SkinManager {

    /**
     * 方法名
     */
    private static final String ADD_ASSET_PATH = "addAssetPath";

    private static SkinManager mInstance;
    private Application mApplication;
    /**
     * 用于加载app内置资源
     */
    private Resources mAppResources;
    /**
     * 用于加载皮肤包资源
     */
    private Resources mSkinResources;
    /**
     * 皮肤包资源所在包名（注：皮肤包不在app内，也不限包名）
     */
    private String mSkinPackageName;
    /**
     * 应用默认皮肤（app内置）
     */
    private boolean mIsDefaultSkin = true;

    private Map<String, SkinCache> mCacheSkin;

    public SkinManager(Application application) {
        mApplication = application;
        mAppResources = application.getResources();
        mCacheSkin = new HashMap<>();
    }

    private static void init(Application application){
        if (mInstance == null){
            synchronized (SkinManager.class){
                if (mInstance == null){
                    mInstance = new SkinManager(application);
                }
            }
        }
    }

    public static SkinManager getInstance(){
        return mInstance;
    }

    private static final String TAG = "SkinManager";

    public void loaderSkinResourses(String skinPath){
        try {
            //创建资源管理器
            AssetManager assetManager = AssetManager.class.newInstance();
            //被@hide，目前只能通过反射去拿
            Method addAssetPath = assetManager.getClass().getDeclaredMethod(ADD_ASSET_PATH,String.class);
            //设置私有方法可访问
            addAssetPath.setAccessible(true);
            //==============================================================================
            // 如果还是担心@hide限制，可以反射addAssetPathInternal()方法，参考源码366行 + 387行
            //==============================================================================

            // 创建加载外部的皮肤包(net163.skin)文件Resources（注：依然是本应用加载）
            mSkinResources = new Resources(
                    assetManager,
                    mAppResources.getDisplayMetrics(),
                    mAppResources.getConfiguration()
                    );

            //根据apk文件路径（皮肤包也是apk文件），获取该应用的包名。兼容5.0 - 9.0
            mSkinPackageName = mApplication.getPackageManager()
                    .getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;

            //无法获取皮肤包应用的包名，则加载app内置资源
            mIsDefaultSkin = TextUtils.isEmpty(mSkinPackageName);
            if (!mIsDefaultSkin){
                mCacheSkin.put(skinPath,new SkinCache(mSkinResources,mSkinPackageName));
            }
            Log.e(TAG, "loaderSkinResourses: mSkinPackageName >>>" + mSkinPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            // 发生异常，预判：通过skinPath获取skinPacakageName失败！
            mIsDefaultSkin = true;
        }
    }


    private int getSkinResourceIds(int resourceId){
        // 优化：如果没有皮肤包或者没做换肤动作，直接返回app内置资源！
        if (mIsDefaultSkin) return resourceId;

        // 使用app内置资源加载，是因为内置资源与皮肤包资源一一对应（“netease_bg”, “drawable”）
        String resourceName = mAppResources.getResourceEntryName(resourceId);
        String resourceType = mAppResources.getResourceTypeName(resourceId);

        //动态获取皮肤包内指定资源ID
        int skinResourceId = mSkinResources.getIdentifier(resourceName,resourceType,mSkinPackageName);

        // 源码1924行：(0 is not a valid resource ID.)
        mIsDefaultSkin = skinResourceId == 0;
        return skinResourceId == 0 ? resourceId : skinResourceId;
    }


}
