package com.root3.photovision_tv_function_unlocker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnCheckedChangeListener {
	ToggleButton tv_toggle, slideshow_toggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_toggle = (ToggleButton)findViewById(R.id.tv_toggle);
		slideshow_toggle = (ToggleButton)findViewById(R.id.slideshow_toggle);

		tv_toggle.setOnCheckedChangeListener(this);
		slideshow_toggle.setOnCheckedChangeListener(this);
	}

	@Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	switch (buttonView.getId()) {
    		case R.id.tv_toggle:
    			//TODO
    		case R.id.slideshow_toggle:
    			//TODO
    	}
    	if (isChecked) /*TODO*/ ;
    	else /*TODO*/ ; 
    }

}
