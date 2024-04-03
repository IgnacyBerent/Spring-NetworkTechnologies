package edu.lb.spring_networktechnologies.infrastructure.dtos.book;

import java.util.List;

public class GetBooksPageDto {
    private List<GetBookDto> books;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private boolean hasMore;

    public GetBooksPageDto() {
    }

    public GetBooksPageDto(List<GetBookDto> books, int currentPage, int totalPages, long totalItems, boolean hasMore) {
        this.books = books;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.hasMore = hasMore;
    }

    public List<GetBookDto> getBooks() {
        return books;
    }

    public void setBooks(List<GetBookDto> books) {
        this.books = books;
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
