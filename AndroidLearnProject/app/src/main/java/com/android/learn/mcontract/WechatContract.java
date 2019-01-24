package com.android.learn.mcontract;

import com.android.learn.base.mmodel.BannerListData;
import com.android.learn.base.mmodel.FeedArticleListData;
import com.android.learn.base.mmodel.FeedArticleListData.FeedArticleData;
import com.android.learn.base.mmodel.WxArticle;
import com.android.learn.base.mview.BaseView;

import java.util.List;

/**
 * Created by gaolei on 2018/6/18.
 */

public class WechatContract {

    public interface Presenter {

        void getWxArticle();

    }

    public interface View extends BaseView {

        void showWxArticle(List<WxArticle> list);


    }
}
