# playerGUI
A simple java GUI game


##


## Swing is not thread-safe, meaning that directly updating the GUI from threads other than the EDT could lead to unpredictable behavior or even deadlocks.



## issue1: icon size unmatch image size
## solution1: adjust image size
    // 假设你想要的新尺寸是宽100px，高100px
    Image image = icon.getImage(); // 从ImageIcon获取Image
    Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // 调整图像尺寸
    icon = new ImageIcon(newimg);  // 创建一个新的ImageIcon


## SwingUtilities.invokeLater(() -> {Runnable doRun});
## explanation
    ensure the creation and showing of a frame occurs on the Event Dispatch Thread (EDT). 
    EDT: thread safety and avoiding potential issues with the GUI.
