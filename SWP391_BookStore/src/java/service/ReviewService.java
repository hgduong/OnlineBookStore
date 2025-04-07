/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ReviewDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Review;

/**
 *
 * @author TungPham
 */
public class ReviewService {
    ReviewDAO reviewDAO = new ReviewDAO();
    
    public Map<String, Float> calAvarageRating(List<Review> reviews){
        Map<String, Float> result = new HashMap<>();
        float rating = 1;
        float sum = 0;
        
        for(Review review: reviews){
            sum += review.getRating();
        }
        
        result.put("rating", sum/reviews.size());
        result.put("numOfVote", (float)reviews.size());
        return result;
    }
    
    public static void main(String[] args) {
        ReviewService thiss = new ReviewService();
        
    }
    
    
}
