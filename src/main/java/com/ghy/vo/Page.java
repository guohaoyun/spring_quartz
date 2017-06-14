package com.ghy.vo;

import java.util.List;

/**  
*  
*
* @author ghy  
* @date 2017年5月13日
* 类说明  :
*/
public class Page<T> {

	private List<T> list; // 要返回的当前查询页的记录列表 

	private int allRow = 0; // 总记录数
	private int totalPage = 1; // 总页数
	private int pageIndex = 1; // 当前页
	private int pageSize = 20; // 每页记录数PageSize

	private int nextPage; // 下一页
	private int previousPage; // 上一页

	private boolean isFirstPage; // 是否为第一页
	private boolean isLastPage; // 是否为最后一页
	private boolean hasPreviousPage; // 是否有前一页
	private boolean hasNextPage; // 是否有下一页

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getAllRow() {
		return allRow;
	}

	/** 数据库中该记录的总条数 */
	public void setAllRow(int allRow) {
		this.allRow = allRow;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		
		return pageIndex;
	}

	public void setCurrentPage(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	

	public int getNextPage() {

		int nextPage = pageIndex + 1;
		if (nextPage > totalPage) {
			return pageIndex;
		} else {
			return nextPage;
		}
	}

	public int getPreviousPage() {

		int previousPage = pageIndex - 1;
		if (previousPage <= 0) {
			return pageIndex;
		} else {
			return previousPage;
		}
	}

	/**
	 * 以下判断页面的信�?,只需getter方法(is方法)即可
	 * 
	 * @return
	 */

	public boolean isFirstPage() {
		return pageIndex == 1; // 如是当前页是第一页
	}

	public boolean isLastPage() {
		return pageIndex == totalPage; // 如果当前页是最后一页

	}

	public boolean isHasPreviousPage() {
		return pageIndex != 1; // 只要当前页不是第1页
	}

	public boolean isHasNextPage() {
		return pageIndex != totalPage; // 只要当前页不是最后一页
	}

	/**
	 * 初始化分页信息
	 */
	public void init() {
		this.isFirstPage = isFirstPage();
		this.isLastPage = isLastPage();
		this.hasPreviousPage = isHasPreviousPage();
		this.hasNextPage = isHasNextPage();
		this.nextPage = getNextPage();
		this.previousPage = getPreviousPage();
	}

	/**
	 * 计算总页数
	 * 
	 * @param pageSize
	 *            每页记录数
	 * @param allRow
	 *            总记录数
	 * @return 总页数
	 */
	public static int countTotalPage(final int pageSize, final int allRow) {
		int totalPage;
		if (allRow != 0) {
			totalPage = allRow % pageSize == 0 ? allRow / pageSize : allRow
					/ pageSize + 1;
		} else {
			totalPage = 1;
		}
		return totalPage;
	}

	/**
	 * 计算当前页开始记数
	 * 
	 * @param pageSize
	 *            每页记录数
	 * @param pageIndex
	 *            当前第几页
	 * @return 当前页起始记录号的偏移量
	 */
	public static int countOffset(final int pageSize, final int pageIndex) {
		final int offset = pageSize * (pageIndex - 1);
		return offset;
	}

	/**
	 * 计算当前页,若为0或1请求的URL中没有"?page=",则用1代替
	 * 
	 * @param page
	 *            传入的参数(可能为空,�?0,则返�?1)
	 * @return 当前�?
	 */

	public static int countCurrentPage(int page) {
		final int curPage = (page == 0 ? 1 : page);
		return curPage;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	@Override
	public String toString() {
		return "Page [list=" + list + ", allRow="
				+ allRow + ", totalPage=" + totalPage + ", pageIndex="
				+ pageIndex + ", pageSize=" + pageSize + ", nextPage="
				+ nextPage + ", previousPage=" + previousPage
				+ ", isFirstPage=" + isFirstPage + ", isLastPage=" + isLastPage
				+ ", hasPreviousPage=" + hasPreviousPage + ", hasNextPage="
				+ hasNextPage + "]";
	}
}

