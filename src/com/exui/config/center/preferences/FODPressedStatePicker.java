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

package com.exui.config.center.preferences;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.settings.R;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.LayoutPreference;
import android.graphics.Color;
import android.provider.Settings;


public class FODPressedStatePicker extends LayoutPreference {

    private boolean mAllowDividerAbove;
    private boolean mAllowDividerBelow;
    private View mRootView;
    private static ImageButton Button0;
    private static ImageButton Button1;
    private static ImageButton Button2;
    private static ImageButton Button3;
    private static ImageButton Button4;
    private static ImageButton Button5;
    private static ImageButton Button6;
    private static ImageButton Button7;

    private static final String TAG = "FODPressedStatePicker";

    public FODPressedStatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0 /* defStyleAttr */);
    }

    public FODPressedStatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Preference);
        mAllowDividerAbove = TypedArrayUtils.getBoolean(a, R.styleable.Preference_allowDividerAbove,
                R.styleable.Preference_allowDividerAbove, false);
        mAllowDividerBelow = TypedArrayUtils.getBoolean(a, R.styleable.Preference_allowDividerBelow,
                R.styleable.Preference_allowDividerBelow, false);
        a.recycle();

        a = context.obtainStyledAttributes(
                attrs, R.styleable.Preference, defStyleAttr, 0);
        int layoutResource = a.getResourceId(R.styleable.Preference_android_layout, 0);
        if (layoutResource == 0) {
            throw new IllegalArgumentException("LayoutPreference requires a layout to be defined");
        }
        a.recycle();

        // Need to create view now so that findViewById can be called immediately.
        final View view = LayoutInflater.from(getContext())
                .inflate(layoutResource, null, false);
        setView(view, context);
    }

    private void setView(View view, Context context) {
        setLayoutResource(R.layout.layout_preference_frame);
        mRootView = view;
        setShouldDisableView(false);
        Button0 = findViewById(R.id.fodpressed_statezero_button);
        Button1 = findViewById(R.id.fodpressed_stateone_button);
        Button2 = findViewById(R.id.fodpressed_statetwo_button);
        Button3 = findViewById(R.id.fodpressed_statethree_button);
        Button4 = findViewById(R.id.fodpressed_statefour_button);
        Button5 = findViewById(R.id.fodpressed_statefive_button);
        Button6 = findViewById(R.id.fodpressed_statesix_button);
        Button7 = findViewById(R.id.fodpressed_stateseven_button);

        int defaultfodpressed_state = Settings.System.getInt(
                context.getContentResolver(), Settings.System.FOD_PRESSED_STATE, 0);
        if (defaultfodpressed_state==0) {
            updateHighlightedItem(Button0, context);
        } else if (defaultfodpressed_state == 1) {
            updateHighlightedItem(Button1, context);
        } else if (defaultfodpressed_state == 2) {
            updateHighlightedItem(Button2, context);
        } else if (defaultfodpressed_state == 3) {
            updateHighlightedItem(Button3, context);
        } else if (defaultfodpressed_state == 4) {
            updateHighlightedItem(Button4, context);
        } else if (defaultfodpressed_state == 5) {
            updateHighlightedItem(Button5, context);
        } else if (defaultfodpressed_state == 6) {
            updateHighlightedItem(Button6, context);
        } else if (defaultfodpressed_state == 7) {
            updateHighlightedItem(Button7, context);
        }

        Button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSettings(0, context);
                updateHighlightedItem(Button0, context);
            }
        });
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSettings(1, context);
                updateHighlightedItem(Button1, context);
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSettings(2, context);
                updateHighlightedItem(Button2, context);
            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSettings(3, context);
                updateHighlightedItem(Button3, context);
            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSettings(4, context);
                updateHighlightedItem(Button4, context);
            }
        });
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSettings(5, context);
                updateHighlightedItem(Button5, context);
            }
        });
        Button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSettings(6, context);
                updateHighlightedItem(Button6, context);
            }
        });
        Button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSettings(7, context);
                updateHighlightedItem(Button7, context);
            }
        });
    }

    private void updateSettings(int fodpressed_state, Context context) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.FOD_PRESSED_STATE, fodpressed_state);
    }

    private void updateHighlightedItem(ImageButton activebutton, Context context) {
        int defaultcolor = context.getResources().getColor(R.color.fod_pressed_state_item_background_stroke_color);
        ColorStateList defaulttint = ColorStateList.valueOf(defaultcolor);
        Button0.setBackgroundTintList(defaulttint);
        Button1.setBackgroundTintList(defaulttint);
        Button2.setBackgroundTintList(defaulttint);
        Button3.setBackgroundTintList(defaulttint);
        Button4.setBackgroundTintList(defaulttint);
        Button5.setBackgroundTintList(defaulttint);
        Button6.setBackgroundTintList(defaulttint);
        Button7.setBackgroundTintList(defaulttint);
        activebutton.setBackgroundTintList(Utils.getColorAttr(getContext(), android.R.attr.colorAccent));
    }
}
