package TDAMapeo;

public class Entrada<K,V> implements Entry<K,V>{
	private K key;
	private V value;
	
	public Entrada(K k, V v) {
		key = k;
		value = v;
	}
	
	//Setter and getters key
	public K getKey() {
		return key;
	}
	public void setKey(K k) {
		key = k;
	}
	
	//Setter and getters value
	public V getValue() {
		return value;
	}
	public void setValue(V v) {
		value = v;
	}
	
	public String toString() {
		return (getKey()+"-"+getValue());
	}
	
	
}
