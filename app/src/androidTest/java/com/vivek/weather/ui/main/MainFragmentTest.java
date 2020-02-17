package com.vivek.weather.ui.main;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.vivek.weather.R;
import com.vivek.weather.ViewModelProviderFactory;

import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainFragmentTest {

    @Rule
    public ActivityScenarioRule activityScenarioRule = new ActivityScenarioRule(MainActivity.class);

    @Inject
    ViewModelProviderFactory providerFactory;


    @Test
    public void test_isFragmentVIsible() {
        onView(withId(R.id.mainFragment)).check(matches(isDisplayed()));
    }

}