package test.juyoufuli.com.myapplication.app.utils;


import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import test.juyoufuli.com.myapplication.R;


/**
 * 类名：BaseShowToast
 *
 * @author 戴小刚<br   />
 * 实现的主要功能:
 * 创建日期：2014-9-28
 * 修改者，修改日期，修改内容。
 */
public class BaseToastView extends Toast {

    private static BaseToastView base_toastview;

    public BaseToastView(Context context) {
        super(context);
    }

    /**
     * 创建位于屏幕下方的 toast
     *
     * @param context
     * @param iconResId
     * @param text
     * @param duration
     * @return
     */
    public static BaseToastView makeTextAtBottom(Context context, int iconResId, CharSequence text, int duration) {
        BaseToastView result = new BaseToastView(context);

        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.base_view_basetoast, null);
        result.setView(v);
        if (iconResId > 0) {
            result.setIcon(iconResId);
        }
        result.setText(text);
//        result.setGravity(Gravity.BOTTOM, 0, 120);
//        result.setDuration(duration);
        result.setGravity(Gravity.CENTER, 0, 0);
        result.setDuration(duration);

        return result;
    }

    public void setIcon(int iconResId) {
        if (getView() == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        ImageView iv = (ImageView) getView().findViewById(R.id.tips_icon);
        if (iv == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        try {
            iv.setImageResource(iconResId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setText(CharSequence s) {
        if (getView() == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        TextView tv = (TextView) getView().findViewById(R.id.tips_msg);
        if (tv == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        try {
            tv.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, int iconResId, String msg) {
        toastShow(context, iconResId, msg);
    }

    private static void toastShow(Context context, int iconResId, String msg) {
        if (base_toastview != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                base_toastview.cancel();
            }
        } else {
            base_toastview = BaseToastView.makeTextAtBottom(context, iconResId, msg, 3000);
        }
        base_toastview.setText(msg);
        base_toastview.show();
//        BaseToastView.makeTextAtBottom(mContext, iconResId, msg, BaseToastView.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String msg) {
        toastShow(context, 0, msg);
    }

}
