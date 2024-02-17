package hu.bme.mit.yakindu.analysis.workhere;

import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		EList<Event> events = s.getScopes().get(0).getEvents();
		EList<Property> variables =s.getScopes().get(0).getVariables();
		System.out.println("package hu.bme.mit.yakindu.analysis.workhere;\r\n" + 
				"\r\n" + 
				"import java.io.IOException;\r\n" + 
				"\r\n" + 
				"// import hu.bme.mit.yakindu.analysis.RuntimeService;\r\n" + 
				"// import hu.bme.mit.yakindu.analysis.TimerService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;\r\n" + 
				"import java.io.IOException;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.RuntimeService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.TimerService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;\r\n" + 
				"import java.io.BufferedReader;\r\n" + 
				"import java.io.IOException;\r\n" + 
				"import java.io.InputStreamReader;\r\n" + 
				"\r\n" + 
				"public class RunStatechart {\r\n" + 
				"	\r\n" + 
				"	public static void main(String[] args) throws IOException {\r\n" + 
				"		ExampleStatemachine s = new ExampleStatemachine();\r\n" + 
				"		s.setTimer(new TimerService());\r\n" + 
				"		RuntimeService.getInstance().registerStatemachine(s, 200);\r\n" + 
				"s.init();s.enter();\r\n" + 
				"				s.runCycle();\r\n" + 
				"				print(s);\r\n" + 
				"				s.raiseStart();\r\n" + 
				"				s.runCycle();"+
				"		try {\r\n" + 
				"			boolean b = true;\r\n" + 
				"			while(b) {\r\n" + 
				"				BufferedReader reader = new BufferedReader(\r\n" + 
				"					new InputStreamReader(System.in));\r\n" + 
				"				String name = reader.readLine();\r\n" + 
				"				switch(name) {");
				for(int i=0;i<events.size();i++) {
						System.out.println("				case \""+events.get(i).getName()+"\":\n					s.raise"+firstGreat(events.get(i).getName())+"();\r\n" + 
								"					break;\r\n" + 
								"					");
				}
		System.out.println("				case \"exit\":\r\n" + 
				"						b=false;\r\n" + 
				"						break;\r\n" + 
				"				}\r\n" + 
				"				if(b) {\r\n" + 
				"					print(s);\r\n" + 
				"				}\r\n" + 
				"			}  \r\n" + 
				"				 \r\n" + 
				"		}	\r\n" + 
				"	    catch(IOException ie) {\r\n" + 
				"	    	\r\n" + 
				"	    }\r\n" + 
				"		System.exit(0);\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	public static void print(IExampleStatemachine s) {");
		for(int i=0;i<variables.size();i++) {
			System.out.println("System.out.println(\""+variables.get(i).getName().charAt(0)+ "= \" + s.getSCInterface().get"+firstGreat(variables.get(i).getName())+"());");
		}
		System.out.println("}\r\n" + 
				"}\r\n" + 
				"");
		/*
		
		System.out.println("}");
		/*TreeIterator<EObject> iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				
				if(state.getName().isEmpty()) {
					int i=0;
					TreeIterator<EObject> iterator2 = s.eAllContents();
					boolean changed=true;
					while (changed) {
						changed=false;
						while (iterator2.hasNext()) {
							EObject content2 = iterator2.next();
							if(content2 instanceof State) {
								State state2 = (State) content2;
								String str="new state"+i;
								if(state2.getName().equals(str)) {
									i++;
									changed=true;
									break;
								}
								
							}
						}
					}
					
					state.setName("new state"+i);
					
					
					
				}
				System.out.println(state.getName());
				if(state.getOutgoingTransitions().isEmpty()) {
					System.out.print(state.getName());
					System.out.println(" is a trap");
				}
			}
			if(content instanceof Transition) {
				Transition t = (Transition) content;
				System.out.print(t.getSource().getName());
				System.out.print(" -> ");
				System.out.println(t.getTarget().getName());
				
			}
		}*/
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
	private static String firstGreat(String input) {
		return Character.toUpperCase(input.charAt(0)) + input.substring(1);
	}
}
