import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Encoder {
	
	private String inputFileName;
	private String outputFileName = "compressed.txt";
	private String codesFileName = "codes.txt";
	private HuffmanNode huffmanTree;
	private byte[] encodedText;
	private MyHashTable<String, Integer> frequencies;
	private MyHashTable<String, String> codes;
	private MyPriorityQueue<HuffmanNode> tree;
	
	String word = "";
		
	//List that holds all words and separators in the text
	private List<String> words;
	
	BufferedReader bufferedReader;
	
	long duration = 0;
	long startTime = System.currentTimeMillis();
	
	//Encoder constructor
	public Encoder(String text) {
		
		this.inputFileName = text;
		frequencies = new MyHashTable<String, Integer>(32767);
		codes = new MyHashTable<String, String>(32767);
		tree = new MyPriorityQueue<>();
		words = new ArrayList<String>();
		
		
		try {
			readInputFile();
			//breakString();
			//countFrequency();
			buildTree();
			encode();
			writeOutputFile();
			writeCodesFile();
			System.out.println(toString());
			codes.stats();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readInputFile() throws IOException {
		
		File inputText = new File(inputFileName);
		
		try {
			FileReader reader = new FileReader(inputText);
			bufferedReader = new BufferedReader(reader);
			
			countFrequency();
			
		} finally {}
	}
	
	//Helper method to determine of character would make a word.
	private static boolean isLetterOrDigit(char c) {
	    return (c >= 'a' && c <= 'z') ||
	           (c >= 'A' && c <= 'Z') ||
	           (c >= '0' && c <= '9') ||
	           (c == '\'');
	}
	
	private void puttingIntoTable(String word) {
		
		if(frequencies.containsKey(word)) {
			
			frequencies.put(word, frequencies.get(word) + 1);
		} else {
			frequencies.put(word, 1);
		}
	}
	
	//Method that breaks string into words.
	private void countFrequency() throws IOException {
		int ch;
		
		String word = "";
		while((ch = bufferedReader.read()) != -1) {
			char c = (char) ch;
			
			if(!isLetterOrDigit(c)) {
				if(word.length() > 0) {
					// Add the word to the list
					words.add(word);
					puttingIntoTable(word);
				} 
				// Reset the word into a blank String
				word = "" + c;
				if(word.length() > 0) {
					words.add(word);
					puttingIntoTable(word);
				}
				word = "";
			} else {
				word += c;
			}
		}
		if(word.length() > 0) {
			words.add(word);
			
			puttingIntoTable(word);
		}	
		bufferedReader.close();
	}
		
	private void buildTree() {
		
		for(String entry : frequencies.keySet()) {
            tree.offer(new HuffmanNode(entry, frequencies.get(entry)));
        }
		
		while (tree.size() > 1) {
			HuffmanNode left = tree.poll();
			HuffmanNode right = tree.poll();
			int weight = left.weight + right.weight;
			huffmanTree = new HuffmanNode(weight, left, right);
			tree.offer(huffmanTree);
		}
		String code = "";
		assignCodes(huffmanTree, code);
	}
	
	private void assignCodes(HuffmanNode node, String code) {
		
		if (node.left == null && node.right == null) {
			codes.put(node.symbol, code);
		} else {
			assignCodes(node.left, code + '0');
			
			assignCodes(node.right, code + '1');
		}
	}
	
	private void encode() {
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < words.size(); i++) {
			sb.append(codes.get(words.get(i)));
		}
		
		encodedText = new byte[sb.length()/8];
		for (int i = 0; i < encodedText.length; i++) {
			encodedText[i] = (byte)Integer.parseInt(sb.substring(i * 8, (i*8) + 8 ), 2);
		}
	}
	
	private void writeOutputFile() throws IOException {
		
		FileOutputStream outputFile = new FileOutputStream(new File(outputFileName));
		outputFile.write(encodedText);
		outputFile.close();
	}
	
	private void writeCodesFile() throws IOException {
		FileOutputStream codesOutputFile = new FileOutputStream(new File(codesFileName));
		codesOutputFile.write(codes.toString().getBytes());
		codesOutputFile.close();
	}
	
	@Override
	public String toString() {
		
		
		StringBuilder string = new StringBuilder();
		try {
			double ratio = (double)(Files.size(Paths.get("compressed.txt"))) / (double)Files.size(Paths.get("WarAndPeace.txt"));
			double percentage = ratio * 100.0;
			
			string.append("Uncompressed file size: " + Files.size(Paths.get("WarAndPeace.txt"))/1024 + " Kb " + "(" +
					Files.size(Paths.get("WarAndPeace.txt")) + " bytes)");
			string.append("\n");
			string.append("Compressed file size: " + Files.size(Paths.get("compressed.txt"))/1024 + " Kb " + "(" + 
					Files.size(Paths.get("compressed.txt")) + " bytes)");
			string.append("\n");
			string.append("Compression ratio: " + (String.format("%d", (long) percentage)) + "%");
			string.append("\n");
			
			long endTime = System.currentTimeMillis();
			duration = endTime - startTime;
			
			string.append("Running time: " + duration + " milliseconds");
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
		
		return string.toString();
		}
	
	
	
	private class HuffmanNode implements Comparable<HuffmanNode> {
		
		public int weight;
		public String symbol;
		public HuffmanNode left;
		public HuffmanNode right;
		
		public HuffmanNode(String string, Integer w) {
			this.symbol = string;
			this.weight = w;
			
			left = null;
			right = null;
			
		}
		public HuffmanNode(Integer w, HuffmanNode l, HuffmanNode r) {
			this.weight = w;
			this.left = l;
			this.right = r;
			this.symbol = null;
			
		}
		
		@Override
		public String toString() {
			String result = "";
			result += symbol;
			result += ", " + weight;
			return result;		
		}
		@Override
		public int compareTo(Encoder.HuffmanNode o) {
			HuffmanNode node = (HuffmanNode) o;
			if (weight > node.weight) {
				return 1;
			} else if (weight < node.weight) {
				return -1;
			} else {
				return 0;
			}
		}
		
	}
}
