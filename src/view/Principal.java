package view;

import controller.*;

public class Principal {

	public static void main(String[] args) {
		String[] palavras = {"banana", "laranja", "ma�a", "abacaxi", "frutas", "lichia"};
		HeapSortLetras.ordenar(palavras, palavras.length);
	}
}
