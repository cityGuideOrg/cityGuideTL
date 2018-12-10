package gr.teicm.cityguidetl.cityguidetl;




import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;
import gr.teicm.cityguidetl.cityguidetl.Activities.MainActivity;
import gr.teicm.cityguidetl.cityguidetl.Adapters.CityListAdapter;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;

import static androidx.test.espresso.intent.Intents.*;
import static org.hamcrest.core.AllOf.allOf;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class OnSelectCityTest {


	private static final String MESSAGE = "This is a test";
	private static final String PACKAGE_NAME = "com.example.myfirstapp";

	/* Instantiate an IntentsTestRule object. */
	@Rule
	public IntentsTestRule<MainActivity> mIntentsRule =
			new IntentsTestRule<>(MainActivity.class);

	@Test
	public void verifyMessageSentToMessageActivity() {

		// Types a message into a EditText element.
//		onData(withId(R.id.citiesList))
//				.perform(typeText(MESSAGE), closeSoftKeyboard());
		//dinw id sto pointsA

		
		// Clicks a button to send the message to another
		// activity through an explicit intent.
//		onData(withId(R.id.)).perform(click());

		// Verifies that the DisplayMessageActivity received an intent
		// with the correct package name and message.
//		intended(allOf(
//				hasComponent(hasShortClassName(".DisplayMessageActivity")),
//				toPackage(PACKAGE_NAME),
//				hasExtra(MainActivity.EXTRA_MESSAGE, MESSAGE)));

	}
}
