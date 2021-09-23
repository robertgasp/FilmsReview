package com.example.filmsreview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.filmsreview.databinding.ActivityMainBinding
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.example.filmsreview.ui.BottomMenuClickInterface
import com.example.filmsreview.ui.CinemasAround
import com.example.filmsreview.ui.DescriptionPage
import com.example.filmsreview.ui.MainPage
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), FilmClickListener,BottomMenuClickInterface {
    val toolbar: Toolbar? = null
    private var isAdult = false
    private lateinit var checkBoxForAdult: MenuItem
    private lateinit var binding: ActivityMainBinding
    private val IS_ADULTS_MODE = "IS_ADULTS_MODE"

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
        menu?.let {
            checkBoxForAdult = menu.findItem(R.id.isAdult)
            isAdult = initDataSet()
            if (isAdult == true) {
                checkBoxForAdult.isChecked = true
            } else checkBoxForAdult.isChecked = false
        }
        return true
    }

//    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
//        return super.onCreatePanelMenu(featureId, menu)
//    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        isAdult = initDataSet()

        if (isAdult == true) {
            item.isChecked = true
        } else item.isChecked = false

        when (item.itemId) {
            R.id.isAdult ->
                if (item.isChecked) {
                    item.isChecked = false
                    isAdult = false
                    Toast.makeText(
                        this,
                        "Просмотр фильмов с рейтингом 18+ выключен",
                        Toast.LENGTH_SHORT
                    ).show()
                    saveDataSettingsToDisk(isAdult)
                } else {
                    item.isChecked = true
                    isAdult = true
                    Toast.makeText(
                        this,
                        "Просмотр фильмов с рейтингом 18+ включен",
                        Toast.LENGTH_SHORT
                    ).show()
                    saveDataSettingsToDisk(isAdult)
                }
        }

        return super.onOptionsItemSelected(item)
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


    private fun initDataSet(): Boolean {
        this.let { activity ->
            activity.getPreferences(Context.MODE_PRIVATE)?.let { preferences ->
                isAdult = preferences.getBoolean(IS_ADULTS_MODE, false)
            }
        }
        return isAdult
    }


    private fun saveDataSettingsToDisk(isAdult: Boolean) {
        this.let { activity ->
            val preferences = activity.getPreferences(Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean(IS_ADULTS_MODE, isAdult)
                .apply()
        }
    }

    fun getAdultMode(): Boolean = isAdult


    override fun selectBottomNavigationTab(selectedfragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container,selectedfragment)
            .commitAllowingStateLoss()
    }
}