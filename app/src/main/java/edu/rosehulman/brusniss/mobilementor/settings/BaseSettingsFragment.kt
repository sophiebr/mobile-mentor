package edu.rosehulman.brusniss.mobilementor.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import edu.rosehulman.brusniss.mobilementor.R

class BaseSettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_gradient_background, container, false)
//    }
}