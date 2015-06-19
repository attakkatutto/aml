/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.main;

import jade.wrapper.PlatformController;
import jade.wrapper.PlatformEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Custom JADE platform listener 
 * implements interface Listener
 * 
 * @author ddefalco
 */
public class JadeObserver 
implements PlatformController.Listener {
    
    public JadeObserver(JadeSubject subject){
       this.subject = subject;
    }

    JadeSubject subject;
    List<String> agents = Collections.synchronizedList(new ArrayList<String>());

                @Override
                public void deadAgent(PlatformEvent anEvent) {
                    // WORKS 
                    String name = anEvent.getAgentGUID();
                    System.out.println(" - "
                            + name
                            + " dead ");
                    agents.remove(name);
                    if (agents.isEmpty()) {
                        System.out.println(" - "
                                + " JADE end! ");
                        subject.halt();
                    }
                }

                @Override
                public void bornAgent(PlatformEvent anEvent) {
                    // WORKS
                    String name = anEvent.getAgentGUID();
                    System.out.println(" - "
                            + name
                            + " born ");
                    agents.add(name);
                }

                @Override
                public void startedPlatform(PlatformEvent pe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void suspendedPlatform(PlatformEvent pe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void resumedPlatform(PlatformEvent pe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void killedPlatform(PlatformEvent pe) {
                    System.out.println(" - "
                            + pe.getPlatformName()
                            + " killed ");
                }

    
}
