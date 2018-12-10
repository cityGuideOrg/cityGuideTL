package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.os.SystemClock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import gr.teicm.cityguidetl.cityguidetl.R;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;


@RunWith(JUnit4.class)
public class PointsActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);
    private String aPointOfAthens = "37.992171,23.729267";

    @Test
    public void buttonClickScenario()
    {

        Espresso.onData(anything())
                .inAdapterView(withId(R.id.citiesList))
                .atPosition(0)
                .perform(click());
        SystemClock.sleep(1000);
        Espresso.onData(anything())
                .inAdapterView(withId(R.id.cardviewPoints))
                .atPosition(0)
                .check(matches(withText(aPointOfAthens)));


    }

}