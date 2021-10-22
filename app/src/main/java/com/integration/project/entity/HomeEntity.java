package com.integration.project.entity;

/**
 * Created by Wongerfeng on 2020/8/13.
 */
public class HomeEntity {
 private Integer id;// : 101, // 员工id
         private int status;// : 1,//  0未实名(灰色图标) 1 已实名 (黄色图标)2实名失败 (图标+文字实名认证失败)3无需认证 不显示图标
         private String text;// :"实名认证失败",//文字提示
         private String name;// : 张三 // 员工姓名

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HomeEntity{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", text='" + text + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
