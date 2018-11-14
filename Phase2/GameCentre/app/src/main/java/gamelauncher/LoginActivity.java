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
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file.ser";

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
        loadFromFile(SAVE_FILENAME);

        mUsernameView = findViewById(R.id.input_username);
        mPasswordView = findViewById(R.id.input_password);
        currentUser = null;

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
            makeToastNoUser();
            return false;
        }

        User u = userAccounts.get(username);
        // Check if password matches for this User
        if (u.getPassword().equals(password)) {
            makeToastLoginSuccess();
            currentUser = userAccounts.get(username);
            return true;
        } else {
            makeToastWrongPass();
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
     * Activate the sign up button
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
                    makeToastUserExists();
                } else {
                    User u = new User(username, password);
                    currentUser = u;
                    addUser(u);
                    saveToFile(SAVE_FILENAME);
                    switchToSlidingTileTitle();
                }
            }
        });
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
     * Display that the user already exists
     */

    private void makeToastUserExists() {
        Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that the password is wrong.
     */
    private void makeToastWrongPass() {
        Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that the user was not found.
     */
    private void makeToastNoUser() {
        Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display login was successful.
     */
    private void makeToastLoginSuccess() {
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
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

    /**
     * Read the user account hashmap from disk.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        userAccounts = (HashMap) intent.getSerializableExtra(IntentKeys.USERACCOUNTS_KEY);
        currentUser = (User) intent.getSerializableExtra(IntentKeys.CURRENTUSER_KEY);
        loadFromFile(SAVE_FILENAME);
    }

    /**
     * Switch to the GameLaunchCentre view to select a game and difficulty.
     */
    private void switchToSlidingTileTitle() {
        Intent tmp = new Intent(this, SlidingTileTitleActivity.class);
        tmp.putExtra(IntentKeys.CURRENTUSER_KEY, currentUser);
        tmp.putExtra(IntentKeys.USERACCOUNTS_KEY, userAccounts);
        startActivity(tmp);
    }


    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    @SuppressWarnings({"unchecked", "SameParameterValue"})
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userAccounts = (HashMap<String, User>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            userAccounts = new HashMap<>();
            saveToFile(SAVE_FILENAME);
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
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userAccounts);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        Intent close = new Intent();
        close.putExtra("CLOSE", true);
        setResult(RESULT_OK, close);
        finish();
    }
}
