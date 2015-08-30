package com.ezonia.android.fastscroll;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class FragmentCountries extends ListFragment implements SearchView.OnQueryTextListener {

    private MainListAdapter mAdapter;

    public FragmentCountries() {
        // Required empty constructor
    }

    public static FragmentCountries newInstance() {
        return new FragmentCountries();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        setHasOptionsMenu(true);
        initialize(view);
        return view;
    }

    List<Main> list = new ArrayList<Main>();
    private void initialize(View view) {
        String[] items = getActivity().getResources().getStringArray(R.array.country_names);
        String[] itemDescriptions = getActivity().getResources().getStringArray(R.array.country_descriptions);
        for (int n = 0; n < items.length; n++){
            Main main = new Main();
            main.setID(n);
            main.setContinent(items[n]);
            main.setDescription(itemDescriptions[n]);
            list.add(main);
        }

        mAdapter = new MainListAdapter(list, getActivity());
        setListAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Set up search view
//        inflater.inflate(R.menu.menu_countries, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//        searchView.setIconifiedByDefault(true);
//        searchView.clearAnimation();
//        searchView.setOnQueryTextListener(this);
//        searchView.setQueryHint("Search");

//        View close = searchView.findViewById(R.id.search_close_btn);
//        close.setBackgroundResource(R.drawable.ic_action_content_clear);
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return false;
    }


}