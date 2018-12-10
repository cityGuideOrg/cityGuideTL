package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import gr.teicm.cityguidetl.cityguidetl.R;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;


@RunWith(AndroidJUnit4.class)
public class PointsActivityTest {

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
        Espresso.onView(withId(R.id.City)).check(matches(withText(City)));
    }

}