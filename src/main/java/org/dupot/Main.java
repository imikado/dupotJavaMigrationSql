package org.dupot;

public class Main {
    public static void main(String[] args) {


        System.out.println("Hello world!");

        Check myCheck = new Check();
        if( false == myCheck.doesTableExist()){
            log("must create missing table");
            myCheck.createTable();
        }else{
            log("table already exists");
        }
    }

    public  static void log(String text){
        System.out.println(text);
    }
}