# reveal-activity-animation
An android activity animation like gewara with reveal effect
# Note
depends on Ozodrukh's animation util for CircularReveal animation for 2.3+ version
# Using

After you have to create special root layout to show in behind current Circular Reveal animated view. The root layout have to be RevealFrameLayout

    <com.angelocyj.library.circularReveal.widget.RevealFrameLayout
        android:id="@+id/reveal_linear_layout"
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/white" >
    
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


startActivity(new Intent(MainActivity.this,     DetailActivity.class).putExtra(RevealActivityAnimationHelper.KEY_REVEAL_ACTIVITY_HELPER,
                        new RevealActivityAnimationHelper((ImageView) view, String.valueOf(mResArray[position]))));

                overridePendingTransition(0, 0);
                
                
