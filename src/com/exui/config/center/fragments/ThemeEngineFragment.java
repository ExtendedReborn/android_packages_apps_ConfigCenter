/*
 * Copyright (C) 2020 ShapeShiftOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.exui.config.center.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import com.android.settings.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceCategory;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.SwitchPreference;

import java.util.Locale;
import android.text.TextUtils;
import android.view.View;

import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.display.darkmode.DarkModeObserver;
import com.android.settings.display.OverlayCategoryPreferenceController;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import com.android.settingslib.search.SearchIndexable;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import android.util.Log;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import com.exui.config.center.display.AccentColorPreferenceController;

public class ThemeEngineFragment extends DashboardFragment {
    private static final String TAG = "ThemeEngineFragment";

    private ContentResolver mResolver;

    private static final String SYSTEM_THEMES = "android.theme.customization.primary_color";
    private static final String FORCE_DARK_PREF = "hwui_force_dark";

    private boolean mEnabled;
    private DarkModeObserver mDarkModeObserver;
    private Runnable mCallback;
    private ListPreference mThemeSwitch;
    private SwitchPreference mForceDarkPref;

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.CUSTOM_SETTINGS;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Context mContext = getContext();

        mThemeSwitch = (ListPreference) findPreference(SYSTEM_THEMES);
        mForceDarkPref = (SwitchPreference) findPreference(FORCE_DARK_PREF);
        mDarkModeObserver = new DarkModeObserver(mContext);
        mCallback = () -> {
            final boolean active = (getContext().getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_YES) != 0;
            if (active) {
                mForceDarkPref.setEnabled(true);
                mThemeSwitch.setEnabled(true);
            } else {
                mForceDarkPref.setEnabled(false);
                mThemeSwitch.setEnabled(false);
                mThemeSwitch.setSummary(R.string.dark_ui_warning);
            }
        };
        mDarkModeObserver.subscribe(mCallback);

        final ContentResolver resolver = getActivity().getContentResolver();
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDarkModeObserver.subscribe(mCallback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDarkModeObserver.unsubscribe();
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.config_center_themer_category;
    }

    @Override
    protected List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, getSettingsLifecycle(), this);
    }

    private static List<AbstractPreferenceController> buildPreferenceControllers(
            Context context, Lifecycle lifecycle, Fragment fragment) {
        final List<AbstractPreferenceController> controllers = new ArrayList<>();
        controllers.add(new OverlayCategoryPreferenceController(context,
                "android.theme.customization.accent_color"));
        controllers.add(new AccentColorPreferenceController(context));
        controllers.add(new OverlayCategoryPreferenceController(context,
                "android.theme.customization.font"));
        controllers.add(new OverlayCategoryPreferenceController(context,
                "android.theme.customization.adaptive_icon_shape"));
        controllers.add(new OverlayCategoryPreferenceController(context,
                "android.theme.customization.icon_pack.android"));
        controllers.add(new OverlayCategoryPreferenceController(context,
                "android.theme.customization.primary_color"));
        return controllers;
    }
} 
