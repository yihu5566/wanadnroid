package test.juyoufuli.com.myapplication.app.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;

import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.stetho.common.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import test.juyoufuli.com.myapplication.R;

/**
 * @Author : dongfang
 * @Created Time : 2018-11-19  15:25
 * @Description:
 */
public class LabelsView extends ViewGroup {


    private Context context;
    private Paint mainPaint;
    private TextPaint textPaint;
    private List<String> textList;
    private List<String> selectList;
    private List<String> tempSelectList;

    private String[] textDefault = {""};
    private LayoutInflater inflater;
    private List<TextView> textViewList;
    private ChildSelectListener childSelectListener;
    private MyChildClickListener childClickListener;
    private int maxWidth;

    private Model model;

    public static enum Model {
        CLICK, SELECT, SINGLE_SELECT

    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setTextList(List<String> textList) {
        //益处以前的view
        removeAllViews();
        this.textList = textList;
        //绘制每一个小的textView
        drawTextView();
        invalidate();
    }

    /**
     * 默认的标签间距
     */
    private int margiHorizontal = 10;
    /**
     * 默认的行间距
     */
    private int margiVertical = 10;

    public void setChildClickListener(MyChildClickListener childClickListener) {
        this.childClickListener = childClickListener;
    }

    public void setChildSelectListener(ChildSelectListener childSelectListener) {
        this.childSelectListener = childSelectListener;
    }

    public LabelsView(@NonNull Context context) {
        this(context, null);
    }

    public LabelsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        inflater = LayoutInflater.from(context);
        mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainPaint.setColor(Color.RED);

        textPaint = new TextPaint();
        textPaint.setTextSize(20);
        textPaint.setColor(Color.BLACK);

        textList = new ArrayList<>();
        textList.addAll(Arrays.asList(textDefault));
        selectList = new ArrayList<>();
        for (int i = 0; i < textList.size(); i++) {
            selectList.add("null");
        }
        textViewList = new ArrayList<>();
        tempSelectList = new ArrayList<>();

        //绘制每一个小的textView
        drawTextView();
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
    }

    Paint mPaint;

