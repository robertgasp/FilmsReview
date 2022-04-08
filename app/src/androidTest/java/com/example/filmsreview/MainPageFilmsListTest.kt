package com.example.filmsreview

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import com.example.filmsreview.ui.FilmsAdapter
import com.example.filmsreview.ui.MainPage
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainPageFilmsListTest {

    private val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()
        uiDevice.swipe(1500, 700, 0, 700, 5)
        val appView = UiScrollable(UiSelector().scrollable(false))
        val filmsReviewApp =
            appView.getChildByText(UiSelector().className(TextView::class.java.name), "FilmsReview")
        filmsReviewApp.clickAndWaitForNewWindow()
        uiDevice.wait(Until.hasObject(By.res(packageName)), 5000L)

        val films = uiDevice.findObject(UiSelector().text("Фильмы"))
        films.clickAndWaitForNewWindow()
    }


    @Test
    fun scrollOnFilm() {
        onView(ViewMatchers.withId(R.id.films_list)).perform(
            RecyclerViewActions.scrollTo<FilmsAdapter.FilmsHolder>(hasDescendant(withText("Зверопой 2")))
        )
        delay()
    }

    @Test
    fun clickOnFilm() {
        onView(ViewMatchers.withId(R.id.films_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<FilmsAdapter.FilmsHolder>(
                    0,
                    click()
                )
            )
        delay()
    }

    private fun delay(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for $5 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(5000L)
            }
        }
    }
}