package ec.com.expensys.service;

import ec.com.expensys.security.PEMUtils;
import ec.com.expensys.web.exception.DecryptException;
import ec.com.expensys.web.exception.MessageCode;
import jakarta.annotation.PostConstruct;
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

    private PrivateKey pemPrivateKey;

    public CryptoService() {}

    @PostConstruct
    public void init() throws Exception{
        pemPrivateKey = PEMUtils.loadPrivateKey(PRIVATE_KEY);
    }

    public String decrypt(String encryptMsg) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, pemPrivateKey);
            byte[] decodeMessage = Base64.getDecoder().decode(encryptMsg);
            byte[] decryptedMessage = cipher.doFinal(decodeMessage);

            return new String(decryptedMessage);

        }catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                IllegalBlockSizeException | BadPaddingException | IllegalArgumentException e){

            throw new DecryptException(MessageCode.DECRYPT_ERROR.getCode(),
                    MessageCode.DECRYPT_ERROR.getMessageTemplate(),
                    CryptoService.class.getName(),
                    true,
                    e.getLocalizedMessage());
        }
    }
}
