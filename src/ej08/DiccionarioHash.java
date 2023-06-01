package ej08;

import java.util.Iterator;

import ListaDe.*;
import TDADiccionario.*;
import TDALista.*;

public class DiccionarioHash<K,V> implements Dictionary<K,V> {
    protected PositionList<Entry<K,V>>[] buckets ;
    protected int size;
    protected int N;
    protected static final float factor = 0.5f;

    public DiccionarioHash() {
        N=11;
        size = 0;
        buckets = new ListaDE[N];
        for(int i=0;i<N;i++) {
			buckets[i]=new ListaDE<Entry<K,V>>();
		}
    }

    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Entry<K,V> find(K key) throws InvalidKeyException {
    	
        checkKey(key);
        Entry<K,V> toRet = null;
        Iterator<Entry<K,V>> it = buckets[hashThisKey(key)].iterator();
        Entry<K,V> e = null;
        boolean flag = false;
        if(!isEmpty()) {
	        while(it.hasNext() && !flag) {
	        	e = it.next();
	        	if(e.getKey().equals(key)) {
	        		toRet = e;
	        		flag = true;
	        	}
	        }
        }    
        return toRet;
    }

    @Override
    public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
    	checkKey(key);
        PositionList<Entry<K,V>> toRet = new ListaDE<Entry<K,V>>();
        if(!isEmpty()) {
	        for(Entry<K,V> e : buckets[hashThisKey(key)]) {
	    	   if(e.getKey().equals(key)) {
	    		   toRet.addLast(e);
	    	   }
	        }
    	}
        return toRet;
    }

    @Override
    public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
    	checkKey(key);
        Entry<K,V> toRet = new Entrada<K,V>(key,value);
        buckets[hashThisKey(key)].addLast((Entrada<K, V>) toRet);
        size++;
		if(size / N >= factor){
            reHash();
        }
		return toRet;
    }

    @Override
    public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException {
    	if(e == null)throw new InvalidEntryException("Entrada nula.");
    	int hk = hashThisKey(e.getKey());
        Entry<K,V> toRet = null;
        Iterable<Position<Entry<K, V>>> pos = buckets[hk].positions();	
        Iterator<Position<Entry<K,V>>> it = pos.iterator();
		Position<Entry<K,V>> p = null;
		boolean encontre =false;
		while(it.hasNext() && !encontre) {
			p = it.next();
			if(p.element().equals(e)) {
				try {
					toRet = buckets[hk].remove(p);
				} catch (InvalidPositionException e2) {
					e2.printStackTrace();
				}
				size--;
				encontre=true;
			}
		}
		if(!encontre)throw new InvalidEntryException("La entrada no se encuentra en el diccionario.");
        return toRet;
    }

    @Override
    public Iterable<Entry<K,V>> entries() {
        PositionList<Entry<K,V>> entries = new ListaDE<Entry<K,V>>();
        
        for(int i=0; i<N; i++){
            //if(p!=null) {
            	for(Entry<K,V> e : buckets[i]) {
            		entries.addLast(e);
            	}
          //  }
        }
        return entries;
    }

    private void reHash(){
    	
    	Iterable<Entry<K, V>> entradas = entries();
    	N = nextPrimo(N * 2);
    	size=0;
        buckets = new ListaDE[N];
        for(int i=0;i<N;i++) {
			buckets[i]=new ListaDE<Entry<K,V>>();
		}
        for(Entry<K,V> e : entradas) {
        	//buckets[hashThisKey (e.getKey())].addLast((Entrada<K, V>) e);
        	try {
				this.insert(e.getKey(), e.getValue());
			} catch (InvalidKeyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
    
    private int hashThisKey(K key) {
        return Math.abs(key.hashCode() % N);
    }

    private void checkKey(K key) throws InvalidKeyException {
        if(key == null) {
            throw new InvalidKeyException("Clave nula.");
        }
    }
    /**
	 * Verifica si el numero es primo.
	 * @param num Numero a verificar.
	 * @return Verdadero si el numero es primo y falso en caso contrario.
	 */
	private boolean isPrime (int num) {
        boolean check = false;
        for (int i = 2; i <= num / 2; i++) {
            if(num % i == 0) {
                check = true;
                break;
            }
        }
        return (!check);
    }
	
	private int nextPrimo(int num) {
        int toReturn = 0;
        boolean isPrime = false;
        while(!isPrime) {

            isPrime = true;
            for (int j = 2; (j<=Math.sqrt(num)) && (isPrime); j++) {
                if((num % j) == 0) {
                    isPrime=false;
                    num++;
                }
            }
            if(isPrime)
                toReturn= num;
        }
        return toReturn;
    }
}
