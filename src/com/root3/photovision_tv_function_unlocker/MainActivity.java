/* 
 * Copyright (C) 2013 173210 <root.3.173210@live.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */

package com.root3.photovision_tv_function_unlocker;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnCheckedChangeListener {
	int locked;
	static Uri URI_PARAMS_TABLE = Uri.parse("content://com.hw.dpf.provider.setting/param_table");
	ToggleButton toggleButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			Cursor cursor;
			cursor = null;
			Uri uri = URI_PARAMS_TABLE;
			String as[] = new String[1];
			as[0] = "strValue";
			String as1[] = new String[1];
			as1[0] = "none_alert_mode";
			cursor = getContentResolver().query(uri, as, "strKey=?", as1, null);
			locked = cursor.getInt(0);
			if(cursor != null)
				cursor.close();
			if(locked != 1 && locked != 0)
				locked = 1;
		} catch (Exception e) {
			locked = 1;
		}

		toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        if (locked == 0) toggleButton.setChecked(false);
        else toggleButton.setChecked(true);
		toggleButton.setOnCheckedChangeListener(this);
	}

	@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ContentValues contentvalues;
        ContentResolver contentresolver;
        String as[];
        if (locked == 0) locked = 1;
        else locked = 0;
        contentvalues = new ContentValues();
        contentvalues.put("strKey", "none_alert_mode");
        contentvalues.put("strValue", String.valueOf(locked));
        contentresolver = getContentResolver();
        as = new String[1];
        as[0] = "none_alert_mode";
        if(contentresolver.update(URI_PARAMS_TABLE, contentvalues, "strKey=?", as) <= 0)
            getContentResolver().insert(URI_PARAMS_TABLE, contentvalues);
        else  {
            if (locked == 0) locked = 1;
            else locked = 0;
        }
        if (locked == 0) toggleButton.setChecked(false);
        else toggleButton.setChecked(true);
    }
}
