package eu.roklapps.fuellog.app.asyncs;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import eu.roklapps.fuellog.app.callbacks.AsyncTaskSaveResult;

public class FuelSaver extends AsyncTask<ContentValues, Void, Void> {
    private AsyncTaskSaveResult mResult;
    private Context mContext;

    public FuelSaver(AsyncTaskSaveResult mResult, Context context) {
        this.mResult = mResult;
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(ContentValues... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mResult.finished();
    }
}
