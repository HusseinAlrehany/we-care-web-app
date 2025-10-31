export interface ReviewDTOResponseProjection {

    reviewId: number,
    comment: string,
    createdAt: string,
    patientName: string,
    totalRating: number,
    averageRating: number,
    doctorId: number,
    rating: number

}
