package com.bootx.mall.product;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Demo {

    public static void main(String[] args) throws Exception{
        String path = "C:\\Users\\black\\IdeaProjects\\mall\\demo-mall\\src";
        File root = new File(path);
        List<File> list = new ArrayList<>();

        System.out.println(root.getAbsolutePath());
        list(root,list);
        List<File> list1 = list.stream().filter(item-> item.getAbsolutePath().lastIndexOf(".ftl")>0).collect(Collectors.toList());
        for (File file:list1) {
            String path1 = file.getAbsolutePath().replace(".ftl",".html");
            FileUtils.copyFile(file,new File(path1));
            FileUtils.deleteQuietly(file);
        }
    }

    public static void list(File parent,List<File> list) {
        File[] files = parent.listFiles();
        for (File file:files) {
            if(file.isDirectory()){
                list(file,list);
            }else{
                list.add(file);
            }
        }
    }
}
