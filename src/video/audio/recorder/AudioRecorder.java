package video.audio.recorder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioRecorder {

	AudioFormat format = new AudioFormat(44100, 16, 2, true, true);

	DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
	DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);
	
	TargetDataLine targetLine;
	SourceDataLine sourceLine;
	byte[] targetData ;
	int index =0;

	private List<byte[]> soundList = new ArrayList<>();

	private List<Integer> accountList = new ArrayList<>();
	
	public AudioRecorder(){
		try{
			
			
			targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
			targetLine.open(format);
			targetLine.start();
			
			sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
			sourceLine.open(format);
			sourceLine.start();

			
			targetData = new byte[targetLine.getBufferSize() / 5];
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public boolean capture() {
		int numBytesRead=0;
		int[] buff = new int[10];
		//for(int x =0;x<10;x++){
			numBytesRead = targetLine.read(targetData, 0, targetData.length);
		//	buff[x] = numBytesRead;
		//}
		if (numBytesRead == -1)	return false;
		//for(int x =0;x<10;x++)
		sourceLine.write(targetData, 0, numBytesRead);
		accountList .add(numBytesRead);
		soundList.add(targetData);
		targetData = new byte[targetLine.getBufferSize() / 5];
		return true;
		
	}
	
	public boolean play(){
		sourceLine.write(soundList.get(index), 0, accountList.get(index));
		index++;
		return index<soundList.size();
	}

}
