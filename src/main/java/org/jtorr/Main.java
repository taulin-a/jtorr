package org.jtorr;

import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;
import org.jtorr.component.InfoHashGenerator;
import org.jtorr.component.factory.impl.BencodeFactoryImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {
            var torrFileByteArr = Files.readAllBytes(Path.of("/home/paulo_alves/Downloads/ebook.torrent"));
            var bencode = new Bencode();
            var bencodeData = new BencodeFactoryImpl().createBencodeData(
                    bencode.decode(torrFileByteArr, Type.DICTIONARY));

            System.out.println(bencodeData);

            var infoHashGen = new InfoHashGenerator();

            System.out.println(infoHashGen.generate(bencodeData.info()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}