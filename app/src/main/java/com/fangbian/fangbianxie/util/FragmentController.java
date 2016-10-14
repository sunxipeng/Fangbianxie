package com.fangbian.fangbianxie.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by sunxipeng on 2016/10/12.
 */
public class FragmentController {

    private static FragmentController mFragmentController = null;

    private FragmentController() {

    }

    public static FragmentController getInstance() {
        if (mFragmentController == null) {
            mFragmentController = new FragmentController();
        }
        return mFragmentController;
    }

    /**
     * @param containerID
     * @param manager
     * @param newFragment
     * @param oldFragment
     */
    public void addFragment2Container(int containerID, FragmentManager manager, Fragment newFragment, Fragment oldFragment) {
        if (manager == null) {
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();

        if (oldFragment != null) {
            transaction.hide(oldFragment);
        }
        if (newFragment.isAdded()) {
            transaction.show(newFragment);
        } else {
            transaction.add(containerID, newFragment, "").show(newFragment);
        }

        transaction.commitAllowingStateLoss();
    }

}
