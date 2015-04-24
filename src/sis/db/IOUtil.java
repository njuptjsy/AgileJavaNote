package sis.db;

import java.io.File;

public class IOUtil {
	public static boolean delete(String... filenames ) {
		boolean deleteAll = true;
		for (String filename:filenames) {
			if (!(new File(filename).delete())) {
				System.out.println(System.currentTimeMillis()+" - >"+filename);
				deleteAll = false;
			}
		}
		System.out.println(System.currentTimeMillis()+" - >"+deleteAll);
		return deleteAll;//all files delete success return true else return false
	}
}
