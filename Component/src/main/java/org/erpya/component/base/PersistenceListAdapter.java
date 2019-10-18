package org.erpya.component.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import org.erpya.base.db.DBManager;
import org.erpya.base.model.InfoField;
import org.erpya.base.model.POInfo;
import org.erpya.base.util.Condition;
import org.erpya.base.util.Criteria;
import org.erpya.base.util.KeyValue;
import org.erpya.base.util.LogM;
import org.erpya.base.util.Util;
import org.erpya.base.util.ValueUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Adapter for List search
 */
public class PersistenceListAdapter extends ArrayAdapter {

    public PersistenceListAdapter(@NonNull Context context, int resource, @NonNull List<KeyValue> objects) {
        super(context, resource, objects);
        this.context = context;
        this.data = objects;
        this.resource = resource;
    }

    /**
     * Default constructor from field
     * @param fieldDefinition
     */
    public PersistenceListAdapter(InfoField fieldDefinition) {
        this(fieldDefinition.getContext(), android.R.layout.simple_dropdown_item_1line, new ArrayList<KeyValue>());
        this.fieldDefinition = fieldDefinition;
    }

    private List<KeyValue> data;
    private Context context;
    private int resource;
    private ListFilter filter = new ListFilter();
    private InfoField fieldDefinition;

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public KeyValue getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public ListFilter getFilter() {
        return filter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = new ArrayList<String>();
                    results.count = 0;
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                //Call to database to get matching records using room
                List<KeyValue> matchValues = getQueryWithData(getContext(), searchStrLowerCase);
                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        private List<KeyValue> getQueryWithData(Context context, String searchValue) {
            List<KeyValue> values = new ArrayList<KeyValue>();
            try {
                Criteria criteria = new Criteria();
                criteria.addCriteria(POInfo.METADATA_TABLE_NAME, Condition.EQUAL, fieldDefinition.getTableName());
                criteria.addCriteria(fieldDefinition.getDisplayColumnName(), Condition.LIKE, "%" + searchValue + "%");
                //  Load Criteria
                List<Map<String, Object>> resultAsList = DBManager.getInstance(getContext()).getListMap(criteria);
                //  Validate Attributes
                if(resultAsList == null) {
                    return values;
                }
                String displayColumnName = fieldDefinition.getDisplayColumnName();
                //  Populate
                resultAsList.stream().forEach(value -> {
                    String uuid = ValueUtil.getValueAsString(value.get(POInfo.ID_KEY));
                    String displayValue = ValueUtil.getValueAsString(value.get(displayColumnName));
                    if(Util.isEmpty(displayValue)) {
                        displayValue = value.values().stream().map(objectValue -> ValueUtil.getValueAsString(objectValue))
                                .collect(Collectors.joining("_", "<", ">"));
                    }
                    //  Validate values
                    if(!Util.isEmpty(uuid)
                            && !Util.isEmpty(displayValue)) {
                        values.add(new KeyValue(uuid, displayValue));
                    }
                });
            } catch (Exception e) {
                LogM.log(context, ListFilter.class.getName(), Level.WARNING, "Error Loading Data for PO Info" + e.getLocalizedMessage());
            }
            return  values;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                data = (ArrayList<KeyValue>)results.values;
            } else {
                data = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}
