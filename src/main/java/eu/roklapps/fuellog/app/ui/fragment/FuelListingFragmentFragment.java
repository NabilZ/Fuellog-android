package eu.roklapps.fuellog.app.ui.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import eu.roklapps.fuellog.app.R;
import eu.roklapps.fuellog.app.db.FuelDatabase;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;


public class FuelListingFragmentFragment extends Fragment {
    private CardArrayAdapter mAdapter;
    private CardListView mCardListView;
    private LinearLayout mUndoBar;
    private Button mUndoBarButton;

    public FuelListingFragmentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuellistingfragment_list, container, false);

        mCardListView = (CardListView) view.findViewById(R.id.fuel_card_listing);
        mUndoBar = (LinearLayout) view.findViewById(R.id.undobar);
        mUndoBarButton = (Button) view.findViewById(R.id.undobar_button);

        mUndoBar.setVisibility(View.INVISIBLE);
        new FuelHistoryLoader().execute();
        return view;
    }

    private class FuelHistoryLoader extends AsyncTask<Void, Void, Void> {
        private List<Card> mList;

        @Override
        protected Void doInBackground(Void... params) {
            FuelDatabase database = new FuelDatabase(getActivity());

            mList = database.getAllFuelEntries();
            database.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mAdapter = new CardArrayAdapter(getActivity(), mList);
            mCardListView.setAdapter(mAdapter);
        }
    }
}
