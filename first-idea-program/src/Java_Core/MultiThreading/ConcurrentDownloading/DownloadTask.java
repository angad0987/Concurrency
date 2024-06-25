package Java_Core.MultiThreading.ConcurrentDownloading;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTask extends Thread{
    public final String url;
    public final ProgressTracker progressTracker;

    public DownloadTask(String url, ProgressTracker progressTracker1) {
        this.url = url;
        this.progressTracker = progressTracker1;
    }
    public void run(){
        try{
            downloadFile(url);
        }catch (Exception e){
           e.printStackTrace();
        }
    }
    public void downloadFile(String Fileurl) throws IOException {

        //implement download logic;
        //first open a connection
        URL url=new URL(Fileurl);
        URLConnection con= url.openConnection();
        long totalBytes=con.getContentLengthLong();
        System.out.println(totalBytes);
        progressTracker.setTotalBytes(totalBytes);
        try(InputStream in =url.openStream();
            BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(getFileName(Fileurl)))){

            //now create byte array for reading data from input stream
            byte [] buffer=new byte[1024];
            int byteReads;
            long startTime=System.nanoTime();
            while((byteReads=in.read(buffer,0, buffer.length))!=-1){
                out.write(buffer,0,byteReads);
                progressTracker.updateProgress(byteReads);
                long endTime=System.nanoTime();
                long workTime=endTime-startTime;
                double downloadRate=(double)progressTracker.getDownloadedBytes()/workTime;
                long leftBytes=totalBytes-progressTracker.getDownloadedBytes();
                long timeRemaining= (long) (leftBytes/downloadRate);
                progressTracker.updateTimeRemaining(formatTime(timeRemaining));

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Print a message when download is complete
        System.out.println("Downloaded file from " + Fileurl);
    }
    private String getFileName(String url){
        int n=url.length();
        return url.substring(url.lastIndexOf('/')+1,n);
    }

    private String formatTime(long nanoSeconds){
        long seconds=(long)(nanoSeconds/Math.pow(10,9));
        long minutes=seconds/60;
        return String.format("%d min %d sec",minutes,seconds);
    }
}
