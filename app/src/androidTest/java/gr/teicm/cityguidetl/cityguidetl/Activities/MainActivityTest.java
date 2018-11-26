package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.maps.model.LatLng;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import gr.teicm.cityguidetl.cityguidetl.R;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);
    private String ll=("37.975512,23.736133");

    @Test
    public void buttonClickScenario()
    {
        Espresso.onView(withId(R.id.button_id)).perform(click());
        SystemClock.sleep(1500);
        Espresso.onView(withId(R.id.button_id)).perform(click());

        Espresso.onView(withId(R.id.textView2)).check(matches(withText(ll)));

    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}