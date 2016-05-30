package video.audio.recorder;

public class TestAudioCapture {
	public static void main(String[] args) {
		
		AudioRecorder audioRecorder = new AudioRecorder();
		
		

		try {
			
			boolean nextBytes = true;
			while (nextBytes) {
				nextBytes = audioRecorder.capture();
				
			}
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}
}
