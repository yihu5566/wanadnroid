package test.juyoufuli.com.myapplication.mvp.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.kingja.loadsir.core.LoadService
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.app.recyclerview.MultiItemTypeAdapter.OnItemClickListener
import test.juyoufuli.com.myapplication.app.utils.ArmsUtils
import test.juyoufuli.com.myapplication.databinding.TabFragmentBinding
import test.juyoufuli.com.myapplication.mvp.ui.tab.adapter.SystemDataAdapter
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-29  11:20
 * Description:
 */
class SystemDataFragment : BaseFragment<TabFragmentBinding>() {

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

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
//        mRecyclerView!!.adapter = mAdapter
//        mPresenter!!.requestSystemDataList()//打开 App 时自动加载列表
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun attachBinding(): TabFragmentBinding {
        return TabFragmentBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {

    }

    private fun initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView!!, mLayoutManager)

        mAdapter?.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
//                LogUtils.d((data as SystemBean).name + position)
//                val (children) = data
//                val intent = Intent(activity, SystemDataDetailsActivity::class.java)
//                tagName!!.clear()
//                var stringb: StringBuffer
//                for (i in 0 until children.size) {
//                    stringb = StringBuffer()
//                    stringb.append(children[i].id)
//                    stringb.append("*")
//                    stringb.append(children[i].name)
//
//                    tagName!!.add(stringb.toString())
//                }
//                intent.putStringArrayListExtra("tagName", tagName)
//
//                launchActivity(intent)
            }

            override fun onItemLongClick(
                view: View?,
                holder: RecyclerView.ViewHolder?,
                position: Int
            ): Boolean {
                TODO("Not yet implemented")
            }

        })
    }

//    override fun refreshData(response: SystemDataRespons) {
//        mBaseLoadService!!.showSuccess()
//        if (response.data.isEmpty()) {
//            mBaseLoadService!!.showCallback(EmptyCallback::class.java)
//        }
//    }
}
