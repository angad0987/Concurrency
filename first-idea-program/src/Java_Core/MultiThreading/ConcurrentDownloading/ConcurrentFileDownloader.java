package Java_Core.MultiThreading.ConcurrentDownloading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentFileDownloader {
    public static void main(String[] args) {
        //give urls for downloading the data
        String [] urls={"https://www.pw.live/exams/wp-content/uploads/2023/11/01.-Engineering-Mathematics_.pdf","https://www.pw.live/exams/wp-content/uploads/2023/11/03.-Digital-Logic_.pdf","https://www.pw.live/exams/wp-content/uploads/2023/11/06.-Algorithm_.pdf"};
        ExecutorService executorService= Executors.newFixedThreadPool(urls.length);
//        ExecutorService executorService=Executors.newSingleThreadExecutor();
        List<ProgressTracker> progressTrackers=new ArrayList<>();
        //submit download tasks to the thread pool
        for(String url : urls){

            String filename=url.substring(url.lastIndexOf('/')+1,url.length());
            ProgressTracker progressTracker=new ProgressTracker(filename,0);
            progressTrackers.add(progressTracker);
            executorService.submit(new DownloadTask(url,progressTracker));
        }
        Thread progressDisplayThread=new Thread(()->{
            while (!executorService.isTerminated()){
                for (ProgressTracker progressTracker : progressTrackers) {
                    progressTracker.getProgress();
                }
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        progressDisplayThread.start();
        executorService.shutdown();
    }
}
