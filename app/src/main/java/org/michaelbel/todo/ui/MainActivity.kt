package org.michaelbel.todo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.todo.R
import org.michaelbel.todo.ui.main.MainFragment

@AndroidEntryPoint
class MainActivity: AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.container, MainFragment.newInstance())
            }
        }
    }
}