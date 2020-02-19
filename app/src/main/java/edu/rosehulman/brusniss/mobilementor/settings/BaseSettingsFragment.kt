package edu.rosehulman.brusniss.mobilementor.settings

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.google.android.material.navigation.NavigationView
import edu.rosehulman.brusniss.mobilementor.MainActivity
import edu.rosehulman.brusniss.mobilementor.R

class BaseSettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        return when (preference?.key) {
            "banner_color" -> {
                val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
                showColorDialog(getString(R.string.banner), toolbar!!)
                true
            }
            "menu_color" -> {
                val navView = activity?.findViewById<NavigationView>(R.id.nav_view)
                showColorDialog(getString(R.string.menu), navView!!.getHeaderView(0))
                true
            }
            "about" -> {
                findNavController().navigate(R.id.nav_about)
                return true
            }
            else -> super.onPreferenceTreeClick(preference)
        }
    }

    private fun showColorDialog(colorType: String, view: View) {
        val builder = ColorPickerDialogBuilder.with(context)
        builder.setTitle(getString(R.string.colorDialogTitle, colorType))
        builder.initialColor((view.background as ColorDrawable).color)
        builder.wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
        builder.density(12)
        builder.setPositiveButton(android.R.string.ok) { dialog, selectedColor, allColors ->
            view.setBackgroundColor(selectedColor)
        }
        builder.setNegativeButton(getString(android.R.string.cancel), null)
        builder.build().show()
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_gradient_background, container, false)
//    }
}