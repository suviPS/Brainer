package com.httppsuvi.temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {

    private Random rand;
    private int a1, a2, optimalNumber = 0, tempInt, result;
    private ArrayList<Integer> arrayList;
    private String problem;

    int chooseOperator;

    public Game(){
        rand = new Random();
        arrayList = new ArrayList<>();
    }

    public void playRound(){

        this.reset();

        a1 = rand.nextInt(120) + 11;
        a2 = rand.nextInt(70) + 12;

        //Choose operator and set operation
        //operator is randomly choosen by rand.nextInt(numberOfOperations)
        chooseOperator = (rand.nextInt(5)) ;
        switch (chooseOperator) {
            case 0:
                //+
                result = a1 + a2;
                problem=a1+" + "+a2;
                optimalNumber = 200;
                break;
            case 1:
                //-
                if (a2 > a1) {
                    tempInt = a1;
                    a1 = a2;
                    a2 = tempInt;
                }
                result = a1 - a2;
                problem =a1+" - "+a2;
                optimalNumber = 90;
                break;
            case 2:
                //*
                result = a1 * a2;
                problem = a1+" * "+a2;
                optimalNumber = 2700;
                break;

            case 3:
                // '/'
                a2=rand.nextInt(20)+11;
                a1*=a2;
                result=a1/a2;
                problem=a1+" / "+a2;
                optimalNumber=120;

                break;

            case 4:
                //pow()
                a2=rand.nextInt(3)+2;
                result= (int) Math.pow(a1,a2);
                problem=a1+" ^ "+a2;
                optimalNumber=result+200;
                break;

            default:
                //won't happend :P
                result=-1;
                problem="Failed";
                optimalNumber=-1;
                break;
        }


        //prepare answears
        arrayList.add(result);
        for (int i = 0; i < 3; i++) {
            arrayList.add(rand.nextInt(optimalNumber) + 9);
        }
        Collections.shuffle(arrayList);

    }

    private void reset(){

//        result=-1;
//        problem="Failed";
//        optimalNumber=-1;
        arrayList.clear();

    }

    public String getProblem() {
        return problem;
    }

    public int getResult() {
        return result;
    }

    public ArrayList<Integer> getArrayList() {
        return arrayList;
    }
}
