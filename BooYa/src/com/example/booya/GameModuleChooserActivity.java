/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.booya;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class GameModuleChooserActivity  extends FragmentActivity {

    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;

    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_module_chooser);

        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager, attaching the adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }
    
	public void launchClassicMazeGame(View view) {
	    Intent intent = new Intent(this, GenericGameActivity.class);
	    startActivity(intent);
	}

    /**
     * A {@link android.support.v4.app.FragmentStatePagerAdapter} that returns a fragment
     * representing an object in the collection.
     */
    public static class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            args.putParcelable(DemoObjectFragment.ARG_PRANK_METHOD, BooyaUser.getprankMethodList().get(i));
            fragment.setArguments(args);
            return fragment;
        }
       

        @Override
        public int getCount() {
            return BooyaUser.getprankMethodList().size();
        }

       @Override
        public CharSequence getPageTitle(int position) 
       {
            return ("" +BooyaUser.getprankMethodList().get(position).getPrankID());
        }
    }


	public static class DemoObjectFragment extends Fragment {

        public static final String ARG_PRANK_METHOD = "curr_prank_method";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) 
        {
            View rootView = inflater.inflate(R.layout.fragment_prank_object, container, false);
            Bundle args = getArguments();
            
            PrankMethod curPrankMethod= (PrankMethod)args.getParcelable(ARG_PRANK_METHOD);
            if(curPrankMethod.getImgResID() == 1)
            {
            	((ImageView)(rootView.findViewById(R.id.imageView1))).setImageResource(R.drawable.classicmaze);
            }
            else if(curPrankMethod.getImgResID() == 2)
            {
            	((ImageView)(rootView.findViewById(R.id.imageView1))).setImageResource(R.drawable.cupsgame);
            }
            	
            return rootView;
        }
    }
}
