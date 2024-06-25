package Java_Core.MultiThreading.ConcurrentDownloading;

public class ProgressTracker {
    private String filename;
    private long totalBytes;
    private long downloadedBytes;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public long getDownloadedBytes() {
        return downloadedBytes;
    }

    public void setDownloadedBytes(long downloadedBytes) {
        this.downloadedBytes = downloadedBytes;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(String timeRemaining) {

        this.timeRemaining = timeRemaining;
    }

    private String timeRemaining;

    public ProgressTracker(String filename,long totalBytes){
        this.filename=filename;
        this.totalBytes=totalBytes;
        this.downloadedBytes=0;
        this.timeRemaining="Calculating...";
    }
    synchronized  public void updateProgress(long downloadedBytes){
        this.downloadedBytes+=downloadedBytes;
    }

    synchronized public void updateTimeRemaining(String timeRemaining){
        this.timeRemaining=" Calculating ..."+timeRemaining;
    }
    synchronized  public void getProgress(){
        int percentage;
        if(totalBytes==0){
            percentage=0;
        }
        else{
            percentage=(int)((((double)downloadedBytes/totalBytes)*100));
//            System.out.println(percentage+"%");
        }
        System.out.printf("File: %-30s %-10s %-20s %-20s\r",filename, percentage + "%", "Downloaded Bytes: " + downloadedBytes, "Time Remaining: " + timeRemaining);
    }
}
