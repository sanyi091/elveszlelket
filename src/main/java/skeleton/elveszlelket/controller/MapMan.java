package skeleton.elveszlelket.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import skeleton.elveszlelket.*;
import skeleton.elveszlelket.door.Door;
import skeleton.elveszlelket.door.OneWayDoor;
import skeleton.elveszlelket.door.TwoWayDoor;
import skeleton.elveszlelket.item.AirFreshener;
import skeleton.elveszlelket.item.Beer;
import skeleton.elveszlelket.item.Camember;
import skeleton.elveszlelket.item.FFP2Mask;
import skeleton.elveszlelket.item.Item;
import skeleton.elveszlelket.item.Logar;
import skeleton.elveszlelket.item.Rag;
import skeleton.elveszlelket.item.TVSZ;
import skeleton.elveszlelket.item.Transistor;

/**
 * A MapMan osztály felelős a játék pálya létrehozásáért és inicializálásáért,
 * valamint a szobák és ajtók közötti kapcsolatok létrehozásáért.
 */
public class MapMan {
    private int size;
    private float curse, gas, fals, item, door, oneway;
    private List<Teacher> teachers;
    private List<Student> students;
    private List<CleaningLady> cleaningLadies;
    private List<Room> map;
    private Random r;

    /**
     * Létrehoz egy új MapMan példányt a megadott paraméterekkel.
     *
     * @param size           A pálya mérete.
     * @param curse          Az átok valószínűsége.
     * @param gas            A gáz valószínűsége.
     * @param fals           A hamis tárgyak valószínűsége.
     * @param item           A tárgyak keletkézésének valószínűsége.
     * @param door           Az ajtók keletkézésének valószínűsége.
     * @param oneway         Az egyirányú ajtók keletkézésének valószínűsége.
     * @param teachers       A tanárok listája.
     * @param students       A diákok listája.
     * @param cleaningLadies A takarítónők listája.
     */
    public MapMan(int size, float curse, float gas, float fals, float item, float door, float oneway,
            List<Teacher> teachers, List<Student> students, List<CleaningLady> cleaningLadies) {
        this.teachers = teachers;
        this.cleaningLadies = cleaningLadies;
        this.students = students;
        this.size = size;
        this.curse = curse;
        this.gas = gas;
        this.fals = fals;
        this.item = item;
        this.door = door;
        this.oneway = oneway;
        r = new Random();
        map = new ArrayList<>();
    }

    /**
     * Inicializálja a pályát, létrehozza a szobákat, ajtókat és elhelyezi a
     * tárgyakat.
     *
     * @return A létrehozott szobák listája.
     */
    public List<Room> init() {
        if (size < 5)
            size = 5;
        if (curse < 0 && curse >= 1)
            curse = 0.1f;
        if (gas < 0 && gas >= 1)
            gas = 0.1f;
        if (fals < 0 && fals >= 1)
            fals = 0.1f;
        if (item < 0 && item >= 1)
            item = 0.5f;
        if (door < 0 && door >= 1)
            door = 0.1f;
        if (oneway < 0 && oneway >= 1)
            oneway = 0.1f;
        // Annyi szoba amekkora a size
        for (int i = 0; i < size; i++) {
            map.add(new Room());
        }

        // minden szobából egy ajtó egy random szobába
        for (Room room : map) {
            Door d = new TwoWayDoor();
            if (size > 1) {
                connectRandom(room, d);
            }
            // extra szobák súly szerint
            while (door > r.nextFloat()) {
                if (size > 1) {
                    System.out.println("EXTRA SZOBA HOZZÁADÁS");
                    connectRandom(room, randomDoorType());
                    if (room.getDoors().size() >= 12)
                        break;
                }
            }
        }

        // make map traversable
        Room startingRoom = map.get(0);
        for (Room room : map) {
            while (distance(startingRoom, room) < 0) {
                System.out.println("EXTRA BEJÁRHATÓSÁGI SZOBA HOZZÁADÁS");
                connectRandom(room, randomDoorType());
                if (room.getDoors().size() >= 12) {
                    map = new ArrayList<>();
                    System.out.println("PÁLYAGENERÁLÁS ELDOBVA!");
                    map = init();
                    return map;
                }
            }
        }

        // populate with items and calculate maxdist for later
        int maxdist = Integer.MIN_VALUE;
        for (Room room : map) {
            int dist = distance(startingRoom, room);
            if (dist > maxdist)
                maxdist = dist;
            while (item > r.nextFloat()) {
                System.out.println("EXTRA ITEM HOZZÁADÁS");
                Item item = getRandomItem();
                if (fals > r.nextFloat())
                    item.setFalse(true);
                room.addItem(item);
            }
            // apply curse
            if (curse > r.nextFloat() && room != startingRoom)
                room.setCursed(true);
            // apply gas
            if (gas > r.nextFloat() && room != startingRoom)
                room.setGas(true);
        }

        // add students
        for (Student student : students) {
            startingRoom.addHuman(student);
            student.setCurrentRoom(startingRoom);
        }

        // Add teachers
        for (Teacher teacher : teachers) {
            boolean added = false;
            while (!added) {
                System.out.println("TEACHER HOZZÁADÁS");
                Room rand = (size == 1) ? map.get(0) : getRandomRoom();
                if (distance(startingRoom, rand) >= (maxdist - 1)) {
                    rand.addHuman(teacher);
                    teacher.setCurrentRoom(rand);
                    added = true;
                }
            }
        }

        // Add cleaners
        for (CleaningLady cleaningLady : cleaningLadies) {
            boolean added = false;
            while (!added) {
                System.out.println("CLEANER HOZZÁADÁS");
                Room rand = (size == 1) ? map.get(0) : getRandomRoom();
                if (distance(startingRoom, rand) >= (maxdist - 2)) {
                    rand.addHuman(cleaningLady);
                    cleaningLady.setCurrentRoom(rand);
                    added = true;
                }
            }
        }

        // Add real logar
        for (Room room : map) {
            if (distance(startingRoom, room) == maxdist) {
                room.addItem(new Logar());
                break;
            }
        }
        return map;
    }

