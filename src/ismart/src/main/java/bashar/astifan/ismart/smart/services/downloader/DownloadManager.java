package bashar.astifan.ismart.smart.services.downloader;

public interface DownloadManager {
/** SAMPLE
 *
 Uri downloadUri = Uri.parse("http://tcrn.ch/Yu1Ooo1");
 Uri destinationUri = Uri.parse(this.getExternalCacheDir().toString()+"/test.mp4");
 DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
 .addCustomHeader("Auth-Token", "YourTokenApiKey")
 .setRetryPolicy(new DefaultRetryPolicy())
 .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
 .setDownloadContext(downloadContextObject)//Optional
 .setDownloadListener(new DownloadStatusListener() {
@Override
public void onDownloadComplete(int id) {

}

@Override
public void onDownloadFailed(int id, int errorCode, String errorMessage) {

}

@Override
public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress)) {

}
});
 */

	/**
	 * Status when the download is currently pending.
	 */
	public final static int STATUS_PENDING = 1 << 0;

	/**
	 * Status when the download is currently pending.
	 */
	public final static int STATUS_STARTED = 1 << 1;

	/**
	 * Status when the download network call is connecting to destination.
	 */
	public final static int STATUS_CONNECTING = 1 << 2;

	/**
	 * Status when the download is currently running.
	 */
	public final static int STATUS_RUNNING = 1 << 3;

	/**
	 * Status when the download has successfully completed.
	 */
	public final static int STATUS_SUCCESSFUL = 1 << 4;

	/**
	 * Status when the download has failed.
	 */
	public final static int STATUS_FAILED = 1 << 5;

	/**
	 * Status when the download has failed due to broken url or invalid download url
	 */
	public final static int STATUS_NOT_FOUND = 1 << 6;

    /**
     * Status when the download is attempted for retry due to connection timeouts.
     */
    public final static int STATUS_RETRYING = 1 << 7;

    /**
	 * Error code when writing download content to the destination file.
	 */
	public final static int ERROR_FILE_ERROR = 1001;

	/**
	 * Error code when an HTTP code was received that download manager can't
	 * handle.
	 */
	public final static int ERROR_UNHANDLED_HTTP_CODE = 1002;

	/**
	 * Error code when an error receiving or processing data occurred at the
	 * HTTP level.
	 */
	public final static int ERROR_HTTP_DATA_ERROR = 1004;

	/**
	 * Error code when there were too many redirects.
	 */
	public final static int ERROR_TOO_MANY_REDIRECTS = 1005;

	/**
	 * Error code when size of the file is unknown.
	 */
	public final static int ERROR_DOWNLOAD_SIZE_UNKNOWN = 1006;

	/**
	 * Error code when passed URI is malformed.
	 */
	public final static int ERROR_MALFORMED_URI = 1007;

	/**
	 * Error code when download is cancelled.
	 */
	public final static int ERROR_DOWNLOAD_CANCELLED = 1008;

	/**
	 * Error code when there is connection timeout after maximum retries
	 */
	public final static int ERROR_CONNECTION_TIMEOUT_AFTER_RETRIES = 1009;

	public int add(DownloadRequest request);

	public int cancel(int downloadId);

	public void cancelAll();

	public int query(int downloadId);

	public void release();
}
