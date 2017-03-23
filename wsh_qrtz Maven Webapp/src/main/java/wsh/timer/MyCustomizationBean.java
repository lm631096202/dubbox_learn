package wsh.timer;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;

@Component  
public class MyCustomizationBean implements EmbeddedServletContainerCustomizer  {  
    /** 
     * @param container 
     * @see org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer#customize(org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer) 
     */  
    @Override  
    public void customize(ConfigurableEmbeddedServletContainerFactory container) {  
        
         container.setPort(8080);  
    }
 
       
}  