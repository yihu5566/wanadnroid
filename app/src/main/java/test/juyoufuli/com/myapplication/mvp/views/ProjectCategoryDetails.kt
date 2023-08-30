package test.juyoufuli.com.myapplication.mvp.views


import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.we.jetpackmvvm.ext.viewBinding
import test.juyoufuli.com.myapplication.app.utils.ImageLoaderUtils
import test.juyoufuli.com.myapplication.databinding.ItemProjectDetailsBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ProjectCategoryDetails @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: ItemProjectDetailsBinding by viewBinding()

    init {
        orientation = HORIZONTAL
    }

    @TextProp
    fun setTitle(title: CharSequence?) {
        binding.tvProjectDetailsTitle.text = title
    }

    @TextProp
    fun setDes(des: CharSequence?) {
        binding.tvProjectDetailsContent.text = des
    }

    @TextProp
    fun setTime(time: CharSequence?) {
        binding.tvProjectDetailsTime.text = time
    }

    @TextProp
    fun setAuthor(author: CharSequence?) {
        binding.tvProjectDetailsAuthor.text = author
    }

    @TextProp
    fun setIsCollect(isCollect: CharSequence?) {
        binding.tvProjectDetailsCollect.isChecked = isCollect == "1"
    }

    @TextProp
    fun setImageUrl(imageUrl: CharSequence?) {
        ImageLoaderUtils.loadImage(binding.ivProjectDetailsIcon, imageUrl.toString(), context)
    }

    @CallbackProp
    fun setClickListener(clickListener: OnClickListener?) {
        setOnClickListener(clickListener)
    }

    @CallbackProp
    fun setCollectClickListener(clickListener: OnClickListener?) {
        binding.tvProjectDetailsCollect.setOnClickListener(clickListener)
    }
}
