package test.juyoufuli.com.myapplication.mvp.ui.project.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.view.View
import test.juyoufuli.com.myapplication.R

/**
 * Author : dongfang
 * Created Time : 2018-10-31  16:12
 * Description:
 */
class ProjectRecycerDecoration
constructor(context: Context, orientation: Int, color: Int, private val inset: Int) : ItemDecoration() {


    var mOrientation: Int = 0
    private var mDivider: Drawable? = null
    private var paint: Paint = Paint()

    companion object {
        const val HORIZONTAL_LIST: Int = LinearLayoutManager.HORIZONTAL
        const val VERTICAL_LIST: Int = LinearLayoutManager.VERTICAL
    }

    init {
        mDivider = context.resources.getDrawable(color)
        paint.color = context.resources.getColor(R.color.gray)
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        setOrientation(orientation)
    }

    private fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw  IllegalArgumentException("invalid orientation")
        }
        mOrientation = orientation

    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        if (parent!!.getLayoutManager() == null || mDivider == null) {
            return
        }

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawHorizontal(canvas: Canvas?, parent: RecyclerView?) {
        val top = parent!!.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childAt = parent.getChildAt(i) as View
            var layoutParams = childAt.layoutParams as RecyclerView.LayoutParams
            val left = childAt.right + layoutParams.rightMargin
            val right = mDivider!!.intrinsicWidth + left

            mDivider!!.setBounds(left, top, right, bottom);
            mDivider!!.draw(canvas)

        }

    }

    private fun drawVertical(canvas: Canvas?, parent: RecyclerView?) {
        val left = parent!!.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i) as View
            var layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val top = child.getBottom() + layoutParams.bottomMargin;
            val bottom = top + mDivider!!.getIntrinsicHeight();

            if (inset > 0) {
                mDivider!!.setBounds(left + inset, top, right - inset, bottom);
            } else {
                mDivider!!.setBounds(left, top, right, bottom);
            }
            mDivider!!.draw(canvas)
        }

    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        if (mOrientation == VERTICAL_LIST) {
            outRect!!.set(0, 0, 0, mDivider!!.intrinsicHeight);
        } else {
            outRect!!.set(0, 0, mDivider!!.intrinsicWidth, 0);
        }

    }

}




























