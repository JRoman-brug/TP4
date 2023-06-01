package ej07;

import java.util.Iterator;

import TDAMapeo.*;

public class Contenido {
	public Contenido() {
		
	}
	
	public <K,V> boolean contenido(Map<K, V> m1,Map<K,V> m2) {
		boolean estaContenido = true;//C1
		boolean encontreM2 = false;//c2
		Iterator<K> it1 = m1.keys().iterator();//m
		Iterator<K> it2 = m2.keys().iterator();//m
		K elemM1 = null;//c3
		K elemM2 = null;//c4
		if(m1.size()<=m2.size()){
			//Recorro las keys para ver si coinciden 
			while(it1.hasNext() && estaContenido) {//c5(Twhil*n)
				elemM1 = it1.next();//c6
				encontreM2 = false;//c7
				while(it2.hasNext() && !encontreM2) {//c8(Twhile*m)
					elemM2 = it2.next();//c8
					//verifico encontre una key de m2 que coincida con m1
					if(elemM1.equals(elemM2)) encontreM2=true;//c9
				}
				//Sino encontre en M2 una key que estuviera en M1, entonces no esta contenido
				if(!encontreM2) estaContenido = false; //c10
			}
		}else estaContenido = false;
		return estaContenido;
	}
}
