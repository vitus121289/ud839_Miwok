package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Vince on 20/03/2017.
 */

public class CategoryAdapter extends FragmentPagerAdapter {

    public CategoryAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new NumbersFragment();
            case 1:
                return new FamilyFragment();
            case 2:
                return new ColorFragment();
            case 3:
                return new PhrasesFragment();
            default:
                return null;
        }
    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch(position) {
//            case 0:
//                return getString(R.string.category_numbers);
//            case 1:
//                return getString(R.string.category_family);
//            case 2:
//                return getString(R.string.category_colors);
//            case 3:
//                return getString(R.string.category_phrases);
//            default:
//                return null;
//        }
//    }
}
