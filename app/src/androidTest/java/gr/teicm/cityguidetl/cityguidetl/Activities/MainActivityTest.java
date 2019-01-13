package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.os.SystemClock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import gr.teicm.cityguidetl.cityguidetl.R;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);
    private String City = "Athens";

    @Test
    public void buttonClickScenario()
    {
        SystemClock.sleep(1000);
        Espresso.onData(anything())
                .inAdapterView(withId(R.id.citiesList))
                .atPosition(0)
                .perform(click());
        SystemClock.sleep(1000);
        Espresso.onView(withId(R.id.cityNameTextView)).check(matches(withText(City)));
    }

}