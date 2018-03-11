package tools;
import java.util.Queue;
//very bad implementation of blockingqueue, which is not included in lejos
public class BlockingQueue<E> extends Queue<E> {

	public BlockingQueue() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	
	public E take(){
		while (super.empty()){

		}
		return (E) super.pop();

	}
}
