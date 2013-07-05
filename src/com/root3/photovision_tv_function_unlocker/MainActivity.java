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
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnCheckedChangeListener {
	private int unlocked;
	private static String strValue = "strValue";
	private static String none_alert_mode = "none_alert_mode";
	private static String selectionArgs[] = {none_alert_mode};
	private static Uri URI_PARAMS_TABLE = Uri.parse("content://com.hw.dpf.provider.setting/param_table");
	private ToggleButton toggleButton;
	private ContentResolver contentresolver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        contentresolver = getContentResolver();
		String projection[] = {strValue};

		try {
			Cursor cursor = contentresolver.query(URI_PARAMS_TABLE, projection, "strKey=?", selectionArgs, null);
			if (cursor != null) {
				cursor.moveToFirst();
				unlocked = cursor.getInt(0);
				cursor.close();
				if(unlocked != 1) unlocked = 0;
			}
		} catch (Exception e) {
			unlocked = 0;
		}

		toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        toggleButton.setChecked(unlocked == 0);
		toggleButton.setOnCheckedChangeListener(this);
	}

	@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ContentValues contentvalues;
        unlocked = unlocked == 0 ? 1 : 0;
        contentvalues = new ContentValues();
        contentvalues.put("strKey", none_alert_mode);
        contentvalues.put(strValue, String.valueOf(unlocked));
        if (contentresolver.update(URI_PARAMS_TABLE, contentvalues, "strKey=?", selectionArgs) <= 0) {
        	Uri uri = contentresolver.insert(URI_PARAMS_TABLE, contentvalues);
        	if (uri == null || ContentUris.parseId(uri) == 0)
        		unlocked = unlocked == 0 ? 1 : 0;
        }

        toggleButton.setChecked(unlocked == 0);
    }
}
