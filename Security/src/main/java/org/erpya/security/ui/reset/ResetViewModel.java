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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import org.erpya.security.R;
import org.erpya.security.data.ResetPasswordRepository;
import org.erpya.security.data.Result;
import org.erpya.security.data.model.RegisteredUser;

import java.lang.ref.WeakReference;

public class ResetViewModel extends ViewModel {

    private MutableLiveData<ResetFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<ResetResult> resetPasswordResult = new MutableLiveData<>();
    private ResetPasswordRepository registerRepository;

    ResetViewModel(ResetPasswordRepository loginRepository) {
        this.registerRepository = loginRepository;
    }

    LiveData<ResetFormState> getResetFormState() {
        return registerFormState;
    }

    LiveData<ResetResult> getResetResult() {
        return resetPasswordResult;
    }

    public void resetPassword(String userName, String password, String token) {
        // can be launched in a separate asynchronous job
        new ResetPasswordProcessTask(this).execute(userName, password, token);
    }

    /**
     * Login calling
     * @param password
     * @param token
     */
    private Result<RegisteredUser> resetModel(String userName, String password, String token) {
        return registerRepository.resetPassword(userName, password, token);
    }

    private void setResult(Result<RegisteredUser> result) {
        if (result instanceof Result.Success) {
            RegisteredUser data = ((Result.Success<RegisteredUser>) result).getData();
            resetPasswordResult.setValue(new ResetResult(new ResetUserView(data.getDisplayName())));
        } else {
            resetPasswordResult.setValue(new ResetResult(R.string.register_failed, result.toString()));
        }
    }

    public void resetDataChanged(String password, String rePassword) {
        if (!isPasswordValid(password)) {
            registerFormState.setValue(new ResetFormState(R.string.invalid_password, null));
        } else if (!isRePasswordValid(password, rePassword)) {
            registerFormState.setValue(new ResetFormState(null, R.string.invalid_password_mismatch));
        } else {
            registerFormState.setValue(new ResetFormState(true));
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 4;
    }

    // A placeholder password validation check
    private boolean isRePasswordValid(String password, String rePassword) {
        return password != null && password.trim().length() > 4 && rePassword != null && rePassword.trim().length() > 4 && password.equals(rePassword);
    }

    private static class ResetPasswordProcessTask extends AsyncTask<String, Void, Result<RegisteredUser>> {
        private final WeakReference<ResetViewModel> registerModel;

        private ResetPasswordProcessTask(ResetViewModel loginModel) {
            this.registerModel = new WeakReference<ResetViewModel>(loginModel);
        }

        @Override
        protected Result<RegisteredUser> doInBackground(String... params) {
            String userName = params[0];
            String password = params[1];
            String token = params[2];
            return registerModel.get().resetModel(userName, password, token);
        }

        @Override
        protected void onPostExecute(Result<RegisteredUser> result) {
            ResetViewModel source = registerModel.get();
            if (source != null) {
                source.setResult(result);
            }
        }
    }
}
