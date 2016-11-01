package org.crawler.cdi;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class CDIUtil {
	private static CDIUtil instance = new CDIUtil();
	 
    public static CDIUtil instance() {
        return instance;
    }
 
    private Weld weld;
    private WeldContainer weldContainer;
 
    private CDIUtil() {
        weld = new Weld();
        weldContainer = weld.initialize();
    }
    
    public WeldContainer getWeldContainer(){
    	return weldContainer;
    }
 
    public <T> T lookup(Class<T> aClazz) {
        return weldContainer.instance().select(aClazz).get();
    }
 
    public void injectAndConstruct(Object aInstance) {
        BeanManager theManager = weldContainer.getBeanManager();
 
        AnnotatedType<Object> theType = (AnnotatedType<Object>) theManager.createAnnotatedType(aInstance.getClass());
        InjectionTarget<Object> theTarget = theManager.createInjectionTarget(theType);
 
        CreationalContext<Object> cc = theManager.createCreationalContext(null);
 
        theTarget.inject(aInstance, cc);
        theTarget.postConstruct(aInstance);
    }
}
