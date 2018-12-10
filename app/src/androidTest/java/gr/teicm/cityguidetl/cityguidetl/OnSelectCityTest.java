package gr.teicm.cityguidetl.cityguidetl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import gr.teicm.cityguidetl.cityguidetl.Activities.MainActivity;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class OnSelectCityTest {


	private String mStringToBetyped;

	@Rule
	public ActivityTestRule<MainActivity> mActivityRule
			= new ActivityTestRule<>(MainActivity.class);

	@Before
	public void initValidString() {
		// Specify a valid string.
		mStringToBetyped = "Espresso";
	}

	@Test
	public void changeText_sameActivity() {
		// Type text and then press the button.
		onView(withId(R.id.editTextUserInput))
				.perform(typeText(mStringToBetyped), closeSoftKeyboard());
		onView(withId(R.id.changeTextBt)).perform(click());

		// Check that the text was changed.
		onView(withId(R.id.textToBeChanged))
				.check(matches(withText(mStringToBetyped)));
	}
}
