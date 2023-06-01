package ListaSe;

import java.util.Iterator;

import TDALista.BoundaryViolationException;
import TDALista.ElementIterator;
import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.Node;
import TDALista.Position;
import TDALista.PositionList;

public class ListaSE<E> implements PositionList<E>{
	private Node<E> head;
	private int size;
	
	public ListaSE() {
		head = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	
	public Position<E> first() throws EmptyListException{
		if(isEmpty()) throw new EmptyListException("Lista vacia");
		
		return head;
	}
	
	public Position<E> last() throws EmptyListException{
		if(isEmpty()) throw new EmptyListException("Lista vacia");
		
		Node<E> n = head;
		
		while(n.getNext() != null) n = n.getNext();
		return n;
	}
	
	public Position<E> next(Position<E> p)throws InvalidPositionException, BoundaryViolationException{
		Node<E> n = CheckPosition(p);
		if(n.getNext()==null)throw new BoundaryViolationException("Queres acceder al siguiente posicion del ultimo");
		return n.getNext();
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		CheckPosition(p);
		Node<E> aux = head;
		try {
			if(p == first()) throw new BoundaryViolationException("Queres acceder a la posicion anterior de la primera posicion");
		}
		catch(EmptyListException e) {
			e.printStackTrace();
		}
		
		while(aux.getNext() != null && aux.getNext() != p) {
			aux = aux.getNext();
		}
		if(aux.getNext()==null) throw new InvalidPositionException("Posicion Invalida");
		return aux;
	}
	
	public void addFirst(E element){
		head = new Node<E>(element,head);
		size++;
	}
	
	public void addLast(E element) {
		if(isEmpty())addFirst(element);
		else {
			try {
				Node<E> p = (Node<E>) last();
				p.setNext(new Node<E>(element));
				
			}catch(EmptyListException e) {
				e.printStackTrace();
			}
			size++;
		}
	}
	
	public void addAfter(Position<E> p, E element) throws InvalidPositionException{
		Node<E> n = CheckPosition(p);
		Node<E> nuevo = new Node<E>(element);
		
		nuevo.setNext(n.getNext());
		n.setNext(nuevo);
		size++;
	}
	
	public void addBefore(Position<E> p, E element) throws InvalidPositionException{
		CheckPosition(p);
		try {
			if(p==first()) addFirst(element);
			else {
				addAfter(prev(p),element);
			}
		}catch(InvalidPositionException | BoundaryViolationException | EmptyListException e ) {
			e.printStackTrace();
		}
		
	}
	
	public E remove(Position<E> p)throws InvalidPositionException {
		Node<E> n = CheckPosition(p);
		
		try {
			if(p == first()) head = n.getNext();
			else CheckPosition(prev(p)).setNext(n.getNext());
		}catch(EmptyListException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		E aux = p.element();
		n.setElement(null);
		n.setNext(null);
		
		size--;
		return aux;
		
	}
	
	public E set(Position<E> p, E element) throws InvalidPositionException{
		Node<E> n = CheckPosition(p);
		E aux = p.element();
		n.setElement(element);
		return aux;
	}
	
	private Node<E> CheckPosition(Position<E> p)throws InvalidPositionException{
		try{
			//Si es nulo lanza que p es una posicion invalida
			if(p==null) throw new InvalidPositionException("Posicion nula");
			//Asumimos que si el elemento de la posicion es null, decimos que fue eliminado
			if(p.element() == null) throw new InvalidPositionException("La posicion fue eliminada previamente");
			
			return (Node<E>) p;
		}catch(ClassCastException e) {
			throw new InvalidPositionException("p no es un nodo de lista");
		}
	}
	
	
	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> p = new ListaSE<Position<E>>();
		try {
			if(!isEmpty()) {
				
				Position<E> pos= first();
				while(pos != last()) {
					p.addLast(pos);
					pos = next(pos);
				}
				p.addLast(pos);
			}
		}catch(EmptyListException |InvalidPositionException |BoundaryViolationException e) {
			e.printStackTrace();
		}
		return p;
	}
}
