package com.angelocyj.library;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.angelocyj.library.circularReveal.animation.SupportAnimator;
import com.angelocyj.library.circularReveal.animation.ViewAnimationUtils;
import com.angelocyj.library.util.UIUtil;
import com.bumptech.glide.Glide;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.io.Serializable;

/**
 * 类描述：
 * 创建人：angelo
 * 创建时间：9/10/15 2:19 PM
 */
public class RevealActivityAnimationHelperCopy implements Serializable {
    public static final String KEY_REVEAL_ACTIVITY_HELPER = "REVEAL_ACTIVITY_HELPER";
    private static final int DEFAULT_TRANSFORM_TIME = 500;
    // Default reveal color
    private static final int DEFAULT_COLOR = 0xfff4f4f4;

    /**
     * 要在targetView上展示的图片
     */
    private String mImageUrl;

    private int mSourceLeft;
    private int mSourceTop;
    private int mSourceWidth;
    private int mSourceHeight;

    private int mLeft;
    private int mTop;
    private float mScaleX;
    private float mScaleY;
    private int mTransformTime = DEFAULT_TRANSFORM_TIME;
    private Callback mCallback;

    private int mSourceCircleImageViewBorderWidth = 0;
    private int mTargetCircleImageViewBorderWidth = 0;

    private int mTargetParentViewHeight;


    public RevealActivityAnimationHelperCopy(View sourceView) {
        this(sourceView, null);
    }

    public RevealActivityAnimationHelperCopy(View sourceView, String imageUrl) {
        this.mImageUrl = imageUrl;
        int location[] = new int[2];
        sourceView.getLocationOnScreen(location);
        mSourceLeft = location[0];
        mSourceTop = location[1];
        mSourceWidth = sourceView.getWidth();
        mSourceHeight = sourceView.getHeight();
//        if (sourceView instanceof CircleImageView) {
//            mSourceCircleImageViewBorderWidth = ((CircleImageView) sourceView).getBorderWidth();
//        }
    }

    /**
     * 在activity的OnCreate中调用
     *
     * @param rootView   根布局，必须为RevealFrameLayout 或 RevealLinearLayout
     * @param targetView 目标控件，视觉效果从前一个activity移动到新activity，层级有限制，与mContentView最多隔2级
     * @param background 根布局背景，辅助alpha过度呈现
     */
    public void onActivityCreate(final ViewGroup rootView, final View targetView, final Drawable background) {

        targetView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                targetView.getViewTreeObserver().removeOnPreDrawListener(this);

//                if (targetView instanceof CircleImageView) {
//                    mTargetCircleImageViewBorderWidth = ((CircleImageView) targetView).getBorderWidth();
//                    ((CircleImageView) targetView).setBorderWidth(mSourceCircleImageViewBorderWidth);
//                }

                if (!TextUtils.isEmpty(mImageUrl)) {
                    if (URLUtil.isNetworkUrl(mImageUrl)){
//                    new BitmapTools(CommonApp.getApp()).display(targetView, mImageUrl, BitmapTools.SizeType.SMALL);
                        Glide.with(rootView.getContext()).load(mImageUrl).into((ImageView)targetView);
                    } else {
                        if (targetView instanceof ImageView) {
                            ((ImageView) targetView).setImageResource(Integer.valueOf(mImageUrl));
                        }

                    }
                }

                int location[] = new int[2];
                targetView.getLocationOnScreen(location);
                mLeft = mSourceLeft - location[0];
                mTop = mSourceTop - location[1];
                mScaleX = mSourceWidth * 1.0f / targetView.getWidth();
                mScaleY = mSourceHeight * 1.0f / targetView.getHeight();

                if (mLeft == 0 && mTop == 0 && mScaleX > 0.95 && mScaleY > 0.95) {
                    mTransformTime = 100;
                }

                if (background == null) {
                    ColorDrawable defaultBackground = new ColorDrawable(DEFAULT_COLOR);
                    rootView.setBackgroundDrawable(defaultBackground);
                    activityEnterAnim(rootView, targetView, defaultBackground);
                } else {
                    rootView.setBackgroundDrawable(background);
                    activityEnterAnim(rootView, targetView, background);
                }
                return true;
            }
        });
    }

    private void activityEnterAnim(final ViewGroup rootView, final View targetView, final Drawable background) {
        // 根布局下的第一个子布局，用于呈现内容
        final ViewGroup contentView = (ViewGroup) rootView.getChildAt(0);

        UIUtil.invisibleChildrenView(contentView);
        final ViewGroup targetParentView = (ViewGroup) targetView.getParent();
        final Drawable parentBackground = targetParentView.getBackground();
//        if (targetParentView != contentView) {
//            targetParentView.setVisibility(View.VISIBLE);
//            mTargetParentViewHeight = targetParentView.getHeight();
//            targetParentView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
//            UIUtil.invisibleChildrenView(targetParentView);
//            targetParentView.setBackgroundDrawable(null);
//        }

        targetView.setVisibility(View.VISIBLE);
//        View tempTargetView = new ImageView();
        final ImageView tempTargetView = new ImageView(rootView.getContext());
        tempTargetView.setLayoutParams(targetView.getLayoutParams());
        tempTargetView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        tempTargetView.setImageResource(Integer.valueOf(mImageUrl));
        rootView.addView(tempTargetView);

        ViewHelper.setPivotX(targetView, 0);
        ViewHelper.setPivotY(targetView, 0);
        ViewHelper.setScaleX(targetView, mScaleX);
        ViewHelper.setScaleY(targetView, mScaleY);
        ViewHelper.setTranslationX(targetView, mLeft);
        ViewHelper.setTranslationY(targetView, mTop);

        ViewPropertyAnimator.animate(targetView).scaleX(1).scaleY(1).translationX(0).translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                UIUtil.showChildrenView(contentView);
                if (targetParentView != contentView) {
                    targetParentView.getLayoutParams().height = mTargetParentViewHeight;
                    targetParentView.setBackgroundDrawable(parentBackground);
                    UIUtil.showChildrenView(targetParentView);
                }
//                if (targetView instanceof CircleImageView) {
//                    ((CircleImageView) targetView).setBorderWidth(mTargetCircleImageViewBorderWidth);
//                }

                startRevealTransition(contentView, targetView);
                if (mCallback != null) {
                    mCallback.onActivityEnterFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).setDuration(mTransformTime).setStartDelay(250).setInterpolator(new DecelerateInterpolator()).start();

        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(background, "alpha", 0, 255);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(150);
        objectAnimator.start();
    }


    protected void startRevealTransition(final View contentView, final View targetView) {
        final Rect bounds = new Rect();
        contentView.getHitRect(bounds);
        int contentLocation[] = new int[2];
        int targetLocation[] = new int[2];
        contentView.getLocationOnScreen(contentLocation);
        targetView.getLocationOnScreen(targetLocation);

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(contentView,
                targetLocation[0] + targetView.getWidth() / 2 - contentLocation[0], targetLocation[1] + targetView.getHeight() / 2 - contentLocation[1], targetView.getWidth() / 2, hypo(bounds.height(), bounds.width()));

        animator.setDuration(600);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public static float hypo(float a, float b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onActivityEnterFinish();
    }
}
