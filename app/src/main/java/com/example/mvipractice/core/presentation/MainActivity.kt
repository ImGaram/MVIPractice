package com.example.mvipractice.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvipractice.add_note.presentation.AddNoteScreen
import com.example.mvipractice.core.presentation.ui.theme.MVIPracticeTheme
import com.example.mvipractice.note_list.presentation.NoteListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVIPracticeTheme {
                Navigation()
            }
        }
    }

    @Composable
    fun Navigation(modifier: Modifier = Modifier) {
        val navController = rememberNavController()

        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Screen.NoteList
        ) {
            composable<Screen.NoteList> {
                NoteListScreen(
                    onNavigateToAddNote = {
                        navController.navigate(Screen.AddNote)
                    }
                )
            }

            composable<Screen.AddNote> {
                AddNoteScreen(
                    onSave = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}