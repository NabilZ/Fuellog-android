package eu.roklapps.fuellog.app.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import eu.roklapps.fuellog.app.R;
import eu.roklapps.fuellog.app.db.FuelDatabase;
import eu.roklapps.fuellog.app.ui.fragment.dummy.DummyContent;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;


public class FuelListingFragmentFragment extends Fragment implements AbsListView.OnItemClickListener {
    private OnFragmentInteractionListener mListener;

    private CardArrayAdapter mAdapter;
    private CardListView mCardListView;
    private LinearLayout mUndoBar;
    private Button mUndoBarButton;

    public FuelListingFragmentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new FuelHistoryLoader().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuellistingfragment, container, false);

        mCardListView = (CardListView) view.findViewById(R.id.fuel_card_listing);
        mUndoBar = (LinearLayout) view.findViewById(R.id.undobar);
        mUndoBarButton = (Button) view.findViewById(R.id.undobar_button);

        mUndoBar.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
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
