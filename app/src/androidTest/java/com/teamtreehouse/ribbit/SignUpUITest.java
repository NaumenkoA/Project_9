package com.teamtreehouse.ribbit;

import android.provider.ContactsContract;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.teamtreehouse.ribbit.ui.MainActivity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class SignUpUITest {
    protected String userName;
    protected String password;
    protected String e_mail;

    @Rule
    public ActivityTestRule <MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
   public void setupTestParams () throws Exception {
        userName = "Alex";
        password = "123";
        e_mail = "goodwin-na@mail.ru";
        onView(withId(R.id.signUpText)).perform(click());
             }

    @Test
    public void userAlreadyExistsSignUpTest() throws Exception {
      // Arrange

        userName = "Alex_N";
        password = "12345";
        e_mail = "teamtreehouse@mail.ru";

      onView (withId (R.id.usernameField)).perform(typeText (userName));
      onView (withId (R.id.usernameField)).perform(pressImeActionButton());
      onView (withId (R.id.passwordField)).perform(typeText (password));
      onView (withId (R.id.passwordField)).perform(pressImeActionButton());
      onView (withId (R.id.emailField)).perform(typeText (e_mail));
      onView (withId (R.id.emailField)).perform(pressImeActionButton());
       onView(withId(R.id.signupButton)).perform(click());

       logout();
       onView(withId(R.id.signUpText)).perform(click());
       userName = "Alex_N";
       password = "123456";
       e_mail = "cartman@mail.ru";

      //Act

      onView (withId (R.id.usernameField)).perform(typeText (userName));
      onView (withId (R.id.usernameField)).perform(pressImeActionButton());
      onView (withId (R.id.passwordField)).perform(typeText (password));
      onView (withId (R.id.passwordField)).perform(pressImeActionButton());
      onView (withId (R.id.emailField)).perform(typeText (e_mail));
      onView (withId (R.id.emailField)).perform(pressImeActionButton());
      onView(withId(R.id.signupButton)).perform(click());

      // Assert
      onView(withText("Username already in use.")).check(matches(notNullValue()));

    }

    @Test
    public void emailAlreadyExistsSignUpTest() throws Exception {

        // Arrange
        userName = "Alex_X";
        password = "123";
        e_mail = "pacman@mail.ru";

        onView (withId (R.id.usernameField)).perform(typeText (userName));
        onView (withId (R.id.usernameField)).perform(pressImeActionButton());
        onView (withId (R.id.passwordField)).perform(typeText (password));
        onView (withId (R.id.passwordField)).perform(pressImeActionButton());
        onView (withId (R.id.emailField)).perform(typeText (e_mail));
        onView (withId (R.id.emailField)).perform(pressImeActionButton());
        onView(withId(R.id.signupButton)).perform(click());

        logout();
        onView(withId(R.id.signUpText)).perform(click());
        userName = "Alex_XYZ";
        password = "1234";
        e_mail = "pacman@mail.ru";

        //Act

        onView (withId (R.id.usernameField)).perform(typeText (userName));
        onView (withId (R.id.usernameField)).perform(pressImeActionButton());
        onView (withId (R.id.passwordField)).perform(typeText (password));
        onView (withId (R.id.passwordField)).perform(pressImeActionButton());
        onView (withId (R.id.emailField)).perform(typeText (e_mail));
        onView (withId (R.id.emailField)).perform(pressImeActionButton());
        onView(withId(R.id.signupButton)).perform(click());

        // Assert
        onView(withText("Email address already in use.")).check(matches(notNullValue()));

    }

    @Test
    public void noUserNameSignUpTest() throws Exception {
        // Arrange

        // Act//
        onView (withId (R.id.passwordField)).perform(typeText (password));
        onView (withId (R.id.passwordField)).perform(pressImeActionButton());
        onView (withId (R.id.emailField)).perform(typeText (e_mail));
        onView (withId (R.id.emailField)).perform(pressImeActionButton());

        onView(withId(R.id.signupButton)).perform(click());

        // Assert
        onView(withText(R.string.signup_error_message)).check(matches(notNullValue()));
    }

    @Test
    public void noPasswordSignUpTest() throws Exception {
        // Arrange

        // Act//
        onView (withId (R.id.usernameField)).perform(typeText (userName));
        onView (withId (R.id.usernameField)).perform(pressImeActionButton());
        onView (withId (R.id.emailField)).perform(typeText (e_mail));
        onView (withId (R.id.emailField)).perform(pressImeActionButton());

        onView(withId(R.id.signupButton)).perform(click());

        // Assert
        onView(withText(R.string.signup_error_message)).check(matches(notNullValue()));

    }

    @Test
    public void noEmailSignUpTest() throws Exception {
        // Arrange

        // Act//
        onView (withId (R.id.usernameField)).perform(typeText (userName));
        onView (withId (R.id.usernameField)).perform(pressImeActionButton());
        onView (withId (R.id.passwordField)).perform(typeText (password));
        onView (withId (R.id.passwordField)).perform(pressImeActionButton());

        onView(withId(R.id.signupButton)).perform(click());

        // Assert
        onView(withText(R.string.signup_error_message)).check(matches(notNullValue()));
    }

    @Test
    public void successfulSignUpTest() throws Exception {
        // Arrange

        // Act//
        onView (withId (R.id.usernameField)).perform(typeText (userName));
        onView (withId (R.id.usernameField)).perform(pressImeActionButton());
        onView (withId (R.id.passwordField)).perform(typeText (password));
        onView (withId (R.id.passwordField)).perform(pressImeActionButton());
        onView (withId (R.id.emailField)).perform(typeText (e_mail));
        onView (withId (R.id.emailField)).perform(pressImeActionButton());

        onView(withId(R.id.signupButton)).perform(click());

        // Assert
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        logout();
    }

    private void logout() {
        Espresso.openContextualActionModeOverflowMenu();
        onView(withText("Logout")).perform(click());
        }

  }
