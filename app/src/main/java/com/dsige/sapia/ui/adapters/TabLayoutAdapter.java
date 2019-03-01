package com.dsige.sapia.ui.adapters;

import com.dsige.sapia.ui.fragments.ConformiteFragment;
import com.dsige.sapia.ui.fragments.DescriptionFragment;
import com.dsige.sapia.ui.fragments.GeneralFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private int id;
    private String estado;

    public TabLayoutAdapter(FragmentManager fm, int numberOfTabs, int Id, String estado) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.id = Id;
        this.estado = estado;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return GeneralFragment.newInstance(estado, estado);
            case 1:
                return DescriptionFragment.newInstance(estado, estado);
            case 2:
                return ConformiteFragment.newInstance(estado, estado);
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}