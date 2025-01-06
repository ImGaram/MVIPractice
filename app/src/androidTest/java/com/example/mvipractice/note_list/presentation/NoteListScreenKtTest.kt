package com.example.mvipractice.note_list.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.mvipractice.core.di.AppModule
import com.example.mvipractice.core.presentation.MainActivity
import com.example.mvipractice.core.presentation.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteListScreenKtTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun insertNote_noteIsDisplayedInList() {
        insertNode(1)
        assertNoteIsDisplayed(1)
    }

    @Test
    fun deleteNote_noteIsNotDisplayedInList() {
        insertNode(1)
        assertNoteIsDisplayed(1)

        deleteNote(1)
        assertNoteIsNotDisplayed(1)
    }

    @Test
    fun noteListScreenEndToEndTest() {
        insertNode(1)
        assertNoteIsDisplayed(1)

        insertNode(2)
        assertNoteIsDisplayed(2)

        insertNode(3)
        assertNoteIsDisplayed(3)

        deleteNote(1)
        assertNoteIsNotDisplayed(1)
        assertNoteIsDisplayed(2)
        assertNoteIsDisplayed(3)

        insertNode(4)
        deleteNote(3)
        assertNoteIsNotDisplayed(3)
        assertNoteIsDisplayed(2)
        assertNoteIsDisplayed(4)
    }

    private fun insertNode(noteNumber: Int) {
        composeRule.onNodeWithTag(TestTags.ADD_NOTE_FAB)
            .performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("title $noteNumber")

        composeRule.onNodeWithTag(TestTags.DESCRIPTION_TEXT_FIELD)
            .performTextInput("description $noteNumber")

        composeRule.onNodeWithTag(TestTags.SAVE_BUTTON)
            .performClick()
    }

    private fun deleteNote(noteNumber: Int) {
        composeRule.onNodeWithContentDescription(TestTags.DELETE_NOTE + "title $noteNumber")
            .performClick()
    }

    private fun assertNoteIsDisplayed(noteNumber: Int) {
        composeRule.onNodeWithText("title $noteNumber")
            .assertIsDisplayed()
    }

    private fun assertNoteIsNotDisplayed(noteNumber: Int) {
        composeRule.onNodeWithText("title $noteNumber")
            .assertIsNotDisplayed()
    }
}