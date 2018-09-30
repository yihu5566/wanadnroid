package test.juyoufuli.com.myapplication.mvp.ui.home.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import test.juyoufuli.com.myapplication.R;
import test.juyoufuli.com.myapplication.mvp.entity.Datas;

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
public class ArticleAdapter extends DefaultAdapter<Datas> {
    public ArticleAdapter(List<Datas> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<Datas> getHolder(View v, int viewType) {
        ArticleItemHolder articleItemHolder = new ArticleItemHolder(v);
        return articleItemHolder;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.article_item;
    }
}
