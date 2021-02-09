
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A Greep is an alien creature that likes to collect tomatoes.
 * 
 * @author Sean Sullivan
 * @version 0.1
 */
public class Greep extends Creature
{
    // Remember: you cannot extend the Greep's memory. So:
    // no additional fields (other than final fields) allowed in this class!
    
    /**
     * Default constructor for testing purposes.
     */
    public Greep()
    {
        this(null);
        
        
    }

    
    /**
     * Create a Greep with its home space ship.
     */
    public Greep(Ship ship)
    {
        super(ship);
        
    }
    

    
    
    
    
    
    
    
    
    protected void addedToWorld(World world){
        setRotation(0);
        turn(-60);
        setFlag(1, false);
        setFlag(2, false);
        setMemory(0);
    }
    
    int num = Greenfoot.getRandomNumber(100);
    
    
    int sum(double x, int y){
        return (int) x + y;
    }
    
    
   
    
    
    
    /**
     * Do what a greep's gotta do.
     */
    
    public void act()
    {
        
        
        super.act();   // do not delete! leave as first statement in act().
        /* have greeps roam around until the find a pile. If they are the
         * first greep there, they will set their flag to true and drop 
         * purple paint. They will be the loaders. 
         * 
         * If a greep stumbles upon a pile, and they are not the first ones 
         * there (they will be able to see the purple paint) they will
         * wait to be loaded. 
         * 
         * If a greep has a tomato, they will head home. 
         * 
         */
        
        
        
        
        
        
        
        
        
        
        //Idea, as leaving a pile spit paint, if another greep sees paint 
        //do a circle until finding a pile. 
    
        // YOUR CODE HERE
        
        //turn(1);
        loadTomato();
        
        //turn(1);
        if (carryingTomato()){ //head home
            if(atWorldEdge()){
                turn(60);
            }
            if (seePaint("red")) {
                setMemory(1);
                if(!getFlag(1)){
                    //turn(40);
                    setFlag(1, true);
                }
            }
            if (atWater()|| atWorldEdge()|| seePaint("red")){
                if (atWater()|| atWorldEdge()) {
                    spit("red");
                    turn(20);
                    
                }
                
                if(getMemory() %16 == 0){
                    turn(30);
                }
                setMemory(1);
                
            
                
            } else {
                
                if(getMemory() > 10){
                    turnHome();
                    
                }
                setMemory((getMemory() +1) %100);

            }
            if (atShip()){
                dropTomato();
                turn(180);
                setFlag(1, false);
                setFlag(2, false);
                setMemory(0);
            }
            move();
        } else {
            
            if (atWater()|| atWorldEdge()){
                turn(23);
                
                
                setFlag(1, false);
                
            } else if(atTomato()){
                loadTomato();
                
                if (!getFlag(1)) {
                    
                    if (getMemory()<15) {
                        setMemory(getMemory()+1);
                    } else {
                        setFlag(1, true);
                    }
                } else {
                    turn(30);
                    
                }
                
            } else {
                if (getMemory()>1) {
                    setFlag(1, true);
                }
                if (getFlag(1)){
                    turn(20);
                    //spit("purple");
                }
                
            }
            
             
            
            
            
        
        
        }
        move();
        loadTomato();
    }  
    
/*
 * 
 * 
 * 
 if (atTomato()){
                    return;
                }
            while(!(atTomato() || (getMemory() >= 105 && getFlag(2)))){
                turn(1);
                setMemory(getMemory()+1);
                if (getMemory() >= 255){
                    setMemory(0);
                    setFlag(2, true);
                }
                if (atTomato()){
                    return;
                }
            }
            setFlag(2, false);
            setMemory(0); 
 
 */


/* good
 * 
  loadTomato();
        if (!getFlag(2)){
            turnHome();
            setFlag(2, true);
            turn(20);
            
        }
        //turn(1);
        if (carryingTomato()){ //head home
            
           
            if (atWater()|| atWorldEdge()){
                setFlag(1, false);
                setMemory(0);
                turn(22);
                
            
                
            } else {
                
                if(getMemory() > 10){
                    turnHome();
                    if (getFlag(1)){
                        turn(30);
                    }
                }
                setMemory((getMemory() +1) %255);

            }
            if (atShip()){
                dropTomato();
                turn(180);
                setFlag(1, false);
            }
            if(getMemory() < 2){
                //turn(7);
            }
            move();
        } else {
            
            if (atWater()|| atWorldEdge()){
                turn(23);
                //spit("red");
                move();
                setFlag(1, false);
                
            } else if(atTomato()){
                loadTomato();
                if (!getFlag(1)) {
                    move();
                    if (getMemory()<6) {
                        setMemory(getMemory()+1);
                        move();
                    } else {
                        setFlag(1, true);
                    }
                }
            } else {
                move();
            }
            
            
        
        
        }
    loadTomato();
    }
 * 
 * 
 * 
 */









//another good one: 
/*
  
  
  
  
 //Idea, as leaving a pile spit paint, if another greep sees paint 
        //do a circle until finding a pile. 
    
        // YOUR CODE HERE
        loadTomato();
        if (!getFlag(2)){
            turnHome();
            setFlag(2, true);
            turn(20);
            
        }
        //turn(1);
        if (carryingTomato()){ //head home
            
           
            if (atWater()|| atWorldEdge()){
                setFlag(1, false);
                setMemory(0);
                turn(22);
                
            
                
            } else {
                
                if(getMemory() > 10){
                    turnHome();
                    if (getFlag(1)){
                        turn(30);
                    }
                }
                setMemory((getMemory() +1) %255);

            }
            if (atShip()){
                dropTomato();
                turn(180);
                setFlag(1, false);
                setMemory(0);
            }
            if(getMemory() < 2){
                //turn(7);
            }
            move();
        } else {
            
            if (atWater()|| atWorldEdge()){
                turn(23);
                //spit("red");
                move();
                setFlag(1, false);
                
            } else if(atTomato()){
                loadTomato();
                if (!getFlag(1)) {
                    move();
                    if (getMemory()<15) {
                        setMemory(getMemory()+1);
                        move();
                    } else {
                        setFlag(1, true);
                    }
                } else {
                    turn(23);
                    move();
                }
            } else {
                if (getMemory()>3) {
                    setFlag(1, true);
                }
                if (getFlag(1)){
                    turn(33);
                }
                move();
            }
            
            
        
        
        }
    loadTomato();
    } 
  
  
  
  
  
  
 
 */




    /**
     * This method specifies the name of the author (for display on the result board).
     */
    public static String getAuthorName()
    {
        return "Sean Sullivan"; //WRITE YOUR NAME HERE!
    }


    /**
     * This method specifies the image we want displayed at any time. (No need 
     * to change this for the competition.)
     */
    public String getCurrentImage()
    {
        if(carryingTomato())
            return "greep-with-food.png";
        else
            return "greep.png";
    }
}