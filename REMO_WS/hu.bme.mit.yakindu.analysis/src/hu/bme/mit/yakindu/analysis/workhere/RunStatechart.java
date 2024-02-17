package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;

// import hu.bme.mit.yakindu.analysis.RuntimeService;
// import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
import java.io.IOException;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		s.runCycle();
		print(s);
		s.raiseStart();
		s.runCycle();
		
		
		try {
			boolean b = true;
			while(b) {
				BufferedReader reader = new BufferedReader(
					new InputStreamReader(System.in));
				String name = reader.readLine();
				switch(name) {
					case "white":
						s.raiseWhite();
						break;
					case "black":
						s.raiseBlack();
						break;
					case "start":
						s.raiseStart();
						break;
					case "exit":
						b=false;
						break;
				}
				if(b) {
					print(s);
				}
			}  
				 
		}	
	    catch(IOException ie) {
	    	
	    }
		System.exit(0);
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
}
