/*************************************************************************************
 * Product: Spin-Suite (Mobile Suite)                       		                 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com				  		                 *
 * This program is free software: you can redistribute it and/or modify              *
 * it under the terms of the GNU General Public License as published by              *
 * the Free Software Foundation, either version 3 of the License, or                 *
 * (at your option) any later version.                                               *
 * This program is distributed in the hope that it will be useful,                   *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                     *
 * GNU General Public License for more details.                                      *
 * You should have received a copy of the GNU General Public License                 *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.            *
 ************************************************************************************/
package org.erpya.app.arduino.remotesetup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.erpya.app.arduino.initialsetup.AddDeviceWizard;
import org.erpya.base.arduino.supported.UNO;
import org.erpya.base.database.support.CouchDBLite_2_0_Support;
import org.erpya.base.device.util.DeviceManager;
import org.erpya.base.device.util.DeviceTypeHandler;
import org.erpya.base.util.Condition;
import org.erpya.base.util.Criteria;
import org.erpya.base.util.Env;

import java.util.List;

/**
 * An activity representing a list of Devices. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DeviceDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class DeviceListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        Env.getInstance(getApplicationContext());
        DeviceManager
                .getInstance(getApplicationContext())
                .addDeviceType(new UNO());
        Env.setCurrentSupportedDatabase(CouchDBLite_2_0_Support.class.getName());
        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final View recyclerView = findViewById(R.id.device_list);
        assert recyclerView != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupRecyclerView((RecyclerView) recyclerView);
            }
        });

        if (findViewById(R.id.device_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, ArduinoDeviceContent.getInstance(Env.getContext()).getItemList(), mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final DeviceListActivity mParentActivity;
        private final List<ArduinoDeviceContent.DeviceItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArduinoDeviceContent.DeviceItem item = (ArduinoDeviceContent.DeviceItem) view.getTag();
                Criteria criteria = new Criteria();
                criteria.addCriteria("DeviceAddress", Condition.EQUAL, item.id);
                //  For table
                Context context = view.getContext();
                Intent intent = new Intent(context, AddDeviceWizard.class);
                //  Set to arguments
                intent.putExtra(Criteria.PARCEABLE_NAME, criteria);
                context.startActivity(intent);
                if (mTwoPane) { //  Ummm.... what happen if exist three pane? hahahaha
//                    Bundle arguments = new Bundle();
                    //
//                    arguments.putString(DeviceDetailFragment.ARG_ITEM_ID, item.id);
//                    DeviceDetailFragment fragment = new DeviceDetailFragment();
//                    fragment.setArguments(arguments);
//                    mParentActivity.getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.device_detail_container, fragment)
//                            .commit();
                } else {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, DeviceDetailActivity.class);
//                    intent.putExtra(DeviceDetailFragment.ARG_ITEM_ID, item.id);
//
//                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(DeviceListActivity parent,
                                      List<ArduinoDeviceContent.DeviceItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.device_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).deviceName);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
            DeviceTypeHandler device = DeviceManager.getInstance().getDeviceHandler(mValues.get(position).id);
            boolean isConnected = device != null && device.isConnected();
            if(isConnected) {
                holder.mIsConnected.setVisibility(View.VISIBLE);
            } else {
                holder.mIsConnected.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final ImageView mIsConnected;

            ViewHolder(View view) {
                super(view);
                mIsConnected = (ImageView) view.findViewById(R.id.isConnected);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.deviceName);
            }
        }
    }
}
