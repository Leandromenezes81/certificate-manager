package br.com.hgisystem.certificatemanager.infra.helpers;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public abstract class ReaderHttpServletRequestBodyHelper {
    public static String getContent(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = reader.readLine();
        }
        reader.close();

        return sb.toString();
    }
}
