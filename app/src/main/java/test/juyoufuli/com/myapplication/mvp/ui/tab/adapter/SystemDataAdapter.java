package test.juyoufuli.com.myapplication.mvp.ui.tab.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import test.juyoufuli.com.myapplication.R;
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean;

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
public class SystemDataAdapter extends DefaultAdapter<SystemBean> {
    public SystemDataAdapter(List<SystemBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<SystemBean> getHolder(View v, int viewType) {
        return new SystemDataItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.article_system_item;
    }
}
