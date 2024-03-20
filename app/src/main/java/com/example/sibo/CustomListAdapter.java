package com.example.sibo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter implements Filterable {
    private Context mContext;
    private int id;
    private ArrayList<String> orginalData;
    private ArrayList<String> filteredData;

    public CustomListAdapter(Context context, int textViewResourceId , ArrayList<String> list )
    {
        super(context, textViewResourceId, list);           
        mContext = context;
        id = textViewResourceId;
        orginalData = list;
        filteredData = list;
    }

    public int getCount()
    {
        return filteredData.size();
    }

    public Object getItem(int position)
    {
        return filteredData.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.textView);

        if(filteredData.get(position) != null )
        {
            String[] parts = filteredData.get(position).toString().split(";");

            if (parts[1].equals("High")){
                text.setText(parts[0]);
                text.setTextColor(Color.parseColor("#DC143C"));
            }
            else if (parts[1].equals("Medium")){
                text.setText(parts[0]);
                text.setTextColor(Color.parseColor("#FF7F27"));
            }
            else if (parts[1].equals("Low")){
                text.setText(parts[0]);
                text.setTextColor(Color.parseColor("#006400"));
            }
            else {
                text.setText(parts[0]);
                text.setTextColor(Color.BLACK);
            }
        }
        return mView;
    }
    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();

                //If there's nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = orginalData;
                    results.count = orginalData.size();
                }
                else
                {

                    ArrayList<String> filterResultsData = new ArrayList<String>();

                    for(String data : orginalData)
                    {
                        if (data.toLowerCase().contains(charSequence.toString().toLowerCase()))
                        {
                            filterResultsData.add(data);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filteredData = (ArrayList<String>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
