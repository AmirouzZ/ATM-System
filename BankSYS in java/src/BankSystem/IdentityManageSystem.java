package BankSystem;

import java.util.HashMap;
import java.util.Map;

public class IdentityManageSystem {
    private static final Map<String,Customer> identityMsg = new HashMap<>();
    public static Map<String,Customer> getIdentifyMsg(){
        return identityMsg;
    }
}


