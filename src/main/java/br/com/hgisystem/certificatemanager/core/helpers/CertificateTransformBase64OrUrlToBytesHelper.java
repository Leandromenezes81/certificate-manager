package br.com.hgisystem.certificatemanager.core.helpers;

import org.apache.commons.codec.binary.Base64;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class CertificateTransformBase64OrUrlToBytesHelper {
    @Inject
    private ObjectConverterHelper objectConverterHelper;

    public byte[] certificateTransformBase64OrUrlToBytes(String certificateData) throws
            IOException {
        boolean isBase64 = Base64.isBase64(certificateData);
        byte[] bytes;
        if(isBase64){
            bytes = objectConverterHelper.base64ToByte(certificateData);
        } else {
            bytes = objectConverterHelper.readBytesFromUrl(certificateData);
        }
        return bytes;
    }
}
