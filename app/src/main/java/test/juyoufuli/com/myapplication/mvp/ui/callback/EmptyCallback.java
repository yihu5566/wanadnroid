package test.juyoufuli.com.myapplication.mvp.ui.callback;

import com.kingja.loadsir.callback.Callback;

import test.juyoufuli.com.myapplication.R;

/**
 * @Author : dongfang
 * @Created Time : 2019-05-20  16:49
 * @Description:
 */
public class EmptyCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.empty_layout;
    }
}
