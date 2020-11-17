package lab.irobot;

import javax.swing.*;

public class PopupSpeaker implements Speaker
{
    @Override
    public void msg(String msg)
    {
        JOptionPane.showConfirmDialog(null, msg);
    }
}
