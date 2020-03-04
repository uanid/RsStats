package com.uanid.minecraft.util;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Uanid
 * @version 1.0.4 2019-05-12
 * @since 2016-??-??
 */
public class YamlConfigurationUtil {
    private File file;
    private List<String> comment = new ArrayList<String>();
    private Yaml yaml;

    private Object javaObject;
    private Map<String, Object> map;

    public YamlConfigurationUtil() {
        this((File) null);
    }

    public YamlConfigurationUtil(String file) {
        this(new File(file));
    }

    public YamlConfigurationUtil(File file) {
        DumperOptions yamlOptions = new DumperOptions();
        Representer yamlRepresent = new Representer();
        yamlOptions.setIndent(2);
        yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yamlRepresent.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yaml = new Yaml(new Constructor(), yamlRepresent, yamlOptions);


        this.file = file;
        if (file == null || !file.exists()) {
            this.map = new LinkedHashMap<String, Object>();
        } else {
            this.reloadYaml();
        }
    }

    public void reloadYaml() {
        try {
            this.reloadYYaml();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveYaml() {
        try {
            this.saveYYaml(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveYaml(File file) {
        try {
            this.saveYYaml(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void reloadYYaml() throws IOException {
        FileInputStream fin = new FileInputStream(file);
        InputStreamReader inr = new InputStreamReader(fin);
        BufferedReader br = new BufferedReader(inr);
        StringBuilder sb = new StringBuilder();
        this.comment.clear();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    this.comment.add(line);
                } else {
                    sb.append(line).append("\n");
                }
            }
        } finally {
            br.close();
            inr.close();
            fin.close();
        }
        Map<String, Object> map = null;
        try {
            map = (Map<String, Object>) this.yaml.load(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (map != null) {
            this.map = map;
        } else {
            this.map = new LinkedHashMap();
        }
    }

    private void saveYYaml(File file) throws IOException {
        File pf = file.getParentFile();
        if (pf != null && !pf.isDirectory()) {
            pf.mkdirs();
        }
        StringBuilder sb = new StringBuilder();
        for (String comment : this.comment) {
            sb.append(comment).append("\n");
        }
        String data = this.yaml.dump(map);
        if (!data.equals("{}\n")) {
            sb.append(data);
        }
        FileWriter fw = new FileWriter(file);
        try {
            fw.write(sb.toString());
        } finally {
            fw.close();
        }
    }

    public static void getResourceInJar(File jarfile, String entryname, File output) {
        try {
            JarFile jfile = new JarFile(jarfile);
            JarEntry jentry = jfile.getJarEntry(entryname);
            if (jentry == null) {
                jfile.close();
                throw new IOException("Can't find entryfile");
            }
            InputStream instream = jfile.getInputStream(jentry);
            Reader reader = new InputStreamReader(instream, Charset.forName("UTF-8"));
            BufferedReader breader = new BufferedReader(reader);
            FileWriter filew = new FileWriter(output);
            BufferedWriter bw = new BufferedWriter(filew);
            String s;
            while ((s = breader.readLine()) != null) {
                bw.append(s).append('\n');
            }
            bw.close();
            filew.close();
            breader.close();
            reader.close();
            instream.close();
            jfile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addComment(String comment) {
        this.comment.add(comment);
    }

    public void removeComment(int index) {
        this.comment.remove(index);
    }

    public void removeComment(String comment) {
        this.comment.remove(comment);
    }

    public List<String> getComment() {
        return this.comment;
    }

    @SuppressWarnings("unchecked")
    public Object get(String key) {
        String[] args = key.replace(".", " ").split(" ");
        Map<String, Object> map = this.map;
        Object o = null;
        for (int i = 0; i < args.length; i++) {
            o = map.get(args[i]);
            if (o == null) {
                return null;
            } else if (o instanceof Map) {
                map = (Map<String, Object>) o;
            }
        }
        return o;
    }

    @SuppressWarnings("unchecked")
    public void set(String key, Object value) {
        String[] args = key.replace(".", " ").split(" ");
        Map<String, Object> map = this.map;
        Object o;
        for (int i = 0; i < args.length; i++) {
            o = map.get(args[i]);
            if ((i + 1) == args.length) {
                if (o instanceof Map) {
                    map = (Map<String, Object>) o;
                }
                if (value == null) {
                    map.remove(args[i]);
                } else {
                    map.put(args[i], value);
                }
            } else {
                if (o instanceof Map) {
                    map = (Map<String, Object>) o;
                } else {
                    Map<String, Object> m = new LinkedHashMap<>();
                    map.put(args[i], m);
                    map = m;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void mapKeys(Map<String, Object> map, Set<String> set, String first) {
        Object o;
        String s;
        for (String key : map.keySet()) {
            o = map.get(key);
            s = first + key;
            set.add(s);
            if (o instanceof Map) {
                this.mapKeys((Map<String, Object>) o, set, s + ".");
            }
        }
    }

    public void setAllMap(Map<String, ?> map) {
        for (String key : map.keySet()) {
            this.set(key, map.get(key));
        }
    }

    public Set<String> getKeys(boolean deep) {
        if (deep) {
            Set<String> set = new LinkedHashSet<>();
            this.mapKeys(this.map, set, "");
            return set;
        } else {
            return this.map.keySet();
        }
    }

    public int getInt(String key) {
        Object va = this.get(key);
        return va instanceof Integer ? (int) va : 0;
    }

    public long getLong(String key) {
        Object va = this.get(key);
        return va instanceof Long ? (long) va : 0;
    }

    public double getDouble(String key) {
        Object va = this.get(key);
        return va instanceof Double ? (double) va : 0;
    }

    public float getFloat(String key) {
        Object va = this.get(key);
        return va instanceof Float ? (float) va : 0;
    }

    public boolean getBoolean(String key) {
        Object va = this.get(key);
        return va instanceof Boolean ? (boolean) va : false;
    }

    public String getString(String key) {
        Object va = this.get(key);
        return va instanceof String ? (String) va : null;
    }

    public List<?> getList(String key) {
        Object va = this.get(key);
        return va instanceof List ? (List<?>) va : null;
    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(String key) {
        Object va = this.get(key);
        return va instanceof List ? (List<String>) va : new LinkedList<String>();
    }

    public Map<?, ?> getMap(String key) {
        Object va = this.get(key);
        return va instanceof Map ? (Map<?, ?>) va : new LinkedHashMap<Object, Object>();
    }

    public boolean isInt(String key) {
        return get(key) instanceof Integer;
    }

    public boolean isLong(String key) {
        return get(key) instanceof Long;
    }

    public boolean isFloat(String key) {
        return get(key) instanceof Float;
    }

    public boolean isDouble(String key) {
        return get(key) instanceof Double;
    }

    public boolean isList(String key) {
        return get(key) instanceof List<?>;
    }

    public boolean isBoolean(String key) {
        return get(key) instanceof Boolean;
    }

    public boolean isMap(String key) {
        return get(key) instanceof Map<?, ?>;
    }

    public boolean isObject(String key) {
        return get(key) != null;
    }

    public void clear() {
        map.clear();
        comment.clear();
    }

    public Map<?, ?> getAllMap() {
        return this.map;
    }
}
