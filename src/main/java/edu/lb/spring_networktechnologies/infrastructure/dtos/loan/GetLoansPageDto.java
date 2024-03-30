package edu.lb.spring_networktechnologies.infrastructure.dtos.loan;

import java.util.List;

public class GetLoansPageDto {
    private List<GetLoanDto> loans;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private boolean hasMore;

    public GetLoansPageDto() {
    }

    public GetLoansPageDto(List<GetLoanDto> loans, int currentPage, int totalPages, long totalItems, boolean hasMore) {
        this.loans = loans;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.hasMore = hasMore;
    }

    public List<GetLoanDto> getLoans() {
        return loans;
    }

    public void setLoans(List<GetLoanDto> loans) {
        this.loans = loans;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
