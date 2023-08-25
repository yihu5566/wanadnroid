package test.juyoufuli.com.myapplication.app.ext

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.Gravity
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import razerdp.basepopup.QuickPopupBuilder
import razerdp.basepopup.QuickPopupConfig
import razerdp.widget.QuickPopup
import test.juyoufuli.com.myapplication.R


/**
 * @author : hgj
 * @date : 2020/6/28
 */
private var loadingDialog: QuickPopup? = null

/**
 * 打开等待框
 */
fun AppCompatActivity.showLoadingExt(message: String = "请求网络中") {
    if (!this.isFinishing) {
        if (loadingDialog == null) {
            loadingDialog = QuickPopupBuilder.with(this)
                .contentView(R.layout.layout_custom_progress_dialog_view)
                .config(
                    QuickPopupConfig().gravity(Gravity.CENTER).outSideDismiss(false)
                        .backpressEnable(true)
                        .backpressEnable(false)
                        .backgroundColor(Color.TRANSPARENT)
                )
                .build()

            loadingDialog?.contentView?.run {
                this.findViewById<TextView>(R.id.loading_tips).text = message
                this.findViewById<ProgressBar>(R.id.progressBar).indeterminateTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            }
        }
        loadingDialog?.showPopupWindow()
    }
}

/**
 * 打开等待框
 */
fun Fragment.showLoadingExt(message: String = "请求网络中") {
    activity?.let {
        if (!it.isFinishing) {
            if (loadingDialog == null) {
                loadingDialog = QuickPopupBuilder.with(this)
                    .contentView(R.layout.layout_custom_progress_dialog_view)
                    .config(
                        QuickPopupConfig().gravity(Gravity.CENTER).outSideDismiss(false)
                            .backpressEnable(true)
                            .backpressEnable(false)
                            .backgroundColor(Color.TRANSPARENT)
                    )
                    .build()

                loadingDialog?.contentView?.run {
                    this.findViewById<TextView>(R.id.loading_tips).text = message
                    this.findViewById<ProgressBar>(R.id.progressBar).indeterminateTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
                }
            }
            loadingDialog?.showPopupWindow()
        }
    }
}

/**
 * 关闭等待框
 */
fun Activity.dismissLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}

/**
 * 关闭等待框
 */
fun Fragment.dismissLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}
