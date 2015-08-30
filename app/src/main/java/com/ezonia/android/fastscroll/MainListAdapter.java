package com.ezonia.android.fastscroll;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainListAdapter extends BaseAdapter implements Filterable, SectionIndexer {

    private List<Main> mData;
    private List<Main> mFilteredData;
    private LayoutInflater mInflater;
    private ItemFilter mFilter;

    private Object[] mSections;
    private int[] mSectionsIndexedByPosition;
    private int[] mPositionsIndexedBySection;

    public MainListAdapter (List<Main> data, Context context) {
        mData = data;
        mFilteredData = new ArrayList<Main>(mData);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setupSections();
    }

    @Override
    public int getCount() {
        return mFilteredData.size();
    }

    @Override
    public Main getItem(int position) {
        return mFilteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            holder = new ViewHolder();

            holder.title = (TextView) convertView.findViewById(android.R.id.text1);
            holder.description = (TextView) convertView.findViewById(android.R.id.text2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Main main = getItem(position);
        holder.title.setText(main.getContinent());
        holder.description.setText(main.getDescription());
        if (main.isSelected()) {
            convertView.setBackgroundColor(Color.parseColor("#1C3F96"));
            holder.title.setTextColor(Color.parseColor("#FFFFFF"));
            holder.description.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
            holder.title.setTextColor(Color.parseColor("#FFFFFF"));
            holder.description.setTextColor(Color.parseColor("#B5B5B5"));
        }

        holder.title.setText(mFilteredData.get(position).getContinent());
        holder.description.setText(mFilteredData.get(position).getDescription());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemFilter();
        }
        return mFilter;
    }

    /**
     * View holder
     */
    static class ViewHolder {
        private TextView title;
        private TextView description;
    }

    /**
     * Filter for filtering list items
     */
    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     */
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (TextUtils.isEmpty(constraint)) {
                results.count = mData.size();
                results.values = new ArrayList<Main>(mData);
            } else {
                //Create a new list to filter on
                List<Main> resultList = new ArrayList<Main>();
                for (Main str : mData) {
                    if (str.getContinent().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        resultList.add(str);
                    }
                }
                results.count = resultList.size();
                results.values = resultList;
            }
            return results;
        }

        /**
         * Runs on ui thread
         * @param constraint the constraint used for the result
         * @param results the results to display
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                mFilteredData.clear();
                notifyDataSetInvalidated();
            } else {
                mFilteredData = (ArrayList<Main>)results.values;
                notifyDataSetChanged();
            }
            setupSections();
        }
    }

    @Override
    public int getPositionForSection(int section) {
        return mPositionsIndexedBySection[section];
    }

    @Override
    public int getSectionForPosition(int position) {
        return mSectionsIndexedByPosition[position];
    }

    @Override
    public Object[] getSections() {

        return mSections;
    }

    private void setupSections() {

        String initial = "\0";
        List<String> sections = new ArrayList<String>();
        mSectionsIndexedByPosition = new int[mFilteredData.size()];
        mPositionsIndexedBySection = new int[mFilteredData.size()];

        int section = 0;
        for (int pos = 0; pos < mFilteredData.size(); pos++) {
            Main country = mFilteredData.get(pos);
            if (initial.charAt(0) != country.getContinent().charAt(0)) {
                initial = country.getContinent().substring(0, 1);
                section = sections.size();
                sections.add(initial);
                mPositionsIndexedBySection[section] = pos;
                mSectionsIndexedByPosition[pos] = section;
            } else {
                mSectionsIndexedByPosition[pos] = section;
            }
        }
        mSections = sections.toArray();
        mPositionsIndexedBySection = Arrays.copyOf(mPositionsIndexedBySection, mSections.length);

    }
}