package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.ReviewDTORequest;
import clinicmangement.com.We_Care.models.Reviews;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewDTOMapper {


    public Reviews toReviews(ReviewDTORequest reviewDTORequest){

        if(reviewDTORequest == null){
            throw new NullPointerException("ReviewDTO request is NULL");
        }

        Reviews reviews = new Reviews();
        reviews.setComment(reviewDTORequest.getComment());
        reviews.setRating(reviewDTORequest.getRating());

        return reviews;
    }

    public ReviewDTORequest toReviewDTORequest(Reviews reviews){
        if(reviews == null){
            throw new NullPointerException("Reviews is null");
        }

        ReviewDTORequest reviewDTORequest = new ReviewDTORequest();
        reviewDTORequest.setId(reviews.getId());
        reviewDTORequest.setComment(reviews.getComment());
        reviewDTORequest.setRating(reviews.getRating());
        reviewDTORequest.setDoctorId(reviews.getDoctor().getId());

        return reviewDTORequest;
    }

   public List<ReviewDTORequest> toReviewsDTORequestList(List<Reviews> reviews){

        return reviews != null ? reviews.stream().map(this::toReviewDTORequest).toList() :
                new ArrayList<>();
   }
}
