package test.juyoufuli.com.myapplication.mvp.ui.tab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_pager_item.*
import test.juyoufuli.com.myapplication.R

/**
 * Author : dongfang
 * Created Time : 2018-10-1215:52
 * Description:
 */
class RecyclerViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val inflate = inflater.inflate(R.layout.view_pager_item, null)
//        val get = arguments!!.get("cid")
//        tv_name.text = "----"
        return inflate
    }

}