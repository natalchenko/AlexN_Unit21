import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.*;

@SuppressWarnings("serial")
public class TestMultiThreading extends JFrame implements MouseListener{
		
		JTextArea jtaIntNewsArea   ;
		JTextArea jtaLocalNewsArea ;
		JLabel    jlbIntNewsProgressLabel   ;
		JLabel    jlbLocalNewsProgressLabel ;
	
		public TestMultiThreading()
		{
			
			JPanel windowContent = new JPanel( new GridBagLayout() ) ;
			GridBagConstraints cst = new GridBagConstraints() ;
			
			JLabel jlbIntNewsHeadLabel   = new JLabel( "International news:" ) ;
			JLabel jlbLocalNewsHeadLabel = new JLabel( "Local news:"         ) ;
			
			jtaIntNewsArea = new JTextArea( 10, 20 ) ;
			jtaIntNewsArea.setEditable( false ) ;

			JScrollPane jspIntNewsArea = new JScrollPane( jtaIntNewsArea );
			jspIntNewsArea.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ) ;
			jspIntNewsArea.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS  ) ;
		     
			jtaLocalNewsArea = new JTextArea( 10, 20 ) ;
			jtaLocalNewsArea.setEditable( false ) ;
			JScrollPane jspLocalNewsArea = new JScrollPane( jtaLocalNewsArea );
			jspLocalNewsArea.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ) ;
			jspLocalNewsArea.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ) ;
			
			jlbIntNewsProgressLabel   = new JLabel( " " ) ;
			jlbLocalNewsProgressLabel = new JLabel( " " ) ;
		        
			JButton goButton = new JButton("Read news");
			goButton.addMouseListener(this);
			JButton clearButton = new JButton("Reset");
			clearButton.addMouseListener(this);
			
			
			
		    cst.fill = GridBagConstraints.HORIZONTAL;
		    cst.gridx = 0;
		    cst.gridy = 0;			
			windowContent.add( jlbIntNewsHeadLabel, cst   );
			
		    cst.fill = GridBagConstraints.HORIZONTAL;
		    cst.gridx = 1;
		    cst.gridy = 0;			
			windowContent.add( jlbLocalNewsHeadLabel, cst );
						
			
			
		    cst.fill = GridBagConstraints.HORIZONTAL;
		    cst.gridx = 0;
		    cst.gridy = 1;			
			windowContent.add( jspIntNewsArea, cst   ) ;

		    cst.fill = GridBagConstraints.HORIZONTAL;
		    cst.gridx = 1;
		    cst.gridy = 1;
			windowContent.add( jspLocalNewsArea, cst ) ;				

			
			
		    cst.fill = GridBagConstraints.HORIZONTAL;
		    cst.gridx = 0;
		    cst.gridy = 2;			
			windowContent.add( jlbIntNewsProgressLabel, cst   );
			
		    cst.fill = GridBagConstraints.HORIZONTAL;
		    cst.gridx = 1;
		    cst.gridy = 2;			
			windowContent.add( jlbLocalNewsProgressLabel, cst );
			
			
		    cst.fill = GridBagConstraints.HORIZONTAL;
		    cst.gridx = 0;
		    cst.gridy = 3;			
			windowContent.add( goButton, cst    ) ;

			cst.fill = GridBagConstraints.HORIZONTAL;
		    cst.gridx = 1;
		    cst.gridy = 3;			
			windowContent.add( clearButton, cst ) ;
			
			
			
			JFrame frame = new JFrame( "Multithread News Reader" ) ;
			frame.setContentPane( windowContent ) ;
			frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			frame.setSize( 500,300 );
			frame.setVisible(true);		
			frame.setResizable(false);
			
			 
		}

		public static void main(String[] args) throws InterruptedException, ExecutionException {
			new TestMultiThreading() ;
		}
		
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) {
				JButton curButton = (JButton)e.getComponent() ; 
				if ( curButton.getText() == "Reset" ) {
					jlbIntNewsProgressLabel.setText( " " ) ;
					jlbLocalNewsProgressLabel.setText( " " ) ;
					jtaIntNewsArea.setText( "" );
					jtaLocalNewsArea.setText( "" );
				} else {
					List<Future<String>> futures = new ArrayList<Future<String>>() ;
					final ExecutorService service = Executors.newFixedThreadPool(2) ; // pool of 2 threads
					
					try { 
						futures.add( (Future<String>) service.submit( new MarketNewsWorker( "Some international news ", jtaIntNewsArea,   jlbIntNewsProgressLabel   ) ) ); 
						futures.add( (Future<String>) service.submit( new MarketNewsWorker( "Some local news ", jtaLocalNewsArea, jlbLocalNewsProgressLabel ) ) ); 
						} finally { 
						service.shutdown(); 
						} 
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

		}		