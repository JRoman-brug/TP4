package ej02;
import TDALista.*;
import TDAMapeo.*;
import ej01.MapeoLista;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import ListaDe.ListaDE;

public class Ej02Test {

	private Map<Integer,Integer> mapeo1; 
	private Map<Integer,Integer> mapeo2;
	private Interseccion inter;
	private PositionList<Entry<Integer,Integer>> lista;
	public void setUp() {
		mapeo1 = new MapeoLista<Integer,Integer>();
		mapeo2 = new MapeoLista<Integer,Integer>();
		
	}
	
	@Test
	public void test() {
		setUp();
		inter = new Interseccion();
		lista = new ListaDE<Entry<Integer,Integer>>();
		try {
			mapeo1.put(1, 100);
			mapeo1.put(2, 60);
			mapeo1.put(3, 70);
			
			mapeo2.put(4, 60);
			mapeo2.put(5, 10);
			mapeo2.put(6, 40);
			
			lista = inter.interseccion(mapeo1, mapeo2);
			int aux = 0;
			Entry<Integer,Integer> aux2; 
			
			for(Iterator<Entry<Integer,Integer>> it = lista.iterator();it.hasNext();) {
				aux2 = it.next();
				System.out.println(aux2.getKey()+"-"+aux2.getValue());
//				System.out.println(aux2.getKey()+"-"+mapeo1.get(aux2.getKey()));
//				assertTrue(aux2.getKey() == mapeo1.get(aux2.getKey()));
//				
//				aux2 = it.next();
//				assertTrue(aux2.getKey() == mapeo1.get(aux2.getKey())); 
//				aux++;
			}
			
		}catch(InvalidKeyException e) {
			e.printStackTrace();
		}
		
		
	}

}
