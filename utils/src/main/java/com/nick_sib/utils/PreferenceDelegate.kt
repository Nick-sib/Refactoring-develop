package com.nick_sib.utils

import android.content.SharedPreferences
import kotlin.reflect.KProperty

class PreferenceDelegate<TValue>(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defValue: TValue
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): TValue {
        with(preferences) {
            return when (defValue) {
                is Boolean -> getBoolean(name, defValue) as TValue ?: defValue
                is Int -> (getInt(name, defValue) as TValue) ?: defValue
                is Float -> (getFloat(name, defValue) as TValue) ?: defValue
                is Long -> (getLong(name, defValue) as TValue) ?: defValue
                is String -> (getString(name, defValue) as TValue) ?: defValue
                else -> throw NotFoundRealizationException(defValue)
            }
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: TValue) {
        with(preferences.edit()) {
            when (value) {
                is Boolean -> putBoolean(name, value)
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is Long -> putLong(name, value)
                is String -> putString(name, value)
                else -> throw NotFoundRealizationException(value)
            }
            apply()
        }
    }
}


class NotFoundRealizationException(value: Any?) : Exception("not found realization for ${value?.javaClass}")