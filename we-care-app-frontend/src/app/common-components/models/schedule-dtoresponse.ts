import { ScheduleDTO } from "./schedule-dto";

export interface ScheduleDTOResponse {
    message: string,
    payload: ScheduleDTO[];
}
