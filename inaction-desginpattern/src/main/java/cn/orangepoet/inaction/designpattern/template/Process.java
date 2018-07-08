package cn.orangepoet.inaction.designpattern.template;

/**
 * Template method designpattern is a behavior design designpattern to defines a program
 * skeleton of algorithm, called template method, defers some steps into
 * subclasses.
 * 
 * It lets one redefine certain steps of an algorithm without changing the
 * algorithm's structure.
 * 
 * @author orange
 *
 */
public class Process {
    public static void main(String[] args) {
        ConreteClass cc = new ConreteClass();
        cc.templateMethod();
    }
}
