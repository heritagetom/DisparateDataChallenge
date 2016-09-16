package challenge.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import challenge.gui.FilterSelection;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Font;

/* 
 * Tom Heritage
 * Booz | Allen | Hamilton
 * Aug. 31 2016
 * This class creates the GUI for sending a REST request to fetch data from
 * the NGA's repositories for the Disparate Data Challenge.
 * See https://www.challenge.gov/challenge/disparate-data-challenge/
 * for more information.
 */

public class MainFrame {	
	//declare and initialize MainFrame GUI components
	private JFrame frame;

	
	JLabel lblDatabase = new JLabel("Database:");
	JLabel lblDatalink = new JLabel("Datalink:");
	JLabel lblDataSets = new JLabel("Data Sets:");
	
	JComboBox<String> comboBox_Database = new JComboBox<String>();
	JComboBox<String> comboBox_DataLink= new JComboBox<String>();
	JComboBox<String> comboBox_DataSet = new JComboBox<String>();
	JButton btnSelectFilters = new JButton("Select Filters");


	
	
	//declare class variables
	String dataBase, dataLink, dataSet = null;
	
	//getters
	public String getDataBaseName()
	{
		return dataBase;
	}
	public String getDataLinkName()
	{
		return dataLink;
	}
	public String getDataSetName()
	{
		return dataSet;
	}

	
	// Launch the application.

