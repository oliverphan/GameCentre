package gamelauncher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import fall2018.csc2017.slidingtiles.R;
import users.User;

public class LoginActivity extends AppCompatActivity {

    /**
     * The save file for UserAccounts.
     * NOTE: Only accessed in LoginActivity and GameActivity.
     */
    public static final String ACCOUNTS_SAVE_FILENAME = "accounts_save_file.ser";

    /**
     * The save file for the Current User.
     * NOTE: Only accessed in LoginActivity and GameActivity.
     */
    public static final String USER_SAVE_FILENAME = "user_save_file.ser";

    /**
     * A HashMap of all the Users created. The key is the username, the value is the User object.
     */
    private HashMap<String, User> userAccounts;

    /**
     * The current logged in user.
     */
    private User currentUser;

    /**
     * UI References
     */
    private EditText mUsernameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_);
        loadFromFile(ACCOUNTS_SAVE_FILENAME, userAccounts);
        mUsernameView = findViewById(R.id.input_username);
        mPasswordView = findViewById(R.id.input_password);
        addLoginButtonListener();
        addSignUpButtonListener();
    }

    /**
     * Attempts to sign in the account specified.
     * If the password does not match for this user, the login attempt fails.
     * Display an error message if the user does not exist.
     */
    private boolean attemptLogin() {
        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        // Check if username exists
        if (!exists(username)) {
            createToast("User not found");
            return false;
        }

        User u = userAccounts.get(username);
        // Check if password matches for this User
        if (u.getPassword().equals(password)) {
            createToast("Login successful");
            currentUser = userAccounts.get(username);
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
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptLogin()) {
                    switchToSlidingTileTitle();
                }
            }
        });
    }

    /**
     * Activate the sign up button.
     */
    private void addSignUpButtonListener() {
        Button signUpButton = findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store values at the time of the login attempt.
                String username = mUsernameView.getText().toString();
                String password = mPasswordView.getText().toString();
                if (exists(username)) {
                    createToast("User already exists");
                } else {
                    User u = new User(username, password);
                    addUser(u);
                    // Successful signup: Save the signed in user, and userAccounts
                    saveToFile(USER_SAVE_FILENAME, u);
                    saveToFile(ACCOUNTS_SAVE_FILENAME, userAccounts);
                    switchToSlidingTileTitle();
                }
            }
        });
    }

    /**
     * Switch to the SlidingTileTitleActivity view to select a game and difficulty.
     */
    private void switchToSlidingTileTitle() {
        Intent tmp = new Intent(this, SlidingTileTitleActivity.class);
        startActivity(tmp);
        finish();
    }

    /**
     * Load the object from fileName.
     *
     * @param fileName the name of the file
     * @param obj the object being assigned to
     */
    @SuppressWarnings({"unchecked", "SameParameterValue"})
    private void loadFromFile(String fileName, Object obj) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                // NOTE: Casting to Object might not work
                obj = (Object) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            userAccounts = new HashMap<>();
            saveToFile(ACCOUNTS_SAVE_FILENAME, userAccounts);
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the user accounts in fileName.
     *
     * @param fileName the name of the file
     * @param obj the object to write to fileName
     */
    public void saveToFile(String fileName, Object obj) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(obj);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
      Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
