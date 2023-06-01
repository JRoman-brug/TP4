package ej08;

import java.util.Iterator;

import ListaDe.ListaDE;
import TDADiccionario.*;
import TDALista.*;

public class DiccionarioHashAbierto <K,V>implements Dictionary<K,V>{

	private int size;
	private int capacidad;
	private PositionList<Entry<K,V>>[] buckets;
	protected static final float factorCarga = 0.5F;
	
	public DiccionarioHashAbierto() {
		size = 0;
		capacidad = 13;
		buckets = new PositionList[capacidad];
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new ListaDE<Entry<K,V>>();
		}
	}


	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key);
		
		Entry<K,V> toReturn = null;
		Entry<K,V> aux = null;
		int code = hashThisKey(key);
		boolean encontre = false;

		PositionList<Entry<K,V>> bucket = buckets[code];
		Iterator<Entry<K,V>> it = bucket.iterator();

		while(it.hasNext() && !encontre) {
			aux = it.next();
			if(aux.getKey().equals(key)) {
				encontre = true;
				toReturn = aux;
			}
		}

		return toReturn;
	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entry<K,V>> toReturn = new ListaDE<Entry<K,V>>();
		Entry<K,V> aux = null;
		int code = hashThisKey(key);

		PositionList<Entry<K,V>> bucket = buckets[code];
		Iterator<Entry<K,V>> it = bucket.iterator();

		while(it.hasNext()) {
			aux = it.next();
			if(aux.getKey().equals(key))toReturn.addLast(aux);;
		}
		return toReturn;
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entry<K,V> toReturn = new Entrada<K,V>(key,value);
		int code = hashThisKey(key);
		buckets[code].addLast(toReturn);
		size++;
		if(size >=factorCarga*capacidad) {
			reHash();
		}
		return toReturn;
	}

	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if(e == null) throw new InvalidEntryException("Entrada invalida");
		Entry<K,V> toReturn = null;
		int code = hashThisKey(e.getKey());
		Iterator<Position<Entry<K,V>>> it = buckets[code].positions().iterator();
		Position<Entry<K,V>> aux = null;
		try {
			while(it.hasNext() && toReturn==null) {
				aux = it.next();
				if(aux.element() == e) {
					toReturn = aux.element();
					buckets[code].remove(aux);
					size--;
				}
			}
		}catch(InvalidPositionException ex) {
			ex.printStackTrace();
		}
		if(toReturn == null) throw new InvalidEntryException("Entrada invalida");
		return toReturn;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> entries = new ListaDE<Entry<K,V>>();

		for (int i = 0; i < buckets.length; i++) {
			for(Entry<K,V>elem :buckets[i]) {
				entries.addLast(elem);
			}
		}

		return entries;
	}

	private void checkKey(K key) throws InvalidKeyException{
		if(key == null) throw new InvalidKeyException("Key invalida");
	}
	private int hashThisKey(K key) {
		return Math.abs(key.hashCode())%capacidad;
	}
	private void reHash() {
		Iterable<Entry<K,V>> entries = entries();
		capacidad = nextPrimo(capacidad);
		buckets = new PositionList[capacidad];
		size=0;
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new ListaDE<Entry<K,V>>();
		}
		for(Entry<K,V> elem:entries) {
			try {
				insert(elem.getKey(),elem.getValue());
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private int nextPrimo(int n){
		int primo = n+1;
		int toReturn = 0;
		boolean encontrePrimo=true,encontre = false;

		while(!encontre) {

			for (int i = 2; i < primo-1 && encontrePrimo; i++) {
				if(primo % i == 0) {
					encontrePrimo = false;
				}

			}
			if(encontrePrimo == true) {
				encontre = true;
				toReturn = primo;
			}
			encontrePrimo = true;
			primo++;
		}

		return toReturn;
	}
}
