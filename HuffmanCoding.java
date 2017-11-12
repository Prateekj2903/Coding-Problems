package huffmanCoding;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.PriorityQueue;

class HuffmanTreeNode implements Comparable<HuffmanTreeNode>{
	char data;
	int freq;
	HuffmanTreeNode left, right;

	public HuffmanTreeNode(char data, int freq) {
		this.data = data;
		this.freq = freq;
		this.left = null;
		this.right = null;
	}

	@Override
	public int compareTo(HuffmanTreeNode node) {
		return this.freq - node.freq;
	}
}


public class HuffmanCoding {

	public static PriorityQueue<HuffmanTreeNode> createMinHeap(HashMap<Character, Integer> map) {
		PriorityQueue<HuffmanTreeNode> minHeap = new PriorityQueue<>();

		for(char key : map.keySet()) {
			minHeap.add(new HuffmanTreeNode(key, map.get(key)));
		}
		return minHeap;
	}

	public static HuffmanTreeNode createTree(PriorityQueue<HuffmanTreeNode> minHeap) {
		while(minHeap.size() != 1) {
			HuffmanTreeNode left = minHeap.remove();
			HuffmanTreeNode right = minHeap.remove();

			HuffmanTreeNode newNode = new HuffmanTreeNode('$', left.freq+right.freq);
			newNode.left = left;
			newNode.right = right;

			minHeap.add(newNode);
		}

		return minHeap.peek();
	}

	static HashMap<Character, String> ansMap = new HashMap<>();
	public static void print(HuffmanTreeNode root, String str) {
		if (root.left == null && root.right == null) {
			System.out.println(root.data + "   " + root.freq + "   " + str);
			ansMap.put(root.data, str);
			return;
		}
		print(root.left, str + "0");
		print(root.right, str + "1");
	}

	public static void main(String[] args) {
		String str = "aaaaabbbbbbbbbccccccccccccdddddddddddddeeeeeeeeeeeeeeeefffffffffffffffffffffffffffffffffffffffffffff";
//	    String str = "abcdaaabbccdddddd";
//		String str = "abca";
		HashMap<Character, Integer> map = new HashMap<>();

		for(int i = 0 ; i < str.length() ; i++) {
			if(map.containsKey(str.charAt(i))) {
				map.put(str.charAt(i), map.get(str.charAt(i))+1);
			} else {
				map.put(str.charAt(i), 1);
			}
		}

		PriorityQueue<HuffmanTreeNode> minHeap = createMinHeap(map);
		HuffmanTreeNode root = createTree(minHeap);
		print(root, "");

		String ans = "";
		for(int i = 0 ; i < str.length() ; i++) {
			ans = ans + ansMap.get(str.charAt(i));
		}

		String org = new BigInteger(str.getBytes()).toString(2);

		System.out.println("Encoded String : " + ans);
		System.out.println("Encoded String bytes = " + (float)ans.length()/8);
		System.out.println("\nOriginal String : " + org);
		System.out.println("Original String bytes = " + str.length());

		try {
			FileOutputStream outEncoded = new FileOutputStream("D:/encoded");
			FileOutputStream outOriginal = new FileOutputStream("D:/org");
			for(int i = 0 ; i < ans.length() ; i++) {
				outEncoded.write(ans.charAt(i));
			}
			for(int i = 0 ; i < org.length() ; i++) {
				outOriginal.write(org.charAt(i));
			}
			outEncoded.close();
			outOriginal.close();
		} 
		catch (FileNotFoundException e) {} 
		catch (IOException e) {}
	}
}
