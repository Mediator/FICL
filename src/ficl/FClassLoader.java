package ficl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;


public class FClassLoader extends ClassLoader {
	
	public String classPath;
	public String classLocation;
	
    public FClassLoader(){
        super(FClassLoader.class.getClassLoader());
        classLocation = "";
    }
    
    public FClassLoader(String classLocation)
    {
    	this();
    	this.classLocation = classLocation;
    }
  
    public Class inLoadClass(String className, byte[] classData) throws ClassNotFoundException
    {
    	Class result=null;
        
        try{
            return findSystemClass(className);
        }
        catch(Exception e)
        {
        	//NOP
        }
        try
        {
           result = defineClass(className,classData,0,classData.length,null);
           return result;
        }catch(Exception e){
        	//todo throw a class load exception
        	System.err.println("Class load failure");
        	e.printStackTrace();
        	//throw new Exception("Class load failure");
            return null;
        } 
    }
    
    public Class loadClass(String className) throws ClassNotFoundException {
         return findClass(className);
    }
 
    
    public Class findClass(String className){
        byte classByte[];
        Class result=null;
        
        try{
            return findSystemClass(className);
        }
        catch(Exception e)
        {
        	//NOP
        }
        try
        {
           String classPath = ((String)FClassLoader.getSystemResource(classLocation + "/" + className.replace('.',File.separatorChar)+".class").getFile()).substring(1);
           classByte = loadClassDataFromFile(classPath);
           result = defineClass(className,classByte,0,classByte.length,null);
           return result;
        }catch(Exception e){
        	//todo throw a class load exception
        	System.err.println("Class load failure");
        	e.printStackTrace();
        	//throw new Exception("Class load failure");
            return null;
        } 
    }
 
    private byte[] loadClassDataFromFile(String className) throws IOException{
    	className = className.replace("%20", " ");
        File f ;
        f = new File(className);
        int size = (int)f.length();
        byte buff[] = new byte[size];
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        dis.readFully(buff);
        dis.close();
        return buff;
    }

} 


