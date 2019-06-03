package com.pad;

import java.io.File;

import com.pad.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.mainlauch);        
        setButtonHandlers();
    }
    
    private void setButtonHandlers() {
		((Button) findViewById(R.id.btnStart)).setOnClickListener(btnClick);
	}
    
    private View.OnClickListener btnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btnStart: {
					
					final File file = new File("/mnt/sdcard/LaunchPadAudio");
					final boolean dir;
					
					if(!file.exists()){
						dir = file.mkdir();
						if(dir){
							mostra("Diretório criado com sucesso.");
						}
						else{
							mostra("Diretório não criado...");
						}						
					}
					
					Intent intent = new Intent(MainActivity.this, LaunchPadActivity.class);
					startActivity(intent);
					finish();
					break;
				}
			}
		}
	};
	
	/*
	 * Esse método é responsavel por mostrar mensagens Toast na tela.
	 * 
	 * @Input String msg
	 */
	private void mostra(String msg){
    	Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}