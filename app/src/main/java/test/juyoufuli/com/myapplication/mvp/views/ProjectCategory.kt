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
import test.juyoufuli.com.myapplication.databinding.ItemProjectTitleBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ProjectCategory @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: ItemProjectTitleBinding by viewBinding()

    init {
        orientation = HORIZONTAL
    }

    @TextProp
    fun setTitle(title: CharSequence?) {
        binding.tvProjectTitle.text = title
    }

    @ModelProp
    fun setTagVisibility(isVisibility: Boolean?) {
        binding.tag.visibility = when (isVisibility) {
            true -> View.VISIBLE
            else -> View.INVISIBLE
        }
    }


    @CallbackProp
    fun setClickListener(clickListener: OnClickListener?) {
        setOnClickListener(clickListener)
    }
}
