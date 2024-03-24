package es.jgalcolea.axpejorgegil.tests

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import es.jgalcolea.axpejorgegil.MainActivity
import es.jgalcolea.axpejorgegil.di.AppModule
import es.jgalcolea.axpejorgegil.ui.ContactNavigation
import es.jgalcolea.axpejorgegil.viewmodel.SharedContactViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(AppModule::class)
@HiltAndroidTest
class ContactListTest {
    //Test rules
    @get:Rule(order = 1) var hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 2) val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun doesRenderAndSearchbarChanges() {
        composeTestRule.activity.setContent {
            val viewModel = hiltViewModel<SharedContactViewModel>()
            ContactNavigation(viewModel)
        }
        composeTestRule.waitForIdle()

        //Initial state
        composeTestRule.onNodeWithText("Contactos").assertIsDisplayed()

        //Click search icon and back button
        composeTestRule.onNodeWithTag("searchButton").performClick()
        composeTestRule.onNodeWithTag("searchInputField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("cancelSearchButton").performClick()

        //Check if we are back to initial state
        composeTestRule.onNodeWithText("Contactos").assertIsDisplayed()
    }

    @Test
    fun doesDisplayContactsAndSearchFilter() {
        composeTestRule.activity.setContent {
            val viewModel = hiltViewModel<SharedContactViewModel>()
            ContactNavigation(viewModel)
        }
        composeTestRule.waitForIdle()

        //Check if mockRepository added contacts from mock_list1.json into ContactList
        composeTestRule.onNodeWithText("Yasser Heesen").assertIsDisplayed()

        //Scroll to bottom and check if first contact from second json is loaded
        composeTestRule.onNodeWithTag("contactLazyColumn").performScrollToIndex(19)
        composeTestRule.onNodeWithText("Cecilie Kristensen").assertIsDisplayed()

        //Click search icon and search for contact Eugenie Frost
        composeTestRule.onNodeWithTag("searchButton").performClick()
        composeTestRule.onNodeWithTag("searchInputField").performTextInput("Eugenie")
        composeTestRule.onNodeWithText("Eugenie Frost").assertIsDisplayed()

    }

    @Test
    fun doesDisplayContactDetailScreen() {
        composeTestRule.activity.setContent {
            val viewModel = hiltViewModel<SharedContactViewModel>()
            ContactNavigation(viewModel)
        }
        composeTestRule.waitForIdle()

        //Click a contact and check if detail screen is displayed
        composeTestRule.onNodeWithText("Petros Mair").performClick()
        composeTestRule.onNodeWithTag("contactDetailTopBar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hombre").assertIsDisplayed()
    }
}