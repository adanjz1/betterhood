package com.lcc3710;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class PopUp extends Activity{
	String text;
	AlertDialog.Builder popUp;
	PopUp(String message){
		text = message;
		
		
	}
	public void makeWindow(){
		popUp = new AlertDialog.Builder(this)
        .setTitle("Event")
        .setMessage("This is where you are")
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                        
                }
        });
		
		
	}

}
