package ej01;

import java.util.Iterator;

import ListaDe.ListaDE;
import ListaSe.ListaSE;
import TDALista.BoundaryViolationException;
import TDALista.DNode;
import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.Position;
import TDALista.PositionList;
import TDAMapeo.Entrada;
import TDAMapeo.Entry;
import TDAMapeo.InvalidKeyException;
import TDAMapeo.Map;

public class MapeoListaSE<K,V> implements Map<K, V>{
	private PositionList<Entry<K,V>> map;

	public MapeoListaSE() {
		map = new ListaSE<Entry<K,V>>();
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public V get(K key) throws InvalidKeyException {
		//Verifico si la llave es valida
		if(key == null) throw new InvalidKeyException("Key invalida");

		V toReturn = null;
		Entry<K,V> aux = null;
		boolean encontre = false;
		//Creo el iterador para buscar k
		Iterator<Entry<K,V>> it = map.iterator();
		//Recorro para buscar el valor
		while(it.hasNext() && !encontre) {
			aux = it.next();
			//Busco una entrada con llave key
			if(aux.getKey().equals(key)) {
				toReturn = aux.getValue();
				encontre = true;
			}
		}

		return toReturn;
	}

	public V put(K key, V value) throws InvalidKeyException {
		//Verifico si la llave es nula
		if(key == null)throw new InvalidKeyException("key invalida");

		V toReturn = null;
		Entry<K,V> aux;
		Iterator<Entry<K,V>> it = map.iterator();
		//Recorro para buscar el valor
		while(it.hasNext()) {
			aux = it.next();
			//Busco si hay una key si coincide con la key del parametro
			if(aux.getKey().equals(key)){
				//Guardo el valor para retorna
				toReturn = aux.getValue();
				//Cambio el valor de la entrada
				((Entrada<K,V>) aux).setValue(value);
			}
		}
		//Si es nulo significa que no se encontro una key en la lista, entonces hay que agregar una entrada
		if(toReturn == null) {
			map.addFirst(new Entrada<K, V>(key,value));
		}
		return toReturn;
	}

	public V remove(K key) throws InvalidKeyException {
		if(key == null)throw new InvalidKeyException("key invalida");


		V toReturn = null;
		Position<Entry<K,V>> aux;
		Iterator<Position<Entry<K,V>>> it = map.positions().iterator();
		//Recorro para buscar el valor
		while(it.hasNext()) {
			aux = it.next();
			//Busco si hay una key si coincide con la key del parametro
			if(aux.element().getKey().equals(key)){
				//Guardo el valor para retorna
				toReturn = aux.element().getValue();
				//Elimino la entrada
				try {
					map.remove(aux);
				}catch(InvalidPositionException e) {
					e.printStackTrace();
				}
			}
		}
		return toReturn;
	}

	public Iterable<K> keys() {
		PositionList<K> toReturn = new ListaDE();
		Iterator<Entry<K,V>> it = map.iterator();
		Entry<K,V> aux;
		for (map.iterator(); it.hasNext();) {
			aux = it.next();

			toReturn.addLast(aux.getKey());

		}


		return toReturn;
	}

	public Iterable<V> values() {
		PositionList<V> toReturn = new ListaDE();
		Iterator<Entry<K,V>> it = map.iterator();
		Entry<K,V> aux;
		for (map.iterator(); it.hasNext();) {
			aux = it.next();

			toReturn.addLast(aux.getValue());

		}


		return toReturn;
	}

	//Retorna una lista de position
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> toReturn = new ListaDE<Entry<K,V>>();

		for (Iterator<Entry<K, V>> it = map.iterator();it.hasNext();) {
			toReturn.addLast(it.next());

		}

		return toReturn;
	}


}
