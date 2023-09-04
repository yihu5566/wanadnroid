package test.juyoufuli.com.myapplication.mvp.views


import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.we.jetpackmvvm.ext.viewBinding
import test.juyoufuli.com.myapplication.databinding.ArticleItemBinding
import test.juyoufuli.com.myapplication.databinding.ArticleItemWechatBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ArticleItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: ArticleItemWechatBinding by viewBinding()

    init {
        orientation = HORIZONTAL
    }

    @TextProp
    fun setTitle(title: CharSequence?) {
        binding.tvChapterName.text = title
    }

    @TextProp
    fun setDes(des: CharSequence?) {
        binding.tvDesc.text = des
    }

    @TextProp
    fun setTime(time: CharSequence?) {
        binding.tvTime.text = time
    }

    @CallbackProp
    fun setClickListener(clickListener: OnClickListener?) {
        setOnClickListener(clickListener)
    }
}
