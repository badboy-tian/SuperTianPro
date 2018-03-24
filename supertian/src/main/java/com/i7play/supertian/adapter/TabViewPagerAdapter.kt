package com.i7play.supertian.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager

/**
 * Created by Administrator on 2017/10/1.
 */
class  TabViewPagerAdapter<T : Fragment>(fm: FragmentManager, val fragments: ArrayList<T>, val titles: ArrayList<String>) : FragmentPagerAdapter(fm), ViewPager.OnPageChangeListener {
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}