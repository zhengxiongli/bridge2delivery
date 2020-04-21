package com.thoughtworks.bridge2delivery.client;

import com.thoughtworks.bridge2delivery.contents.Messages;
import com.thoughtworks.bridge2delivery.exception.CustomException;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.HeadlessException;
import java.awt.Button;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import com.apple.eawt.Application;

public class DiDaClient extends JFrame {
    private static final String ICON_CLASSPATH = "static/assets/di-da.png";
    private static final int X = 360;
    private static final int Y = 360;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 450;

    public DiDaClient() throws HeadlessException {
        this.setTitle("嘀哒验收小工具（V2.0.0）");
        Button begin = new Button("点击这里，开始文档转换吧");
        this.add(begin);
        this.setBounds(X, Y, WIDTH, HEIGHT);
        //居中
        this.setLocationRelativeTo(null);
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

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        BufferedImage bufferedImage = getBufferedImage();
        this.setIconImage(bufferedImage);
        try {
            if (System.getProperty("os.name").startsWith("Mac OS X")) {
                Application application = Application.getApplication();
                application.setDockIconImage(bufferedImage);
                application.removeAboutMenuItem();
                application.removePreferencesMenuItem();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setVisible(true);
    }

    private BufferedImage getBufferedImage() {
        try {
            ClassPathResource resource = new ClassPathResource(ICON_CLASSPATH);
            try (InputStream inputStream = resource.getInputStream()) {
                return ImageIO.read(inputStream);
            }
        } catch (IOException e) {
            throw new CustomException(Messages.TEMPLATE_FILE_NOT_FOUND);
        }
    }
}
