package com.oomall.myapplication.aaaa

import androidx.fragment.app.Fragment

import java.util.ArrayList

class A {


    private fun initFragment(): Array<Fragment?> {
        val fragments = arrayOfNulls<Fragment>(2)
        fragments[0] = Fragment()
        fragments[1] = Fragment()
        return fragments
    }
}
