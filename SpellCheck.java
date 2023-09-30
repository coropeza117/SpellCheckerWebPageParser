package edu.uwm.cs351;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import edu.uwm.cs351.util.Element;
import edu.uwm.cs351.util.SortUtils;
import edu.uwm.cs351.util.XMLParseException;
import edu.uwm.cs351.util.XMLReader;

/**
 * @author * CHRISTIAN OROPEZA CS-351 ...RECIEVED HELP FROM BOOK, CS LIBRARY TUTORS, ONLINE CS TUTOR, AND ADVICE FROM FRIENDS ON HOW TO APPROACH FIXING PERSISTENT BUGS.
 * COLLABORATORS: JOSHUA KNIGHT, JULLIAN MURENO, BIJAY PANTA, JIAHUI YANG , MIGUEL GARCIA, MARAWAN SALAMA, ESTELLE BRADY (WHILE IN TUTORING LIBRARY SECTION) BUT NO CODE WAS SHARED.
 * Online Sources: https://codereview.stackexchange.com/questions/159547/website-spell-checker-in-java
 * 				   https://www.javatips.net/api/java.text.normalizer
 *                 https://stackoverflow.com/questions/9849015/java-regex-using-strings-replaceall-method-to-replace-newlines
 *                 https://www.geeksforgeeks.org/arrays-aslist-method-in-java-with-examples/
 *                 https://www.geeksforgeeks.org/arraylist-sublist-method-in-java-with-examples/
 */

public class SpellCheck 
{

	private final String[] dictionary;
	
	public SpellCheck() throws IOException 
	{
		ArrayList<String> temp = new ArrayList<String>();
		InputStream is = new FileInputStream(new File("lib","dictionary.txt"));
		Reader r = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(r);
		String s;
		while ((s = br.readLine()) != null) 
		{
			temp.add(s);
		}
		br.close();
		dictionary = temp.toArray(new String[temp.size()]);
	}
	
	/**
	 * Check the words in the given element.
	 * Return a list of all words that do not occur in the dictionary.
	 * @param e HTML element (ignore script and style tags!)
	 * @return non-null list of all words not occurring in the dictionary.
	 * Get words in the elements (easiest using a recursive helper)
	 * Sort, uniq, remove correctly spelled ones
	 * Return a list of the remaining.
	 * See java.util.Arrays and also look at methods available for java.util.List
	 * sources to get idea of what functions to use above.
	 */
	public List<String> check(Element e) 
	{
		ArrayList<String> wordsContent = checkHelper(e, e.contentList(), new ArrayList<>());

		SortUtils<String> sortedList = new SortUtils<>(null);

		int wcSize = wordsContent.size();

		String[] wordsArray = new String[wcSize];

		wordsContent.toArray(wordsArray);

		sortedList.mergeSort(wordsArray);

		List<String> finalList = Arrays.asList(wordsArray)
				.subList(0, sortedList
						.difference(0, wordsArray.length, 0, dictionary.length, wordsArray, dictionary.clone(), wordsArray));

		return finalList;
	}

	private ArrayList<String> checkHelper(Element e, List<Object> in, ArrayList<String> strList)
	{
		if(e.getName().equals("script") || e.getName().equals("style"))	return strList;

		int i = 0;

		while (i < in.size()) 
		{
			Object obj = in.get(i);

			if (obj instanceof Element) 
			{
				Element objElem = (Element) obj;

				checkHelper(objElem, objElem.contentList(), strList);
			} 

			else 
			{
				String temp = ((String) obj);

				temp = temp.replaceAll("[.?\",|:;()]","").replace("\n"," ");

				String[] strSplit = temp.split(" ");

				int j = 0;

				while (j < strSplit.length) 
				{
					String jSplit = strSplit[j++];

					if(jSplit.equals(""))	strList.remove(jSplit);

					else	strList.add(jSplit);
				}
			}

			++i;
		}

		return strList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		SpellCheck sc = new SpellCheck();
		for (String url : args) {
			System.out.println("Checking spelling of HTML in " + url);
			try {
				Reader r= new InputStreamReader(new BufferedInputStream(new URL(url).openStream()));
				final XMLReader t = new XMLReader(r);
				// t.addCDATA("script");
				Object next = t.readElement();
				if (next instanceof Element) { // could be null
					Element contents = (Element)next;
					if (!contents.getName().equalsIgnoreCase("html")) {
						throw new XMLParseException("element must be HTML not " + contents.getName());
					}
					System.out.println("Mispelled:");
					for (String s : sc.check(contents)) {
						System.out.println("  " + s);
					}
				} else {
					throw new XMLParseException("contents must be HTML, not " + next);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
