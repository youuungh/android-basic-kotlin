package com.example.database

import android.os.Bundle
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MySettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val idPreference: EditTextPreference? = findPreference("id")
        val colorPreference: ListPreference? = findPreference("color")
        colorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        idPreference?.summaryProvider =
            Preference.SummaryProvider<EditTextPreference> { preference ->
                val text = preference.text
                if (TextUtils.isEmpty(text)) {
                    "설정되지 않음 "
                } else {
                    "설정된 ID 값은 : $text 입니다"
                }
            }
    }
}