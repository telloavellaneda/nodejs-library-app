package force.eai.mx.util;

import java.util.HashMap;

public class Pager {

	private int clCurrent = 0;
	private int clFrom = 0;
	private int clTo = 0;
	private double clTotal = 0;
	private double clSolic = 0;
	private double clAprob = 0;
	private double clFinal = 0;
	private HashMap<Integer,HashMap<String,Integer>> clPages = new HashMap<Integer,HashMap<String,Integer>>();

	private int prCurrent = 0;
	private int prFrom = 0;
	private int prTo = 0;
	private double prTotal = 0;
	private double prSolic = 0;
	private double prAprob = 0;
	private double prFinal = 0;
	private HashMap<Integer,HashMap<String,Integer>> prPages = new HashMap<Integer,HashMap<String,Integer>>();

	private int allCurrent = 0;
	private int allFrom = 0;
	private int allTo = 0;
	private double allTotal = 0;
	private double allSolic = 0;
	private double allAprob = 0;
	private double allFinal = 0;
	private HashMap<Integer,HashMap<String,Integer>> allPages = new HashMap<Integer,HashMap<String,Integer>>();
	
	public int getClCurrent() {
		return clCurrent;
	}
	public int getClFrom() {
		return clFrom;
	}
	public int getClTo() {
		return clTo;
	}
	public double getClTotal() {
		return clTotal;
	}
	public double getClSolic() {
		return clSolic;
	}
	public double getClAprob() {
		return clAprob;
	}
	public double getClFinal() {
		return clFinal;
	}
	public HashMap<Integer, HashMap<String, Integer>> getClPages() {
		return clPages;
	}
	public int getPrCurrent() {
		return prCurrent;
	}
	public int getPrFrom() {
		return prFrom;
	}
	public int getPrTo() {
		return prTo;
	}
	public double getPrTotal() {
		return prTotal;
	}
	public double getPrSolic() {
		return prSolic;
	}
	public double getPrAprob() {
		return prAprob;
	}
	public double getPrFinal() {
		return prFinal;
	}
	public HashMap<Integer, HashMap<String, Integer>> getPrPages() {
		return prPages;
	}
	public int getAllCurrent() {
		return allCurrent;
	}
	public int getAllFrom() {
		return allFrom;
	}
	public int getAllTo() {
		return allTo;
	}
	public double getAllTotal() {
		return allTotal;
	}
	public double getAllSolic() {
		return allSolic;
	}
	public double getAllAprob() {
		return allAprob;
	}
	public double getAllFinal() {
		return allFinal;
	}
	public HashMap<Integer, HashMap<String, Integer>> getAllPages() {
		return allPages;
	}
	public void setClCurrent(int clCurrent) {
		this.clCurrent = clCurrent;
	}
	public void setClFrom(int clFrom) {
		this.clFrom = clFrom;
	}
	public void setClTo(int clTo) {
		this.clTo = clTo;
	}
	public void setClTotal(double clTotal) {
		this.clTotal = clTotal;
	}
	public void setClSolic(double clSolic) {
		this.clSolic = clSolic;
	}
	public void setClAprob(double clAprob) {
		this.clAprob = clAprob;
	}
	public void setClFinal(double clFinal) {
		this.clFinal = clFinal;
	}
	public void setClPages(HashMap<Integer, HashMap<String, Integer>> clPages) {
		this.clPages = clPages;
	}
	public void setPrCurrent(int prCurrent) {
		this.prCurrent = prCurrent;
	}
	public void setPrFrom(int prFrom) {
		this.prFrom = prFrom;
	}
	public void setPrTo(int prTo) {
		this.prTo = prTo;
	}
	public void setPrTotal(double prTotal) {
		this.prTotal = prTotal;
	}
	public void setPrSolic(double prSolic) {
		this.prSolic = prSolic;
	}
	public void setPrAprob(double prAprob) {
		this.prAprob = prAprob;
	}
	public void setPrFinal(double prFinal) {
		this.prFinal = prFinal;
	}
	public void setPrPages(HashMap<Integer, HashMap<String, Integer>> prPages) {
		this.prPages = prPages;
	}
	public void setAllCurrent(int allCurrent) {
		this.allCurrent = allCurrent;
	}
	public void setAllFrom(int allFrom) {
		this.allFrom = allFrom;
	}
	public void setAllTo(int allTo) {
		this.allTo = allTo;
	}
	public void setAllTotal(double allTotal) {
		this.allTotal = allTotal;
	}
	public void setAllSolic(double allSolic) {
		this.allSolic = allSolic;
	}
	public void setAllAprob(double allAprob) {
		this.allAprob = allAprob;
	}
	public void setAllFinal(double allFinal) {
		this.allFinal = allFinal;
	}
	public void setAllPages(HashMap<Integer, HashMap<String, Integer>> allPages) {
		this.allPages = allPages;
	}
}