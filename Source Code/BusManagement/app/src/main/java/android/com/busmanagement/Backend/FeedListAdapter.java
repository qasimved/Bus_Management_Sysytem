package android.com.busmanagement.Backend;

import android.com.busmanagement.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class FeedListAdapter extends BaseAdapter

    {

    private Context context;
    public static ArrayList<AllFeedbackListModel> modelArrayList;


    public FeedListAdapter(Context context, ArrayList<AllFeedbackListModel> modelArrayList) {

        this.context = context;
        this.modelArrayList = modelArrayList;

    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FeedListAdapter.ViewHolder holder;

        try {


            if (convertView == null) {
                holder = new FeedListAdapter.ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.feedback_listview, null, true);


                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.routeNo = (TextView) convertView.findViewById(R.id.routeNo);
                holder.feedback = (TextView) convertView.findViewById(R.id.feedbackDescription);




                convertView.setTag(holder);
            } else {
                // the getTag returns the viewHolder object set as a tag to the view
                holder = (FeedListAdapter.ViewHolder) convertView.getTag();
            }


            holder.name.setText(modelArrayList.get(position).getParentName());
            holder.routeNo.setText(modelArrayList.get(position).getRouteNo());
            holder.feedback.setText(String.valueOf(modelArrayList.get(position).getFeedbaackDescription()));



        }
        catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }

    private class ViewHolder {


        private TextView name;
        private TextView routeNo;
        private TextView feedback;


    }
}
