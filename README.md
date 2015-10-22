# reveal-activity-animation
An android activity animation like gewara with reveal effect.

![alt text](http://7xnpdq.com1.z0.glb.clouddn.com/reveal4.gif)

# Note
depends on [Ozodrukh's](https://github.com/ozodrukh/CircularReveal "CircularReveal") animation util for CircularReveal animation for 2.3+ version
# Using
For a working implementation, have a look at the app module

1. Include the library as local library project.

2. You need to RevealActivityAnimation object as extra

        
        startActivity(new Intent(MainActivity.this, DetailActivity.class)
                    .putExtra(RevealActivityAnimationHelper.KEY_REVEAL_ACTIVITY_HELPER,
                        new RevealActivityAnimationHelper(sourceView, imageUrl)));
        overridePendingTransition(0, 0);
                

3.  In your onCreate method you need to receive bundle and set transparent theme, build animation with RevealActivityAnimationHelper.onActivityCreate

    
        if (getIntent().getExtras() != null) {
            mHelper = (RevealActivityAnimationHelper) getIntent().getSerializableExtra(RevealActivityAnimationHelper.KEY_REVEAL_ACTIVITY_HELPER);
            if (mHelper != null) {
                setTheme(R.style.AppThemeTransparentActivity);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (mHelper != null) {
            ViewGroup rootView = (ViewGroup) findViewById(R.id.root_layout);
            mHelper.onActivityCreate(rootView, (ImageView) rootView.findViewById(R.id.targetView), null);
        }

After you have to create special root layout to show in behind current Circular Reveal animated view. The root layout have to be RevealFrameLayout. To make the full screem be clipped, please make sure this root layout has only one viewgroup. For example:

    <com.angelocyj.library.circularReveal.widget.RevealFrameLayout
        android:id="@+id/root_layout"
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent" >
    
        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@android:color/white">
            <ImageView
                android:id="@+id/targetView"
                android:layout_width="120dp"
                android:layout_height="180dp"
                android:layout_marginTop="100dp"
                android:layout_marginLeft="10dp"
                android:scaleType="centerCrop"
                android:src="@drawable/d1"/>
            <TextView
                android:text="@string/hello_world"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:padding="10dp"/>
    
        </FrameLayout>
    
    </com.angelocyj.library.circularReveal.widget.RevealFrameLayout>


