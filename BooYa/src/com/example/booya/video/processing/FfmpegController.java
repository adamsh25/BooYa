package com.example.booya.video.processing;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import com.example.booya.video.processing.ShellUtils.ShellCallback;


import android.content.Context;
import android.util.Log;

public class FfmpegController {

	File fileBinDir;
	Context mContext;
	private String ffmpegBin;
	
	private final static String TAG = "FFMPEG";
		
	private String mCmdCat;
	
	public FfmpegController(Context context) throws FileNotFoundException, IOException {
		mContext = context;
		
		checkBinary ();
		
	}
	
	public String GetFfmpegBin()
	{
		return ffmpegBin;
	}
	
	private void checkBinary () throws FileNotFoundException, IOException
	{
		fileBinDir = new File(mContext.getFilesDir().getParentFile(),"lib");

		File fileBin = new File(fileBinDir,"libffmpeg.so");
		
		ffmpegBin = fileBin.getCanonicalPath();
		
		mCmdCat = "sh cat";
		
	}
	
	private void execFFMPEG (List<String> cmd, ShellCallback sc, File fileExec) throws IOException, InterruptedException {
	
		String ffmpegBin = new File(fileBinDir,"ffmpeg").getCanonicalPath();
		Runtime.getRuntime().exec("chmod 777 " +ffmpegBin);
    	
		execProcess (cmd, sc, fileExec);
	}
	
	public void execFFMPEG (List<String> cmd, ShellCallback sc) throws IOException, InterruptedException {
		execFFMPEG (cmd, sc, fileBinDir);
	}
	
	private int execProcess(List<String> cmds, ShellCallback sc, File fileExec) throws IOException, InterruptedException {		
        
		//ensure that the arguments are in the correct Locale format
		for (String cmd :cmds)
		{
			cmd = String.format(Locale.US, "%s", cmd);
		}
		
		ProcessBuilder pb = new ProcessBuilder(cmds);
		pb.directory(fileExec);
		
		StringBuffer cmdlog = new StringBuffer();

		for (String cmd : cmds)
		{
			cmdlog.append(cmd);
			cmdlog.append(' ');
		}
		
		Log.d(TAG,cmdlog.toString());
		
		pb.redirectErrorStream(true);
	//	pb.redirectErrorStream(true);
    	Process process = pb.start();    
    
    	
    	  // any error message?
    	/*
        StreamGobbler errorGobbler = new 
            StreamGobbler(process.getErrorStream(), "ERROR", sc);            
        */
    	
    	 // any output?
        StreamGobbler outputGobbler = new 
            StreamGobbler(process.getInputStream(), "OUTPUT", sc);

        // kick them off
     //   errorGobbler.start();
        outputGobbler.start();

        int exitVal = process.waitFor();
        
        sc.processComplete(exitVal);
        
        return exitVal;


		
	}
	

	private int execProcess(String cmd, ShellCallback sc, File fileExec) throws IOException, InterruptedException {		
        
		//ensure that the argument is in the correct Locale format		
		cmd = String.format(Locale.US, "%s", cmd);
		
		ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.directory(fileExec);

		Log.d(TAG,cmd);
		
	//	pb.redirectErrorStream(true);
    	Process process = pb.start();    
    	
    
    	  // any error message?
        StreamGobbler errorGobbler = new 
            StreamGobbler(process.getErrorStream(), "ERROR", sc);            
        
    	 // any output?
        StreamGobbler outputGobbler = new 
            StreamGobbler(process.getInputStream(), "OUTPUT", sc);
            
        // kick them off
        errorGobbler.start();
        outputGobbler.start();
     

        int exitVal = process.waitFor();
        
        sc.processComplete(exitVal);
        
        return exitVal;


		
	}
	
	
	public int killVideoProcessor (boolean asRoot, boolean waitFor) throws IOException
	{
		int killDelayMs = 300;

		String ffmpegBin = new File(mContext.getDir("bin",0),"ffmpeg").getCanonicalPath();

		int result = -1;
		
		int procId = -1;
		
		while ((procId = ShellUtils.findProcessId(ffmpegBin)) != -1)
		{
			
			Log.d(TAG, "Found PID=" + procId + " - killing now...");
			
			String[] cmd = { ShellUtils.SHELL_CMD_KILL + ' ' + procId + "" };
			
			try { 
			result = ShellUtils.doShellCommand(cmd,new ShellCallback ()
			{

				@Override
				public void shellOut(String msg) {
					
					Log.d(TAG,"Killing ffmpeg:" + msg);
					
				}

				@Override
				public void processComplete(int exitValue) {
					
					
				}
				
			}, asRoot, waitFor);
			Thread.sleep(killDelayMs); }
			catch (Exception e){}
		}
		
		return result;
	}


		
	
	
	
	class StreamGobbler extends Thread
	{
	    InputStream is;
	    String type;
	    ShellCallback sc;
	    
	    StreamGobbler(InputStream is, String type, ShellCallback sc)
	    {
	        this.is = is;
	        this.type = type;
	        this.sc = sc;
	    }
	    
	    public void run()
	    {
	        try
	        {
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            String line=null;
	            while ( (line = br.readLine()) != null)
	            	if (sc != null)
	            		sc.shellOut(line);
	                
	            } catch (IOException ioe)
	              {
	                Log.e(TAG,"error reading shell slog",ioe);
	              }
	    }
	}
}


