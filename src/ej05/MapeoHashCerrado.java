package ej05;

import java.util.Iterator;

import ListaDe.*;
import TDALista.*;
import TDAMapeo.*;

public class MapeoHashCerrado<K,V> implements Map<K,V>{


	private int size;
	private int capacidad;
	private Entry<K,V>[] buckets;
	private Entry<K,V> disponible;
	protected static final float factorCarga = 0.5F;

	public MapeoHashCerrado(){
		size = 0;
		capacidad = 1013;
		buckets = (Entry<K,V>[]) new Entry[capacidad];
		disponible = new Entrada<K,V>(null,null);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		V toReturn = null;
		int code = hashThisKey(key);
		boolean encontre = false;

		int i = 0;
		while(i<capacidad && !encontre) {
			if(buckets[code] == null || buckets[code] == disponible) {//No existe la entrada
				encontre = true;
			}else if(buckets[code].getKey().equals(key)){//Existe la entrada
				encontre = true;
				//Guardo la entrada
				toReturn = buckets[code].getValue();
			}else {				
				code = (code+1)%capacidad;
			}
			i++;
		}



		return toReturn;
	}

	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		V toReturn = null;
		int code = hashThisKey(key);
		boolean encontre = false;
		int i = 0;



		while(i<capacidad && !encontre) {
			if(buckets[code] == null || buckets[code] == disponible) {//Crear nueva entrada
				buckets[code] = new Entrada<K,V>(key,value);
				encontre = true;
				size++;
			}else {
				if(buckets[code].getKey().equals(key)){//Actualizar

					toReturn = buckets[code].getValue();
					((Entrada<K,V>)buckets[code]).setValue(value);
					encontre = true;
				}
			}
			code = (code+1)%capacidad;
			i++;
		}

		if(size >= factorCarga*capacidad) {
			reHash();
		}
		return toReturn;
	}

	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		V toReturn = null;
		int code = hashThisKey(key);
		boolean encontre = false;

		int i = 0;
		while(i<capacidad && !encontre) {
			if(!(buckets[code] == null || buckets[code] == disponible) && buckets[code].getKey().equals(key)) {//No encontre entrad
				encontre = true;
				toReturn = buckets[code].getValue();
				buckets[code] = disponible;
				size--;
			}else {				
				code = (code+1)%capacidad;
			}
			i++;
		}

		return toReturn;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> keys = new ListaDE<K>();
		for (int i = 0; i < buckets.length; i++) {
			if(buckets[i]!=null && buckets[i]!=disponible) {
				keys.addLast(buckets[i].getKey());
			}
		}

		return keys;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> values = new ListaDE<V>();
		for (int i = 0; i < buckets.length; i++) {
			if(buckets[i]!=null && buckets[i]!=disponible) {
				values.addLast(buckets[i].getValue());
			}
		}

		return values;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> entries = new ListaDE<Entry<K,V>>();

		for (int i = 0; i < buckets.length; i++) {
			if(buckets[i]!=null && buckets[i]!=disponible) {
				entries.addLast(buckets[i]);
			}
		}

		return entries;
	}

	private void checkKey(K key)throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Key invalida");
	}
	private int hashThisKey(K key) throws InvalidKeyException{
		checkKey(key);
		return Math.abs(key.hashCode() % capacidad);
	}
	private void reHash() throws InvalidKeyException{
		Iterable<Entry<K,V>> entries = this.entries();
		capacidad = nextPrimo(capacidad*2);
		buckets = (Entrada<K,V> []) new Entrada[capacidad];
		size = 0;
		for(Entry<K,V> e : entries) {
			this.put(e.getKey(), e.getValue());
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
