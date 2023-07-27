package test.juyoufuli.com.myapplication.mvp.ui.tab

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.Preconditions.checkNotNull
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.di.component.DaggerSystemDataComponent
import test.juyoufuli.com.myapplication.di.module.SystemDataModule
import test.juyoufuli.com.myapplication.mvp.contract.SystemDataContract
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean
import test.juyoufuli.com.myapplication.mvp.entity.SystemDataRespons
import test.juyoufuli.com.myapplication.mvp.presenter.SystemDataPresenter
import test.juyoufuli.com.myapplication.mvp.ui.callback.EmptyCallback
import test.juyoufuli.com.myapplication.mvp.ui.callback.LoadingCallback
import test.juyoufuli.com.myapplication.mvp.ui.tab.adapter.SystemDataAdapter
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-29  11:20
 * Description:
 */
class SystemDataFragment : BaseFragment<SystemDataPresenter>(), SystemDataContract.View {


    @JvmField
    @BindView(R.id.recyclerView)
    internal var mRecyclerView: RecyclerView? = null
    @JvmField
    @Inject
    internal var mLayoutManager: RecyclerView.LayoutManager? = null
    @JvmField
    @Inject
    internal var mAdapter: SystemDataAdapter? = null
    @JvmField
    @Inject
    internal var tagName: ArrayList<String>? = null
    var mBaseLoadService: LoadService<*>? = null

    override val fragment: Fragment
        get() = this

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerSystemDataComponent.builder().appComponent(appComponent).systemDataModule(SystemDataModule(this)).build().inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.tab_fragment, null)
        mBaseLoadService = LoadSir.getDefault().register(rootView) {
            mBaseLoadService!!.showCallback(LoadingCallback::class.java)
        }
        return mBaseLoadService!!.getLoadLayout()
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        mRecyclerView!!.adapter = mAdapter
        mPresenter!!.requestSystemDataList()//打开 App 时自动加载列表

    }

    private fun initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView!!, mLayoutManager)

        mAdapter!!.setOnItemClickListener { view: View, viewType: Int, data: Any, position: Int ->
            LogUtils.debugInfo((data as SystemBean).name + position)
            val (children) = data
            val intent = Intent(activity, SystemDataDetailsActivity::class.java)
            tagName!!.clear()
            var stringb: StringBuffer
            for (i in 0 until children.size) {
                stringb = StringBuffer()
                stringb.append(children[i].id)
                stringb.append("*")
                stringb.append(children[i].name)

                tagName!!.add(stringb.toString())
            }
            intent.putStringArrayListExtra("tagName", tagName)

            launchActivity(intent)

        }
    }

    override fun setData(data: Any?) {

    }


    override fun showMessage(message: String) {
        checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }


    override fun refreshData(response: SystemDataRespons) {
        mBaseLoadService!!.showSuccess()
        if (response.data.isEmpty()) {
            mBaseLoadService!!.showCallback(EmptyCallback::class.java)
        }
    }
}
