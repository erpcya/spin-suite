package org.erpya.app.retail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import org.erpya.app.sales.R;
import org.erpya.app.sales.databinding.ActivityPriceCheckingBinding;
import org.erpya.base.database.support.CouchDBLite_2_0_Support;
import org.erpya.pos.PointOfSalesService;
import org.erpya.pos.wrapper.PointOfSalesWrapper;
import org.erpya.pos.wrapper.ProductPriceWrapper;
import org.erpya.base.util.DisplayType;
import org.erpya.base.util.Env;
import org.erpya.base.util.Util;
import org.erpya.security.data.model.SessionInfo;
import org.erpya.security.ui.login.Login;
import org.erpya.security.util.RSACrypt;

import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PriceChecking extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private EditText barcodeReader;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            initReader();
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };
    private ActivityPriceCheckingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPriceCheckingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Env.getInstance(getBaseContext());
        Env.setCurrentSupportedDatabase(CouchDBLite_2_0_Support.class.getName());
        Env.setCurrentSupportedSecureEngine(RSACrypt.class.getName());
        mVisible = true;
        mControlsView = binding.fullscreenContentControls;
        mContentView = binding.fullscreenContent;
        barcodeReader = binding.barcodeReader;
        barcodeReader.setInputType(InputType.TYPE_NULL);
        barcodeReader.requestFocus();
        barcodeReader.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_MULTIPLE) {
                    try {
                        ProductPriceWrapper productPrice = PointOfSalesService.getInstance()
                                .getProductPrice(SessionInfo.getInstance().getSessionUuid(), Env.getContext("#POS_CurrentPOS_UUID"), event.getCharacters());
                        setProductPriceInfo(productPrice);
                    } catch (Exception e) {
                        clearProductPriceInfo();
                        binding.totalAmount.setText(getString(R.string.price_checking_unavailable));
                        Log.e("PriceChecking", e.getLocalizedMessage());
                    }
                    //  Clear Reader
                    barcodeReader.setText("");
                }
                return false;
            }
        });

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initReader();
            }
        });
        //  Clear values
        clearProductPriceInfo();
        if(!SessionInfo.getInstance().isLogged()) {
            startLogin();
        }
        //  Get Point Of Sales UUID
        if(Util.isEmpty(Env.getContext("#POS_CurrentPOS_UUID"))) {
            List<PointOfSalesWrapper> sellingPoints = PointOfSalesService.getInstance().getPointOfSalesList(SessionInfo.getInstance().getSessionUuid(), SessionInfo.getInstance().getUserInfo().getUserUuid());
            sellingPoints.stream().findFirst().ifPresent(pointOfSales -> {
                Env.setContext("#POS_CurrentPOS_UUID", pointOfSales.getUuid());
            });
        }
    }

    /**
     *  Set values for show to user
     */
    private void setProductPriceInfo(ProductPriceWrapper productPrice) {
        binding.productName.setText(productPrice.getName());
        binding.productDescription.setText(productPrice.getDescription());
        if(Util.isEmpty(productPrice.getDescription())) {
            binding.productDescription.setVisibility(View.GONE);
        } else {
            binding.productDescription.setVisibility(View.VISIBLE);
        }
        binding.price.setText(productPrice.getCurrencyCode() + " " + DisplayType.getNumberFormat(Env.getContext(), DisplayType.AMOUNT).format(productPrice.getPrice()));
        binding.taxIndicator.setText(productPrice.getTaxIndicator());
        binding.taxAmount.setText(DisplayType.getNumberFormat(Env.getContext(), DisplayType.AMOUNT).format(productPrice.getTaxAmount()));
        binding.totalAmount.setText(productPrice.getCurrencyCode() + " " + DisplayType.getNumberFormat(Env.getContext(), DisplayType.AMOUNT).format(productPrice.getPriceWithTax()));
        binding.displayTotalAmount.setText(productPrice.getDisplayCurrencyCode() + " " + DisplayType.getNumberFormat(Env.getContext(), DisplayType.AMOUNT).format(productPrice.getDisplayPriceWithTax()));
    }

    /**
     * Clean all info about product price
     */
    private void clearProductPriceInfo() {
        binding.productName.setText("");
        binding.productDescription.setText("");
        binding.labelPrice.setText("");
        binding.productDescription.setVisibility(View.GONE);
        binding.price.setText("");
        binding.taxAmount.setText("");
        binding.taxIndicator.setText("");
        binding.totalAmount.setText("");
        binding.displayTotalAmount.setText("");
    }

    private void startLogin() {
        Intent intent = new Intent(PriceChecking.this, Login.class);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Initialize reader
     */
    private void initReader() {
        barcodeReader.requestFocus();
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}