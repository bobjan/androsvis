package com.logotet.util;

import com.logotet.util.exception.ObjectNotInMemoryException;

import java.lang.ref.SoftReference;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Omogucava kontrolu nad objektima koji treba da ostanu persistenti u VM nakon instanciranja,
 * tj. koji Garbage Collector ne treba da budu ukloni. Takodje, obezbedjuje da u VM
 * postoji samo jedna instanca nekog objekta
 * (znaci nemoguce je sa istim uniqe identifikatorm kreirati dve instance)
 * <p/>
 * koristi se na sledeci nacin:
 * <PRE>
 * import com.logotet.util.exception.*;
 * import com.logotet.util.*;
 * import java.util.*;
 * <p/>
 * public class Player implements Cacheable{
 * private static final PersistentCollection cache = new PersistentCollection();
 * private String playerId;
 * private String someField;
 * <p/>
 * public static Player getInstance(String id, String something){
 * Player objekat = null;
 * try{
 * objekat = (Player)cache.get(id);
 * }catch(ObjectNotInMemoryException onime){
 * objekat = new Player(id, something);
 * cache.put(objekat);
 * }
 * return objekat;
 * }
 * <p/>
 * public static Player getInstance(String code) throws RecordNotFoundException{
 * Player objekat = null;
 * try{
 * objekat = (Player)cache.get(code);
 * }catch(ObjectNotInMemoryException onime){
 * objekat = createNewInstance(code);
 * if(objekat != null)
 * cache.put(objekat);
 * }
 * if(objekat == null)
 * throw new RecordNotFoundException();
 * return objekat;
 * }
 * <p/>
 * private static Player createNewInstance(String code)throws RecordNotFoundException{
 * PlayerDB pdb = new PlayerDB();// fetch from database
 * pdb.findByKey(code);
 * Player player = pdb.getPlayer();
 * //		Player player = new Player(pdb.getIdd(), db.getSomething());
 * return player;
 * <p/>
 * }
 * <p/>
 * private Player(String id) throws ConstructorException{
 * PlayerDB pdb = new PlayerDB();
 * try{
 * pdb.findByKey(id);
 * Player plr = pdb.getPlayer();
 * this.playerId = id;
 * this.someField = plr.getSomething();
 * }catch(RecordNotFoundException rnf){
 * throw new ConstructorException("No such object");
 * }
 * }
 * <p/>
 * private Player(String id, String something){
 * this.playerId = id;
 * this.someField = something;
 * }
 * ...
 * <p/>
 * public void makePersistent(){
 * PlayerDB pdb = new PlayerDB();
 * pdb.insert(this);
 * }
 * <p/>
 * }
 * </PRE>
 */
public class PersistentCollection {
    private Hashtable instances = new Hashtable();

    /**
     * smesta Cacheable objekat u kolekciju
     */
    public void put(Cacheable bo) {
        SoftReference ref = new SoftReference(bo);
        instances.put(bo.getKeyValue(), ref);
//		System.out.println("Hashiran " + bo.getKeyValue());
    }

    /**
     * na osnovu kljuca u String obliku pronalazi instancu
     */
    public Cacheable get(String key) throws ObjectNotInMemoryException {
//		System.out.println("Trazhen == " + key);
        SoftReference ref = (SoftReference) instances.get(key);
        if (ref == null)
            throw new ObjectNotInMemoryException();
        Cacheable bo = null;
        bo = (Cacheable) ref.get();
        if (bo == null) {
            instances.remove(key);
            throw new ObjectNotInMemoryException();

        }
//		System.out.println("a nadjen  == " + bo.getKeyValue());
        return bo;
    }

    /**
     * vraca Vector sa svim objektima koji su insncirani
     */
    public Vector getAllObjects() {
        Vector all = new Vector();
        Enumeration enm = instances.keys();
        while (enm.hasMoreElements()) {
            String key = (String) enm.nextElement();
            try {
                Cacheable bo = get(key);
                all.addElement(bo);
            } catch (ObjectNotInMemoryException oe) {
            }
        }
        return all;
    }

    /**
     * unistava objekat
     */
    public void destroy(String key) {
        SoftReference ref = (SoftReference) instances.get(key);
        if (ref != null)
            ref.clear();
        ref = null;
        System.gc();
    }

}
