package com.pokeniao;

import javax.swing.*;
import java.awt.*;

import static com.pokeniao.MyPanel.MaxY;

/**
 * ClassName:
 * Package:com.pokeniao
 * Description:
 * Autor:
 *
 * @Create:-20:06
 */
public class Module{
    GridBagLayout gridBagLayout=null;
    GridBagConstraints constraints=null;
    Insets insets=null;//填充宽度
    JPanel jPanel=null;
    JDialog jDialog=null;
    public Module(GridBagLayout gridBagLayout,JPanel jPanel){
        this.gridBagLayout=gridBagLayout;
        this.jPanel=jPanel;
        constraints = new GridBagConstraints();
        insets=new Insets(0, 0, 0, 0);//默认不填充

    }
    public Module(GridBagLayout gridBagLayout, JDialog jDialog){
        this.gridBagLayout=gridBagLayout;
        this.jDialog=jDialog;
        constraints = new GridBagConstraints();
        insets=new Insets(0, 0, 0, 0);//默认不填充

    }
    public void initConstraints(){
        constraints.gridwidth=1;
        constraints.gridheight=1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.ipadx=0;
        constraints.ipady=0;

        constraints.insets =insets;
    }
    public JLabel Text(String context,int x,int y,int size,int widthOfGrid){
        JLabel jLabel = new JLabel(context);
        jLabel.setFont(new Font("微软宋体", Font.PLAIN, size));
        this.initConstraints();
        constraints.gridx=x;
        constraints.gridy=y;
        constraints.gridwidth=widthOfGrid;
        gridBagLayout.setConstraints(jLabel,constraints);
        jPanel.add(jLabel);
        return jLabel;
    }
    public JTextField TextBox(int x,int y,String type){
        // 创建文本输入框
        JTextField userText = new JTextField();
        this.initConstraints();
        constraints.gridx=x;
        constraints.gridy=y;
        constraints.gridwidth=4;
        constraints.ipadx=200;
        gridBagLayout.setConstraints(userText,constraints);
        if(type.equals("jPanel")) {
            jPanel.add(userText);
        }
        if (type.equals("jDialog"))
        {
            jDialog.add(userText);
        }else {
            new RuntimeException("type类型错误");
        }
        return userText;
    }
    public JButton Button(String name,int x,int y,int size,int[] insets){
        JButton openButton = new JButton(name);
        openButton.setFont(new Font("微软宋体", Font.PLAIN, size));
        this.initConstraints();
        constraints.gridx=x;
        constraints.gridy=y;
        if(insets!=null)
        constraints.insets=new Insets(insets[0],insets[1],insets[2],insets[3]);
        gridBagLayout.setConstraints(openButton,constraints);
        jPanel.add(openButton);
        return openButton;
    }

    public JButton ButtonbyjDialog(String name,int x,int y,int size,int[] insets){
        JButton openButton = new JButton(name);
        openButton.setFont(new Font("微软宋体", Font.PLAIN, size));
        this.initConstraints();
        constraints.gridx=x;
        constraints.gridy=y;
        if(insets!=null)
            constraints.insets=new Insets(insets[0],insets[1],insets[2],insets[3]);
        gridBagLayout.setConstraints(openButton,constraints);
        jDialog.add(openButton);
        return openButton;
    }
    public JList SelectBox(DefaultListModel defaultListModel,int x,int y){

        JList jList = new JList(defaultListModel);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setSelectedIndex(0);
        JScrollPane scrollPane = new JScrollPane(jList);

        this.initConstraints();
        constraints.gridx=x;
        constraints.gridy=y;
        constraints.gridwidth=4;
        constraints.gridheight=5;
        constraints.ipadx=100;
        constraints.ipady=100;

        constraints.insets=new Insets(10,0,0,0);
        gridBagLayout.setConstraints(scrollPane,constraints);

        jPanel.add(scrollPane);
        return jList;
    }
    public void bottom(){
        JLabel jLabel = new JLabel("by bilbil：学不会破壳的鸟");
        this.initConstraints();
        constraints.weighty=0.1;
        constraints.gridx=5;
        constraints.gridy=MaxY;
        constraints.gridwidth=5;
        gridBagLayout.setConstraints(jLabel,constraints);
        jPanel.add(jLabel);
    }
}
