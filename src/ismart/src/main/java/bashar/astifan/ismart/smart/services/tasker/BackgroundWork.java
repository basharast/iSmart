package bashar.astifan.ismart.smart.services.tasker;

public interface BackgroundWork<T> {
    T doInBackground() throws Exception;
}
