package ec.com.expensys.service;

import ec.com.expensys.config.security.PEMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.util.Base64;

@Service
public class CryptoService {

    private final PrivateKey pemPrivateKey;

    public CryptoService() throws Exception {
        this.pemPrivateKey = PEMUtils.loadPrivateKey("RSA/private_pkcs8.pem");
    }

    public String decrypt(String encryptMsg) throws Exception {
        Cipher cipher  = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pemPrivateKey);
        byte[] decodeMessage = Base64.getDecoder().decode(encryptMsg);
        byte[] decryptedMessage = cipher.doFinal(decodeMessage);
        return new String(decryptedMessage);
    }
}
