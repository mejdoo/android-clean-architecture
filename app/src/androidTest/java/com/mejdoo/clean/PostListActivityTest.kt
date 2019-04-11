package com.mejdoo.clean


import android.os.SystemClock

import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4

import com.mejdoo.clean.presentation.ui.list.PostListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class PostListActivityTest {
    @get:Rule
    var mActivityRule = ActivityTestRule<PostListActivity>(PostListActivity::class.java)


    @Test
    fun test() {

        waitForDelay()
    }


    private fun waitForDelay() {
        SystemClock.sleep(5000)

    }


}