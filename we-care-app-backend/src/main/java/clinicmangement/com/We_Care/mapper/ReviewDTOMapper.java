package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.ReviewDTORequest;
import clinicmangement.com.We_Care.DTO.ReviewDTOResponseProjection;
import clinicmangement.com.We_Care.DTO.ReviewsDTOPage;
import clinicmangement.com.We_Care.models.Reviews;
import org.springframework.data.domain.Page;
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
        reviewDTORequest.setTotalRating(reviews.getDoctor().getTotalRating());
        reviewDTORequest.setAverageRating(reviews.getDoctor().getAverageRating());

        return reviewDTORequest;
    }


    public ReviewsDTOPage toReviewDTOPage(Page<ReviewDTOResponseProjection> reviewProjectionPage){

        ReviewsDTOPage reviewsDTOPage = new ReviewsDTOPage();
        reviewsDTOPage.setContent(reviewProjectionPage.getContent().stream().toList());
        reviewsDTOPage.setTotalPages(reviewProjectionPage.getTotalPages());
        reviewsDTOPage.setNumber(reviewProjectionPage.getNumber());
        reviewsDTOPage.setSize(reviewProjectionPage.getSize());
        reviewsDTOPage.setTotalElements(reviewProjectionPage.getTotalElements());
        reviewsDTOPage.setNumberOfElements(reviewProjectionPage.getNumberOfElements());

        return reviewsDTOPage;

    }

}
