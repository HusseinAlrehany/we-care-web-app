import { ReviewDTOResponseProjection } from "./review-dtoresponse-projection";

export interface ReviewsDTOPage {

    content: ReviewDTOResponseProjection [],
    totalPages: number,
    totalElements: number,
    size: number,
    number: number,
    numberOfElements: number
}
