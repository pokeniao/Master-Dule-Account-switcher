package com.pokeniao;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName:${Name}
 * Package:com.pokeniao
 * Description:
 * Autor:
 *
 * @Create:${Date}-19:41
 */
public class FrameMain extends JFrame {
    public static void main(String[] args) {
        FrameMain frameMain = new FrameMain();
        frameMain.setTitle("Master Duel账号切换器");
        frameMain.setSize(400,300);
        frameMain.setLocationRelativeTo(null);//页面居中设置
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击×关闭整个项目
        GridBagLayout gridBagLayout = new GridBagLayout();// 网格分布模板
        MyPanel myPanel = new MyPanel(gridBagLayout);
        myPanel.Draw();
        frameMain.add(myPanel);
        frameMain.setVisible(true);

    }

}