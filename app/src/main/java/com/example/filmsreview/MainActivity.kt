package com.example.filmsreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import com.example.filmsreview.databinding.ActivityMainBinding
import com.example.filmsreview.ui.MainPage
import org.koin.android.ext.android.bind

class MainActivity : AppCompatActivity() {
    val toolbar: Toolbar? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainPage.newInstance())
                .commit()
        }


        val toolbar: Toolbar = findViewById(R.id.widget_toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_action_menu, menu)
        return true
    }


}