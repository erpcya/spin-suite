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
package org.erpya.security.ui.register;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Patterns;

import org.erpya.security.R;
import org.erpya.security.data.RegisterRepository;
import org.erpya.security.data.Result;
import org.erpya.security.data.model.RegisteredUser;

import java.lang.ref.WeakReference;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> loginResult = new MutableLiveData<>();
    private RegisterRepository registerRepository;

    RegisterViewModel(RegisterRepository loginRepository) {
        this.registerRepository = loginRepository;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<RegisterResult> getRegisterResult() {
        return loginResult;
    }

    public void enroll(String name, String username, String email) {
        // can be launched in a separate asynchronous job
        new AccessProcessTask(this).execute(name, username, email);
    }

    /**
     * Login calling
     * @param name
     * @param username
     * @param email
     */
    private Result<RegisteredUser> registerModel(String name, String username, String email) {
        return registerRepository.enroll(name, username, email);
    }

    private void setResult(Result<RegisteredUser> result) {
        if (result instanceof Result.Success) {
            RegisteredUser data = ((Result.Success<RegisteredUser>) result).getData();
            loginResult.setValue(new RegisterResult(new RegisteredUserView(data.getUserName(), data.getDisplayName(), data.getToken())));
        } else {
            loginResult.setValue(new RegisterResult(R.string.register_failed, result.toString()));
        }
    }

    public void enrollDataChanged(String name, String username, String email) {
        if (!isNameValid(name)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_name, null, null));
        } else if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_username, null));
        } else if (!isEMailValid(email)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_email));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return !username.trim().isEmpty();
    }

    private boolean isEMailValid(String email) {
        if (email == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // A placeholder password validation check
    private boolean isNameValid(String name) {
        return name != null && name.trim().length() > 4;
    }

    private static class AccessProcessTask extends AsyncTask<String, Void, Result<RegisteredUser>> {
        private final WeakReference<RegisterViewModel> registerModel;

        private AccessProcessTask(RegisterViewModel loginModel) {
            this.registerModel = new WeakReference<RegisterViewModel>(loginModel);
        }

        @Override
        protected Result<RegisteredUser> doInBackground(String... params) {
            String name = params[0];
            String username = params[1];
            String email = params[2];
            return registerModel.get().registerModel(name, username, email);
        }

        @Override
        protected void onPostExecute(Result<RegisteredUser> result) {
            RegisterViewModel source = registerModel.get();
            if (source != null) {
                source.setResult(result);
            }
        }
    }
}
