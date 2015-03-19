package UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.beans.*;
import java.util.Random;


public class test extends JFrame
									implements ActionListener, 
												PropertyChangeListener  {
    private JProgressBar progressBar;
    private JButton startButton;
    private JTextArea taskOutput;
	private JTextField textField;
	private JTextField textField_1;
	JLabel lblNewLabel;
	JLabel lblNewLabel_1;
	JComboBox comboBox;	
    private Task task;
    
	/**
	 * Create the panel.
	 */
	public test() {
		setLayout(null);		 String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };	
		comboBox = new JComboBox(petStrings);
		comboBox.setBounds(44, 32, 28, 20);
		add(comboBox);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 79, 46, 14);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(76, 76, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(172, 79, 46, 14);
		add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(250, 76, 86, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		progressBar = new JProgressBar();
		progressBar.setValue(0);
	    progressBar.setStringPainted(true);
		progressBar.setBounds(20, 114, 344, 26);
		add(progressBar);
		
		taskOutput = new JTextArea();
		taskOutput.setBounds(30, 151, 344, 138);
		add(taskOutput);
		
		startButton = new JButton("Search");
		startButton.setActionCommand("start");
	    startButton.addActionListener(this);
		startButton.setBounds(231, 31, 89, 23);
		add(startButton);

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
    
}
