package test.juyoufuli.com.myapplication.mvp.ui.webview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.juyoufuli.com.myapplication.R;

/**
 * Author : ludf
 * Created Time : 2018-09-30  10:40
 * Description:
 */
public class WebViewActivity extends AppCompatActivity {
    @BindView(R.id.fl_web)
    FrameLayout flWeb;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbar_back;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    private AgentWeb mAgentWeb;
    private String link;
    private String title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        link = intent.getStringExtra("link");
        title = intent.getStringExtra("title");
    }

    @Override
    protected void onStart() {
        super.onStart();
        toolbar_back.setVisibility(View.VISIBLE);
        toolbar_title.setText(title);
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((FrameLayout) flWeb, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready().go(link);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