    /**
     * Véletlenszerűen kiválaszt egy tárgyat a megadott valószínűségekkel.
     *
     * @return Egy véletlenszerűen kiválasztott tárgy.
     */
    private Item getRandomItem() {
        Item item;
        float rand = r.nextFloat();
        if (rand < 0.2) {
            item = new Beer();
        } else if (rand < 0.3) {
            item = new AirFreshener();
        } else if (rand < 0.4) {
            item = new Camember();
        } else if (rand < 0.5) {
            item = new FFP2Mask();
        } else if (rand < 0.6) {
            item = new Logar();
            // igazi logart később rakjuk le
            item.setFalse(true);
        } else if (rand < 0.7) {
            item = new Rag();
        } else if (rand < 0.9) {
            item = new Transistor();
        } else {
            item = new TVSZ();
        }
        return item;
    }

    /**
     * Kiszámítja a megadott két szoba közötti távolságot BFS algoritmussal.
     *
     * @param start A kiindulási szoba.
     * @param end   A cél szoba.
     * @return A két szoba közötti távolság. Ha a két szoba közötti távolság 0,
     *         akkor ugyan azt a szobát adtunk kiindulási és cél szobának.
     *         Ha viszont nincs út, akkor -1-et ad vissza.
     */
    private int distance(Room start, Room end) {
        if (start.equals(end)) {
            return 0; // ugyan az a szoba
        }

        // BFS
        Queue<Room> queue = new LinkedList<>();
        Set<Room> visited = new HashSet<>();
        Map<Room, Integer> distances = new HashMap<>();

        queue.add(start);
        visited.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Room current = queue.poll();
            int currentDistance = distances.get(current);

            for (Door door : current.getDoors()) {
                Room neighbor = door.getDest(current);
                if (neighbor == current) { // ha ajtó egyirányú
                    break;
                }
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    distances.put(neighbor, currentDistance + 1);

                    if (neighbor.equals(end)) {
                        return distances.get(neighbor);
                    }
                }
            }
        }

        return -1; // No path found
    }

    /**
     * Véletlenszerűen kiválaszt egy szobát.
     *
     * @return Egy véletlenszerűen kiválasztott szoba.
     */
    private Room getRandomRoom() {
        return map.get(r.nextInt(size - 1));
    }

    /**
     * Véletlenszerűen kapcsol egy szobát egy másik szobához egy ajtóval.
     *
     * @param room Az egyik szoba.
     * @param door Az ajtó, amellyel a kapcsolatot létrehozzuk.
     */
    private void connectRandom(Room room, Door door) {
        int randindx = r.nextInt(size - 1);
        while (room == map.get(randindx)) {
            randindx = r.nextInt(size - 1);
        }
        Room room2 = map.get(randindx);
        door.setRooms(room, room2);
        room.addDoor(door);
        room2.addDoor(door);
    }

    /**
     * Véletlenszerűen kiválaszt egy ajtótípust a megadott valószínűségekkel
     * (egyirányú szoba sorsolás súllyal).
     *
     * @return Egy véletlenszerűen kiválasztott ajtótípus.
     */
    // egyirányú szoba sorsolás súllyal
    private Door randomDoorType() {
        Door d;
        if (oneway > r.nextFloat())
            d = new OneWayDoor();
        else
            d = new TwoWayDoor();
        return d;
    }

}
