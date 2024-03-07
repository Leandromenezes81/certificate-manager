package br.com.hgisystem.certificatemanager.core.helpers;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Named;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Named
public class CryptoHelper {
    @Inject
    private ObjectConverterHelper objectConverterHelper;

    @Value("${cryptography.encrypt_algorithm}")
    private String encryptAlgorithm;

    @Value("${cryptography.secret_key}")
    private String secretKey;

    @Value("${cryptography.transformation}")
    private String transformation;

    public String encryptValue(String data) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return objectConverterHelper.byteToBase64(encryptedData);
    }

    public byte[] encryptValue(byte[] data) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        return cipher.doFinal(data);
    }

    public String decryptValue(String encryptedValue) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
        byte[] encryptedData = objectConverterHelper.base64ToByte(encryptedValue);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    private Cipher getCipher(int mode) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, encryptAlgorithm);
        Cipher cipher = Cipher.getInstance(transformation);
        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        cipher.init(mode, secretKeySpec, ivParams);
        return cipher;
    }
}