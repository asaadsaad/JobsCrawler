package UI2;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.beans.*;
import java.util.Random;

public class test extends JFrame implements ActionListener, 
					PropertyChangeListener  {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	JButton startButton;
	JProgressBar progressBar;
	JTextArea taskOutput;
	JComboBox comboBox;
	Task task;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Website:");
		lblNewLabel.setBounds(29, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };	
		comboBox = new JComboBox(petStrings);		
		comboBox.setBounds(104, 8, 117, 20);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Position:");
		lblNewLabel_1.setBounds(29, 45, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("City:");
		lblNewLabel_2.setBounds(29, 80, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(102, 42, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(112, 77, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		startButton = new JButton("Start");
		startButton.setActionCommand("start");
	    startButton.addActionListener(this);
		startButton.setBounds(236, 76, 89, 23);
		contentPane.add(startButton);
		
		progressBar = new JProgressBar();
		progressBar.setValue(0);
	    progressBar.setStringPainted(true);
		progressBar.setBounds(21, 130, 304, 30);
		contentPane.add(progressBar);
		
		taskOutput = new JTextArea();
		taskOutput.setBounds(29, 177, 296, 61);
		contentPane.add(taskOutput);
	}
	
	 /**
     * Invoked when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
        startButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            taskOutput.append(String.format(
                    "Completed %d%% of task.\n", task.getProgress()));
        } 
    }
    
    class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException ignore) {}
                //Make random progress.
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            taskOutput.append("Done!\n");
        }
    }
}
