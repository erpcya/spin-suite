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
package org.erpya.security.ui.reset;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.erpya.base.util.Util;
import org.erpya.security.R;

public class ResetPassword extends AppCompatActivity {

    private ResetViewModel resetViewModel;
    private Button resetPasswordButton;
    private String userName;
    private String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        resetViewModel = ViewModelProviders.of(this, new ResetViewModelFactory())
                .get(ResetViewModel.class);
        final TextView userDisplayName = findViewById(R.id.user_display_name);
        final TextInputEditText passwordEditText = findViewById(R.id.password);
        final TextInputEditText rePasswordEditText = findViewById(R.id.re_password);
        resetPasswordButton = findViewById(R.id.reset_password);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        token = getIntent().getStringExtra("Token");
        userName = getIntent().getStringExtra("UserName");
        String displayName = getIntent().getStringExtra("DisplayName");
        if(!Util.isEmpty(displayName)) {
            userDisplayName.setVisibility(View.VISIBLE);
            userDisplayName.setText(displayName);
        } else {
            userDisplayName.setVisibility(View.GONE);
        }

        resetViewModel.getResetFormState().observe(this, new Observer<ResetFormState>() {
            @Override
            public void onChanged(@Nullable ResetFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                resetPasswordButton.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getRePasswordError() != null) {
                    rePasswordEditText.setError(getString(registerFormState.getRePasswordError()));
                }
            }
        });

        resetViewModel.getResetResult().observe(this, new Observer<ResetResult>() {
            @Override
            public void onChanged(@Nullable ResetResult resetResult) {
                if (resetResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (resetResult.getError() != null) {
                    showLoginFailed(resetResult.getError(), resetResult.getErrorMessage());
                    resetPasswordButton.setEnabled(true);
                }
                if (resetResult.getSuccess() != null) {
                    updateUiWithUser(resetResult.getSuccess());
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                resetViewModel.resetDataChanged(passwordEditText.getText().toString(), rePasswordEditText.getText().toString());
            }
        };
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        rePasswordEditText.addTextChangedListener(afterTextChangedListener);
        rePasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && resetPasswordButton.isEnabled()) {
                    resetPasswordModel(passwordEditText.getText().toString(), rePasswordEditText.getText().toString());
                }
                return false;
            }
        });

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                resetPasswordModel(passwordEditText.getText().toString(), token);
            }
        });
    }

    /**
     * Login from user and password
     * @param password
     * @param token
     */
    private void resetPasswordModel(String password, String token) {
        resetPasswordButton.setEnabled(false);
        resetViewModel.resetPassword(userName, password, token);
    }

    private void updateUiWithUser(ResetUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString, String error) {
        Toast.makeText(getApplicationContext(), Util.isEmpty(error)? "Error": error, Toast.LENGTH_SHORT).show();
    }
}
