package org.erpya.app.arduino.remotesetup;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.erpya.base.arduino.setup.ArduinoSetup;
import org.erpya.base.arduino.setup.SetupAttribute;
import org.erpya.base.arduino.setup.WIFIAttribute;
import org.erpya.base.arduino.util.IArduinoStatus;
import org.erpya.base.device.util.DeviceManager;
import org.erpya.base.device.util.DeviceTypeHandler;
import org.erpya.base.device.util.IDeviceType;
import org.erpya.base.util.LogM;
import org.erpya.base.util.Util;

/**
 * An activity representing a single Device detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link DeviceListActivity}.
 */
public class DeviceDetailActivity extends AppCompatActivity {

    private IArduinoStatus listener;
    private String result = null;
    private DeviceTypeHandler handler = null;
    private LogM log = null;
    private ProgressDialog progress;
    private String currentDeviceId;
    private Context context;
    private Handler eventHandler;
    private ArduinoSetup commandThread;
    private DeviceDetailFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        //  Set device ID
        currentDeviceId = getIntent().getStringExtra(DeviceDetailFragment.ARG_ITEM_ID);
        loadDevice();
        //
        listener = new IArduinoStatus() {
            @Override
            public void publishStatus(String message) {
                //
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCommands();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(DeviceDetailFragment.ARG_ITEM_ID,
                    currentDeviceId);
            currentFragment = new DeviceDetailFragment();
            currentFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.device_detail_container, currentFragment)
                    .commit();
        }
    }

    /**
     * Send Arduino commands
     */
    private void sendCommands() {
        if(handler != null
                && handler.isConnected()) {
            //  Validate values
            try {
                if(currentFragment != null) {
                    WIFIAttribute attribute = currentFragment.getWIFIAttribute();
                    if(attribute == null) {
                        showMessage(getString(R.string.msg_ValidError));
                        return;
                    }
                    if(Util.isEmpty(attribute.getSSID())) {
                        showMessage(getString(R.string.msg_ValidError) + " - " + getString(R.string.SSID));
                        return;
                    }
                    //
                    commandThread.withAttribute(attribute).send();


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, DeviceListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Show message
     * @param message
     */
    private void showMessage(String message) {
        Snackbar.make(getCurrentFocus(), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /**
     * Load device for connect
     */
    @SuppressLint("HandlerLeak")
    private void loadDevice() {
        eventHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if(msg.what == ArduinoSetup.MESSAGE_READED) {
                    SetupAttribute attribute = (SetupAttribute) msg.obj;
                    WIFIAttribute wifiAttribute = new WIFIAttribute(attribute);
                    if(currentFragment != null) {
                        currentFragment.setWIFIAttribute(wifiAttribute);
                    }
                }
            }
        };
        context = getApplicationContext();
        handler = DeviceManager
                .getInstance(context)
                .getDefaultDeviceHandler(IDeviceType.TYPE_ARDUINO);
        //  Set current device
        handler.setCurrentDevice(currentDeviceId);
        log = new LogM(getApplicationContext(), this.getClass());
        connect();
    }

    /**
     * Connect to device
     */
    private void connect() {
        new ConnectionTask().execute(currentDeviceId);
    }

    /**
     * After connect with device
     */
    private void afterConnect() {
        commandThread = new ArduinoSetup(handler, eventHandler);
        commandThread.start();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect
     */
    private void diconnect() {
        if(handler != null) {
            try {
                handler.close();
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }
        }
    }

    /**
     * This class demonstrates printing in a background thread and updates
     * the UI in the UI thread.
     */
    public class ConnectionTask extends AsyncTask<String, Integer, String> {


        /**
         * Runs on the UI thread before doInBackground(Params...).
         */
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(DeviceDetailActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        /**
         * This method runs on a background thread. The specified parameters
         * are the parameters passed to the execute method by the caller of
         * this task. This method can call publishProgress to publish updates
         * on the UI thread.
         */
        @Override
        protected String doInBackground(String... args) {
            try {
                String deviceId = null;
                if(args != null
                        && args.length > 0
                        && !Util.isEmpty(args[0])) {
                    deviceId = args[0];
                }
                log.fine("Connecting...");
                if(!Util.isEmpty(deviceId)) {
                    handler.setCurrentDevice(deviceId);
                }
                //
                if(!handler.isConnected()) {
                    //	Connect
                    publishProgress(IArduinoStatus.OPENING_CONNECTION);
                    //  Connect
                    handler.connect();
                    publishProgress(IArduinoStatus.CONNECTED);
                    result = context.getResources().getString(R.string.Arduino_Connected) + " " + handler.getCurrentDevice().getAddress();
                }
            } catch (Exception e) {
                result = e.getLocalizedMessage();
                publishProgress(IArduinoStatus.ERROR);
                log.severe("Printing Error " + e.getLocalizedMessage());
            }
            // The result string will be passed to the onPostExecute method
            // for display in the the Progress and Status text box.
            return result;
        }

        /**
         * Runs on the UI thread after publishProgress is invoked. The
         * specified values are the values passed to publishProgress.
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            if(listener == null) {
                return;
            }
            // Access the values array.
            int progress = values[0];

            switch (progress) {
                case IArduinoStatus.OPENING_CONNECTION:
                    listener.publishStatus(context.getResources().getString(R.string.Arduino_Opening_Connection));
                    break;
                case IArduinoStatus.CLOSING_CONNECTION:
                    listener.publishStatus(context.getResources().getString(R.string.Arduino_Closing_Connection));
                    break;
                case IArduinoStatus.CONNECTED:
                    listener.publishStatus(context.getResources().getString(R.string.Arduino_Connected) + " " + handler.getCurrentDevice().getAddress());
                    break;
                case IArduinoStatus.SENDING_TEXT:
                    listener.publishStatus(context.getResources().getString(R.string.Arduino_Sending_Text));
                    break;
                case IArduinoStatus.CONNECTION_ERROR:
                    listener.publishStatus(context.getResources().getString(R.string.Arduino_Connection_Error));
                    break;
                case IArduinoStatus.ERROR:
                    listener.publishStatus(context.getResources().getString(R.string.Arduino_Generic_Error));
                    break;
            }
        }

        /**
         * Runs on the UI thread after doInBackground method. The specified
         * result parameter is the value returned by doInBackground.
         */
        @Override
        protected void onPostExecute(String result) {
            // Displays the result (number of bytes sent to the printer or
            // exception message) in the Progress and Status text box.
            if (result != null) {
                showMessage(result);
            }
            progress.dismiss();
            afterConnect();
        }
    }
}
