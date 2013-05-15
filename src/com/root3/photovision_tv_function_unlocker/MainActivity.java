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
	private int locked;
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

		try {
			Cursor cursor = null;
			Uri uri = URI_PARAMS_TABLE;
			String projection[] = {strValue};
			cursor = contentresolver.query(uri, projection, "strKey=?", selectionArgs, null);
			locked = cursor.getInt(0);
			if(cursor != null) cursor.close();
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
        if (locked == 0) locked = 1;
        else locked = 0;
        contentvalues = new ContentValues();
        contentvalues.put("strKey", none_alert_mode);
        contentvalues.put(strValue, String.valueOf(locked));
        if (contentresolver.update(URI_PARAMS_TABLE, contentvalues, "strKey=?", selectionArgs) <= 0) {
        	Uri uri = contentresolver.insert(URI_PARAMS_TABLE, contentvalues);
        	if (uri == null || ContentUris.parseId(uri) == 0){
            	if (locked == 0) locked = 1;
            	else locked = 0;
        	}
        }

        if (locked == 0) toggleButton.setChecked(false);
        else toggleButton.setChecked(true);
    }
}
