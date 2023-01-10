package com.lmy.filter;


import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器模式
 * 使用不同的标准或结合多个标准来过滤一组对象
 * */
public class FilterDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void printPersons(List<Person> persons){
        for (Person person : persons) {
            System.out.println("Person : [ Name : " + person.getName()
                    +", Gender : " + person.getGender()
                    +", Marital Status : " + person.getMaritalStatus()
                    +" ]");
        }
    }

    public static void main(String[] args) {
        List<Person> persons = new ArrayList<Person>();

        persons.add(new Person("Robert","Male", "Single"));
        persons.add(new Person("John","Male", "Married"));
        persons.add(new Person("Laura","Female", "Married"));
        persons.add(new Person("Diana","Female", "Single"));
        persons.add(new Person("Mike","Male", "Single"));
        persons.add(new Person("Bobby","Male", "Single"));

        println("Males: ");
        IFilter male = new FilterMale();
        printPersons(male.filter(persons));
        println("Females: ");
        IFilter female = new FilterFemale();
        printPersons(female.filter(persons));
        println("Males and Females: ");
        IFilter maleAndFemale= new AndFilter(female, male);
        printPersons(maleAndFemale.filter(persons));
    }
}


/**
 * 过滤器接口
 * */
interface IFilter{
    public List<Person> filter(List<Person> persons);
}



/**
 * [Male]过滤器
 * */
class FilterMale implements IFilter{

    @Override
    public List<Person> filter(List<Person> persons) {
        List<Person> malePersons = new ArrayList<>();
        for (Person person : persons) {
            if(person.getGender().equalsIgnoreCase("MALE")){
                malePersons.add(person);
            }
        }
        return malePersons;
    }
}



/**
 * [Female]过滤器
 * */
class FilterFemale implements IFilter {

    @Override
    public List<Person> filter(List<Person> persons) {
        List<Person> femalePersons = new ArrayList<Person>();
        for (Person person : persons) {
            if(person.getGender().equalsIgnoreCase("FEMALE")){
                femalePersons.add(person);
            }
        }
        return femalePersons;
    }
}


/**
 * [and]过滤器
 * */
class AndFilter implements IFilter {
    private IFilter filter;
    private IFilter otherFilter;

    public AndFilter(IFilter filter, IFilter otherFilter){
        this.filter = filter;
        this.otherFilter = otherFilter;
    }

    @Override
    public List<Person> filter(List<Person> persons) {
        List<Person> femalePersons = filter.filter(persons);
        return otherFilter.filter(femalePersons);
    }
}
