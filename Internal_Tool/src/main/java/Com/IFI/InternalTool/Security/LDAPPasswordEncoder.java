package Com.IFI.InternalTool.Security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by ndlong on 8/10/2018.
 */
public class LDAPPasswordEncoder implements PasswordEncoder{

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return true;
    }
}
