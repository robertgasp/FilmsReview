package com.example.filmsreview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
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
import org.koin.core.component.getScopeId

class MainActivity : AppCompatActivity(), FilmClickListener {
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

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        return super.onCreatePanelMenu(featureId, menu)
    }

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
                    Toast.makeText(this, "18+ выключен", Toast.LENGTH_SHORT).show()
                    saveDataSettingsToDisk(isAdult)
                } else {
                    item.isChecked = true
                    isAdult = true
                    Toast.makeText(this, "18+ включен", Toast.LENGTH_SHORT).show()
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


    private fun initDataSet():Boolean {
        this.let { it2 ->
            it2.getPreferences(Context.MODE_PRIVATE)?.let { preferences ->
                isAdult = preferences.getBoolean(IS_ADULTS_MODE, false)
            }
        }
        return isAdult
    }


    private fun saveDataSettingsToDisk(isAdult: Boolean) {
        this.let { it3 ->
            val preferences = it3.getPreferences(Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putBoolean(IS_ADULTS_MODE, isAdult)
                .apply()
        }
    }

    fun getAdultMode():Boolean = isAdult
}