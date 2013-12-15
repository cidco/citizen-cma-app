package com.cma.pgrssystem.listener;

public interface AsyncTaskCallBack {
	
	void onPreExecute();
    void onProgressUpdate(int percent);
    void onCancelled();
    void onPostExecute();

}
