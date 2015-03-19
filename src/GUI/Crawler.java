package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.beans.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLDocument.Iterator;

import Report.PieChart;
import jobscrawler.CyberCodersParser;
import jobscrawler.DiceParser;
import jobscrawler.IParser;
import jobscrawler.JobVacancy;
import jobscrawler.ParserFactory;

public class Crawler extends JPanel implements ActionListener,
		PropertyChangeListener {

	private JProgressBar progressBar;
	private JButton startButton;
	private JEditorPane taskOutput;
	private JTextField txtPosition;
	private JTextField txtCity;
	JLabel lblNewLabel;
	JLabel lblNewLabel_1;
	JComboBox comboBox;
	private Task task;
	private JLabel lblWebsites;
	String[] siteStrings = { "CyberCoders.com", "Dice.com" };
	final String pMark = "<p style=\"margin-top: 0\">";

	class Task extends SwingWorker<Void, Void> {
		Map<String, Integer> locationDic = new HashMap<String, Integer>();
		Map<String, Integer> salaryDic = new HashMap<String, Integer>();
		Map<String, Integer> titalDic = new HashMap<String, Integer>();

		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			cleanStatistics();
			IParser parser = ParserFactory.getParser(siteStrings[comboBox
					.getSelectedIndex()]);
			java.util.List<JobVacancy> list = new ArrayList<JobVacancy>();

			String position = txtPosition.getText();
			String city = txtCity.getText();

			if (parser.Authenticate()) {
				list = parser.DoParse(position, city);
			}
			int total = list.size();
			int current = 0;
			int progress = 0;
			java.util.Iterator<JobVacancy> it = list.iterator();
			Random random = new Random();

			while (it.hasNext()) {
				JobVacancy job = it.next();
				current++;

				statistics(job);

				int now = current * 100 / total;
				if (now != progress)
					progress = now;
				// Sleep for up to one second.
				try {
					Thread.sleep(random.nextInt(1000));
				} catch (InterruptedException ignore) {
				}
				appendText(String
						.format("Applying <font color=\"green\">%s</font> in company <font color=\"blue\">%s</font> ",
								job.getTitle(), job.getCompany()));
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
			setCursor(null); // turn off the wait cursor
			PieChart pie = new PieChart();
			String path = pie.update(locationDic, salaryDic, titalDic);

			appendText("Done!\n");
			appendText("Please find report at <a href=\"" + path + "\">" + path
					+ "</a>");
		}

		private void cleanStatistics() {
			locationDic.clear();
			salaryDic.clear();
			titalDic.clear();

		}

		private void statistics(JobVacancy job) {
			dictAdd(locationDic, job.getLocation());
			dictAdd(salaryDic, getMaxSalary(job.getSalary()));
			dictAdd(titalDic, job.getTitle());
		}

		private String getMaxSalary(String salary) {
			return salary.substring(salary.indexOf("-") + 1, salary.length())
					.trim();
		}

		private void dictAdd(Map<String, Integer> m, String k) {
			if (m.containsKey(k)) {
				m.put(k, m.get(k) + 1);
			} else {
				m.put(k, 1);
			}
		}
	}

	public Crawler() {

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBounds(20, 43, 410, 33);
		// Create the demo's UI.
		startButton = new JButton("Start");
		startButton.setToolTipText("Search and Apply");
		startButton.setActionCommand("start");
		startButton.addActionListener(this);

		taskOutput = new JEditorPane();
		taskOutput.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					if (Desktop.isDesktopSupported()) {
						try {
							// Desktop.getDesktop().browse(e.getURL().toURI());
							Desktop.getDesktop().open(
									new File(e.getDescription()));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

			}
		});
		taskOutput.setContentType("text/html");
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);
		setLayout(null);

		lblNewLabel = new JLabel("Position:");
		panel.add(lblNewLabel);
		lblNewLabel.setBounds(10, 26, 46, 14);

		txtPosition = new JTextField("Java");
		panel.add(txtPosition);
		txtPosition.setBounds(56, 23, 200, 20);
		txtPosition.setColumns(10);

		lblNewLabel_1 = new JLabel("City:");
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setBounds(233, 26, 29, 14);

		txtCity = new JTextField("New York");
		panel.add(txtCity);
		txtCity.setBounds(272, 23, 86, 20);
		txtCity.setColumns(10);
		panel.add(startButton);

		add(panel);
		JScrollPane scrollPane = new JScrollPane(taskOutput);
		scrollPane.setBounds(20, 131, 410, 149);
		add(scrollPane);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(20, 87, 410, 33);
		add(progressBar);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		panel_1.setBounds(22, 11, 408, 33);
		add(panel_1);

		lblWebsites = new JLabel("Websites:");
		panel_1.add(lblWebsites);
		comboBox = new JComboBox(siteStrings);
		panel_1.add(comboBox);

	}

	/**
	 * Invoked when the user presses the start button.
	 */
	public void actionPerformed(ActionEvent evt) {
		startButton.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// Instances of javax.swing.SwingWorker are not reusuable, so
		// we create new instances as needed.
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
			// appendText(String.format(
			// "Completed %d%% of task.\n", task.getProgress()));
		}
	}

	private void appendText(String text) {
		String all = taskOutput.getText();
		String body = all.substring(all.indexOf(pMark) + pMark.length(),
				all.indexOf("</p>"));
		text = text + "<br>";
		body += text;
		all = "<html><head></head><body>" + pMark + body + "</p></body></html>";
		taskOutput.setText(all);

		taskOutput.setCaretPosition(taskOutput.getDocument().getLength());
	}

	/**
	 * Create the GUI and show it. As with all GUI code, this must run on the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Jobs Crawler");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new Crawler();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
