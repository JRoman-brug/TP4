package ej06;


import TDAMapeo.*;
import ej04.MapeoHashAbierto;

public class InvertirMapeo {
	public InvertirMapeo(){

	}

	public <V,K> Map<V,K> invertir(Map<K,V> mapeo)throws MapInvertibleException, MapInyectiveException {
		Map<V,K> toReturn = new MapeoHashAbierto<V,K>();
		K aux = null;
		try {
			for(Entry<K,V> elem: mapeo.entries()) {
				if(elem.getValue() == null) throw new MapInvertibleException("No se puede invertir");
				aux = toReturn.put(elem.getValue(), elem.getKey());
				
				if(aux != null) throw new MapInyectiveException("map no inyectivo");
			}
		}catch(InvalidKeyException  e) {
			e.printStackTrace();
		}

		return toReturn;
	}
}
