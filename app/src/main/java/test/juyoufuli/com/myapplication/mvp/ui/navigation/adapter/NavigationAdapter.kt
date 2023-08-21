package test.juyoufuli.com.myapplication.mvp.ui.navigation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.entity.Article
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
class NavigationAdapter @Inject
constructor(val context: Context, var parentList: ArrayList<String>, var childList: ArrayList<List<Article>>) : BaseExpandableListAdapter() {
    private val layoutInflater: LayoutInflater? = LayoutInflater.from(context)

    override fun getGroup(groupPosition: Int): Any {
        return parentList[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val groupView = layoutInflater!!.inflate(R.layout.item_expandable_title, null)
        val parentText = groupView.findViewById<TextView>(R.id.tv_title)
        var expandedText = groupView.findViewById<ImageView>(R.id.tv_icon)
        parentText.text = parentList[groupPosition]
        if (isExpanded) {
            expandedText.setImageResource(R.drawable.ic_expand_less_black_24dp)
        } else {
            expandedText.setImageResource(R.drawable.ic_expand_more_black_24dp)

        }
        return groupView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return childList[groupPosition].size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return childList[groupPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    @SuppressLint("ResourceAsColor")
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var groupView = layoutInflater!!.inflate(android.R.layout.simple_list_item_1, null)
//        groupView.setBackgroundColor(R.color.white)
        val parentText = groupView.findViewById<TextView>(android.R.id.text1)
        parentText.text = childList[groupPosition][childPosition].title
        return groupView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return parentList.size
    }


}
