package test.juyoufuli.com.myapplication.app.view;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.annotation.Nullable;

import test.juyoufuli.com.myapplication.R;
import test.juyoufuli.com.myapplication.app.listener.CustomSearchListener;

/**
 * @Author : dongfang
 * @Created Time : 2019-03-27  16:07
 * @Description:
 */
public class CustomSearchView extends LinearLayout implements View.OnClickListener {

    private ImageView ivCustomClose;
    private TextInputEditText etCustomSearch;
    public CustomSearchListener customSearchListener;
    private String editTextString;

    public String getEditTextString() {
        return editTextString;
    }

    public void setEditTextString(String editTextString) {
        this.editTextString = editTextString;
        etCustomSearch.setText(editTextString);
    }

    public void setCustomSearchListener(CustomSearchListener customSearchListener) {
        this.customSearchListener = customSearchListener;
    }

    public CustomSearchView(Context context) {
        this(context, null);
    }

    public CustomSearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.custom_search_view, this, true);
        ivCustomClose = findViewById(R.id.iv_custom_close);
        etCustomSearch = findViewById(R.id.et_custom_search);

        initListener();

    }

    private void initListener() {
        ivCustomClose.setOnClickListener(this);
        //输入框，实时更新删除图标
        etCustomSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    ivCustomClose.setVisibility(View.GONE);
                } else {
                    ivCustomClose.setVisibility(View.VISIBLE);
                }
                editTextString = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (customSearchListener != null) {
                    customSearchListener.OnContentChangeListener(s.toString());
                }
            }
        });

        etCustomSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (customSearchListener != null) {
                    customSearchListener.OnSearchButtonPressListener(true);
                }
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_custom_close:
                etCustomSearch.setText("");
                break;
            default:
                break;
        }

    }
}
