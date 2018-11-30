package fall2018.csc2017.gamelauncher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

import fall2018.csc2017.R;
import fall2018.csc2017.common.SaveAndLoadFiles;
import fall2018.csc2017.users.User;

public class LoginActivity extends AppCompatActivity implements SaveAndLoadFiles {

    /**
     * A HashMap of all the Users created. The key is the username, the value is the User object.
     */
    private Map<String, User> userAccounts;

    /**
     * The current logged in user.
     */
    private String currentUser;

    /**
     * UI References
     */
    private EditText mUsernameView;
    private EditText mPasswordView;

    /**
     * TextWatcher to ensure no empty username.
     */
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkFieldsForEmptyValues();
        }
    };

    /**
     * Checks if username field is empty and turns login button on accordingly.
     */
    void checkFieldsForEmptyValues() {
        Button loginButton = findViewById(R.id.login_button);
        Button signUpButton = findViewById(R.id.signup_button);
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        loginButton.setClickable(!username.equals("") && !password.equals(""));
        signUpButton.setClickable(!username.equals("") && !password.equals(""));
        loginButton.setAlpha(username.equals("") || password.equals("") ? 0.5f : 1f);
        signUpButton.setAlpha(username.equals("") || password.equals("") ? 0.5f : 1f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_);
        mUsernameView = findViewById(R.id.input_username);
        mUsernameView.addTextChangedListener(mTextWatcher);
        mPasswordView = findViewById(R.id.input_password);
        mPasswordView.addTextChangedListener(mTextWatcher);
        userAccounts = loadUserAccounts();
        addLoginButtonListener();
        checkFieldsForEmptyValues();
        addSignUpButtonListener();
    }

    /**
     * Attempts to sign in the account specified.
     * If the password does not match for this user, the login attempt fails.
     * Display an error message if the user does not exist.
     */
    private boolean attemptLogin() {
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (!exists(username)) {
            createToast("User not found");
            return false;
        }

        User u = userAccounts.get(username);
        if (u.getPassword().equals(password)) {
            createToast("Login successful");
            currentUser = u.getName();
            return true;
        } else {
            createToast("Wrong password, try again");
            return false;
        }
    }

    /**
     * Activate the login button.
     */
    private void addLoginButtonListener() {
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            if (attemptLogin()) {
                switchToSlidingTileTitle();
            }
        });
    }

    /**
     * Activate the sign up button.
     */
    private void addSignUpButtonListener() {
        Button signUpButton = findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(v -> {
            String username = mUsernameView.getText().toString();
            String password = mPasswordView.getText().toString();
            if (exists(username)) {
                createToast("User already exists");
            } else {
                User u = new User(username, password);
                addUser(u);
                createToast("Sign Up Successful");
                currentUser = u.getName();
                switchToSlidingTileTitle();
            }
        });
    }

    /**
     * Switch to the SlidingTileTitleActivity view to select a game and difficulty.
     */
    private void switchToSlidingTileTitle() {
        Intent tmp = new Intent(this, MainActivity.class);
        saveCurrentUsername(currentUser);
        saveUserAccounts(userAccounts);
        startActivity(tmp);
        finish();
    }

    /**
     * Add a User to userAccounts collection.
     * Key: The username
     * Value: The User object
     *
     * @param u The User to be added.
     */
    private void addUser(User u) {
        userAccounts.put(u.getName(), u);
    }

    /**
     * Verify whether the User already exists by their unique username.
     *
     * @param name The username to verify
     * @return Whether name exists in UserAccounts or not.
     */
    private boolean exists(String name) {
        return name.equals("") || userAccounts.containsKey(name);
    }


    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    /**
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(
                this, msg, Toast.LENGTH_SHORT).show();
    }

    public Context getActivity() {
        return this;
    }
}
