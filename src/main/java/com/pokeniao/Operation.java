package com.pokeniao;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

/**
 * ClassName:
 * Package:com.pokeniao
 * Description:
 * Autor:
 *
 * @Create:-22:02
 */
public class Operation {
    static String propertiesPath;//配置文件所在的位置

    public static DefaultListModel defaultListModel = null; //账号集合

    //静态代码块初始化
    static {
        try {
            //获取系统下当前文件的位置
            URL resource = FrameMain.class.getProtectionDomain().getCodeSource().getLocation();
            String jarPath = new File(resource.toURI()).getAbsolutePath();
            String parentPath = new File(jarPath).getParent();
            propertiesPath = parentPath + "/main.properties";
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取properties
     *
     * @return
     */
    public Properties getproperties() {
        Properties properties = new Properties();
        File file = new File(propertiesPath);
        //配置文件不存在退出
        if (!file.exists()) return null;
        try (FileInputStream fileInputStream = new FileInputStream(propertiesPath)) {
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 配置文件页面初始化
     * 创建配置文件+设置主路径+刷新账号列表
     *
     * @param path
     */
    public void saveMainPath(String path) {
        try {
            File file = new File(propertiesPath);
            if (!file.exists()) file.createNewFile();//判断配置文件是否存在，不在则创建

            Properties properties = getproperties();
            if (properties == null) return;

            properties.setProperty("GamePath", path);
            properties.store(new FileOutputStream(propertiesPath), null);
            readPropertiesForValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 主路径栏
     * 显示路径文件到主路径栏
     *
     * @param jTextField
     */
    public void showMP_TextBox(JTextField jTextField) {

        Properties properties = getproperties();
        if (properties == null) return;

        jTextField.setText(properties.getProperty("GamePath"));

    }

    /**
     * 保存Value
     *
     * @param username
     * @param value
     * @throws IOException
     */
    public void saveValue(String username, String value) throws IOException {
        Properties properties = getproperties();
        properties.setProperty(username, value);
        properties.store(new FileOutputStream(propertiesPath), null);
    }

    /**
     * 账号列表
     * 读取配置文件中的,所有账号,创建主账号
     *
     * @return String[]
     */
    public String[] readPropertiesForValue() {
        try {
            String value;
            ArrayList<String> strings = new ArrayList<>();
            boolean createUsername = false;//是否创建主账号
            Properties properties = getproperties();
            if (properties == null) return null;

            value = PathOrValue(properties, "Value");//获取Value值
            if (value == null) return null;//路径不对,获取不到值


            for (String key : properties.stringPropertyNames()) {
                //遍历查看是否有账号
                int i;
                if ((i = key.indexOf("user_")) != -1) {
                    //是否有主账号
                    if (properties.getProperty(key).equals(value)) {
                        createUsername = true;//有主账号不需要创建
                    }
                    String name = key.substring(i + 5, key.length());
                    strings.add(name);
                }
            }
            //没有主账号进行创建
            if (!createUsername) {

                saveValue("user_" + value, value);
            }
            return (String[]) strings.toArray(new String[strings.size()]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取Mainkey
     *
     * @param properties
     * @return
     */
    public String getMainKey(Properties properties) {
        String value = PathOrValue(properties, "Value");//获取Value值
        if (value == null) return null;//路径不对,获取不到值
        for (String key : properties.stringPropertyNames()) {
            if (properties.getProperty(key).equals(value)) {
                return key;
            }
        }
        return null;
    }

    /**
     * 显示当前账号
     *
     * @param jLabel
     */
    public void showCurrent(JLabel jLabel) {
        String name = null;
        if (name == null) name = "";
        Properties properties = getproperties();
        if (properties == null) return;
        String key = getMainKey(properties);
        if(key==null) return;
        name = key.substring(key.indexOf("user_") + 5, key.length());
        jLabel.setText("当前游戏账号为：" + name);
    }

    /**
     * 重命名账号
     *
     * @param name
     * @param NewName
     */
    public void Rename(String name, String NewName) {

            Properties properties = getproperties();
            String keyname = "user_" + name;
            String value = properties.getProperty(keyname);
            properties.remove(keyname);
            keyname = "user_" + NewName;
            properties.setProperty(keyname, value);
        try (FileOutputStream fileOutputStream = new FileOutputStream(propertiesPath)){
            properties.store(fileOutputStream, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 选择账号登入
     *
     * @param name
     */
    public String select(String name) {

        String keyname = "user_" + name;
        Properties properties = getproperties();

        String value = properties.getProperty(keyname);
        String MainGame = properties.getProperty("GamePath");
        if (MainGame.indexOf("LocalData") != -1) {
            int i = MainGame.lastIndexOf("\\");

            //标记主存档
            String MainGameCurrent = MainGame + "Main";
            //先标记主存档,判断主存档，除了自己还有没有别的同名文件
            File file = new File(MainGame);
            File file1 = new File(MainGameCurrent);
            file.renameTo(file1);

            //进行文件重命名
            String Path = MainGame.substring(0, i);
            String newGame = Path + "\\" + value;
            properties.setProperty("GamePath",newGame);
            File file2 = new File(newGame);
            if (file2.exists()) {
                 DeleteFile(file2);
                file1.renameTo(file2);
            } else {
                file1.renameTo(file2);
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(propertiesPath)){
                properties.store(fileOutputStream, null);
                return newGame;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "路径错误", "警告", JOptionPane.WARNING_MESSAGE);
            return MainGame;
        }

    }
    public void DeleteFile(File file){
        File[] files = file.listFiles();
        if(files!=null)
        {
            for (File f:files)
            {
                //判断是否为文件夹，是文件夹就删除
                if(f.isFile()){
                    f.delete();
                }else {
                    DeleteFile(f);
                }
            }
            //全删除后删除自己
            file.delete();
        }
    }

    /**
     * 添加账号集合
     * @param string 可以为null,为null从readValue中读取配置文件，
     */
    public void setDefaultListModel(String string) {
        if (defaultListModel == null)
            defaultListModel = new DefaultListModel<>();
        //如果有传入值直接添加
        if(string!=null)
        {
            defaultListModel.addElement(string);
            return;
        }
        //从配置文件读
        String[] strings = readPropertiesForValue();
        if (strings == null) {
            defaultListModel.clear();
            return;
        }
        for (int i = 0; i < strings.length; i++) {
            defaultListModel.addElement(strings[i]);
        }

    }

    /**
     * 检验是否重复点击一个路径
     *
     * @param path
     * @return
     */
    public boolean NORepetition(String path) {
        Properties properties = getproperties();
        if (properties == null) return false;
        String value = properties.getProperty("GamePath");
        return path.equals(value);
    }

    /**
     * 获取Path 或者Value
     *
     * @param type 传入需要的类型 Path表示根路径 ,Value 表示账号代码
     * @return
     */
    public String PathOrValue(Properties properties, String type) {
        String gamePath = properties.getProperty("GamePath");
        String value;
        String path;
        //检验是不是LocalData路径
        if (gamePath.indexOf("LocalData") != -1) {
            if (type.equals("Value")) {
                int i = gamePath.lastIndexOf("\\");
                value = gamePath.substring(i + 1, gamePath.length());
                return value;
            } else if (type.equals("Path")) {
                int i = gamePath.lastIndexOf("\\");
                path = gamePath.substring(0, i);
                return path;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public String[] GetValues(Properties properties){
        ArrayList<String> strings = new ArrayList<>();
        for (String key:properties.stringPropertyNames())
        {
            strings.add(properties.getProperty(key));
        }
        String[] array = strings.toArray(new String[strings.size()]);
        return array;
    }
    /**
     * 搜索账号
     */
    public void searchFile() {
        Properties properties = getproperties();
        String path = PathOrValue(properties, "Path");
        String[] values=GetValues(properties);
        //遍历得到key
        String mainKey = getMainKey(properties);
        //搜索除了主存档以外的存档
        File file = new File(path);
        File[] files = file.listFiles();
        for(File f:files)
        {
            if(f.getName().length()>4)
            {
                boolean add=true;
                //是否是有账号
                for (int i = 0; i < values.length; i++) {
                  if(values[i].equals(f.getName()))
                  {
                      add=false;
                      break;
                  }
                }
                if(add==true)
                {
                    setDefaultListModel(f.getName());
                    properties.setProperty("user_"+f.getName(),f.getName());
                }
            }
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(propertiesPath)){
            properties.store(fileOutputStream,null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 删除账号
     */
    public void DeleteUser(String name){
        Properties properties=getproperties();
        properties.remove("user_"+name);
        defaultListModel.remove(defaultListModel.indexOf(name));

        try (FileOutputStream fileOutputStream = new FileOutputStream(propertiesPath)){
            properties.store(fileOutputStream,null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
