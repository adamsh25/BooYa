package com.example.booya;


import com.example.booya.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.example.booya.video.processing.BooyaFFMPEG;

import java.io.IOException;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        BooyaFFMPEG a = new BooyaFFMPEG(getBaseContext());
        a.execute("a");

//        try {
//            a.CreateBooyaVideo("/sdcard/videokit/video.mp4", "/sdcard/videokit/outFull.mp4");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
/*	     Intent i = new Intent();     
	       startActivity(i);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

//	public void makeMagic() throws IOException, InterruptedException
//	{
//
//		File fileBinDir = new File(getBaseContext().getFilesDir().getParentFile(),"lib");
//
//		File fileBin = new File(fileBinDir,"libffmpeg.so");
//		//File fileBin = new File(fileBinDir,"ffmpeg.so");
//
//
//		String ffmpegBin = fileBin.getCanonicalPath();
//
//	     FfmpegController a = new FfmpegController(getBaseContext());
//
//			MediaDesc in = new MediaDesc();
//			in.path= "/sdcard/videokit/video.mp4";
//
//			MediaDesc in2 = new MediaDesc();
//			in2.path= "/sdcard/videokit/in2.mp4";
//
//			MediaDesc in3 = new MediaDesc();
//			in3.path= "/sdcard/videokit/file.png";
//
//			MediaDesc out = new MediaDesc();
//			out.path = "/sdcard/videokit/out3.mp4";
//
//
//			ShellCallback sc = new ShellCallback() {
//
//				@Override
//				public void shellOut(String shellLine) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void processComplete(int exitValue) {
//					// TODO Auto-generated method stub
//
//				}
//
//			};
//
//	    	ArrayList<String> cmd = new ArrayList<String>();
//	    	// Watermarking - Working using -VF
////			cmd.add(ffmpegBin);
////			cmd.add("-y");
////			cmd.add("-i");
////			cmd.add(new File(in.path).getCanonicalPath());
////			cmd.add("-strict");
////			cmd.add("-2");
////			cmd.add("-vf");
////			cmd.add("movie="+ new File(in3.path).getCanonicalPath()+" [watermark];[in][watermark] overlay=10:10 [out]");
////		    cmd.add(new File(out.path).getCanonicalPath());
//
//
//	    	// Watermarking - Working using -filter_complex
////			cmd.add(ffmpegBin);
////			cmd.add("-y");
////			cmd.add("-i");
////			cmd.add(new File(in.path).getCanonicalPath());
////			cmd.add("-i");
////			cmd.add(new File(in3.path).getCanonicalPath());
////			cmd.add("-strict");
////			cmd.add("-2");
////			cmd.add("-filter_complex");
////			cmd.add("overlay");
////			cmd.add(new File(out.path).getCanonicalPath());
////			a.execFFMPEG(cmd, sc);
////
//
//			cmd.add(ffmpegBin);
//			cmd.add("-y");
//			cmd.add("-i");
//			cmd.add(new File(in.path).getCanonicalPath());
//			cmd.add("-i");
//			cmd.add(new File(in2.path).getCanonicalPath());
//			cmd.add("-strict");
//			cmd.add("-2");
//			cmd.add("-filter_complex");
//			cmd.add("[1]scale=iw/5:ih/5 [pip]; [0][pip] overlay=main_w-overlay_w-10:main_h-overlay_h-10");
//			//cmd.add("[1]scale=iw/2:ih/2 [pip]; [0][pip] overlay=100:100");
//			cmd.add(new File(out.path).getCanonicalPath());
//			a.execFFMPEG(cmd, sc);
//    	}
