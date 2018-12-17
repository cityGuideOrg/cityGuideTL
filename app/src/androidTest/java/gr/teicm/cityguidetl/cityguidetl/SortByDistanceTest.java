package gr.teicm.cityguidetl.cityguidetl;


import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import gr.teicm.cityguidetl.cityguidetl.Activities.MainActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;

@RunWith(JUnit4.class)
public class SortByDistanceTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);
    String closestPoint = "37.96856 23.700428";
        //String closestPoint = "37.96857 23.700428";

    @Test
    public void buttonScenario()
    {
        Espresso.onData(anything())
                .inAdapterView(withId(R.id.citiesList))
                .atPosition(0)
                .perform(click());
        SystemClock.sleep(1000); //select Athens as city

        Espresso.onView(withId(R.id.SortByDistanceButton)).perform(click());
        SystemClock.sleep(1000); //click Sort By Distance Button

        Espresso.onView(withId(R.id.cardviewPoints))
                .check(matches(hasDescendant(withText(closestPoint)))); //check if closest point right

        SystemClock.sleep(1000);


    }

}
