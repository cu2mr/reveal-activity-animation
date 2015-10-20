package com.angelocyj.revealactivityanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.angelocyj.library.RevealActivityAnimationHelper;

/**
 * 类描述：
 * 创建人：angelo
 * 创建时间：10/16/15 3:34 PM
 */
public class DetailActivity  extends AppCompatActivity {
    private RevealActivityAnimationHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getExtras() != null) {
            mHelper = (RevealActivityAnimationHelper) getIntent().getSerializableExtra(RevealActivityAnimationHelper.KEY_REVEAL_ACTIVITY_HELPER);
            if (mHelper != null) {
                setTheme(R.style.AppThemeTransparentActivity);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (mHelper != null) {
            ViewGroup rootView = (ViewGroup) findViewById(R.id.reveal_linear_layout);
            mHelper.onActivityCreate(rootView, rootView.findViewById(R.id.targetView), null);
        }
    }
}
