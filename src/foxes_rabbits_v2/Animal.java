package foxes_rabbits_v2;

import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;

    /**
     * @author Donovan Guerra
     */
    // The age of the animal.
    private int age;
    //The breeding probability of the animal.
    abstract protected double getBreedingProbability();
    // The max litter size of the animal.
    abstract protected int getMaxLitterSize();
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();


    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param age The age of the animal.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        setAge(0);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * @author Donovan Guerra
     * Gets the age of the animal.
     * @return The age of the animal.
     */
    protected int getAge()
    {
        return age;
    }
    /**
     * @author Donovan Guerra
     * Sets the age of the animal.
     * @param age The age of the animal.
     */
    protected void setAge(int age)
    {
        this.age = age;
    }

    /**
     * A animal can breed if it has reached the breeding age.
     * @return true if the animal can breed.
     * @author Donovan Guerra
     */
    public boolean canBreed()
    {
        return age > getBreedingAge();
    }

    /**
     * @author Donovan Guerra
     * Returns the breeding age of the animal.
     * @return The breeding age of the animal.
     */
    protected abstract int getBreedingAge();

    /**
     * @author Donovan Guerra
     * Increase the age of the animal.
     */
    protected void incrementAge(){
        setAge(getAge() + 1);
        if(getAge() > getMaxAge()) {
            setDead();
        }
    }

    /**
     * @author Donovan Guerra
     * Added abstract max age method for the animal superclass.
     * @return The max age of the animal.
     */
    protected abstract int getMaxAge();
    protected abstract Animal createYoung(Field field, Location loc);

    /**
     * @author Donovan Guerra
     * Shifted the breed method from the fox and rabbit classes to the animal superclass.
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     *  @author Hamid Nazir
     * giveBirth method moved to Animal
     */
    protected void giveBirth(List<Animal> newAnimals)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = createYoung(field, loc);
            newAnimals.add(young);
        }
    }
}

    