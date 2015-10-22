package com.angelocyj.revealactivityanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.angelocyj.library.RevealActivityAnimationHelper;

public class MainActivity extends AppCompatActivity {
    private static final int COUNT = 21;

    private int[] mResArray = new int[COUNT];
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 设置item的动画，可以不设置
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MyAdapter(initData());
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                // send RevealActivityAnimationHelper with intent as putExtra
                startActivity(new Intent(MainActivity.this, DetailActivity.class).putExtra(RevealActivityAnimationHelper.KEY_REVEAL_ACTIVITY_HELPER,
                        new RevealActivityAnimationHelper((ImageView) view, String.valueOf(mResArray[position]))));

                // remove default transition animation of activity
                overridePendingTransition(0, 0);

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(30));

    }

    private int[] initData() {
        for (int i = 0; i < COUNT; i++) {
            // Add an image for each view.
            mResArray[i] = getResources().getIdentifier("d" + (i % 11 + 1), "drawable", getPackageName());

        }
        return mResArray;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
