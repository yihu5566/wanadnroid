package test.juyoufuli.com.myapplication.mvp.views


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.we.jetpackmvvm.ext.viewBinding
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.app.view.LabelsView
import test.juyoufuli.com.myapplication.databinding.ItemExpandableTitleBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class NavigationItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: ItemExpandableTitleBinding by viewBinding()

    init {
        orientation = VERTICAL
    }

    @TextProp
    fun setTitle(title: CharSequence?) {
        binding.tvTitle.text = title
    }

    @ModelProp
    fun setTagVisibility(isExpand: Boolean) {
        if (isExpand) {
            binding.tvIcon.setImageResource(R.drawable.ic_expand_less_black_24dp)
            binding.lbvSearch.visibility = View.VISIBLE
        } else {
            binding.tvIcon.setImageResource(R.drawable.ic_expand_more_black_24dp)
            binding.lbvSearch.visibility = View.GONE
        }
    }

    @ModelProp
    fun setLabsView(listText: List<String>) {
        LogUtils.d("lab size--》${listText.size}")
//        binding.lbvSearch.setModel(LabelsView.Model.CLICK)
        binding.lbvSearch.setLabels(listText)
    }

    @CallbackProp
    fun setClickVisListener(clickListener: OnClickListener?) {
        binding.clExpandClick.setOnClickListener(clickListener)
    }
}
