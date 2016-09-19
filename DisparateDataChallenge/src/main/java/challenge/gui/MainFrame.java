package challenge.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import challenge.main.MainApplication;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Desktop;
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

	//labels
	JLabel lblDatabase = new JLabel("Database:");
	JLabel lblDatalink = new JLabel("Datalink:");
	JLabel lblDataSets = new JLabel("Data Sets:");

	//comboboxes
	JComboBox<String> comboBox_Database = new JComboBox<String>();
	JComboBox<String> comboBox_DataLink= new JComboBox<String>();
	JComboBox<String> comboBox_DataSet = new JComboBox<String>();

	//buttons
	JButton btn_AnalyzeData = new JButton("Analyze Data");
	JButton btn_AddDataSet = new JButton("Add Data File");


	//declare class variables
	final String LOCALHOST = "http://localhost:9200";
	String dataIndex, dataType, dataFile= null;
	String[] fileNames;
	StringBuilder path = new StringBuilder("");
	int fileIndex=0;

	//determines if kibana window is open
	boolean kibanaOpen = false;

	//getters
	public String getDataBaseName()
	{
		return dataIndex;
	}
	public String getDataLinkName()
	{
		return dataType;
	}
	public String getDataSetName()
	{
		return dataFile;
	}


	// Launch the application,start splash screen and delay main frame from 
	//starting for three seconds, closing the splash screen before doing so
	//instantiating the Main Frame object will call the main method, initialize

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
				lblDataSets.setVisible(true);
				comboBox_DataSet.setVisible(true);  
				comboBox_DataLink.setVisible(true);

				switch(selected)
				{
				case "Other":
				{
					comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"Water Quality Data", "Offline Map Service User Documentation", "Hazard Specific Data", "weater.mfc.nasa.gov/Goes/"}));
					comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {"Instantaneous Values Web Service", "Site Service", "Daily Values Web Service", "Water Quality Web Services", "Groundwater Levels Web Service", "Statistics Web Service"}));
					comboBox_DataSet.setVisible(true);            	

					break;
				}
				case "WMS & WFS":
				{
					comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"Nigeria Maps,Water Table", "Disaster Response- NEPAL", "Wildlife Trafficking", "Geonames- foreign", "Navy BlueMarble- low res (WMS)", "Navy BlueMarble- low res (WMTS)"})); 
					comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {""}));

					break;
				}
				case "NITF Data":
				{
					comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"Sample NITF Data"})); 
					comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {""}));
					break;
				}

				case "Imagery":
				{		
					comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"FAA Airports", "LANSAT Data", "AsiaMaritime Transparency (Public service)", "Bathymetric, GeophysicalMaps(NOAA)"}));  
					comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {""}));
					break;
				}
				case "Humanitarian Data":
				{
					comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"West Africa Ebola Outbreak", "Data for World Food Program", "UN Humanitarian response", "Humanitarian Open Street Map", "Humanitarian Data Exchange", "U.S. Fire Data"}));
					comboBox_DataSet.setVisible(true);            	
					break;
				}

				case "Raw Data":
				{
					comboBox_DataLink.setModel(new DefaultComboBoxModel(new String[] {"Practical Exercise", "Image File (Courtesy of Digital Globe)", "LiDar", "Phase 1", "Raw Imagery (Courtesy of Digital Globe)"}));
					comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {}));
					break;
				}
				}
				if(comboBox_DataSet.getItemCount()>0)
				{
					comboBox_DataSet.setVisible(true);
					btn_AnalyzeData.setEnabled(true);
					lblDataSets.setVisible(true);
				}
				else
				{
					comboBox_DataSet.setVisible(false);
					btn_AnalyzeData.setEnabled(false);
				}

			}
		});

		//Similar to the Database action listener, however this time, in the Data Link listener we populate the Data Sets combo box based on the 
		//Data Link selection
		//Default case refers to any case we did not populate yet
		comboBox_DataLink.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				FileNameLoader loader;
				JComboBox<String> comboBox = (JComboBox) e.getSource();
				String selected = (String)comboBox.getSelectedItem();

				dataIndex= comboBox_Database.getSelectedItem().toString().replaceAll("\\s+","");
				dataType = comboBox_DataLink.getSelectedItem().toString().replaceAll("\\s+","");
				path.append("src/main/resources/"+ dataIndex + "/" + dataType+"/");
				System.out.println(path.toString());


				File[] files;
				
				
				

				switch(selected)
				{
				case "Water Quality Data":
				{	
					comboBox_DataSet.setVisible(true);
					comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {"Instantaneous Values Web Service", "Site Service", "Daily Values Web Service", "Water Quality Web Services", "Groundwater Levels Web Service", "Statistics Web Service"}));
					break;
				}
				case "West Africa Ebola Outbreak":
				{
					loader = new FileNameLoader(path.toString());
					files = loader.getNames();
					fileNames = new String[files.length-1];
					int index =0;
					for(File f:files)
					{
						if(f.getName().startsWith("."))
							continue;
						else
						{
							fileNames[index]=f.getName().toString();
							index++;
						}

					}
					//populateComboBox;
					comboBox_DataSet.setModel(new DefaultComboBoxModel(fileNames));
					comboBox_DataSet.setVisible(true);
					break;
				}
				default:
				{
					comboBox_DataSet.setVisible(false);
					comboBox_DataSet.setModel(new DefaultComboBoxModel(new String[] {}));
					break;

				}
				}
				if(comboBox_DataSet.getItemCount()>0)
				{
					comboBox_DataSet.setVisible(true);
					btn_AnalyzeData.setEnabled(true);
				}
				else
				{
					comboBox_DataSet.setVisible(false);
					btn_AnalyzeData.setEnabled(false);
				}


			}
		});
		btn_AnalyzeData.setAutoscrolls(true);
		btn_AnalyzeData.setEnabled(false);

		btn_AnalyzeData.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{

				if(comboBox_DataSet.getItemCount() ==0)
				{
					JOptionPane.showMessageDialog(null, "Data search is not complete.", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					dataIndex = comboBox_Database.getSelectedItem().toString().replaceAll("\\s+","").toLowerCase();
					dataType = comboBox_DataLink.getSelectedItem().toString().replaceAll("\\s+","").toLowerCase();
					dataFile = comboBox_DataSet.getSelectedItem().toString();


					//Run main application
					MainApplication main = new MainApplication();
					path.append(dataFile);		
					comboBox_Database.setEnabled(false);
					comboBox_DataLink.setEnabled(false);
					comboBox_DataSet.setEnabled(false);
					btn_AnalyzeData.setVisible(false);
					btn_AnalyzeData.setEnabled(false);
					btn_AddDataSet.setVisible(true);
					btn_AddDataSet.setEnabled(true);

					if(!kibanaOpen && Desktop.isDesktopSupported()){
						Desktop desktop = Desktop.getDesktop();
						try {
							desktop.browse(new URI("http://localhost:5601/app/kibana"));
							kibanaOpen = true;
						} catch (IOException | URISyntaxException ioex) {
							// TODO Auto-generated catch block
							ioex.printStackTrace();
						}
					}
					main.run(dataIndex, dataType,path.toString());

					//FilterSelection.show(dataBase, dataLink, dataSet);
				}
			}
		});
		btn_AddDataSet.setVisible(false);
		btn_AddDataSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_AddDataSet.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						btn_AddDataSet.setVisible(false);
						btn_AddDataSet.setEnabled(false);
						btn_AnalyzeData.setVisible(true);
						btn_AnalyzeData.setEnabled(true);
						comboBox_Database.setEnabled(true);
						comboBox_DataLink.setEnabled(true);
						comboBox_DataSet.setEnabled(true);
					}
				});
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
		btn_AnalyzeData.setBounds(151, 144, 124, 29);
		frame.getContentPane().add(btn_AnalyzeData);
		btn_AddDataSet.setBounds(151, 144, 124, 29);
		frame.getContentPane().add(btn_AddDataSet);

	}
	private void checkComboBox() {
		// TODO Auto-generated method stub
		
	}
}
