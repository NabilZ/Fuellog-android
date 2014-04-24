package eu.roklapps.fuellog.app.asyncs;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import eu.roklapps.fuellog.app.callbacks.AsyncTaskSaveResult;
import eu.roklapps.fuellog.app.db.FuelDatabase;

public class FuelSaver extends AsyncTask<ContentValues, Void, Void> {
    private AsyncTaskSaveResult mResult;
    private Context mContext;

    public FuelSaver(AsyncTaskSaveResult mResult, Context context) {
        this.mResult = mResult;
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(ContentValues... params) {
        FuelDatabase database = new FuelDatabase(mContext);

        database.saveNewFuelRecord(params[0]);

        database.close();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mResult.finished();
    }
}
