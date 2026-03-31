package foxes_rabbits_v2;

import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    
    // Individual characteristics (instance fields).
    
    // The rabbit's age.
        //private int age;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        setAge(0);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    @Override
    public void act(List<Animal> newRabbits)
    {
        incrementAge();
        if(isAlive()) {
             giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * @author Donovan Guerra
     * Altered the incrementAge method to getMaxAge instead.
     * Returns max age of the rabbit.
     */
    @Override
    protected int getMaxAge(){
        return MAX_AGE;
    }
    /**
     * @author Hamid Nazir
     * Animal.giveBirth() will use this to add a new Rabbit of the proper type.
     */
    @Override protected Animal createYoung(Field field, Location loc) { 
        return new Rabbit(false, field, loc); }
        
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
     * @author Donovan Guerra and Hamid Nazir
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
