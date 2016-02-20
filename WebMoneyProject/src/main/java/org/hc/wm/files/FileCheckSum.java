package org.hc.wm.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

public class FileCheckSum {

    public static String md5checkSum(File file) throws IOException{
        String checksum = null;
        FileInputStream fis = new FileInputStream(file);
        checksum = DigestUtils.md5Hex(fis);
        fis.close();
        return checksum;
    }

}
