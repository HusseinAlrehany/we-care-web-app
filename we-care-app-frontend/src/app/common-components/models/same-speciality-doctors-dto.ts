export interface SameSpecialityDoctorsDTO {

                id?: number,
                fullName: string,
                specialityName: string,
                fees: number,
                briefIntroduction: string,
                averageRating: number | null,
                totalRating: number | null,
                specialityId: number,
                lastUpdated: number
}
