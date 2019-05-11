package kr.tpsw.rsstats.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.TreeMap;

public class StatsCmdAPI {

    public static Map<String, Map<Integer, String>> map = null;
    public static File file = new File("plugins\\RsStats\\cmd.ser");

    public static void firstSetMap() {
        map = new TreeMap<String, Map<Integer, String>>();
    }

    public static void serialize() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void deserialize() {
        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                map = (Map<String, Map<Integer, String>>) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkNull() {
        if (map == null) {
            firstSetMap();
        }
    }
}
