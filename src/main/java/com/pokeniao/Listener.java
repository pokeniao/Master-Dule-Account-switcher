package com.pokeniao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.pokeniao.Operation.defaultListModel;

/**
 * ClassName:
 * Package:com.pokeniao
 * Description:
 * Autor:
 *
 * @Create:-21:19
 */
public class Listener {

    public void attionSelectListener(JButton openButton,JTextField userText){
        //打开按钮监听器
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                //设置只能选择文件夹
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //选择的类型
                int returnValue = fileChooser.showOpenDialog(null);
                //选择的类型是确认
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    userText.setText(filePath);
                }
            }
        });
    }
    public void attionConfirmListener(JLabel jLabel,JButton openButton,JTextField userText){
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation operation = new Operation();
                if(!operation.NORepetition(userText.getText()))
                {
                    operation.saveMainPath(userText.getText());
                    operation.setDefaultListModel(null);//刷新集合
                    //刷新当前账号
                    operation.showCurrent(jLabel);

                }

            }
        });
    }

    public void attionRenameListener(JButton openButton, JList jList, JLabel jLabel, GridBagLayout gridBagLayout)
    {
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue =(String) jList.getSelectedValue();
                JDialog jDialog = new JDialog();
                jDialog.setLayout(new GridBagLayout());
                jDialog.setTitle("重命名");
                jDialog.setSize(300, 100);
                jDialog.setLocationRelativeTo(null);

                Module module = new Module(gridBagLayout, jDialog);
                JTextField jTextField = module.TextBox(0, 0, "jDialog");
                JButton button = module.ButtonbyjDialog("确认", 0, 6, 8, null);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = jTextField.getText();
                      if(! jList.isSelectionEmpty() ) {
                            if (!name.equals("")) {
                                Operation operation = new Operation();
                                operation.Rename(selectedValue, name);
                                jDialog.dispose();
                                defaultListModel.setElementAt(name, defaultListModel.indexOf(selectedValue));
                                operation.showCurrent(jLabel);
                            }
                        }
                    }
                });
                jDialog.setVisible(true);
            }
        });
    }

    public void attionSelectListener(JButton openButton,JList jList,JLabel jLabel,JTextField jTextField)
    {
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) jList.getSelectedValue();
                Operation operation = new Operation();
                String pathname = operation.select(selectedValue);
                operation.showCurrent(jLabel);
                jTextField.setText(pathname);
            }
        });
    }

    public void  attionAddUserListener(JButton openButton)
    {
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation operation = new Operation();
                operation.searchFile();
            }
        });
    }
    public void  attionDeleteUserListener(JButton openButton,JList jList){
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectName = (String) jList.getSelectedValue();
                Operation operation = new Operation();
                operation.DeleteUser(selectName);
            }
        });

    }
}
