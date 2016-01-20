package net.distjee.common.pages;

import java.io.Serializable;
import java.util.List;

public class Pager implements Serializable {
    private static final long serialVersionUID = -1886255970252256895L;
    private int currentPage;
    private int startNum;
    private int endNum;
    private int pageSize;
    private int startRow;
    private int totalPages;
    private int totalRows;
    private List<?> data;

    public Pager() {
        this.currentPage = 1;
        this.startRow = 0;
        this.totalRows = 0;
        this.startNum = 0;
        this.endNum = 10;
    }

    public Pager(int _totalRows, int onePageSize) {
        this.setPageSize(onePageSize);
        this.totalRows = _totalRows;
        this.totalPages = this.totalRows / this.pageSize;
        int mod = this.totalRows % this.pageSize;
        if(mod > 0) {
            ++this.totalPages;
        }

        if(this.totalRows == 0) {
            this.currentPage = 0;
        } else {
            this.currentPage = 1;
        }

        this.startRow = 0;
        if(this.totalRows == 0) {
            this.startNum = 0;
        } else {
            this.startNum = 1;
        }

        if(this.totalRows <= this.pageSize) {
            this.endNum = this.totalRows;
        } else if(this.currentPage == this.totalPages) {
            this.endNum = this.totalRows;
        } else {
            this.endNum = this.pageSize * this.currentPage;
        }

    }

    public Pager(int _totalRows, int onePageSize, int currentPage) {
        this.setPageSize(onePageSize);
        this.totalRows = _totalRows;
        this.totalPages = this.totalRows / this.pageSize;
        int mod = this.totalRows % this.pageSize;
        if(mod > 0) {
            ++this.totalPages;
        }

        if(this.totalPages < currentPage) {
            currentPage = this.totalPages;
        }

        if(currentPage < 1) {
            currentPage = 1;
        }

        if(this.totalRows == 0) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }

        this.startRow = 0;
        if(this.totalRows == 0) {
            this.startNum = 0;
        } else {
            this.startNum = (currentPage - 1) * onePageSize;
        }

        if(this.totalRows <= this.pageSize) {
            this.endNum = this.totalRows;
        } else if(currentPage == this.totalPages) {
            this.endNum = this.totalRows;
        } else {
            this.endNum = this.pageSize * currentPage;
        }

    }

    public void first() {
        if(this.totalRows == 0) {
            this.currentPage = 0;
        } else {
            this.currentPage = 1;
        }

        this.startRow = 0;
        if(this.totalRows == 0) {
            this.startNum = 0;
        } else {
            this.startNum = 1;
        }

        if(this.totalPages <= this.pageSize) {
            this.endNum = this.totalRows;
        } else if(this.currentPage == this.totalPages) {
            this.endNum = this.totalRows;
        } else {
            this.endNum = this.pageSize * this.currentPage;
        }

    }

    public void last() {
        if(this.totalPages != 0) {
            this.currentPage = this.totalPages;
            this.startRow = (this.currentPage - 1) * this.pageSize;
            this.startNum = this.startRow + 1;
            this.endNum = this.totalRows;
        }

    }

    public void next() {
        if(this.currentPage < this.totalPages && this.totalPages != 0) {
            ++this.currentPage;
        }

        if(this.totalPages != 0) {
            this.startRow = (this.currentPage - 1) * this.pageSize;
        }

        this.startNum = this.startRow + 1;
        if(this.currentPage < this.totalPages && this.totalPages != 0) {
            this.endNum = this.startRow + this.pageSize;
        } else {
            this.endNum = this.totalRows;
        }

    }

    public void previous() {
        if(this.currentPage <= 1) {
            this.first();
        } else {
            --this.currentPage;
            this.startRow = (this.currentPage - 1) * this.pageSize;
            this.startNum = this.startRow + 1;
            if(this.currentPage < this.totalPages && this.totalPages != 0) {
                this.endNum = this.startRow + this.pageSize;
            } else {
                this.endNum = this.totalRows;
            }
        }

    }

    public void refresh(int _currentPage) {
        this.currentPage = _currentPage;
        if(this.currentPage > this.totalPages) {
            this.last();
        }

        if(this.currentPage < 0) {
            this.first();
        }

    }

    public void go(int _pageNo) {
        if(_pageNo <= 1) {
            this.first();
        } else if(_pageNo >= this.totalPages) {
            this.last();
            if(this.totalPages < 1) {
                this.currentPage = 0;
            }
        } else {
            this.startRow = (this.currentPage - 1) * this.pageSize;
            this.startNum = this.startRow + 1;
            this.endNum = this.startRow + this.pageSize;
        }

    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getStartRow() {
        return this.startRow;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public int getTotalRows() {
        return this.totalRows;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getEndNum() {
        return this.endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public int getStartNum() {
        if(this.startNum < 0) {
            this.startNum = 0;
        }

        return this.startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public List<?> getData() {
        return this.data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}