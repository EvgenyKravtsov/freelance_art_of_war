package com.tesseractumstudios.warhammer_artofwar.tools;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FileSearcher {
    public static ArrayList<String> searchFiles(String path, String searchQuery) {
        ArrayList<String>   result      = new ArrayList<>();
        Iterator<File>      iterator    = FileUtils.iterateFiles(new File(path),
                                                new String[]{"html"}, true);
        String              constraint  = searchQuery.toLowerCase();

        while (iterator.hasNext() ) {
            File            file    = iterator.next();

//            parseFile(file, constraint, result);
            if ( file.getName().toLowerCase().contains(constraint) ) {
                result.add(file.getAbsolutePath());
            }
        }

        return result;
    }

    private static void parseFile(File file, String constraint, ArrayList<String> result) {
        BufferedReader  br      = null;

        try {
            br = new BufferedReader(new FileReader(file));
            for ( String line; (line = br.readLine()) != null; ) {
                if ( line.toLowerCase().contains(constraint) ) {
                    result.add(file.getAbsolutePath());

                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            } catch ( NullPointerException e ) {
                e.printStackTrace();
            }
        }
    }
}
