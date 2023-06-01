package ej10;

import java.util.Iterator;

import TDADiccionario.Dictionary;
import TDADiccionario.Entry;
import TDADiccionario.InvalidKeyException;
import ej08.DiccionarioHashAbierto;

public class LimpiarClaves {
	
	public static void main(String[] args) {
		Dictionary<Integer,Character> d1 = new DiccionarioHashAbierto<Integer,Character>();
		Dictionary<Integer,Character> d2;
		try {
			d1.insert(1, 'a');
			d1.insert(2, 'b');
			d1.insert(3, 'a');
			d1.insert(2, 'c');
			d1.insert(1, 'd');
			d1.insert(4, 'b');
			
		}catch(InvalidKeyException e) {
			e.printStackTrace();
		}
		imprimir(d1);
		d2 = acomodar(d1);
		imprimir(d2);
	}
	
	
	public static <K,V> Dictionary<K,V> acomodar(Dictionary<K,V> d){
		Dictionary<K,V> toReturn = new DiccionarioHashAbierto<K,V>();
		Entry<K,V> aux = null;
		
		Iterator<Entry<K,V>> it = null;
		//Recorro las entradas
		for(Entry<K,V> entrada: d.entries()) {
			try {
				//Hago un iterador
				it = d.findAll(entrada.getKey()).iterator();
				//recorro todas las entradas con la misma clave 
				while(it.hasNext() ) {
					//Obtengo la entrada
					aux = it.next();
					if(!it.hasNext()) {
						toReturn.insert(aux.getKey(),aux.getValue());
					}
				}
				
			}catch(InvalidKeyException e) {
				e.printStackTrace();
			}
			
		}
		
		return toReturn;
	}
	public static <K,V> void imprimir(Dictionary<K, V> d) {
		System.out.println("\n"+d.size());
		for(Entry<K,V> elem:d.entries()) {
			System.out.println(elem.getKey()+"-"+elem.getValue());
		}
	}
}
