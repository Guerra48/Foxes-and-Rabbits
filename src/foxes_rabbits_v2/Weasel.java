package foxes_rabbits_v2;

import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, hunt, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Weasel extends Animal {
    // Characteristics shared by all Weasels (class variables).

    // The age at which a Weasel can start to breed.
    private static final int BREEDING_AGE = 2;
    // The age to which a Weasel can live.
    private static final int MAX_AGE = 30;
    // The likelihood of a Weasel breeding.
    private static final double BREEDING_PROBABILITY = 0.4;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a weasel can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 3;
    // Individual characteristics (instance fields).
    private int foodLevel;
    /**
     * Create a new weasel. A weasel may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Weasel(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else{
            setAge(0);
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the Weasel does most of the time - it runs 
     * around, and hunts. Sometimes it will breed or die of old age.
     * @param newWeasels A list to return newly born Weasel.
     */
    public void act(List<Animal> newWeasels) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newWeasels);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
     /**
      * @author Hamid Nazir
      * Called by Animal superclass when a birth happens.
      */
    @Override
    protected Animal createYoung(Field field, Location loc) {
        return new Weasel(false, field, loc);
    }

    /**
     * @author Donovan Guerra
     * Altered the incrementAge method to getMaxAge instead.
     * Returns max age of the weasel.
     */
    @Override
    protected int getMaxAge(){
        return MAX_AGE;
    }
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    private Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
        
 
       /** 
     * @author Donovan Guerra
     * Returns the breeding age of the rabbit.
     * @return The breeding age of the rabbit.
     */
    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;    
    }

    /**
     * @author Donovan Guerra
     * @return the breeding probability of the rabbit.
     * @return the max litter size of the rabbit.
     */
    @Override
    protected double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }
    @Override
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }

    
}

