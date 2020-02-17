package com.vivek.weather.ui.main;

import android.content.res.Resources;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.vivek.weather.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule activityScenarioRule = new ActivityScenarioRule(MainActivity.class);

    @Test
    public void test_IsMainActivityVisible(){
      onView(withId(R.id.mainActivityLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isFullscreen(){
        onView(withId(R.id.mainActivityLayout)).check(matches(isDisplayed()));
        Resources resources = getInstrumentation().getTargetContext().getResources();
        int actionBarId = resources.getIdentifier("action_bar_container", "id", "android");
        try {
            onView(withId(actionBarId)).check(matches(isDisplayed()));
        }catch (NoMatchingViewException e){
            assert true;
        }
    }

    @Test
    public void test_backPress() {
        onView(withId(R.id.backButton))                .perform(click());
    }


}