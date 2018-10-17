package test.juyoufuli.com.myapplication.mvp.ui.tab

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.tbruyelle.rxpermissions2.RxPermissions
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsItemContract
import test.juyoufuli.com.myapplication.mvp.presenter.SystemDataDetailsItemPresenter
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-10-1215:52
 * Description:
 */
class RecyclerViewFragment : BaseFragment<SystemDataDetailsItemPresenter>(), SystemDataDetailsItemContract.View {

    //    @BindView(R.id.rlv_pager)
//    var mRecyclerView: RecyclerView? = null
//    @Inject
//    var mRxPermissions: RxPermissions? = null


    override val fragment: Fragment
        get() = this


    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val inflate = inflater.inflate(R.layout.view_pager_item, null)
        return inflate

    }

    override fun initData(savedInstanceState: Bundle?) {
        val cid = arguments!!.get("cid")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun showLoading() {
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
    }

    override fun killMyself() {

    }

    override fun showMessage(message: String) {
    }




    override fun setData(data: Any?) {
    }


}