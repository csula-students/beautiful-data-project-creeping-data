package edu.csula.datascience.acquisition.models;

public class TestMeth
{
    static int i = 1;
    public static void main(String args[])
    {
        System.out.print(i+ "");
        //testmeth.m(i);
        System.out.println(i);
    }
    public static void m(int i)
    {
        i += 2;
    }
}