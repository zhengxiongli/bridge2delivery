package com.thoughtworks.bridge2delivery.client;

import javax.swing.JFrame;
import java.awt.Button;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DiDaClient extends JFrame {
    private static final int X = 360;
    private static final int Y = 360;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 450;
    private JFrame frame;

    public DiDaClient() throws HeadlessException {
        frame = new JFrame("嘀哒验收小工具");
        Button begin = new Button("点击这里，开始文档转换吧");
        frame.add(begin);
        frame.setBounds(X, Y, WIDTH, HEIGHT);
        //居中
        frame.setLocationRelativeTo(null);
        begin.addActionListener(ae -> {
            try {
                String url = "http://localhost:8080";
                java.net.URI uri = java.net.URI.create(url);
                // 获取当前系统桌面扩展
                java.awt.Desktop dp = java.awt.Desktop.getDesktop();
                // 判断系统桌面是否支持要执行的功能
                if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    dp.browse(uri);
                    // 获取系统默认浏览器打开链接
                }
            } catch (Exception e) {
                System.exit(0);
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public void start() {
        frame.setVisible(true);
    }
}
