package ej02;

import java.util.Iterator;

import ListaSe.ListaSE;
import TDALista.*;
import TDAMapeo.*;
import ej01.MapeoLista;

public class Interseccion {
	
	public PositionList<Entry<Integer,Integer>> interseccion(Map<Integer,Integer> m1,Map<Integer,Integer> m2){
		//Una lista de entradas de enteros-enteros
		//c1
		PositionList<Entry<Integer,Integer>> toReturn = new ListaSE<Entry<Integer,Integer>>();
		Entry<Integer,Integer> entrada1,entrada2;
		
		//m1 tiene n entradas
		//m2 tiene m entradas
		//peor caso que sean iguales
		
		//Recorro el primer mapeo
		for(Iterator<Entry<Integer,Integer>> it = m1.entries().iterator(); it.hasNext();) {
			//Recorro el segundo mapeo
			for(Iterator<Entry<Integer,Integer>> it2 = m2.entries().iterator(); it2.hasNext();) {
				entrada1 = it.next(); //O(1) c2
				entrada2 = it2.next();//O(1) c3
				//Si las key's son iguales agrego a la lista
				//c4
				if(entrada1.getKey() == entrada2.getKey()) {
					toReturn.addFirst(entrada1);//c5
					toReturn.addFirst(entrada2);//c6
				}
			}
		}
		
		return toReturn;
	}
}
