package br.com.hgisystem.certificatemanager.core.helpers;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Named
public class ObjectConverterHelper {
    public byte[] pathToByte(String certificatePath) throws IOException {
        Path pathPj = Paths.get(certificatePath);
        return Files.readAllBytes(pathPj);
    }

    public byte[] readBytesFromUrl(String url) {
        RestTemplate restTemplate = new RestTemplate();

        RequestCallback requestCallback = restTemplate
                .httpEntityCallback(null, null);

        ResponseExtractor<ResponseEntity<byte[]>> responseExtractor = restTemplate
                .responseEntityExtractor(byte[].class);

        ResponseEntity<byte[]> responseEntity = restTemplate.execute(
                URI.create(url),
                HttpMethod.GET,
                requestCallback,
                responseExtractor);

        byte[] bytes = new byte[0];
        if (responseEntity != null)
            bytes = responseEntity.getBody();

        return bytes;
    }

    public String byteToBase64(byte[] file) {
        return Base64.getEncoder().encodeToString(file);
    }

    public byte[] base64ToByte(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
