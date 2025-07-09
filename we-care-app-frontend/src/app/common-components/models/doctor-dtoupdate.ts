export interface DoctorDTOUpdate {

     id?: number;

     firstName:string;
     lastName: string;

     briefIntroduction:string;

     medicalCardFile: File ;
     medicalCardURL: string;

     doctorImgFile: File;
     doctorImageURL: string;

     specialityId: number;
}
