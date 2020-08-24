package com.vss.client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.vss.surveillace.VCBridge;
import com.vss.surveillace.VideoCapture;

public class Driver
{

   private JFrame frame;
   private JTextField textField;
   File feederDir = null;
   VCBridge vcBridge = null;

   /**
    * Launch the application.
    */
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
      {
         public void run()
         {
            try
            {
               Driver window = new Driver();
               window.frame.setVisible(true);
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the application.
    */
   public Driver()
   {
      initialize();
   }

   /**
    * Initialize the contents of the frame.
    */
   private void initialize()
   {

      frame = new JFrame("Video Surveillance");
      frame.setBounds(100, 100, 450, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setLayout(null);

      JLabel lblStartSurveillanceCamera = new JLabel("Start Surveillance Camera");
      lblStartSurveillanceCamera.setBounds(34, 84, 166, 14);

      frame.getContentPane().add(lblStartSurveillanceCamera);
      JLabel result = new JLabel("");
      result.setBounds(148, 160, 170, 14);
      frame.getContentPane().add(result);

      JButton btnStart = new JButton("START");
      JButton btnStop = new JButton("STOP");

      JFileChooser fc = new JFileChooser();
      fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnVal = fc.showDialog(frame, "Frames Feeding Directory");
      if (returnVal == JFileChooser.APPROVE_OPTION)
      {
         feederDir = fc.getSelectedFile();
      }

      btnStart.addActionListener(new ActionListener()
      {

		public void actionPerformed(ActionEvent arg0)
         {
            new VideoCapture(feederDir.getAbsolutePath());
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            vcBridge = new VCBridge(feederDir.getAbsolutePath());
            result.setText("SURVEILLANCE STARTED");
         }
      });
      btnStart.setBounds(56, 109, 89, 23);
      frame.getContentPane().add(btnStart);

      JLabel lblStopSurveillanceCamera = new JLabel("Stop Surveillance Camera");
      lblStopSurveillanceCamera.setBounds(242, 84, 155, 14);
      frame.getContentPane().add(lblStopSurveillanceCamera);

      btnStop.setEnabled(false);
      btnStop.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            VideoCapture.continueRunning = false;
            btnStop.setEnabled(false);
            //btnStart.setEnabled(true);
            result.setText("SURVEILLANCE STOPPED");
         }
      });
      btnStop.setBounds(263, 109, 89, 23);
      frame.getContentPane().add(btnStop);

      JLabel lblFeederLocation = new JLabel("Feeder Location");
      lblFeederLocation.setBounds(159, 11, 107, 14);
      frame.getContentPane().add(lblFeederLocation);

      textField = new JTextField();
      textField.setBounds(132, 30, 134, 20);
      frame.getContentPane().add(textField);
      textField.setColumns(10);
      textField.setEditable(false);
      textField.setText(feederDir.getAbsolutePath());

   }
}
