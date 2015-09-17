package estei.fr.welcometohogwarts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;


/**
 * Created by mdepe on 17/09/2015.
 */
public class MainActivity extends Activity {

    public final static String EXTRA_USERNAME = "estei.fr.welcometohogwarts.EXTRA_USERNAME";
    public final static String EXTRA_EMAIL = "estei.fr.welcometohogwarts.EXTRA_EMAIL";

    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private TextInputLayout emailWrapper;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameWrapper = (TextInputLayout) findViewById(R.id.userNameWrapper);
        passwordWrapper =  (TextInputLayout) findViewById(R.id.passwordWrapper);
        emailWrapper = (TextInputLayout) findViewById(R.id.emailWrapper);
        signUpButton  =(Button) findViewById(R.id.sign_up);

        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");
        emailWrapper.setHint("Email");
        this.addListener();
    }


    private boolean checkEmail() {

        String email = emailWrapper.getEditText().getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            emailWrapper.setError("Email need to be valid");
            requestFocus(emailWrapper.getEditText());
            return false;
        } else {
            emailWrapper.setErrorEnabled(false);
        }

        return true;
    }
    private boolean checkUsername() {
        if (usernameWrapper.getEditText().getText().toString().trim().isEmpty()) {
            usernameWrapper.setError("You need to set an username");
            requestFocus(usernameWrapper.getEditText());
            return false;
        } else {
            usernameWrapper.setErrorEnabled(false);
        }
        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private void addListener(){
        signUpButton.setOnClickListener(signUpListener );
        signUpButton.setOnLongClickListener(signUpLongListener);
    }
    private OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeyboard();
            if (!checkEmail() || !checkUsername())return;
            submit();
        }
    };
    private View.OnLongClickListener signUpLongListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(getApplicationContext(),"Really !!! is not so magic",Toast.LENGTH_LONG).show();
            return true;
        }
    };
    private  void submit(){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_USERNAME,this.usernameWrapper.getEditText().getText());
        intent.putExtra(EXTRA_EMAIL,this.emailWrapper.getEditText().getText());
        this.startActivity(intent);
    }
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
