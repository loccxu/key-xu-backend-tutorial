package com.simoncomputing.sample.dto;

import java.util.List;
import java.util.Objects;

public class PagingResponse<T> {
    private List<T> list;
    private int page;
    private long total;
    
    public PagingResponse() {}
    
    public PagingResponse(List<T> list, int page, long total) {
    	this.list = list;
    	this.page = page;
    	this.total = total;
    }

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		return Objects.hash(list, page, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PagingResponse<?> other = (PagingResponse<?>) obj;
		return Objects.equals(list, other.list) && page == other.page && total == other.total;
	}

	@Override
	public String toString() {
		return "PagingResponse [list=" + list + ", page=" + page + ", total=" + total + "]";
	}   
}
