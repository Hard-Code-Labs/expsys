package ec.com.expensys.service;

import ec.com.expensys.security.PEMUtils;
import ec.com.expensys.web.exception.DecryptException;
import ec.com.expensys.web.exception.MessageCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

@Service
public class CryptoService {

    @Value("${constant.privateKey}")
    private String PRIVATE_KEY;

    public CryptoService() {}

    public String decrypt(String encryptMsg) {

        PrivateKey pemPrivateKey = PEMUtils.loadPrivateKey(PRIVATE_KEY);

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, pemPrivateKey);
            byte[] decodeMessage = Base64.getDecoder().decode(encryptMsg);
            byte[] decryptedMessage = cipher.doFinal(decodeMessage);

            return new String(decryptedMessage);

        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException | IllegalArgumentException e){

            throw new DecryptException(MessageCode.DECRYPT_ERROR.getCode(),
                    MessageCode.DECRYPT_ERROR.getMessageTemplate(),
                    CryptoService.class.getName(),
                    true,
                    e.getLocalizedMessage());
        }
        catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
