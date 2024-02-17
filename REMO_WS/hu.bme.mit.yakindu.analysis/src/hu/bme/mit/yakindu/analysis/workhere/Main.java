package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;

import hu.bme.mit.model2gml.Model2GML;
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
		TreeIterator<EObject> iterator = s.eAllContents();
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
		}
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
