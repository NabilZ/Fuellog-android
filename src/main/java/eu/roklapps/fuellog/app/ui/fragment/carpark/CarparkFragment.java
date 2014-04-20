package eu.roklapps.fuellog.app.ui.fragment.carpark;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import eu.roklapps.fuellog.app.R;
import eu.roklapps.fuellog.app.db.FuelDatabase;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;


public class CarparkFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private CardListView mListView;
    private CardArrayAdapter mAdapter;
    private LinearLayout mUndoBar;
    private Button mUndoBarButton;

    public CarparkFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
        new CarParkLoader().execute();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.carpark_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_car) {
            mListener.onAddNewCar();
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carpark, container, false);

        mListView = (CardListView) view.findViewById(R.id.carrpark_card_listing);
        mUndoBar = (LinearLayout) view.findViewById(R.id.undobar);
        mUndoBar.setVisibility(View.INVISIBLE);
        mUndoBarButton = (Button) view.findViewById(R.id.undobar_button);

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

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public void onClick(View v) {

    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);

        public void onAddNewCar();
    }

    private class CarParkLoader extends AsyncTask<Void, Void, Void> {
        private ArrayList<Card> mCars;

        @Override
        protected Void doInBackground(Void... params) {
            FuelDatabase db = new FuelDatabase(getActivity());
            mCars = db.getAllCars();
            db.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mAdapter = new CardArrayAdapter(getActivity(), mCars);
            mListView.setAdapter(mAdapter);
        }
    }
}