	public static void main(String[] args) throws InterruptedException {
		SplashWindow w = new SplashWindow();
		TimeUnit.SECONDS.sleep(3);
		w.dispose();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	//Create the application.

	public MainFrame() 
	{
		initialize();
	}


	//Initialize the contents of the frame.
	 
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setTitle("NGA Database Search");
		comboBox_Database.setFont(new Font("DecoType Naskh", Font.BOLD, 13));
		

		
		//Main Database Action Listener
		//On first click, show Data Link and Data Set comboxboxes
		//Populate Data Link combobox box based on Database selection
		comboBox_Database.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				JComboBox<String> comboBox = (JComboBox) e.getSource();
                String selected = (String)comboBox.getSelectedItem();
                lblDatalink.setVisible(true);
            	comboBox_DataLink.setVisible(true);
				switch(selected)
				{
					case "Other":
	                {
	            		comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"Water Quality Data", "Offline Map Service User Documentation", "Hazard Specific Data", "weater.mfc.nasa.gov/Goes/"}));
	            		comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {"Instantaneous Values Web Service", "Site Service", "Daily Values Web Service", "Water Quality Web Services", "Groundwater Levels Web Service", "Statistics Web Service"}));
	                	break;
	                }
					case "WMS & WFS":
					{
						comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"Nigeria Maps,Water Table", "Disaster Response- NEPAL", "Wildlife Trafficking", "Geonames- foreign", "Navy BlueMarble- low res (WMS)", "Navy BlueMarble- low res (WMTS)"})); 
	            		comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {}));

	                	break;
					}
					case "NITF Data":
					{
						comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"Sample NITF Data"})); 
	            		comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {}));
	                	break;
					}
					
					case "Imagery":
					{		
						comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"FAA Airports", "LANSAT Data", "AsiaMaritime Transparency (Public service)", "Bathymetric, GeophysicalMaps(NOAA)"}));  
	            		comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {}));
	                	break;
					}
					case "Humanitarian Data":
					{
						comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"West Africa Ebola Outbreak", "Data for World Food Program", "UN Humanitarian response", "Humanitarian Open Street Map", "Humanitarian Data Exchange", "U.S. Fire Data"}));
	                	comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[]{"Ebola - West Africa - Ebola Treatment Centres, Isolation Wards Hospitals and...","Number of Ebola Cases and Deaths in Affected Countries","Liberia - Admin Level 2 Boundaries",
								"Guinea Capitale of Région, District and Sub District","Data for Ebola Recovery","Liberia Food Security 3W","Topline Ebola Outbreak Figures",
								"Funding Coverage of the Ebola Virus Outbreak Emergency","Liberia Education 3","Liberia WASH 3W","Financial tracking of private sector contributions Ebola 2014",
								"3W OCHA Guinea as of 16 June 2015","3W Ebola Sierra Leone","Ebola outbreaks before 2014","Sierra Leone NERC Ebola Care Facilities Master List",""}));
	                	break;
					}
					
					case "Raw Data":
					{
						comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"Practical Exercise", "Image File (Courtesy of Digital Globe)", "LiDar", "Phase 1", "Raw Imagery (Courtesy of Digital Globe)"}));
	            		comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {}));
	                	break;
					}
				}
                lblDataSets.setVisible(true);
				if(comboBox_DataSet.getItemCount()>0)
				{
                	comboBox_DataSet.setVisible(true);
                	btnSelectFilters.setEnabled(true);
				}
				else
				{
					comboBox_DataSet.setVisible(false);
                	btnSelectFilters.setEnabled(false);
				}
					
			}
		});
		comboBox_DataLink.setFont(new Font("DecoType Naskh", Font.BOLD, 13));
		
		//Similar to the Database action listener, however this time, in the Data Link listener we populate the Data Sets combo box based on the 
		//Data Link selection
		//Default case refers to any case we did not populate yet
		comboBox_DataLink.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JComboBox<String> comboBox = (JComboBox) e.getSource();
                String selected = (String)comboBox.getSelectedItem();

                switch(selected)
                {
	                case "Water Quality Data":
	                {	
	            		comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {"Instantaneous Values Web Service", "Site Service", "Daily Values Web Service", "Water Quality Web Services", "Groundwater Levels Web Service", "Statistics Web Service"}));
	                	break;
	                }
	                case "West Africa Ebola Outbreak":
	                {	
	            		comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[]{"Ebola - West Africa - Ebola Treatment Centres, Isolation Wards Hospitals and...","Number of Ebola Cases and Deaths in Affected Countries","Liberia - Admin Level 2 Boundaries",
	            																		"Guinea Capitale of Région, District and Sub District","Data for Ebola Recovery","Liberia Food Security 3W","Topline Ebola Outbreak Figures",
	            																		"Funding Coverage of the Ebola Virus Outbreak Emergency","Liberia Education 3","Liberia WASH 3W","Financial tracking of private sector contributions Ebola 2014",
	            																		"3W OCHA Guinea as of 16 June 2015","3W Ebola Sierra Leone","Ebola outbreaks before 2014","Sierra Leone NERC Ebola Care Facilities Master List",""}));
	                	break;
	                }
	                default:
	                {
	            		comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {}));
	            		break;

	                }
                }
                if(comboBox_DataSet.getItemCount()>0)
				{
                	comboBox_DataSet.setVisible(true);
                	btnSelectFilters.setEnabled(true);
				}
				else
				{
					comboBox_DataSet.setVisible(false);
                	btnSelectFilters.setEnabled(false);
				}

						
			}
		});
		btnSelectFilters.setEnabled(false);
		
		btnSelectFilters.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{

				if(comboBox_DataSet.getItemCount() ==0)
				{
					JOptionPane.showMessageDialog(null, "Data search is not complete.", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
				dataBase = comboBox_Database.getSelectedItem().toString();
				dataLink = comboBox_DataLink.getSelectedItem().toString();
				dataSet = comboBox_DataSet.getSelectedItem().toString();
				
				//FilterSelection.show(dataBase, dataLink, dataSet);
				}
			}
		});
		frame.getContentPane().setLayout(null);
		
		
		
		//DataBase combobox and label config
		comboBox_Database.setModel(new DefaultComboBoxModel(new String[] {"WMS & WFS", "NITF Data", "Imagery", "Humanitarian Data", "Other", "Raw Data"}));
		comboBox_Database.setToolTipText("");
		comboBox_Database.setBounds(197, 52, 151, 27);
		frame.getContentPane().add(comboBox_Database);
		lblDatabase.setBounds(98, 56, 74, 16);
		frame.getContentPane().add(lblDatabase);
		
		//DataLink combobox and label config
		lblDataSets.setVisible(false);
		comboBox_DataLink.setVisible(false);
		lblDatalink.setVisible(false);
		lblDatalink.setBounds(98, 88, 74, 16);
		frame.getContentPane().add(lblDatalink);
		comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"Nigeria Maps,Water Table", "Disaster Response- NEPAL", "Wildlife Trafficking", "Geonames- foreign", "Navy BlueMarble- low res (WMS)", "Navy BlueMarble- low res (WMTS)"}));		
		comboBox_DataLink.setBounds(197, 84, 151, 27);
		frame.getContentPane().add(comboBox_DataLink);

		
		//DataSets combobox and label config
		lblDataSets.setVisible(false);
		comboBox_DataSet.setFont(new Font("DecoType Naskh", Font.BOLD, 13));
		comboBox_DataSet.setVisible(false);
		lblDataSets.setText("Data Set:");
		lblDataSets.setBounds(98, 116, 100, 16);
		frame.getContentPane().add(lblDataSets);
		comboBox_DataSet.setToolTipText("");
		comboBox_DataSet.setBounds(197, 112, 151, 27);
		frame.getContentPane().add(comboBox_DataSet);
		btnSelectFilters.setBounds(141, 144, 124, 29);
		frame.getContentPane().add(btnSelectFilters);
		
	}
}
