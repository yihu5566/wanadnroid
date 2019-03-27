package test.juyoufuli.com.myapplication.app.listener;

/**
 * @Author : dongfang
 * @Created Time : 2019-03-27  17:03
 * @Description:
 */
public interface CustomSearchListener {
    /**
     * 文字变化
     *
     * @param s
     */
    void OnContentChangeListener(String s);

    void OnSearchButtonPressListener(Boolean b);

}
