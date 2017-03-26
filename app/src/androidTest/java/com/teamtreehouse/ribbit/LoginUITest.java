package com.teamtreehouse.ribbit;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.MonitoringInstrumentation;

import com.teamtreehouse.ribbit.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;

//Run all test together
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginUITest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    String userName = "Alex";
    String password = "123";

      public void firstSuccessfulLoginTest() throws Exception {
        // Arrange
        String e_mail = "goodwin-na@mail.ru";
        onView(withId(R.id.signUpText)).perform(click());
        onView(withId(R.id.usernameField)).perform(typeText(userName));
        onView(withId(R.id.usernameField)).perform(pressImeActionButton());
        onView(withId(R.id.passwordField)).perform(typeText(password));
        onView(withId(R.id.passwordField)).perform(pressImeActionButton());
        onView(withId(R.id.emailField)).perform(typeText(e_mail));
        onView(withId(R.id.emailField)).perform(pressImeActionButton());
        onView(withId(R.id.signupButton)).perform(click());
        logout();
        // Act//
        onView (withId (R.id.usernameField)).perform(typeText (userName));
        onView (withId (R.id.usernameField)).perform(pressImeActionButton());
        onView (withId (R.id.passwordField)).perform(typeText (password));
        onView (withId (R.id.passwordField)).perform(pressImeActionButton());

        onView(withId(R.id.loginButton)).perform(click());

        // Assert
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
        logout();
    }

    @Test
    public void noUserNameEnteredLoginTest() throws Exception {
        // Arrange

        // Act//
        onView (withId (R.id.passwordField)).perform(typeText (password));
        onView (withId (R.id.passwordField)).perform(pressImeActionButton());

        onView(withId(R.id.loginButton)).perform(click());

        // Assert
        onView(withText(R.string.login_error_message)).check(matches(notNullValue()));
        }

    @Test
    public void noPasswordEnteredLoginTest() throws Exception {
        // Arrange

        // Act//
        onView (withId (R.id.usernameField)).perform(typeText (userName));
        onView (withId (R.id.usernameField)).perform(pressImeActionButton());

        onView(withId(R.id.loginButton)).perform(click());

        // Assert
        onView(withText(R.string.login_error_message)).check(matches(notNullValue()));
    }

    @Test
    public void wrongPasswordEnteredLoginTest() throws Exception {
        // Arrange

        // Act//
        onView (withId (R.id.usernameField)).perform(typeText (userName));
        onView (withId (R.id.usernameField)).perform(pressImeActionButton());
        onView (withId (R.id.passwordField)).perform(typeText ("wrong_password"));
        onView (withId (R.id.passwordField)).perform(pressImeActionButton());

        onView(withId(R.id.loginButton)).perform(click());

        // Assert
        onView(withText(R.string.login_error_message2)).check(matches(notNullValue()));
    }

    @Test
    public void wrongLoginEnteredLoginTest() throws Exception {
        // Arrange

        // Act//
        onView (withId (R.id.usernameField)).perform(typeText ("wrong_login"));
        onView (withId (R.id.usernameField)).perform(pressImeActionButton());
        onView (withId (R.id.passwordField)).perform(typeText (password));
        onView (withId (R.id.passwordField)).perform(pressImeActionButton());

        onView(withId(R.id.loginButton)).perform(click());

        // Assert
        onView(withText(R.string.login_error_message2)).check(matches(notNullValue()));
    }


    private void logout() {
        Espresso.openContextualActionModeOverflowMenu();
        onView(withText("Logout")).perform(click());
    }

    }
