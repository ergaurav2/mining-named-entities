package com.ire.indextool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.ire.entity.LocationIdentifier;
import com.ire.entity.OrgIdentifier;
import com.ire.entity.Page;
import com.ire.entity.PersonIdentifier;
import com.ire.entity.StopWords;
import com.ire.tag.TagName;

/**
 * The class to do the indexing of the wikipedia data
 * @author gaurav
 *
 */
public class IndexingTools {
	public static int maxsize = 0;
	
	public static int pagereaded=0;
	
	public static long currenttime = System.currentTimeMillis();
	
	public static int totalpages = pagereaded;
	
	ArrayList<String> arr[] = new ArrayList[3];
	public static Map<String, ArrayList[]> maparrindex = new TreeMap<String, ArrayList[]>();
	// public static Map<String, StringBuffer> mapfinalindex = new
	// HashMap<String, StringBuffer>();
	public static String indexoutputfolder = "index";
	private static boolean isExtlinkfound;
	private static int MAX_SIZE = 6000;
	public static int pagecount = 0;
	public static String outtempfile = "temp";
	public static int fileno = 1;
	// static File f = new File("out.txt");
	static FileWriter fw = null;
	static boolean isFirst = true;
	public static File outfile =  new File(outtempfile+"final.txt");;
	public static TreeMap<String, ArrayList[]> sortedmap = new TreeMap<String, ArrayList[]>();
	public static String filein = "";
	public static String fileoutname = "out";
	public static String fileextn = ".txt";
	public static String filesec = "";
	public static String fileindex = "";

	public static List<File> filelist = new ArrayList<File>();

	static  {
		indexoutputfolder = "index";
		filein = indexoutputfolder + File.separatorChar + "temp1.txt";
		// fileout = indexoutputfolder + File.separatorChar + "temp2.txt";
		filesec = indexoutputfolder + File.separatorChar + "filesec.txt";
		outtempfile = indexoutputfolder + File.separatorChar + "temp";

	}

