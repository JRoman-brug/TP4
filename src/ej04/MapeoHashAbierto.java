package ej04;

import ListaDe.ListaDE;
import TDALista.PositionList;
import TDAMapeo.Entrada;
import TDAMapeo.Entry;
import TDAMapeo.InvalidKeyException;
import TDAMapeo.Map;
import ej01.MapeoLista;

public class MapeoHashAbierto<K,V> implements Map<K, V>{
	private Map<K,V>[] a;
	private int n;
	private int N = 13;

	public MapeoHashAbierto() {
		n=0;
		a = new MapeoLista[N];
		for (int i = 0; i < a.length; i++) {
			a[i] = new MapeoLista<K,V>();
		}
	}


	public int size() {
		return n;
	}


	public boolean isEmpty() {
		return n==0;
	}


	public V get(K key) throws InvalidKeyException {
		if(key == null) throw new InvalidKeyException("Key invalida");
		//Voy al bucket hash(k) y busco en esa lista la misma key
		return a[hash(key)].get(key);
	}

	public V put(K key, V value) throws InvalidKeyException {
		V toReturn;
		if(key == null) throw new InvalidKeyException("Key invalida");

		toReturn = a[hash(key)].put(key, value);

		if(toReturn == null ) {
			n++;
		}
		if(!(n/N < 0.5)) {
			reHash();
		}
		return toReturn;
	}


	public V remove(K key) throws InvalidKeyException {
		V toReturn;
		if(key == null) throw new InvalidKeyException("Key invalida");
		toReturn = a[hash(key)].remove(key);

		if(toReturn != null) {
			n--;
		}

		return toReturn;
	}


	public Iterable<K> keys() {
		PositionList<K> llaves = new ListaDE<K>();
		for (int i = 0; i < a.length; i++) {		
			for(K p: a[i].keys()) {
				llaves.addLast(p);
			}
		}
		return llaves;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> values = new ListaDE<V>();
		for (int i = 0; i < a.length; i++) {		
			for(V p: a[i].values()) {
				values.addLast(p);
			}
		}
		return values;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> entrada = new ListaDE<Entry<K,V>>();
		for (int i = 0; i < a.length; i++) {		
			for(Entry<K,V> p: a[i].entries()) {
				entrada.addLast(p);
			}
		}
		return entrada;
	}

	private int hash(K key) {
		return Math.abs(key.hashCode() % N); //por si acaso pasan un
	}
	private void reHash() {
		Iterable<Entry<K,V>> entradas = entries();
		N=nextPrimo(N); n=0;

		//Creo un nuevo arreglo de mapeoConLista
		a = (Map<K,V>[]) new MapeoLista[N]; //cambio la rer. del atributo
		
		//Creo los buckets
		for(int i=0;i<N;i++){
			a[i]= new MapeoLista<K,V>();
		}
		//Reingreso todas las entradas
		for(Entry<K,V> e: entradas){
			try {
				this.put(e.getKey(), e.getValue());
			} catch (InvalidKeyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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

