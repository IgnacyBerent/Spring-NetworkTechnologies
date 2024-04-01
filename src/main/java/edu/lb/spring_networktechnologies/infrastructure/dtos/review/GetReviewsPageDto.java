package edu.lb.spring_networktechnologies.infrastructure.dtos.review;


import java.util.List;

public class GetReviewsPageDto {
    private List<GetReviewDto> reviews;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private boolean hasMore;

    public GetReviewsPageDto() {
    }

    public GetReviewsPageDto(List<GetReviewDto> reviews, int currentPage, int totalPages, long totalItems, boolean hasMore) {
        this.reviews = reviews;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.hasMore = hasMore;
    }

    public List<GetReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<GetReviewDto> reviews) {
        this.reviews = reviews;
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
