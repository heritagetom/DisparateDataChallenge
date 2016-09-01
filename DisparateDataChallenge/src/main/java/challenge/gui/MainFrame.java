package challenge.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JMenuBar;

public class MainFrame {	
	//declare and initialize MainFrame gui components
	private JFrame frame;
	
	JLabel lblDatabase = new JLabel("Database:");
	JLabel lblDatalink = new JLabel("Datalink:");
	JLabel lblWaterQualServices = new JLabel("Web Services");
	
	JComboBox<String> comboBox_Database = new JComboBox<String>();
	JComboBox<String> comboBox_WMS= new JComboBox<String>();
	JComboBox<String> comboBox_NITF= new JComboBox<String>();
	JComboBox<String> comboBox_Imagery= new JComboBox<String>();
	JComboBox<String> comboBox_Raw = new JComboBox<String>();
	JComboBox<String> comboBox_Other= new JComboBox<String>();
	JComboBox<String> comboBox_Humanitarian= new JComboBox<String>();
	JComboBox<String> comboBox_WaterQualServices = new JComboBox<String>();
	private final JButton btnSelectFilters = new JButton("Select Filters");



	// Launch the application.

	public static void main(String[] args) {
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
		frame.getContentPane().setLayout(null);
		
		//comboBox action listeners 
		comboBox_Database.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				JComboBox<String> comboBox = (JComboBox) e.getSource();
				

                String selected = (String)comboBox.getSelectedItem();
				switch(selected)
				{
					case "Other":
	                {
	                	comboBox_WMS.setVisible(false);
	                	comboBox_NITF.setVisible(false);
	                	comboBox_Imagery.setVisible(false);
	                	comboBox_Raw.setVisible(false);
	                	comboBox_Humanitarian.setVisible(false);
	                	comboBox_Other.setVisible(true);
	                	comboBox_WaterQualServices.setVisible(true);
	                	break;
	                }
					case "WFS & WMS":
					{
	                	comboBox_WMS.setVisible(true);
	                	comboBox_NITF.setVisible(false);
	                	comboBox_Imagery.setVisible(false);
	                	comboBox_Raw.setVisible(false);
	                	comboBox_Humanitarian.setVisible(false);
	                	comboBox_Other.setVisible(false); 
	                	comboBox_WaterQualServices.setVisible(false);
	                	break;
					}
					case "NITF Data":
					{
	                	comboBox_WMS.setVisible(false);
	                	comboBox_NITF.setVisible(true);
	                	comboBox_Imagery.setVisible(false);
	                	comboBox_Raw.setVisible(false);
	                	comboBox_Humanitarian.setVisible(false);
	                	comboBox_Other.setVisible(false);  
	                	comboBox_WaterQualServices.setVisible(false);
	                	break;
					}
					
					case "Imagery":
					{		
	                	comboBox_WMS.setVisible(false);
	                	comboBox_NITF.setVisible(false);
	                	comboBox_Imagery.setVisible(true);
	                	comboBox_Raw.setVisible(false);
	                	comboBox_Humanitarian.setVisible(false);
	                	comboBox_Other.setVisible(false);   
	                	comboBox_WaterQualServices.setVisible(false);
	                	break;
					}
					case "Humanitarian Data":
					{
	                	comboBox_WMS.setVisible(false);
	                	comboBox_NITF.setVisible(false);
	                	comboBox_Imagery.setVisible(false);
	                	comboBox_Raw.setVisible(false);
	                	comboBox_Humanitarian.setVisible(true);
	                	comboBox_Other.setVisible(false);
	                	comboBox_WaterQualServices.setVisible(false);

	                	break;
					}
					
					case "Raw Data":
					{
	                	comboBox_WMS.setVisible(false);
	                	comboBox_NITF.setVisible(false);
	                	comboBox_Imagery.setVisible(false);
	                	comboBox_Raw.setVisible(true);
	                	comboBox_Humanitarian.setVisible(false);
	                	comboBox_Other.setVisible(false); 
	                	comboBox_WaterQualServices.setVisible(false);
	                	break;
					}
				}
			}
		});
		
		comboBox_Other.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JComboBox<String> comboBox = (JComboBox) e.getSource();
                String selected = (String)comboBox.getSelectedItem();
                switch(selected)
                {
	                case "Water Quality Data":
	                {	
	                	comboBox_WaterQualServices.setVisible(true);
	                	lblWaterQualServices.setVisible(true);	                	
	                	break;
	                }
                }
						
			}
		});	
		
		comboBox_WaterQualServices.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				btnSelectFilters.setEnabled(true);
			}
		});
			
		comboBox_Database.setModel(new DefaultComboBoxModel(new String[] {"WMS & WFS", "NITF Data", "Imagery", "Humanitarian Data", "Other", "Raw Data"}));
		comboBox_Database.setToolTipText("");
		comboBox_Database.setBounds(197, 52, 151, 27);
		frame.getContentPane().add(comboBox_Database);
		
		lblDatabase.setBounds(98, 56, 74, 16);
		frame.getContentPane().add(lblDatabase);
		
		lblDatalink.setBounds(98, 88, 74, 16);
		frame.getContentPane().add(lblDatalink);
		comboBox_WMS.setVisible(false);
		
		
		comboBox_WMS.setModel(new DefaultComboBoxModel(new String[] {"Nigeria Maps,Water Table", "Disaster Response- NEPAL", "Wildlife Trafficking", "Geonames- foreign", "Navy BlueMarble- low res (WMS)", "Navy BlueMarble- low res (WMTS)"}));
		
		comboBox_WMS.setBounds(197, 84, 151, 27);
		frame.getContentPane().add(comboBox_WMS);
		comboBox_NITF.setModel(new DefaultComboBoxModel(new String[] {"Sample NITF Data"}));
		comboBox_NITF.setVisible(false);
		
		comboBox_NITF.setBounds(197, 84, 151, 27);
		frame.getContentPane().add(comboBox_NITF);
		comboBox_Imagery.setModel(new DefaultComboBoxModel(new String[] {"FAA Airports", "LANSAT Data", "AsiaMaritime Transparency (Public service)", "Bathymetric, GeophysicalMaps(NOAA)"}));
		comboBox_Imagery.setVisible(false);
		
		comboBox_Imagery.setBounds(197, 84, 151, 27);
		frame.getContentPane().add(comboBox_Imagery);
		comboBox_Other.setVisible(false);
		
		comboBox_Other.setModel(new DefaultComboBoxModel(new String[] {"Water Quality Data", "Offline Map Service User Documentation", "Hazard Specific Data", "weater.mfc.nasa.gov/Goes/"}));
		comboBox_Other.setBounds(197, 84, 151, 27);
		frame.getContentPane().add(comboBox_Other);
		comboBox_Raw.setModel(new DefaultComboBoxModel(new String[] {"Practical Exercise", "Image File (Courtesy of Digital Globe)", "LiDar", "Phase 1", "Raw Imagery (Courtesy of Digital Globe)"}));
		comboBox_Raw.setVisible(false);
		
		comboBox_Raw.setBounds(197, 84, 151, 27);
		frame.getContentPane().add(comboBox_Raw);
		comboBox_Humanitarian.setModel(new DefaultComboBoxModel(new String[] {"West Africa Ebola Outbreak", "Data for World Food Program", "UN Humanitarian response", "Humanitarian Open Street Map", "Humanitarian Data Exchange", "U.S. Fire Data"}));
		comboBox_Humanitarian.setVisible(false);
		
		comboBox_Humanitarian.setBounds(197, 84, 151, 27);
		frame.getContentPane().add(comboBox_Humanitarian);
		lblWaterQualServices.setVisible(false);
		lblWaterQualServices.setText("Web Services:");
		lblWaterQualServices.setBounds(98, 116, 100, 16);
		frame.getContentPane().add(lblWaterQualServices);

		comboBox_WaterQualServices.setVisible(false);
		comboBox_WaterQualServices.setModel(new DefaultComboBoxModel(new String[] {"Instantaneous Values Web Service", "Site Service", "Daily Values Web Service", "Water Quality Web Services", "Groundwater Levels Web Service", "Statistics Web Service"}));
		comboBox_WaterQualServices.setToolTipText("");
		comboBox_WaterQualServices.setBounds(197, 112, 151, 27);
		frame.getContentPane().add(comboBox_WaterQualServices);
		btnSelectFilters.setEnabled(false);
		btnSelectFilters.setBounds(147, 147, 117, 29);
		
		frame.getContentPane().add(btnSelectFilters);
	}
}
