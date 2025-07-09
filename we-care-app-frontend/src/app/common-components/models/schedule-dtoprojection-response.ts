import { ScheduleDTOProjection } from "./schedule-dtoprojection"

export interface ScheduleDTOProjectionResponse {
    message: string,
    payload: {
        content: ScheduleDTOProjection[],
        page: {
            size: number,
            number: number,
            totalElements: number,
            totalPages: number
        }
    }
}
