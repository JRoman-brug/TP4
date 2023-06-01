package ej07;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import TDAMapeo.InvalidKeyException;
import TDAMapeo.Map;
import ej04.MapeoHashAbierto;

class ContenidoTest {
	private Map<Integer,Integer> m1,m2;
	private Contenido conte = new Contenido();
	private Map<Integer,Integer> getMap() {
		return new MapeoHashAbierto<Integer,Integer>();
	}
	@Test
	void testFullContenido() {
		m1 = getMap();
		m2 = getMap();
		try {
			for (int i = 1; i <= 4; i++) {
				m1.put(i,(int) Math.random()*10+1);
			}
			for (int i = 1; i <= 6; i++) {
				m2.put(i,(int) Math.random()*10+1);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		assertTrue("M1 tendria que estar contenido en M2",conte.contenido(m1,m2));
	}
	@Test
	void testUnoContenido() {
		m1 = getMap();
		m2 = getMap();
		try {
			m1.put(1, 10);
			for (int i = 1; i <= 6; i++) {
				m2.put(i,(int) Math.random()*10+1);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		assertTrue("M1 tendria que estar contenido en M2",conte.contenido(m1,m2));
	}
	@Test
	void testMitadContenido() {
		m1 = getMap();
		m2 = getMap();
		try {
			m1.put(1, 10);
			m1.put(2, 10);
			m1.put(4, 10);
			
			for (int i = 1; i <= 6; i++) {
				m2.put(i,(int) Math.random()*10+1);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		assertTrue("M1 tendria que estar contenido en M2",conte.contenido(m1,m2));
	}
	@Test
	void testNoContenido() {
		m1 = getMap();
		m2 = getMap();
		try {
			m1.put(1, 10);
			m1.put(2, 10);
			m1.put(777, 10);
			for (int i = 1; i <= 6; i++) {
				m2.put(i,(int) Math.random()*10+1);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		assertFalse("M1 tendria que estar contenido en M2",conte.contenido(m1,m2));
	}
	@Test
	void testNoPuedeEstarContendio() {
		m1 = getMap();
		m2 = getMap();
		try {
			m1.put(1, 10);
			m1.put(2, 10);
			m1.put(777, 10);
			m1.put(666, 10);
			m1.put(123, 10);
			for (int i = 1; i <= 4; i++) {
				m2.put(i,(int) Math.random()*10+1);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

		assertFalse("M1 tendria que estar contenido en M2",conte.contenido(m1,m2));
	}


}
