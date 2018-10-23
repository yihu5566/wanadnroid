package test.juyoufuli.com.myapplication.mvp.ui.home.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import javax.inject.Inject;

import test.juyoufuli.com.myapplication.R;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean;

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
public class ArticleAdapter extends DefaultAdapter<ArticleBean> {
    private ArticleItemHolder.ChildClickListener mChildClickListener;

    @Inject
    public ArticleAdapter(List<ArticleBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<ArticleBean> getHolder(View v, int viewType) {
        ArticleItemHolder articleItemHolder = new ArticleItemHolder(v, mChildClickListener);
        return articleItemHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.article_item;
    }

    public void setChildClickListener(ArticleItemHolder.ChildClickListener mChildClickListener) {
        this.mChildClickListener = mChildClickListener;
    }
}
