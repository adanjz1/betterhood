/**
 * 
 */
package com.lcc3710;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InfoBubble extends RelativeLayout {

	private TextView title;
	private TextView creator;
	private TextView date;
	private TextView address;
	private TextView distance;
	
	/**
	 * @param context
	 */
	public InfoBubble(Context context) {
		super(context);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public InfoBubble(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public InfoBubble(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		title = new TextView(this.getContext());
		creator = new TextView(this.getContext());
		date = new TextView(this.getContext());
		address = new TextView(this.getContext());
		distance = new TextView(this.getContext());
		
		this.setBackgroundResource(R.drawable.map_dialogue_bubble);
		Bitmap graphic = BitmapFactory.decodeResource(this.getResources(), R.drawable.map_dialogue_bubble);
		RelativeLayout.LayoutParams attr = new RelativeLayout.LayoutParams(graphic.getWidth(), graphic.getHeight());
		this.setLayoutParams(attr);
	}
	
	public void update(Template t) {
		// reset the text fields
		title.setText("");
		creator.setText("");
		date.setText("");
		address.setText("");
		distance.setText("");
		
		// start filling in our text fields
		creator.setText(t.creator);
		
		TemplateWidget[] widgets = t.widgets;
		for (int i = 0; i < widgets.length; i++) {
			TemplateWidget w = widgets[i];
			if (w.label.equals("Title")) {
				title.setText(w.value);
			}
			if (w.type.equals("Location")) {
				address.setText(w.value);
			}
			if (w.type.equals("StartDate")) {
				date.setText(w.value);
			}
			if (w.type.equals("EndDate")) {
				date.append(w.value);
			}
			
			// hard coding shit
			distance.setText("1.0 mi");
		}
	}
}