	/**
	 * This method represent a treatment on a Page and updating the index for that page
	 * @param p @Page
	 */
	public static void treatPage(Page p) {
		//System.out.println("Treat page");
		try {
			pagereaded++;
			 //System.out.println(pagereaded);
			char[] ch = Tools.convertSBtoCharArr(p.getBodytext());
			extractDatafromBody(ch, p, p.getBodytext().length());
			int type = identifyType(p);
			if(type == -1) {
				return;
			} else {
				char [] chartitle = Tools.convertSBtoCharArr(p.getTitle());
				StringBuffer sbtitle = removeNoiseCaseStopWord(chartitle, 0,chartitle.length , p.getId());
				
				addTitletoIndex(p.getId(), sbtitle.toString(), type);
				//System.out.println("Map "+maparrindex);
				//System.out.println(""+maparrindex);
			}

		} catch (Exception e) {
			//System.out.println("Error in Page count " + pagereaded +" with page id"+p.getId());
			e.printStackTrace();
		}
		pagecount++;
		// System.out.println("Remove noise -->");
		try {
			
		} catch (Exception e) {
			//System.out.println("Error in Page count " + pagereaded +" with page id"+p.getId());
			e.printStackTrace();
		}
		try {
			//System.out.println("Size "+maparrindex.size());
			if (maparrindex.size() > MAX_SIZE) {
				StringBuffer sb = new StringBuffer();
				sb.append(outtempfile);
				fileno++;
				sb.append(fileno);
				sb.append(fileextn);
				File f = new File(sb.toString());
				filelist.add(f);
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				sortedmap.putAll(maparrindex);
				System.out.println("Time elapsed "+(System.currentTimeMillis()-currenttime)/60000+" min");
				System.out.println("Page readed " +pagereaded+" Writing..."+f.getName());
				System.out.println("File "+f.getAbsolutePath());
				writeMapTofile(f, sortedmap);
			//	writeToFile(f, sortedmap);
				maparrindex.clear();
				sortedmap.clear();
				isFirst = false;
				pagecount = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("Index "+maparrindex);
		//maparrindex.clear();
	}
	
	/**
	 *  To remove the noise including the conversion of upper case to lower case and removal of stop words
	 * @param ch array of characters
	 * @param start start index
	 * @param end end index
	 * @param pageid id of the page
	 */
		public static StringBuffer removeNoiseCaseStopWord(char[] ch, int start, int end,
				String pageid) {
			// List<String> finalsb = new ArrayList<String>();
			// System.out.println(" To treat"+new String(ch));
			StringBuffer sb = new StringBuffer();
			StringBuffer sbtitle = new StringBuffer();
			int len = start + end;
			for (int i = start; i < len; i++) {
				int chin = (int) ch[i];
				if (chin >= 97 && chin <= 122) {
					sb.append((ch[i]));
				} else {
					try {
						if (sb.length() > 0
								&& !StopWords.stopwordsarrList.contains(sb
										.toString())) {
							sbtitle.append(sb);
							sbtitle.append(" ");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sb.setLength(0);
				}
			}
			// System.out.println("Tokens "+tokens);
			return sbtitle;
		}


	public static void fileend() {
		try {
			if (maparrindex.size() >= 1) {
				StringBuffer sb = new StringBuffer();
				sb.append(outtempfile);
				fileno++;
				sb.append(fileno);
				sb.append(fileextn);
				File f = new File(sb.toString());
				filelist.add(f);
				sortedmap.putAll(maparrindex);
				writeMapTofile(f, sortedmap);
				//write(f, sortedmap);
				sortedmap.clear();
				// mapfinalindex.clear();
			}
			totalpages = pagereaded;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			narraymerge(filelist);
			//createfirstlevelindex(outfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//generatealpaheticindex(fileindex, filesec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//indexoutputfolder = "/media/Local Disk/Sem2/IRE/workspace/MiniProject_2/index";
		List<File> listFile = new ArrayList<File>();
		for(int i=2;i<=296;i++) {
			File f = new File(outtempfile+i+".txt");
			listFile.add(f);
			System.out.println("Adding "+f.getAbsolutePath());
		}
		narraymerge(listFile);
	}
	
/**
 * Extracting data from the body
 * @param ch Text as a array of character
 * @param p @Page
 * @param len length of character array
 */
	public static void extractDatafromBody(char[] ch, Page p, int len) {
		// System.out.println(""+new String(ch));
		int countopen = 0;
		int infoboxstart = 0;
		int infoboxend = 0;
		StringBuffer sb = new StringBuffer();
		// String s = new String(ch);
		for (int i = 0; i < len - 12; i++) {
			if (ch[i] == '{' && ch[i + 1] == '{' && ch[i + 2] == 'I'
					&& ch[i + 3] == 'n' && ch[i + 4] == 'f' && ch[i + 5] == 'o'
					&& ch[i + 6] == 'b' && ch[i + 7] == 'o' && ch[i + 8] == 'x') {
				infoboxstart = i + 9;
				countopen = 1;
				i = i + 9;
			}
		}
		for (int i = infoboxstart; i < len - 1; i++) {
			// System.out.println("char "+ch[i]);
			if ((ch[i] == '{') && ch[i + 1] == '{') {
				countopen++;
			} else if (ch[i] == '}' && ch[i + 1] == '}') {
				// System.out.println("content"+sb.toString());
				countopen--;
			} else {
				sb.append(ch[i]);
				ch[i] = ' ';
			}
			if (countopen == 0) {
				infoboxend = i + 2;
				p.setInfobox(sb);
				// System.out.println("Infobox " + sb.toString());
				break;
			}
		}
		// System.out.println("Infoboxxxxx"+sb.toString());
		sb = new StringBuffer();
		int extlinkstart = infoboxend;
		int exlinkend = infoboxend;
		for (int i = infoboxend; i + 17 < len; i++) {
			if (ch[i] == '=' && ch[i + 1] == '=' && ch[i + 2] == 'e'
					&& ch[i + 3] == 'x' && ch[i + 4] == 't' && ch[i + 5] == 'e'
					&& ch[i + 6] == 'r' && ch[i + 7] == 'n' && ch[i + 8] == 'a'
					&& ch[i + 9] == 'l' && ch[i + 10] == ' '
					&& ch[i + 11] == 'l' && ch[i + 12] == 'i'
					&& ch[i + 13] == 'n' && ch[i + 14] == 'k'
					&& ch[i + 15] == 's' && ch[i + 16] == '='
					&& ch[i + 17] == '=') {
				isExtlinkfound = true;
				extlinkstart = i + 17;
				break;
			}
		}
		if (isExtlinkfound) {
			for (int i = extlinkstart; i < len; i++) {
				// System.out.println("character"+ch[i]);
				if (ch[i] == '[') {
					i++;
					if (i < len && ch[i] == '[') {
						break;
					}
					while (i< len && ch[i] != ']') {
						sb.append(ch[i]);
						ch[i] = ' ';
						i++;
					}
					exlinkend = i;
				}
			}
			p.setExternallinks(sb);
			sb = new StringBuffer();
		}
		// int catstart = 0;
		for (int i = exlinkend; i < len - 12; i++) {
			if (ch[i] == '[' && ch[i + 1] == '[' && ch[i + 2] == 'c'
					&& ch[i + 3] == 'a' && ch[i + 4] == 't' && ch[i + 5] == 'e'
					&& ch[i + 6] == 'g' && ch[i + 7] == 'o' && ch[i + 8] == 'r'
					&& ch[i + 9] == 'y') {
				i = i + 10;
				// catstart = i + 10;
				while (i < len && ch[i] != ']') {
					sb.append(ch[i]);
					ch[i] = ' ';

					i++;
				}
				i = i + 2;
			}
		}
		// System.out.println("Cateogry"+sb.toString());
		p.setCategory(sb);
		// sb = new StringBuffer();
		p.setText(ch);
		// System.out.println("Character "+new String(ch));
	}

	/**
	 * N array Merging
	 * @param files List of files to merge
	 */
	public static void narraymerge(List<File> files) {
		System.out.println("Merging files..... ");
		int nooffiles = files.size();
		List<BufferedReader> listbr = new ArrayList<BufferedReader>();
		for (int i = 0; i < nooffiles; i++) {
			try {
				listbr.add(new BufferedReader(new FileReader(files.get(i))));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			 outfile = new File(outtempfile+"final.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(outfile,
					false));
			String fileword = "";
			List<String> words = new ArrayList<String>();
			List<String> lines = new ArrayList<String>();
			for (BufferedReader br : listbr) {
				lines.add(br.readLine());
			}
			while (!listbr.isEmpty()) {
				words.clear();
				// System.out.println("Lines"+lines);
				for (String line : lines) {
					String[] splits = line.split(TagName.equalstring);
					fileword = splits[0];
					words.add(fileword);
				}
				// System.out.println("Words"+words);
				String minword = words.get(0);
				int minindex = 0;
				for (int i = 1; i < words.size(); i++) {
					String currentword = words.get(i);
					int flag = minword.compareTo(currentword);
					if (flag > 0) {
						minword = currentword;
						minindex = i;
					}
				}
				// System.out.println("Min Word"+minword);
				List<String> newlines = new ArrayList<String>();
				newlines.addAll(lines);
				newlines.remove(minindex);
				String newline = listbr.get(minindex).readLine();
				newlines.add(minindex, newline);
				String minline = lines.get(minindex);
				StringBuffer towriteline = new StringBuffer();
				StringBuffer sb0 = new StringBuffer();
				StringBuffer sb1 = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				String [] splitsmin = minline.split("-");
				sb0.append(splitsmin[0]);
				sb1.append(splitsmin[1]);
				sb2.append(splitsmin[2]);
				for (int i = 1; i < words.size(); i++) {
					String currentword = words.get(i);
					if (currentword.equals(minword) && i != minindex) {
						newlines.remove(i);
						newline = listbr.get(i).readLine();
						newlines.add(i, newline);
						String[] splits = lines.get(i).split(
								TagName.equalstring);
						// towriteline.append(TagName.sep);
						String toadd = splits[1];
						String [] splitstoadd = toadd.split("-");
						String [] split0 = splitstoadd[0].split(TagName.colonString);
						if(split0.length>1) {
						sb0.append(split0[1]);
						}
						String [] split1 = splitstoadd[1].split(TagName.colonString);
						if(split1.length>1) {
						sb1.append(split1[1]);
						}
						String [] split2 = splitstoadd[2].split(TagName.colonString);
						if(split2.length>1) {
						sb2.append(split2[1]);
						}
					}
				}
				towriteline.append(sb0);
				towriteline.append(TagName.hypen);
				towriteline.append(sb1);
				towriteline.append(TagName.hypen);
				towriteline.append(sb2);
				bw.write(towriteline.toString());
				bw.write("\n");
				for (int i = 0; i < newlines.size(); i++) {
					if (newlines.get(i) == null) {
						listbr.remove(i);
						newlines.remove(i);
					}
				}
				lines = newlines;
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * To identify type of the page 
	 * @param p @Page
	 * @return 0-Person 1-Org 2-Loc
	 */
	public static int identifyType(Page p) {
		StringBuffer category = p.getCategory();
		StringBuffer infobox = p.getInfobox();
		if (category != null) {
			String categorystr = category.toString();
			if(PersonIdentifier.isPerson(categorystr)) {
				return 0;
			} else if(OrgIdentifier.isOrg(categorystr)) {
				return 1;
			} else if(LocationIdentifier.isLocation(categorystr)) {
				return 2;
			} 
		}
		if(infobox!=null) {
			String infostr = infobox.toString();
			if(PersonIdentifier.isPerson(infostr)) {
				return 0;
			} else if(OrgIdentifier.isOrg(infostr)) {
				return 1;
			} else if(LocationIdentifier.isLocation(infostr)) {
				return 2;
			} 
		}
		return -1;
	}
		
	/**
	 * Method to add the Title words to the index
	 * @param pageid Id of the page
	 * @param title Title of the page
	 * @param type Type of the title
	 */
	public static void addTitletoIndex(String pageid, String title, int type) {
		
		String words [] = title.split(" ");
		int titlelen = words.length;
		//System.out.println("Title "+title);
		//System.out.println("Title len "+titlelen);
		List<String> wordstobeadded = new ArrayList<String>();
	//	List<Integer> wordslen = new ArrayList<Integer>();
		for(int i=0;i<titlelen;i++) {
			StringBuffer sb = new StringBuffer();
			sb.append(words[i]);
			wordstobeadded.add(sb.toString());
			//System.out.println("word "+sb.toString());
		//	wordslen.add(1);
			int count = 1;
			for(int j=i+1;j<words.length;j++) {
				count++;
				sb.append(" ");
				sb.append(words[j]);
			wordstobeadded.add(sb.toString());
			//wordslen.add(count);
			}
			//System.out.println("word "+sb.toString());
		}
		for(int i=0;i<wordstobeadded.size();i++) {
			String word = wordstobeadded.get(i);
			if(maparrindex.containsKey(word)) {
				ArrayList<String>[] pageidsarr = maparrindex.get(word);
				pageidsarr[type].add(pageid);
			} else {
				ArrayList<String>[] pageidsarr = new ArrayList[3];
				pageidsarr[0] = new ArrayList<String>();
				pageidsarr[1] = new ArrayList<String>();
				pageidsarr[2] = new ArrayList<String>();
				pageidsarr[type].add(pageid);
				maparrindex.put(word, pageidsarr);
			}
		}
	}
	
/**
 * Writing the data structure Tree Map to the Flat text file format
 * @param outfile output file
 * @param sortedMap THe map to be written to the file
 */
	public static void writeMapTofile(File outfile, Map<String, ArrayList[]> sortedMap) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(outfile));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		Set<String> words = sortedMap.keySet();
		for(String word: words) {
			sb.append(word);
			sb.append(TagName.equalstring);
			ArrayList<String> pageidarr [] = sortedMap.get(word);
		
		//	if(!pageidarr[0].isEmpty()) {
				sb.append(TagName.ZERO);
				sb.append(TagName.colon);
				for(String pageid:pageidarr[0]) {
					sb.append(pageid);
					sb.append(TagName.comma);
				}
				sb.append(TagName.hypen);
		//	}
			
		//	if(!pageidarr[1].isEmpty()) {
				sb.append(TagName.ONE);
				sb.append(TagName.colon);
				for(String pageid:pageidarr[1]) {
					sb.append(pageid);
					sb.append(TagName.comma);
				}
				sb.append(TagName.hypen);
		//	}
		//	if(!pageidarr[2].isEmpty()) {
				sb.append(TagName.TWO);
				sb.append(TagName.colon);
				for (String pageid:pageidarr[2]) {
					sb.append(pageid);
					sb.append(TagName.comma);
		//		}
				sb.append(TagName.hypen);
			}
			
			try {
				//System.out.println("Adding "+sb);
				bw.write(sb.toString());
			bw.newLine();
			sb.setLength(0);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}