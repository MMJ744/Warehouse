package route_planning;

import java.util.Comparator;

public class NodeComparatorF implements Comparator<Node>{
	
	@Override
	public int compare(Node a, Node b) {
		//returns pos if a > b, 0 if equal, neg if a < b
		double aF = a.getF();
		double bF = b.getF();
		
		return (int) (aF - bF); 
	}

	

}
