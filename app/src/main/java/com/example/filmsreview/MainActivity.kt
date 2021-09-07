package com.example.filmsreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.filmsreview.databinding.ActivityMainBinding
import com.example.filmsreview.model.Film
import com.example.filmsreview.repository.FilmsList
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.example.filmsreview.ui.DescriptionPage
import com.example.filmsreview.ui.DescriptionPage.Companion.BUNDLE_EXTRA
import com.example.filmsreview.ui.MainPage
import org.koin.android.ext.android.bind

class MainActivity : AppCompatActivity(), FilmClickListener {
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

    override fun filmClicked(film: FactDataObj) {

        val fragmentManager = this.supportFragmentManager

        val bundle = Bundle()
        bundle.putParcelable(DescriptionPage.BUNDLE_EXTRA, film)

        fragmentManager.beginTransaction()
            .replace(R.id.container, DescriptionPage.newInstance(bundle))
            .addToBackStack("")
            .commitAllowingStateLoss()
    }
}