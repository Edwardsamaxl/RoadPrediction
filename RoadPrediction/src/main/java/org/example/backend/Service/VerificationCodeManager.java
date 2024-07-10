package org.example.backend.Service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.HashMap;
@Component
public class VerificationCodeManager {
    private Map<String, String> verificationCodes = new HashMap<>();

    public void storeVerificationCode(String mail, String code){
        verificationCodes.put(mail, code);
    }

    public String getVerificationCode(String mail){
        return verificationCodes.get(mail);
    }

    public void removeVerificationCode(String mail){
        verificationCodes.remove(mail);
    }
}
