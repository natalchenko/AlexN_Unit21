
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;


public class MarketNewsWorker extends SwingWorker<List<String>, String> {
	
	final static Integer newsRecordsCount = 1000 ;
	
	private String    threadName ; 
	private JTextArea newsArea   ;     // News area
	private JLabel    indicatorLabel ; // Progress indicator
	
	MarketNewsWorker( String name, JTextArea area, JLabel label ){
		super();
		threadName     = name ;
		newsArea       = area ;
		indicatorLabel = label ;
	}
	
	@Override
	public List<String> doInBackground() throws InterruptedException{
		
		List<String> outputList = new ArrayList<String>() ;
		
		// Imitate long reading from the file ...
		for ( int i = 1; i<=newsRecordsCount; i++) {
			outputList.add( threadName + String.valueOf(i) + "\n" ) ;
			publish( threadName + " loading: " + String.valueOf((i*100/newsRecordsCount)) + "%" ) ; 
			Thread.sleep(5); // to slow down the process of "reading" and visualize it
		}

		return outputList ; 
		}
		
	@Override
	protected void process(List<String> progressMessage){
			indicatorLabel.setText( progressMessage.get( progressMessage.size()-1 ) );
		}
		
	@Override 
	protected void done(){
		try {
			List<String> results = this.get();
			for (String res: results){
				newsArea.append( res ) ;
				indicatorLabel.setText(threadName + " - loaded!");
				}				
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}	

}
