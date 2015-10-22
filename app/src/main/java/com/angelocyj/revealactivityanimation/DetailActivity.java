package com.angelocyj.revealactivityanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.angelocyj.library.RevealActivityAnimationHelper;
import com.angelocyj.library.circularReveal.widget.RevealFrameLayout;

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
            RevealFrameLayout rootView = (RevealFrameLayout) findViewById(R.id.root_layout);
            mHelper.onActivityCreate(rootView, (ImageView) rootView.findViewById(R.id.targetView), null);
        }
    }

}
