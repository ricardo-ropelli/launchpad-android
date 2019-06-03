package com.pad;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.pad.R;
import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class LaunchPadActivity extends Activity {
	
	private static final String TAG = "LaunchPadApp";	
	Map<String, Key> launchpad = new HashMap<String, Key>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main);
        
        //Iniciando os botões do launchpad.
        iniciaKey(R.id.A1, "A1", "/mnt/sdcard/LaunchPadAudio/song1.mp3");
        iniciaKey(R.id.B1, "B1", "/mnt/sdcard/LaunchPadAudio/song2.mp3");
        iniciaKey(R.id.C1, "C1", "/mnt/sdcard/LaunchPadAudio/song3.mp3");
        iniciaKey(R.id.D1, "D1", "/mnt/sdcard/LaunchPadAudio/song4.mp3");
        
        iniciaKey(R.id.A2, "A2", "/mnt/sdcard/LaunchPadAudio/song5.mp3");
        iniciaKey(R.id.B2, "B2", "/mnt/sdcard/LaunchPadAudio/song6.mp3");
        iniciaKey(R.id.C2, "C2", "/mnt/sdcard/LaunchPadAudio/song7.mp3");
        iniciaKey(R.id.D2, "D2", "/mnt/sdcard/LaunchPadAudio/song8.mp3");
        
        iniciaKey(R.id.A3, "A3", "/mnt/sdcard/LaunchPadAudio/song9.mp3");
        iniciaKey(R.id.B3, "B3", "/mnt/sdcard/LaunchPadAudio/song10.mp3");
        iniciaKey(R.id.C3, "C3", "/mnt/sdcard/LaunchPadAudio/song11.mp3");
        iniciaKey(R.id.D3, "D3", "/mnt/sdcard/LaunchPadAudio/song12.mp3");
        
        iniciaKey(R.id.A4, "A4", "/mnt/sdcard/LaunchPadAudio/song13.mp3");
        iniciaKey(R.id.B4, "B4", "/mnt/sdcard/LaunchPadAudio/song14.mp3");
        iniciaKey(R.id.C4, "C4", "/mnt/sdcard/LaunchPadAudio/song15.mp3");
        iniciaKey(R.id.D4, "D4", "/mnt/sdcard/LaunchPadAudio/song16.mp3");
    }
    
    /*
     * Para cada Click normal nas teclas ele verificará se a mesma
     * tem algum áudio associado, caso tenha, chamará em forma de
     * uma nova thread o play setando o loop do MediaPlayer para false.
     * Caso não tenha nenhum áudio associado, o mesmo chamará o método
     * startRecord() e na segunda vez que clicado no mesmo botão, chamará o
     * stopRecord() e assim terminando o cilco de gravar o áudio e associando-o
     * à tecla.
     *         
     * Para cada Click longo nas teclas ele verificará se a mesma
     * tem algum áudio associado, caso tenha, mudará o visual do botão
     * para vermelho, e chamará em forma de uma nova thread o play
     * setando o loop do MediaPlayer para true.
     */
    private void iniciaKey(int id, final String key, final String path){
    	
    	final File file = new File(path);
    	final ImageButton launchkey = (ImageButton) findViewById(id);
        final Key k = new Key(launchkey);

        if(file.exists()){
        	k.setPath(path);
        	launchkey.setImageResource(R.drawable.gravado);
        }
        
        launchkey.setOnClickListener(new OnClickListener() {
        	public void onClick(final View v) {
    			if (file.exists()){
    				Thread play = new Thread(new Runnable() {
						public void run() {
							startPlay(k.getPath(), false, k);
					    }
					});
    				k.setThread(play);
    				if(k.getThread() != null){
    					k.getThread().start();
    				}
        		}
        		else {
					mostra("Som não existente.");
        		}
        	}        	
        });
        
        launchkey.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				if(!k.isLoop()){
					ImageButton b = (ImageButton) v;
	            	b.setImageResource(R.drawable.executando);
	            	k.setLoop(true);
	            	Thread play = new Thread (new Runnable() {
						public void run() {
							startPlay(k.getPath(), true, k);
					    }
					});
					play.start();
	            	return true;
				}
				else{
					k.setLoop(false);
					ImageButton b = (ImageButton) v;
	            	b.setImageResource(R.drawable.gravado);
	            	k.getPlayer().stop();	            	
	            	return true;
				}			
			}
		});
        launchpad.put(key, k);
    }
	
	/*
	 * Esse método é responsavel por montar o MediaPlayer e
	 * executar o arquivo associado à tecla clicada na tela.
	 * De acordo com a variavel loop o método setara o loop da
	 * música em questão.
	 * 
	 * @Input String path
	 * @Input boolean loop
	 */
	private void startPlay(String path, boolean loop, Key k){
		Log.d(TAG, "Begin startPlay()");
		
		File file = new File(path);
		
		if(file.exists()){			
			if(file.isFile()){				
				if(file.canRead()){					
					MediaPlayer player = MediaPlayer.create(this, Uri.fromFile(file));
					k.setPlayer(player);
					if (player != null){
						try {
							player.start();					
							player.setLooping(loop);					
							if (!loop){
								while(player.isPlaying());
								player.release();
							}					
						}
						catch (IllegalStateException e) {
							e.printStackTrace();
						}
					}
					else{
						mostra("Player null.");
					}
				}
				else{
					mostra("Arquivo não pode ser lido.");
				}
			}
			else{
				mostra("Caminho não se refere a um arquivo.");
			}
		}
		else{
			mostra("Arquivo não encontrado.");
		}		
	}
	
	
	/*
	 * Esse método é responsavel por mostrar mensagens Toast na tela.
	 * 
	 * @Input String msg
	 */
	private void mostra(String msg){
    	Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}