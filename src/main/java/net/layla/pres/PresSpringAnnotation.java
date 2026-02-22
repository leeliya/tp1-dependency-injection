package net.layla.pres;

import net.layla.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresSpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("net.layla");
        IMetier metier = (IMetier) context.getBean(IMetier.class);
        System.out.println("RES="+metier.calcul());
    }
}