    public LabelsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //由于布局非常规，所以要自己测量
        measureMyChild(widthMeasureSpec, heightMeasureSpec);

    }

    private void measureMyChild(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //宽度
        maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int count = getChildCount();
        //总高度
        int contentHeight = 0;
        //记录最宽的行宽
        int maxLineWidth = 0;
        // 每行宽度
        int startLayoutWidth = 0;
        //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少
        int maxChildHeight = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LogUtil.i("onLayout--getPaddingRight:" +
                    child.getPaddingRight() +
                    "getPaddingLeft：" + child.getPaddingLeft());

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //测量的宽高
            int childMeasureWidth = child.getMeasuredWidth();
            int childMeasureHeight = child.getMeasuredHeight();
            LogUtil.i("onLayout--width:" + maxWidth + "startLayoutWidth：");
            if (startLayoutWidth + childMeasureWidth < maxWidth) {
                //如果一行没有排满，继续往右排列
                startLayoutWidth += childMeasureWidth + margiHorizontal;
            } else {
                // 初始化为0
                maxChildHeight = 0;
                startLayoutWidth = 0;

            }
            if (childMeasureHeight > maxChildHeight) {
                maxChildHeight = childMeasureHeight;
            }
            //获取总的高度
            contentHeight += maxChildHeight + margiVertical;
            //获取最长的行总的宽度
            maxLineWidth = Math.max(maxLineWidth, startLayoutWidth);
        }

        //如果没有子元素，就设置宽高都为0（简化处理）
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        } else
            //宽和高都是AT_MOST，则设置宽度所有子元素的宽度的和；高度设置为第一个元素的高度；
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(maxLineWidth, contentHeight);
            }
            //如果宽度是wrap_content，则宽度为所有子元素的宽度的和
            else if (widthMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(maxLineWidth, heightSize);
            }
            //如果高度是wrap_content，则高度为第一个子元素的高度
            else if (heightMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(widthSize, contentHeight);
            }

    }


    private void drawTextView() {
        for (String text : textList) {
            TextView label = new TextView(context);
            label.setPadding(30, 30, 0, 0);
            label.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
            label.setBackgroundResource(R.drawable.selector_text_bg);
            label.setText(text);
            label.setTextColor(createColorStateList("#ffffffff", "#ff44e6ff"));
            addView(label);
        }

    }

    private static ColorStateList createColorStateList(String selected, String normal) {
        int[] colors = new int[]{Color.parseColor(selected), Color.parseColor(normal)};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.i("onSizeChanged");

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        LogUtil.i("onLayout--width:" + maxWidth);
        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        // 开始的X位置
        int startLayoutWidth = getPaddingLeft();
        // 开始的Y位置
        int startLayoutHeight = getPaddingTop();
        //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少
        int maxChildHeight = 0;
        for (int i = 0; i < count; i++) {
            int position = i;
            TextView child = (TextView) getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth() + child.getPaddingLeft() + child.getPaddingRight();
            childMeasureHeight = child.getMeasuredHeight() + child.getPaddingTop() + child.getPaddingBottom();
            LogUtil.i("onLayout--width:" + maxWidth + "startLayoutWidth：" + startLayoutWidth);
            if (startLayoutWidth + childMeasureWidth < maxWidth - getPaddingRight()) {
                //如果一行没有排满，继续往右排列
                left = startLayoutWidth;
                right = left + childMeasureWidth;
                top = startLayoutHeight;
                bottom = top + childMeasureHeight;
            } else {
                //排满后换行
                startLayoutWidth = getPaddingLeft();
                startLayoutHeight += maxChildHeight + margiVertical;
                maxChildHeight = 0;

                left = startLayoutWidth;
                right = left + childMeasureWidth;
                top = startLayoutHeight;
                bottom = top + childMeasureHeight;
            }
            //宽度累加
            startLayoutWidth += childMeasureWidth + margiHorizontal;
            if (childMeasureHeight > maxChildHeight) {
                maxChildHeight = childMeasureHeight;
            }
            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom);
            initListener(child, position);
        }

    }

    private void initListener(TextView child, final int position) {
        textViewList.add(child);
        child.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Model.SELECT == model) {
                    for (int i = 0; i < textList.size(); i++) {
                        selectList.remove(i);
                        if (position == i) {
                            selectList.add(i, textList.get(i));
                        } else {
                            selectList.add(i, "null");
                        }
                    }
                    v.setSelected(!"null".equals(selectList.get(position)));
                    //转换一下，不能操作原始集合。
                    tempSelectList.addAll(selectList);
                    tempSelectList.removeAll(Collections.singleton("null"));
                    childSelectListener.onChildSelect(tempSelectList, position);

                } else if (Model.SINGLE_SELECT == model) {
                    for (int i = 0; i < textList.size(); i++) {
                        selectList.remove(i);
                        if (position == i) {
                            selectList.add(i, textList.get(i));
                        } else {
                            selectList.add(i, "null");
                        }
                        boolean equals = "null".equals(selectList.get(i));
                        LogUtil.i("SINGLE_SELECT" + !equals);
                        textViewList.get(i).setSelected(!equals);
                    }
                    childSelectListener.onChildSelect(textList.get(position), position);
                } else if (Model.CLICK == model) {
                    if (childClickListener != null) {
                        childClickListener.onChildClick(v, textList.get(position), position);
                    }
                }
            }
        });
    }

    public interface MyChildClickListener {
        /**
         * @param view
         * @param string
         * @param position
         * @return
         */
        void onChildClick(View view, String string, int position);
    }


    interface ChildSelectListener {
        /**
         * @param stringList
         * @param position
         * @return
         */
        void onChildSelect(List<String> stringList, int position);

        /**
         * @param object
         * @param position
         */
        void onChildSelect(Object object, int position);

    }

}
