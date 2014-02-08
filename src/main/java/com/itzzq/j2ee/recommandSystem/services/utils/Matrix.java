package com.itzzq.j2ee.recommandSystem.services.utils;

public class Matrix {
	private Long row;
	private Long col;
	
	public Matrix(){
		row = (long) 0;
		col = (long) 0;
	}
	
	public Matrix(Long r, Long c) {
		row = r;
		col = c;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Long getCol() {
		return col;
	}

	public void setCol(Long col) {
		this.col = col;
	}

	@Override
	public int hashCode() {
		return row.hashCode() + col.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Matrix newobj = (Matrix) obj;
		if(row == newobj.getRow() && col == newobj.getCol())
			return true;
		else
			return false;
	}
	
}
