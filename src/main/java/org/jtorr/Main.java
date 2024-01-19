package org.jtorr;

import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            var torrFileByteArr = Files.readAllBytes(Path.of("/home/paulo_alves/Downloads/ebook.torrent"));
            var bencode = new Bencode();
            Map<String, Object> dict = bencode.decode(torrFileByteArr, Type.DICTIONARY);

            System.out.println(dict);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}