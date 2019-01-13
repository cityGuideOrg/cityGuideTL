package gr.teicm.cityguidetl.cityguidetl.Activities;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import gr.teicm.cityguidetl.cityguidetl.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4.class)
@LargeTest
public class LoginScreenTest {

	private String mStringToBetyped;

	@Rule
	public ActivityTestRule<LoginActivity> loginActivityActivityTestRule
			= new ActivityTestRule<>(LoginActivity.class);

	@Test
	public void testActivityResultIsHandledProperly() {
		onView(withId((R.id.usernameET))).check(matches(isDisplayed()));
		onView(withId((R.id.passwordET))).check(matches(isDisplayed()));
		onView(withId((R.id.passwordTV))).check(matches(isDisplayed()));
		onView(withId((R.id.usernameTV))).check(matches(isDisplayed()));

	}
}
