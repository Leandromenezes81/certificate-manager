package br.com.hgisystem.certificatemanager.core.helpers;

import javax.inject.Named;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Named
public class ZipFileHelper {
    public void addBytesToZip(byte[] bytes, String entryName, ZipOutputStream zipOutputStream)
            throws IOException {
        ZipEntry zipEntry = new ZipEntry(entryName);
        zipOutputStream.putNextEntry(zipEntry);
        zipOutputStream.write(bytes);
        zipOutputStream.closeEntry();
    }
}
