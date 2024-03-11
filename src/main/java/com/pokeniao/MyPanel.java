package com.pokeniao;

import javax.swing.*;
import java.awt.*;

import static com.pokeniao.Operation.defaultListModel;

/**
 * ClassName:
 * Package:com.pokeniao
 * Description:
 * Autor:
 *
 * @Create:-19:46
 */
public class MyPanel extends JPanel{
    Module module = null;
    Listener listener =null;

    Operation operation=null;
    public static int MaxY=20;
    public static int Current=10;
    GridBagLayout gridBagLayout;
    public MyPanel(GridBagLayout gridBagLayout){
        this.setLayout(gridBagLayout);
        module = new Module(gridBagLayout,this);
        listener=new Listener();
        operation = new Operation();
        this.gridBagLayout=gridBagLayout;
    }
    public void Draw(){
        int CurrentY=0;
        module.Text("Master Duel账号切换器",3,CurrentY++,20,6);
        module.Text("游戏主存档路径",1,CurrentY++,15,3);
        JTextField TextBoxMain = module.TextBox(1,CurrentY,"jPanel");
        operation.showMP_TextBox(TextBoxMain);//显示路径文件到TextBox
        JButton buttonSelect = module.Button("选择",6,CurrentY,8, setInsert(0,0,0,10));

        JButton buttonConfirm= module.Button("确认",8,CurrentY++,8,null);
        JLabel jLabel = module.Text("当前游戏账号为：", 1, CurrentY++, 15, 0);
        operation.showCurrent(jLabel);//显示当前账号
        operation.setDefaultListModel(null);//初始化账号集合
        JList jList = module.SelectBox(defaultListModel, 1, CurrentY);
        JButton buttonSelectGeme = module.Button("选择账号",6,CurrentY++,8, setInsert(10,0,0,0));
        JButton rename = module.Button("重命名", 6, CurrentY++, 8, setInsert(10, 0, 0, 0));
        JButton addUser = module.Button("添加账号", 6, CurrentY++, 8, setInsert(10, 0, 0, 0));
        JButton DeleteUser = module.Button("删除", 6, CurrentY, 8, setInsert(10, 0, 0, 0));
        module.bottom();

        listener.attionSelectListener(buttonSelect,TextBoxMain);
        listener.attionConfirmListener(jLabel,buttonConfirm,TextBoxMain);
        listener.attionSelectListener(buttonSelectGeme,jList,jLabel,TextBoxMain);
        listener.attionRenameListener(rename,jList,jLabel,gridBagLayout);
        listener.attionAddUserListener(addUser);
        listener.attionDeleteUserListener(DeleteUser,jList);
    }
    public int[] setInsert(int top, int left, int botton, int right){
        int[] ints = new int[4];
        ints[0]=top;
        ints[1]=left;
        ints[2]=botton;
        ints[3]=right;
        return ints;
    }


}
